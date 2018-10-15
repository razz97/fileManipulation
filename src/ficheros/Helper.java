/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase sirve para agrupar funciones utiles usadas a lo largo del
 * programa.
 *
 * @author alex_bou
 */
public abstract class Helper {

    /**
     * Pide al usuario una cadena, la cual no puede estar vacia.
     *
     * @param texto enunciado a mostrar al usuario.
     * @return String input del usuario.
     */
    public static String pedirCadena(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cadena = "";
        do {
            try {
                System.out.print(texto);
                cadena = br.readLine();
                if (cadena.equals("")) {
                    System.err.println("No se puede dejar el campo en blanco.");
                }
            } catch (IOException ex) {
                System.err.println("Error de entrada / salida.");
            }
        } while (cadena.equals(""));
        return cadena;
    }

    /**
     * Pide al usuario un entero.
     *
     * @param texto enunciado a mostrar al usuario.
     * @return String input del usuario.
     */
    public static int pedirEntero(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.print(texto);
                num = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.err.println("Error de entrada / salida.");
                error = true;
            } catch (NumberFormatException ex) {
                System.err.println("Debes introducir un número entero.");
                error = true;
            }
        } while (error);
        return num;
    }

    /**
     * Pide al usuario a escoger entre los deportes disponibles.
     *
     * @return String con el deporte escogido.
     */
    public static String pedirDeporte() {
        int opcion = -1;
        boolean opcionInvalida = true;
        ArrayList<String> disp = Main.borrados.getDisponibles();
        String enunciado = "Selecciona un deporte: \n";
        for (int i = 0; i < disp.size(); i++) {
            enunciado += ((i + 1) + ". " + StringUtils.capitalize(disp.get(i)) + "\n");
        }
        do {
            opcion = pedirEntero(enunciado);
            if (opcionInvalida = opcion > disp.size() || opcion < 1) {
                System.err.println("Selecciona una opcion valida.");
            }
        } while (opcionInvalida);

        return disp.get(opcion - 1);
    }

    /**
     * Pide al usuario a escoger entre los cursos disponibles.
     *
     * @return String con el curso escogido.
     */
    public static String pedirCurso() {
        int opcion = -1;
        boolean opcionInvalida = true;
        String cursos[] = new String[]{"1BAT", "2BAT", "DAW1", "DAW2", "DAM1", "ASIX1", "ASIX2", "DAM2"};
        String enunciado = "Selecciona un curso: \n";
        for (int i = 0; i < cursos.length; i++) {
            enunciado += (i + 1) + ". " + cursos[i] + " \n";
        }
        do {
            opcion = pedirEntero(enunciado);
            if (opcionInvalida = opcion > 8 || opcion < 1) {
                System.err.println("Selecciona una opcion valida.");
            }
        } while (opcionInvalida);
        return cursos[opcion - 1];
    }

    /**
     * Pide al usuario a escoger entre los generos disponibles.
     *
     * @return String con el genero escogido.
     */
    public static String pedirSexo() {
        int opcion = -1;
        boolean opcionInvalida = true;
        String[] generos = new String[]{"hombre", "mujer", "otro"};
        String enunciado = "Selecciona un sexo: \n"
                + "1. Hombre \n"
                + "2. Mujer \n"
                + "3. Otro \n";
        do {
            opcion = pedirEntero(enunciado);
            if (opcionInvalida = opcion > generos.length || opcion < 1) {
                System.err.println("Selecciona una opcion valida.");
            }
        } while (opcionInvalida);
        return generos[opcion - 1];
    }

    /**
     * Comprueba si un alumno esta inscrito a las jornadas.
     *
     * @param alu Alumno a encontrar. Suele ser un alumno con solo nombre y
     * apellidos.
     * @return true si existe, false si no existe.
     */
    public static boolean isAlumnoInscrito(Alumno alu) {
        ArrayList<String> disp = Main.borrados.getDisponibles();
        for (String dep : disp) {
            HashMap<String, Alumno> alumnos = Main.deportes.get(dep).getAlumnos();
            if (alumnos.get(alu.getNombreApellidos()) != null) {
                return true;
            }
        }
        return false;
    }
    /**
     * Genera la carpeta y archivo de deporte si no existen.
     * @param file archivo que crear.
     * @throws Exception 
     */
    public static void genFile(File file) throws Exception {
        try {
            Main.ruta.mkdir();
            file.createNewFile();
        } catch (IOException | SecurityException ex) {
            throw new Exception("ERROR: " + ex.getMessage());
        }
    }
}
