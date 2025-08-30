/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitoreo.atleta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value.format(formatter));
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        return LocalDate.parse(in.nextString(), formatter);
    }
}

    public void guardarEnJSON(String archivo) {
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())  // <-- Aquí registras el adaptador
            .setPrettyPrinting()
            .create();
    try (FileWriter writer = new FileWriter(archivo)) {
        gson.toJson(atletas, writer);  // atletas es tu lista interna
        System.out.println("Datos exportados a JSON en: " + archivo);
    } catch (IOException e) {
        System.out.println("Error al guardar JSON: " + e.getMessage());
    }
}

}