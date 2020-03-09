package reminder;

public class Tarea {

    private static int idGlobal = 0;

    private String comentario;
    private String fechaLimite;
    private int id;

    /**
     * Constructor de clase
     * @param comentario comentario de la tarea
     * @param fechaLimite fecha limite de la tarea
     */
    public Tarea(String comentario, String fechaLimite) {
        this.comentario = comentario;
        this.fechaLimite = fechaLimite;
        this.id = idGlobal;

        idGlobal++;
    }

    /**
     * Constructor de clase vacio
     */
    public Tarea() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public static int getIdGlobal() {
        return idGlobal;
    }

    public static void increaseIdGlobal() {
        idGlobal++;
    }

    public static void setIdGlobal(int idGlobalNuevo) {
        idGlobal = idGlobalNuevo;
    }
}
