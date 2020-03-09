package reminder;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage window;

    /**
     * Funcion main
     * @param args argumentos de ejecucion
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Retorno de Stage principal
     * @return objeto Stage de vista
     */
    public static Stage getWindow() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setResizable(false);
        window.setMaximized(false);
        window.setTitle("Reminder");

        ReminderBDD.getInstance();

        window.setScene(View.getInstance(Controller.getInstance()).getScene());
        window.show();
    }

    public static void exit(int out) {
        System.exit(out);
    }
}