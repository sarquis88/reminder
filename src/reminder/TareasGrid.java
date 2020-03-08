package reminder;

import javafx.geometry.Insets;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TareasGrid implements ReminderParameters {

	private GridPane thisGrid;
	private int rowIndex;

    /**
     * Constructor de clase
     */
    public TareasGrid() {
       
    	this.rowIndex = 0;
    	
        this.thisGrid = new GridPane();
        this.thisGrid.setHgap(hPadding);
        this.thisGrid.setVgap(vPadding);
        this.thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));
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
    
    /**
     * Adicion de tarea al grid
     * @param tarea tarea a añadir
     */
    public void addTarea(String tarea) {
    	Label label = new Label(tarea);
    	label.setStyle(tareasStyle);
    	this.thisGrid.add(label, 0, this.rowIndex);
    	this.rowIndex++;
    }
}
