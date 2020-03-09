package reminder;

import javafx.scene.paint.Color;

public interface ReminderParameters {

    int sceneHeight = 400;
    int sceneWidth = 800;
	int vPadding = 10;
    int hPadding = 10;
    int buttonsWidth = 150;
    int buttonsHeight = 30;
    int arcWidth = 15;
    int arcHeight = 15;
    
    String buttonsStyle = "-fx-font-size: 15;";
    String resourcesPath = "./src/reminder/resources/";

    Color rectangleColor = Color.rgb(0, 0, 0, 0.7);
}
