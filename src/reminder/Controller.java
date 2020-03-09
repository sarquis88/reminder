package reminder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
	
    private static Controller thisController = null;
    private static ObservableList<Tarea> tareasList = FXCollections.observableArrayList();

    private View thisView;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static Controller getInstance() {
        if(thisController == null)
        	thisController = new Controller();
        return thisController;
    }
    
    /**
     * Constructor de clase
     */
    public Controller() {
    	this.thisView = View.getInstance(this);
    }

    /**
     * Respuesta a eventos
     * @param event tipo de evento
     */
    public void actionPerformed(String event) {
        switch (event) {
            case "nuevaTarea":
                this.addTarea();
                break;
            case "borrarTarea":
                this.borrarTarea();
                break;
            case "borrarTodo":
                this.borrarTodo();
                break;
            default:
                break;
        }
    }

    /**
     * Metodo reaccion al boton de nueva tarea
     * Ingreso de datos sobre nueva tarea y posterior agregado a lista
     */
    public void addTarea() {

        SimpleInputDialog simpleInputDialog = new SimpleInputDialog("Ingrese nueva tarea");
        simpleInputDialog.show();
        String comentario = simpleInputDialog.getResult();

        if(comentario != null && !comentario.isBlank()) {
            if(comentarioExists(comentario)) {
                MessagesManager.showErrorAlert("Tarea ya existente");
            }
            else {
                Tarea tarea = new Tarea();
                tarea.setComentario(comentario);

                simpleInputDialog = new SimpleInputDialog("Ingrese fecha límite");
                simpleInputDialog.show();
                String fecha = simpleInputDialog.getResult();

                if (fecha != null && !fecha.isBlank())
                    tarea.setFechaLimite(fecha);
                else
                    tarea.setFechaLimite("Sin fecha");

                tarea.setId(Tarea.getIdGlobal());
                Tarea.increaseIdGlobal();

                addTareaToList(tarea);

                ReminderBDD.getInstance().insertarTarea(tarea.getId(), tarea.getComentario(), tarea.getFechaLimite());
                thisView.refresh();
            }
        }
    }

    /**
     * Metodo reaccion al boton de borrar tarea
     */
    public void borrarTarea() {
        String comentario = thisView.getTareaClickeada();

        if(comentario != null) {
            if(MessagesManager.confirmation("Desea borrar la tarea: '" + comentario + "' ?")) {
                ReminderBDD.getInstance().deleteTarea(getTareaByComentario(comentario).getId());
                deleteTareaFromList(comentario);
            }
            thisView.clearTareaClickeada();
        }

        thisView.refresh();
    }

    /**
     * Metodo reaccion al metodo de borrar todas las tareas
     * Restablece la base de datos
     * Cierra la aplicacion
     */
    private void borrarTodo() {
        if(MessagesManager.confirmation("Desea borrar todas las tareas? Deberá reiniciar la aplicación.")) {
            ReminderBDD.getInstance().restablecerBDD();
            Main.exit(0);
        }
    }

    /**
     * Getter de la lista de tareas
     * @return ObservableList con las tareas guardadas
     */
    public static ObservableList<Tarea> getTareasList() {
        return tareasList;
    }

    /**
     * Agregado de tarea a lista
     * Metodo llamado por Controller y ReminderBDD
     * @param tarea tarea a agregar
     */
    public static void addTareaToList(Tarea tarea) {
        tareasList.add(tarea);
    }

    /**
     * Borrado de tarea de lista
     * Metodo llamado por Controller
     * @param comentario comentario de la tarea a eliminar
     */
    private static void deleteTareaFromList(String comentario) {
        tareasList.removeIf(tarea -> tarea.getComentario().equalsIgnoreCase(comentario));
    }

    /**
     * Busqueda de tarea por comentario
     * @param comentario comentario de tarea a buscar
     * @return tarea cuyo comentario coincida con el ingresado, si no hay ninguna, retorna null
     */
    public Tarea getTareaByComentario(String comentario) {
        for(Tarea tarea : tareasList) {
            if(tarea.getComentario().equalsIgnoreCase(comentario))
                return tarea;
        }
        return null;
    }

    /**
     * Consulta si existe un comentario en la lista
     * @param comentario comentario a buscar
     * @return true si el comentario ya existe, de lo contrario false
     */
    private boolean comentarioExists(String comentario) {
        for(Tarea tarea : tareasList) {
            if(tarea.getComentario().equalsIgnoreCase(comentario))
                return true;
        }
        return false;
    }

    /**
     * Consulta si la lista esta vacia
     * @return true si esta vacia, de lo contrario false
     */
    public boolean isTareasListEmpty() {
        return tareasList.isEmpty();
    }
}
