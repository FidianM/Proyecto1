/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitoreo.atleta;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Atleta {
    private String carnet;
    private String nombre;
    private int edad;
    private String disciplina;
    private String departamento;
    private List<Entrenamiento> entrenamientos;

    public Atleta(String carnet, String nombre, int edad, String disciplina, String departamento) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.edad = edad;
        this.disciplina = disciplina;
        this.departamento = departamento;
        this.entrenamientos = new ArrayList<>();
    }

    public void agregarEntrenamiento(Entrenamiento entrenamiento) {
        entrenamientos.add(entrenamiento);
    }

    public List<Entrenamiento> getEntrenamientos() {
        return new ArrayList<>(entrenamientos);
    }

    public double getPromedio() {
        if (entrenamientos.isEmpty()) return 0.0;
        double suma = 0.0;
        for (Entrenamiento e : entrenamientos) {
            suma += e.getMarca();
        }
        return suma / entrenamientos.size();
    }

    public double getMejorMarca() {
        if (entrenamientos.isEmpty()) return 0.0;
        if (disciplina.equals("Pesas")) {
            return Collections.max(entrenamientos, Comparator.comparing(Entrenamiento::getMarca)).getMarca();
        } else {
            return Collections.min(entrenamientos, Comparator.comparing(Entrenamiento::getMarca)).getMarca();
        }
    }

    public List<Entrenamiento> getEvolucion() {
        List<Entrenamiento> sorted = new ArrayList<>(entrenamientos);
        sorted.sort(Comparator.comparing(Entrenamiento::getFecha));
        return sorted;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getDisciplina() {
        return disciplina;
    }
    public String getDepartamento() {
        return departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(carnet).append(",").append(nombre).append(",").append(edad).append(",").append(disciplina).append(",").append(departamento);
        for (Entrenamiento e : entrenamientos) {
            sb.append("|").append(e.toCSV());
        }
        return sb.toString();
    }

    public static Atleta fromCSV(String linea) {
        String[] partes = linea.split("\\|");
        String[] infoBasica = partes[0].split(",");
        Atleta atleta = new Atleta(infoBasica[0], infoBasica[1], Integer.parseInt(infoBasica[2]), infoBasica[3], infoBasica[4]);
        for (int i = 1; i < partes.length; i++) {
            atleta.agregarEntrenamiento(Entrenamiento.fromCSV(partes[i]));
        }
        return atleta;
    }
}