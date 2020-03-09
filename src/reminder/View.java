package reminder;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class View implements ReminderParameters {

    private static View thisView = null;
    private static Controller thisController;

    private MainGrid mainGrid;
    private Scene thisScene;
    private String tareaClickeada;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static View getInstance(Controller controller) {
        if(thisView == null)
            thisView = new View(controller);
        return thisView;
    }

    /**
     * Constructor de clase
     */
    private View(Controller controller) {
        Pane layout = new Pane();

        thisController = controller;

        BackgroundImage myBI= new BackgroundImage(new Image("file:" + resourcesPath + "background"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        layout.setBackground(new Background(myBI));
        
        Rectangle leftRectangle = new Rectangle(hPadding, vPadding,
        		buttonsWidth + hPadding * 2, sceneHeight - vPadding * 2);
        leftRectangle.setArcHeight(arcHeight);
        leftRectangle.setArcWidth(arcWidth);
        leftRectangle.setFill(this.rectangleColor);
        
        Rectangle rightRectangle = new Rectangle(leftRectangle.getX() + leftRectangle.getWidth() + hPadding,
        		leftRectangle.getY(), sceneWidth - hPadding - (leftRectangle.getX() + leftRectangle.getWidth() + hPadding),
        		leftRectangle.getHeight());
        rightRectangle.setArcHeight(arcHeight);
        rightRectangle.setArcWidth(arcWidth);
        rightRectangle.setFill(this.rectangleColor);
        
        this.mainGrid = new MainGrid(controller);
        this.mainGrid.setLayout(hPadding, vPadding);
        this.mainGrid.setDisable("borrarTodo", thisController.isTareasListEmpty());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(rightRectangle.getX() + hPadding);
        scrollPane.setLayoutY(rightRectangle.getY() + vPadding);
        scrollPane.setPrefWidth(rightRectangle.getWidth() - 2 * hPadding);
        scrollPane.setPrefHeight(rightRectangle.getHeight() - 2 * vPadding);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        TableView<Tarea> tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setPrefWidth(scrollPane.getPrefWidth());
        tableView.setPrefHeight(scrollPane.getPrefHeight());

        TableColumn<Tarea, String> c0 = new TableColumn<>("Nro.");
        c0.setCellValueFactory(new PropertyValueFactory<>("id"));
        c0.setPrefWidth( tableView.getPrefWidth()  * 0.1);

        TableColumn<Tarea, String> c1 = new TableColumn<>("Tarea");
        c1.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        c1.setPrefWidth( tableView.getPrefWidth() * 0.7);

        TableColumn<Tarea, Integer> c2 = new TableColumn<>("Fecha limite");
        c2.setCellValueFactory(new PropertyValueFactory<>("fechaLimite"));
        c2.setPrefWidth( tableView.getPrefWidth() * 0.2);

        tableView.setItems(Controller.getTareasList());
        tableView.setOnMouseClicked(mouseEvent -> {
            // handler de clicks en tableView
            try {
                // localizacion de fila clickeada
                TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
                this.tareaClickeada = c1.getCellData(pos.getRow()); // nombre de fila clickeada
                mainGrid.setDisable("borrarTarea", false);
            }
            catch (IndexOutOfBoundsException e) {
                e.getMessage();
            }
        });

        tableView.getColumns().add(c0);
        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);

        scrollPane.setContent(tableView);

        layout.getChildren().addAll(leftRectangle, rightRectangle,
                mainGrid.getGridPane(), scrollPane);
        
        this.thisScene = new Scene(layout, sceneWidth, sceneHeight);
    }

    /**
     * Devuelve ventana de vista
     * @return objeto Scene
     */
    public Scene getScene() {
        return this.thisScene;
    }

    /**
     * Retorna comentario de la tarea clickeada en la GUI
     * @return String del comentario
     */
    public String getTareaClickeada() {
        return this.tareaClickeada;
    }

    /**
     * Vuelve a null la tarea clickeada
     * Vuelve Disable el boton de borrado
     */
    public void clearTareaClickeada() {
        this.tareaClickeada = null;
        mainGrid.setDisable("borrarTarea", true);
    }

    /**
     * Actualizacion de vista
     */
    public void refresh() {
        mainGrid.setDisable("borrarTodo", thisController.isTareasListEmpty());
    }
}
