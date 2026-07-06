package gridrunner.menu;

import gridrunner.Constants;
import gridrunner.Main;
import gridrunner.Maps;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LevelSelect extends Group {

    public LevelSelect(double width, double height, Stage stage) {
        // Pozadina celog ekrana
        Rectangle background = new Rectangle(0, 0, width, height);
        background.setFill(Color.web("#3b6ea5"));
        this.getChildren().add(background);

        String[][] maps = { Maps.TEST, Maps.LEVEL_1, Maps.LEVEL_2, Maps.LEVEL_3, Maps.LEVEL_4};
        String[] names = { "TEST", "LEVEL 1", "LEVEL 2", "LEVEL 3", "LEVEL 4" };

        double margin = 30;
        double squareSize = width / maps.length - margin;
        double squareY = height / 2.0 - squareSize / 2.0;

        for (int i = 0; i < maps.length; i++) {
            final String[] chosenMap = maps[i];
            double squareX = i * (width / maps.length) + margin / 2.0;

            // Beli kvadratic
            Rectangle square = new Rectangle(squareX, squareY, squareSize, squareSize);
            square.setFill(Color.WHITE);
            square.setStroke(Color.web("#22334455"));
            square.setStrokeWidth(2);

            // Naziv nivoa - gornja polovina, centrirano
            Label label = new Label(names[i]);
            label.setLayoutX(squareX);
            label.setLayoutY(squareY + squareSize * 0.25 - 10);
            label.setPrefWidth(squareSize);
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: center;");

            // Dugme - donja polovina, centrirano
            Button btn = new Button("Izaberi");
            btn.setPrefWidth(100);
            btn.setLayoutX(squareX + squareSize / 2.0 - 50);
            btn.setLayoutY(squareY + squareSize * 0.65);

            btn.setOnAction(e -> {
                Constants.MAP = chosenMap;
                Constants.updateWindowSize();
                new Main().showClassSelect(stage);
            });

            this.getChildren().addAll(square, label, btn);
        }
    }
}