package ficheros;

import java.util.Objects;

/**
 * Modelo de la aplicacion jornadas deportivas, representa un alumno. Se
 * identifican y ordenan por nombre y apellido. Los atributos de esta clase son
 * inmutables.
 *
 * @author alex_bou
 */
public class Alumno implements Comparable<Alumno> {

    private String nombre;
    private String apellidos;
    private String curso;
    private String deporte;
    private String sexo;
    private int edad;

    /**
     * Constructor: instancia un objeto de clase alumno con todos los atributos.
     *
     * @param nombre String
     * @param apellidos String
     * @param edad int
     * @param sexo String
     * @param curso String
     * @param deporte String
     */
    public Alumno(String nombre, String apellidos, int edad, String sexo, String curso, String deporte) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curso = curso;
        this.edad = edad;
        this.deporte = deporte;
        this.sexo = sexo;
    }

    /**
     * Constructor: instancia un objeto solo con nombre y apellidos. Se puede
     * usar para encontrar alumnos en una lista dado que @equals esta
     * sobrescrito.
     *
     * @param nombre String
     * @param apellidos String
     */
    public Alumno(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    /**
     * @return sexo del alumno.
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Estos atributos representan la clave principal del alumno, es coherente
     * usarlos como key en un hashmap o similar y por eso este metodo existe.
     *
     * @return nombre y apellidos del alumno
     */
    public String getNombreApellidos() {
        return nombre + " " + apellidos;
    }

    /**
     * @return nombre del alumno.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return deporte al que esta inscrito el alumno.
     */
    public String getDeporte() {
        return deporte;
    }

    /**
     * @return apellidos del alumno.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @return curso del alumno.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * @return edad del alumno
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @return representacion en String del objeto.
     */
    @Override
    public String toString() {
        return nombre + ";" + apellidos + ";" + edad + ";" + sexo + ";" + curso + ";" + deporte;
    }

    /**
     * @return codigo identificatiorio del objeto.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.nombre);
        hash = 41 * hash + Objects.hashCode(this.apellidos);
        return hash;
    }

    /**
     * Para saber si otro alumno es igual a este. Tiene en cuenta el nombre y
     * los apellidos del alumno.
     *
     * @param alumno Alumno con el que se compara.
     * @return true si el alumno es igual, false si no lo es.
     */
    @Override
    public boolean equals(Object alumno) {
        if (alumno instanceof Alumno) {
            return this.getNombreApellidos().equals(((Alumno) alumno).getNombreApellidos());
        }
        return false;
    }

    /**
     * Para saber si un alumno es mayor o menor a otro. En este caso se mira el
     * orden alfabetico del apellido, en caso de ser iguales se mira el nombre.
     *
     * @param alumno Alumno con el que se compara.
     * @return int positivo en caso de mayor, negativo en caso de menor o 0 en
     * caso de igual.
     */
    @Override
    public int compareTo(Alumno alumno) {
        if (apellidos.equals(alumno.getApellidos())) {
            return nombre.compareToIgnoreCase(alumno.getNombre());
        }
        return apellidos.compareToIgnoreCase(alumno.getApellidos());
    }

}
