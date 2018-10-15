package ficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Esta clase gestiona la anulacion de deportes y la entrada/salida de datos en
 * su fichero.
 *
 * @author alex_bou
 */
public class FileDeportesBorrados extends File {

    private final String[] deportes = {"futbol", "futbol sala", "basquet", "badminton", "volei playa"};
    private ArrayList<String> disponibles;
    private ArrayList<String> borrados;

    /**
     * Se genera el fichero si no existe, se lee toda la informacion referente
     * deportes borrados.
     *
     * @throws Exception
     */
    public FileDeportesBorrados() throws Exception {
        super(Main.ruta.getPath() + File.separator + "borrados.txt");
        Helper.genFile(this);
        this.disponibles = new ArrayList<>();
        this.borrados = new ArrayList<>();
        this.readData();
    }

    /**
     * @return ArrayList de strings con los deportes disponibles.
     */
    public ArrayList<String> getDisponibles() {
        return disponibles;
    }

    /**
     * Añade un deporte al fichero de borrados.
     *
     * @param deporte deporte a borrar.
     * @throws Exception
     */
    public void addDeporte(String deporte) throws Exception {
        this.disponibles.remove(deporte);
        this.borrados.add(deporte);
        this.writeData();
    }

    /**
     * Lee la informacion referente a los deportes borrados y lo guarda en los
     * arraylist para facil acceso.
     *
     * @throws Exception
     */
    private void readData() throws Exception {
        FileReader r = null;
        try {
            r = new FileReader(this);
            BufferedReader br = new BufferedReader(r);
            String content = br.readLine();
            this.disponibles.clear();
            this.borrados.clear();
            for (String deporte : this.deportes) {
                if (content == null || !content.contains(deporte)) {
                    this.disponibles.add(deporte);
                } else {
                    this.borrados.add(deporte);
                }
            }
        } catch (IOException ex) {
            throw new Exception("ERROR: " + ex.getMessage());
        } finally {
            r.close();
        }
    }

    /**
     * Escribe los deportes borrados del arraylist al fichero borrados.
     *
     * @throws Exception
     */
    private void writeData() throws Exception {
        FileWriter fw = new FileWriter(this);
        try {
            for (String deporte : this.borrados) {
                fw.write(deporte + System.lineSeparator());
            }
        } catch (IOException ex) {
            throw new Exception("ERROR: " + ex.getMessage());
        } finally {
            fw.close();
        }
    }
}
