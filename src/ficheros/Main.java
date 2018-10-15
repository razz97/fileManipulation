package ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Clase principal para el control del workflow del programa. 
 * @author alex_bou
 */
public class Main {

    public static final File ruta = new File(System.getProperty("user.dir") + File.separator + "deportes" + File.separator);
    public static FileDeportesBorrados borrados = null;
    public static HashMap<String, FileDeporte> deportes = new HashMap<>();
    /**
     * Metodo que se ejecuta al iniciar el programa.
     * Se encarga de mostrar el menu y ejecutar sus metodos.     * 
     */
    public static void main(String[] args) {

        int opcion = -1;

        String enunciado = "Selecciona una opcion: \n"
                + "1. Registrar inscripcion de un alumno. \n"
                + "2. Listar alumnos de un curso determinado. \n"
                + "3. Listar deportes con incidencias. \n"
                + "4. Eliminar a un alumno. \n"
                + "5. Ver lista agrupada y ordenada. \n"
                + "6. Anular un deporte. \n"
                + "7. Salir.\n";
        try {
            borrados = new FileDeportesBorrados();
            for (String deporte : borrados.getDisponibles()) {
                deportes.put(deporte, new FileDeporte(deporte));
            }
            while ((opcion = Helper.pedirEntero(enunciado)) != 7) {
                switch (opcion) {
                    case 1:
                        inscribirAlumno();
                        break;
                    case 2:
                        listarAlumnosPorCurso();
                        break;
                    case 3:
                        listarDeportesIncompletos();
                        break;
                    case 4:
                        eliminarAlumno();
                        break;
                    case 5:
                        listarTodosOrdenados();
                        break;
                    case 6:
                        anularDeporte();
                }
            }

        } catch (Exception ex) {
            System.err.println("Error I/O");
        }

    }
    /**
     * Inscribe un alumno y lo añade al fichero correspondiente.
     */
    private static void inscribirAlumno() {
        String deporte = Helper.pedirDeporte();
        String nombre = Helper.pedirCadena("Introduce el nombre:\n");
        String apellidos = Helper.pedirCadena("Introduce los apellidos:\n");
        String sexo = Helper.pedirSexo();
        String curso = Helper.pedirCurso();
        int edad = Helper.pedirEntero("Introduce la edad:\n");
        Alumno alu = new Alumno(nombre, apellidos, edad, sexo, curso, deporte);
        try {
            if (Helper.isAlumnoInscrito(alu)) {
                System.err.println("Este alumno ya esta inscrito en un deporte!!");
            } else {
                deportes.get(deporte).addAlumno(alu);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    /**
     * Pide el curso al usuario y lista todos los alumnos inscritos. 
     */
    private static void listarAlumnosPorCurso() {
        String curso = Helper.pedirCurso();
        ArrayList<String> disp = Main.borrados.getDisponibles();
        for (String dep : disp) {
            Collection<Alumno> alumnos = Main.deportes.get(dep).getAlumnos().values();
            for (Alumno alum : alumnos) {
                if (alum.getCurso().equals(curso)) {
                    System.out.println(alum);
                }
            }
        }
    }
    /**
     * Lista las incompatibilidades de cada deporte disponible. 
     */
    private static void listarDeportesIncompletos() {
        ArrayList<String> disp = Main.borrados.getDisponibles();
        int cantidad;
        for (String dep : disp) {
            cantidad = deportes.get(dep).getAlumnos().size();
            switch (dep) {
                case "futbol":
                    if (cantidad < 22) {
                        System.out.println("Futbol: solo hay " + cantidad + " jugadores. (min 22)");
                    }
                    break;

                case "futbol sala":
                    if (cantidad < 14) {
                        System.out.println("Futbol sala: solo hay " + cantidad + " jugadores. (min 14)");
                    }
                    break;

                case "basquet":
                    if (cantidad < 10) {
                        System.out.println("Basquet: solo hay " + cantidad + " jugadores. (min 10)");
                    }
                    break;

                case "badminton":
                    if (cantidad < 4) {
                        System.out.println("Badminton: solo hay " + cantidad + " jugadores. (min 4)");
                    } else if (cantidad % 2 != 0) {
                        System.out.println("Badminton: Hay un numero impar de jugadores.");
                    }
                    break;

                case "volei playa":
                    if (cantidad < 4) {
                        System.out.println("Volei playa: solo hay " + cantidad + " jugadores. (min 4)");
                    } else if (cantidad % 2 != 0) {
                        System.out.println("Voley playa: Hay un numero impar de jugadores.");
                    }
                    break;
            }
        }
    }
    /**
     * Elimina un alumno de las jornadas y actualiza el fichero correspondiente.
     */
    private static void eliminarAlumno() {
        ArrayList<String> disp = Main.borrados.getDisponibles();
        String nombre = Helper.pedirCadena("Introduce el nombre del alumno a borrar:");
        String apellidos = Helper.pedirCadena("Introduce los apellidos:");
        Alumno alumno = new Alumno(nombre, apellidos);
        if (Helper.isAlumnoInscrito(alumno)) {
            for (String dep : disp) {
                try {
                    if (deportes.get(dep).delAlumno(alumno)) {
                        System.out.println("Alumno borrado.");
                    }
                } catch (Exception ex) {
                    System.err.println("Error I/O");
                }
            }
        } else {
            System.err.println("Este alumno no esta inscrito.");
        }
    }
    /**
     * Lista todos los alumnos ordenados por deporte y por curso;
     */
    private static void listarTodosOrdenados() {
        ArrayList<String> disp = Main.borrados.getDisponibles();
        for (String dep : disp) {
            ArrayList<Alumno> alumnos = new ArrayList<>(Main.deportes.get(dep).getAlumnos().values());
            Collections.sort(alumnos);
            for (Alumno alu : alumnos) {
                System.out.println(alu);
            }
        }
    }
    /**
     * Anula un deporte de las jornadas añadiendo su nombre en el fichero de borrados.
     */
    private static void anularDeporte() {
        try {
            String deporte = Helper.pedirDeporte();
            borrados.addDeporte(deporte);
            System.out.println("Deporte anulado.");
        } catch (Exception ex) {
            System.err.println("Error I/O");
        } 
    }
}
