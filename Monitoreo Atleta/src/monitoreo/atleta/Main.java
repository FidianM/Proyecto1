package monitoreo.atleta;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;



public class Main {
    private static final String ARCHIVO = "atletas.csv";
    private static GestorAtletas gestor = new GestorAtletas();
    private static Scanner scanner = new Scanner(System.in);
    private static final String[] DISCIPLINAS = {"Carreras", "Marcha Atletica", "Saltos", "Lanzamientos", "Prueba combinada", "Pesas"};
    private static final String[] DEPARTAMENTOS = {
        "Alta Verapaz", "Baja Verapaz", "Chimaltenango", "Chiquimula", "El Progreso", "El Quiche",
        "Escuintla", "Guatemala (departamento)", "Huehuetenango", "Izabal", "Jalapa", "Jutiapa",
        "Peten", "Quetzaltenango", "Retalhuleu", "Sacatepequez", "San Marcos", "Santa Rosa",
        "Solola", "Suchitepequez", "Totonicapan", "Zacapa"
    };

    public static void main(String[] args) {
        cargarDatos();
        boolean salir = false;
        while (!salir) {
            mostrarBanner();
            System.out.println("1. Registrar nuevo atleta");
            System.out.println("2. Registrar sesion de entrenamiento");
            System.out.println("3. Mostrar historial de un atleta");
            System.out.println("4. Calcular promedio, mejor marca y evolucion");
            System.out.println("5. Guardar y salir");
            System.out.print("Elige una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarAtleta();
                    break;
                case 2:
                    registrarEntrenamiento();
                    break;
                case 3:
                    mostrarHistorial();
                    break;
                case 4:
                    mostrarEstadisticas();
                    break;
                case 5:
                    guardarDatos();
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void mostrarBanner() {
        System.out.println("\n========================================");
        System.out.println(" Sistema de Monitoreo de Rendimiento de ");
        System.out.println("          Atletas - Guatemala           ");
        System.out.println("========================================");

        // Bandera de Guatemala (simplificada en ASCII)
        System.out.println("  ||      ||      ++      ||      ||   ");
        System.out.println("  ||      ||      ++      ||      ||   ");
        System.out.println("  ||      ||   ( Guate)   ||      ||   ");
        System.out.println("  ||      ||      ++      ||      ||   ");
        System.out.println("  ||      ||      ++      ||      ||   ");
        
        System.out.println("\nMenu de Deportes:");
    }

    private static void registrarAtleta() {
    boolean agregarMas = true;
    while (agregarMas) {
        System.out.print("Carnet de deportista: ");
        String carnet = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Edad: ");
        int edad = Integer.parseInt(scanner.nextLine()); // Leer con nextLine y convertir

        System.out.println("Disciplinas:");
        for (int i = 0; i < DISCIPLINAS.length; i++) {
            System.out.println((i + 1) + ". " + DISCIPLINAS[i]);
        }
        System.out.print("Elige numero de disciplina: ");
        int numDis = Integer.parseInt(scanner.nextLine());
        String disciplina = (numDis >= 1 && numDis <= 6) ? DISCIPLINAS[numDis - 1] : "Desconocida";

        System.out.println("Departamentos:");
        for (int i = 0; i < DEPARTAMENTOS.length; i++) {
            System.out.println((i + 1) + ". " + DEPARTAMENTOS[i]);
        }
        System.out.print("Elige numero de departamento: ");
        int numDep = Integer.parseInt(scanner.nextLine());
        String departamento = (numDep >= 1 && numDep <= 22) ? DEPARTAMENTOS[numDep - 1] : "Desconocido";

        Atleta atleta = new Atleta(carnet, nombre, edad, disciplina, departamento);
        gestor.registrarAtleta(atleta);
        System.out.println("Atleta registrado.");

        System.out.print("Desea agregar uno mas? (si/no): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        agregarMas = respuesta.equals("si");
    }
}


    private static void registrarEntrenamiento() {
        System.out.print("Carnet del atleta: ");
        String carnet = scanner.nextLine();
        Atleta atleta = gestor.buscarAtleta(carnet);
        if (atleta == null) {
            System.out.println("Atleta no encontrado.");
            return;
        }
        System.out.println("Disciplina del atleta: " + atleta.getDisciplina());
        System.out.println("Opciones de entrenamiento (elige escribiendo el numero):");
        for (int i = 0; i < DISCIPLINAS.length; i++) {
            System.out.println((i + 1) + ". " + DISCIPLINAS[i]);
        }
        System.out.print("Elige numero de tipo de entrenamiento: ");
        int numTipo = scanner.nextInt();
        scanner.nextLine();
        String tipo = (numTipo >= 1 && numTipo <= 6) ? DISCIPLINAS[numTipo - 1] : "Desconocido";

        System.out.print("Dia (1-31): ");
        int dia = scanner.nextInt();
        System.out.print("Mes (1-12): ");
        int mes = scanner.nextInt();
        System.out.print("Ano (ej. 2025): ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        LocalDate fecha;
        try {
            fecha = LocalDate.of(ano, mes, dia);
        } catch (DateTimeParseException e) {
            System.out.println("Fecha invalida.");
            return;
        }
        String promptMarca = atleta.getDisciplina().equals("Pesas") ? "Kg: " : "Marca (valor numerico): ";
        System.out.print(promptMarca);
        double marca = scanner.nextDouble();
        scanner.nextLine();
        Entrenamiento entrenamiento = new Entrenamiento(fecha, tipo, marca);
        atleta.agregarEntrenamiento(entrenamiento);
        System.out.println("Entrenamiento registrado.");
    }

    private static void mostrarHistorial() {
        System.out.println("1. Mostrar todos los datos");
        System.out.println("2. Buscar por carnet");
        System.out.print("Elige opcion: ");
        int subOpcion = scanner.nextInt();
        scanner.nextLine();

        if (subOpcion == 1) {
            List<Atleta> atletas = gestor.getAtletas();
            if (atletas.isEmpty()) {
                System.out.println("No hay atletas registrados.");
            } else {
                // Encabezado de la tabla
                System.out.println("+------------+--------------------+-------------------+--------------------+");
                System.out.println("| Carnet     |          Nombre    | Disciplina        | Departamento       |");
                System.out.println("+------------+---------------------+-------------------+--------------------+");
                for (Atleta a : atletas) {
                    System.out.printf("| %-10s | %-10s | %-15s | %-16s |\n", 
                        a.getCarnet(), a.getNombre(), a.getDisciplina(), a.getDepartamento());
                    // Mostrar entrenamientos si los hay
                    List<Entrenamiento> entrenamientos = a.getEntrenamientos();
                    if (!entrenamientos.isEmpty()) {
                        System.out.println("|            |            | Entrenamientos    |                    |");
                        for (Entrenamiento e : entrenamientos) {
                            System.out.printf("|            |            | %-15s | %-16s |\n", 
                                e.getTipo() + ": " + e.getMarca(), e.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        }
                    }
                    System.out.println("+------------+------------+-------------------+--------------------+");
                }
            }
        } else if (subOpcion == 2) {
            System.out.print("Ingresa el carnet del atleta: ");
            String carnet = scanner.nextLine();
            Atleta atleta = gestor.buscarAtleta(carnet);
            if (atleta == null) {
                System.out.println("Atleta no encontrado.");
            } else {
                // Encabezado de la tabla
                System.out.println("+------------+----------------------+-------------------+--------------------+");
                System.out.println("| Carnet     |        Nombre        | Disciplina        | Departamento       |");
                System.out.println("+------------+----------------------+-------------------+--------------------+");
                System.out.printf("| %-10s | %-10s | %-15s | %-16s |\n", 
                    atleta.getCarnet(), atleta.getNombre(), atleta.getDisciplina(), atleta.getDepartamento());
                // Mostrar entrenamientos si los hay
                List<Entrenamiento> entrenamientos = atleta.getEntrenamientos();
                if (!entrenamientos.isEmpty()) {
                    System.out.println("|            |            | Entrenamientos    |                    |");
                    for (Entrenamiento e : entrenamientos) {
                        System.out.printf("|            |            | %-15s | %-16s |\n", 
                            e.getTipo() + ": " + e.getMarca(), e.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                }
                System.out.println("+------------+------------+-------------------+--------------------+");
            }
        } else {
            System.out.println("Opcion invalida.");
        }
    }

   private static void mostrarEstadisticas() {
    System.out.print("Carnet del atleta: ");
    String carnet = scanner.nextLine();
    Atleta atleta = gestor.buscarAtleta(carnet);

    if (atleta == null) {
        System.out.println("Atleta no encontrado.");
        return;
    }

    // Encabezado principal
    System.out.println("\n+---------------+----------------+-----------+-------------+");
    System.out.println("| Nombre         | Promedio       | Mejor     | Disciplina  |");
    System.out.println("+---------------+----------------+-----------+-------------+");
    System.out.printf("| %-10s | %-14.2f | %-9.2f | %-11s |\n",
            atleta.getNombre(), atleta.getPromedio(), atleta.getMejorMarca(), atleta.getDisciplina());
    System.out.println("+------------+----------------+-----------+-------------+");

    // Evolución
    System.out.println("\nEvolucion de Entrenamientos:");
    System.out.println("+------------+-------------------+-----------+");
    System.out.println("| Fecha      | Tipo              | Marca     |");
    System.out.println("+------------+-------------------+-----------+");

    List<Entrenamiento> evolucion = atleta.getEvolucion();
    if (evolucion.isEmpty()) {
        System.out.println("|   (sin registros)              |           |");
    } else {
        for (Entrenamiento e : evolucion) {
            System.out.printf("| %-10s | %-17s | %-9.2f |\n",
                    e.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    e.getTipo(), e.getMarca());
        }
    }
    System.out.println("+------------+-------------------+-----------+");
}


    private static void cargarDatos() {
        try {
            gestor.cargarDesdeCSV(ARCHIVO);
            System.out.println("Datos cargados desde " + ARCHIVO);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el archivo o no existe.");
        }
    }
private static void guardarDatos() {
    try {
        gestor.guardarEnCSV(ARCHIVO);
        System.out.println("Datos CSV guardados correctamente.");
    } catch (Exception e) {
        System.out.println("Error al guardar CSV: " + e.getMessage());
    }

    try {
        gestor.guardarEnJSON("atletas.json");
        System.out.println("Datos JSON guardados correctamente.");
    } catch (Exception e) {
        System.out.println("Error al guardar JSON: " + e.getMessage());
    }
}
   
}