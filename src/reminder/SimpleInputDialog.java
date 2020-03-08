package reminder;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SimpleInputDialog implements ReminderParameters {

    private TextField textField;
    private Dialog<ButtonType> thisDialog;

    public SimpleInputDialog(String title) {
        thisDialog = new Dialog<>();
        thisDialog.setTitle(title);
        thisDialog.setHeaderText("");

        ButtonType cancelar = new ButtonType("Cancelar");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        thisDialog.getDialogPane().getButtonTypes().addAll(cancelar);

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        textField = new TextField();
        textField.setPromptText(title);
        textField.setPrefWidth(buttonsWidth * 2);

        grid.add(textField, 0, 0);

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Retorna el texto ingresado
     * @return objeto String con texto
     */
    public String getResult() {
        if(thisDialog.getResult() == ButtonType.OK)
            return textField.getText();
        else
            return null;
    }
    
    /**
     * Muestra de ventana
     */
    public void show() {

        //Stage stage = (Stage) thisDialog.getDialogPane().getScene().getWindow();
        //stage.getIcons().add(new Image("file:./src/images/logo.png")); // To add an icon

        Platform.runLater(() -> thisDialog.getDialogPane().getScene().getWindow().sizeToScene());
        thisDialog.showAndWait();
    }
}
