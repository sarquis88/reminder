package reminder;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainGrid implements ReminderParameters {

	private GridPane thisGrid;
    private Button borrarTarea;
    private Button borrarTodo;

    /**
     * Constructor de clase
     */
    public MainGrid(Controller controller) {
       
        this.thisGrid = new GridPane();
        this.thisGrid.setHgap(hPadding);
        this.thisGrid.setVgap(vPadding);
        this.thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button nuevaTarea = new Button("Nueva tarea");
        nuevaTarea.setPrefSize(buttonsWidth, buttonsHeight);
        nuevaTarea.setStyle(buttonsStyle);
        nuevaTarea.setOnAction(e -> controller.actionPerformed("nuevaTarea"));

        this.borrarTarea = new Button("Borrar tarea");
        this.borrarTarea.setPrefSize(buttonsWidth, buttonsHeight);
        this.borrarTarea.setStyle(buttonsStyle);
        this.borrarTarea.setOnAction(e -> controller.actionPerformed("borrarTarea"));
        this.borrarTarea.setDisable(true);

        this.borrarTodo = new Button("Borrar todo");
        this.borrarTodo.setPrefSize(buttonsWidth, buttonsHeight);
        this.borrarTodo.setStyle(buttonsStyle);
        this.borrarTodo.setOnAction(e -> controller.actionPerformed("borrarTodo"));

        Button salir = new Button("Salir");
        salir.setPrefSize(buttonsWidth, buttonsHeight);
        salir.setStyle(buttonsStyle);
        salir.setOnAction(e -> Main.getWindow().close());

        // 3 : numero de botones. ecuacion para dinamicidad
        int indexSalir = (sceneHeight - buttonsHeight * 4) / vPadding - 4;

        this.thisGrid.add(nuevaTarea, 0, 0);
        this.thisGrid.add(borrarTarea, 0, 1);
        this.thisGrid.add(borrarTodo, 0, indexSalir - 1);
        this.thisGrid.add(salir, 0, indexSalir);
    }
    
    /**
     * Seteo de posicion de la grid
     * @param x layout x
     * @param y layout y
     */
    public void setLayout(double x, double y) {
        this.thisGrid.setLayoutX(x);
        this.thisGrid.setLayoutY(y);
    }

    /**
     * Retorna el grid para que las vistas lo utilicen
     * @return objeto nodo GridPane
     */
    public GridPane getGridPane() {
        return this.thisGrid;
    }

    public void setDisable(String button, boolean bool) {
        if(button.equalsIgnoreCase("borrarTarea"))
            this.borrarTarea.setDisable(bool);
        else
            this.borrarTodo.setDisable(bool);
    }
}
