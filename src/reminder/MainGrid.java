package reminder;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainGrid implements ReminderParameters {

	private GridPane thisGrid;
    private Button nuevaTarea;
    private Button borrarTarea;
    private Button opciones;
    private Button salir;

    /**
     * Constructor de clase
     */
    public MainGrid(Controller controller) {
       
        this.thisGrid = new GridPane();
        this.thisGrid.setHgap(hPadding);
        this.thisGrid.setVgap(vPadding);
        this.thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        this.nuevaTarea = new Button("Nueva tarea");
        this.nuevaTarea.setPrefSize(buttonsWidth, buttonsHeight);
        this.nuevaTarea.setStyle(buttonsStyle);
        this.nuevaTarea.setOnAction(e -> controller.actionPerformed("nueva-tarea"));

        this.borrarTarea = new Button("Borrar tarea");
        this.borrarTarea.setPrefSize(buttonsWidth, buttonsHeight);
        this.borrarTarea.setStyle(buttonsStyle);
        this.borrarTarea.setOnAction(e -> controller.actionPerformed("borrar-tarea"));
        this.borrarTarea.setDisable(true);


        this.opciones = new Button("Opciones");
        this.opciones.setPrefSize(buttonsWidth, buttonsHeight);
        this.opciones.setStyle(buttonsStyle);
        this.opciones.setOnAction(e -> System.out.println("opciones"));

        this.salir = new Button("Salir");
        this.salir.setPrefSize(buttonsWidth, buttonsHeight);
        this.salir.setStyle(buttonsStyle);
        this.salir.setOnAction(e -> Main.getWindow().close());

        // 3 : numero de botones. ecuacion para dinamicidad
        int indexSalir = (sceneHeight - buttonsHeight * 4) / vPadding - 4;

        this.thisGrid.add(nuevaTarea, 0, 0);
        this.thisGrid.add(borrarTarea, 0, 1);
        this.thisGrid.add(opciones, 0, indexSalir - 1);
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

    public void setDisable(boolean bool) {
        this.borrarTarea.setDisable(bool);
    }
}
