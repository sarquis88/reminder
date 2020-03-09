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
                this.deleteTarea();
                break;
            case "borrarTodo":
                this.borrarTodo();
                break;
            default:
                break;
        }
    }

    public void addTarea() {
        Tarea tarea = new Tarea();

        SimpleInputDialog simpleInputDialog = new SimpleInputDialog("Ingrese nueva tarea");
        simpleInputDialog.show();
        String comentario = simpleInputDialog.getResult();

        if(comentario != null && !comentario.isBlank()) {

            if(comentarioExists(comentario)) {
                MessagesManager.showErrorAlert("Tarea ya existente");
            }
            else {
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

    public void deleteTarea() {
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

    public static void addTareaToList(Tarea tarea) {
        tareasList.add(tarea);
    }

    public static void deleteTareaFromList(String comentario) {
        tareasList.removeIf(tarea -> tarea.getComentario().equalsIgnoreCase(comentario));
    }

    public static ObservableList<Tarea> getTareasList() {
        return tareasList;
    }

    public Tarea getTareaByComentario(String comentario) {
        for(Tarea tarea : tareasList) {
            if(tarea.getComentario().equalsIgnoreCase(comentario))
                return tarea;
        }
        return null;
    }

    private boolean comentarioExists(String comentario) {
        for(Tarea tarea : tareasList) {
            if(tarea.getComentario().equalsIgnoreCase(comentario))
                return true;
        }
        return false;
    }

    public boolean isTareasListEmpty() {
        return tareasList.isEmpty();
    }

    private void borrarTodo() {
        if(MessagesManager.confirmation("Desea borrar todas las tareas? Deberá reiniciar la aplicación.")) {
            ReminderBDD.getInstance().restablecerBDD();
            Main.exit();
        }
    }
}
