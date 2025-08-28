/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitoreo.atleta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorAtletas {
    private List<Atleta> atletas;

    public GestorAtletas() {
        atletas = new ArrayList<>();
    }

    public void registrarAtleta(Atleta atleta) {
        atletas.add(atleta);
    }

    public Atleta buscarAtleta(String carnet) {
        for (Atleta a : atletas) {
            if (a.getCarnet().equalsIgnoreCase(carnet)) {
                return a;
            }
        }
        return null;
    }

    public List<Atleta> getAtletas() {
        return new ArrayList<>(atletas);
    }

    public void guardarEnCSV(String archivo) throws IOException {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (Atleta a : atletas) {
                writer.write(a.toCSV() + "\n");
            }
        }
    }

    public void cargarDesdeCSV(String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                atletas.add(Atleta.fromCSV(linea));
            }
        }
    }
}