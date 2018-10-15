package ficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Clase para leer y escribir en los ficheros de los deportes. Almacena los
 * alumnos.
 *
 * @author alex_bou
 */
public class FileDeporte extends File {

    private HashMap<String, Alumno> alumnos;
    private final String tipo;

    /**
     * A partir del deporte (tipo), se genera el fichero si no existe, se lee
     * toda la informacion referente a alumnos para almacenarla en un hashmap.
     *
     * @param tipo String
     * @throws Exception
     */
    public FileDeporte(String tipo) throws Exception {
        super(Main.ruta + File.separator + tipo + ".txt");
        this.tipo = tipo;
        try {
            Helper.genFile(this);
            this.readData();
        } catch (IOException ex) {
            throw new Exception("ERROR: " + ex.getMessage());
        }
    }

    /**
     * @return HashMap con los alumnos inscritos en el deporte.
     */
    public HashMap<String, Alumno> getAlumnos() {
        return alumnos;
    }

    /**
     * @return tipo o deporte al que se refiere la instancia.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Lee el fichero en cuestion y almacena los alumnos en el HashMap.
     *
     * @throws Exception
     */
    private void readData() throws Exception {
        FileReader r = null;
        this.alumnos = new HashMap();
        try {
            r = new FileReader(this);
            BufferedReader br = new BufferedReader(r);
            String alumno = "";
            while ((alumno = br.readLine()) != null) {
                String[] datos = alumno.split(";");
                //nombre;apellidos;edad;sexo;curso;deporte
                Alumno objAlumno = new Alumno(datos[0], datos[1], Integer.parseInt(datos[3]), datos[2], datos[4], this.tipo);
                this.alumnos.put(objAlumno.getNombreApellidos(), objAlumno);
            }
        } catch (IOException ex) {
            throw new Exception("ERROR: " + ex.getMessage());
        } finally {
            r.close();
        }
    }

    /**
     * Inscribe un alumno al deporte, se escribe la informacion en el fichero.
     *
     * @param alumno
     * @throws Exception
     */
    public void addAlumno(Alumno alumno) throws Exception {
        this.alumnos.put(alumno.getNombreApellidos(), alumno);
        this.writeData();
    }

    /**
     * Borra un alumno del deporte, se comprueba si existe y se escribe la
     * informacion en el fichero.
     *
     * @param alumno
     * @return true si el alumno existia, false si no existia.
     * @throws Exception
     */
    public boolean delAlumno(Alumno alumno) throws Exception {
        if (this.alumnos.remove(alumno.getNombreApellidos()) instanceof Alumno) {
            this.writeData();
            return true;
        }
        return false;
    }

    /**
     * Actualiza la informacion del fichero teniendo en cuenta la informacion
     * del HashMap de alumnos.
     *
     * @throws Exception
     */
    private void writeData() throws Exception {
        FileWriter fw = new FileWriter(this);
        try {
            for (Alumno alumno : this.alumnos.values()) {
                fw.write(alumno.toString() + System.lineSeparator());
            }
        } catch (IOException ex) {
            throw new Exception("ERROR: " + ex.getMessage());
        } finally {
            fw.close();
        }
    }

}
