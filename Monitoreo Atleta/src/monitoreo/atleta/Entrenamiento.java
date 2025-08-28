/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitoreo.atleta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrenamiento {
    private LocalDate fecha;
    private String tipo;
    private double marca;

    public Entrenamiento(LocalDate fecha, String tipo, double marca) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.marca = marca;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMarca() {
        return marca;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Fecha: " + fecha.format(formatter) + ", Tipo: " + tipo + ", Marca: " + marca;
    }

    public String toCSV() {
        return fecha.toString() + "," + tipo + "," + marca;
    }

    public static Entrenamiento fromCSV(String linea) {
        String[] partes = linea.split(",");
        LocalDate fecha = LocalDate.parse(partes[0]);
        String tipo = partes[1];
        double marca = Double.parseDouble(partes[2]);
        return new Entrenamiento(fecha, tipo, marca);
    }
}