package gridrunner.menu;

import gridrunner.Main;
import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GroupFast extends Group {

    public GroupFast(double width, double height, Stage stage) {
        // Pozadinski rectangle
        Rectangle background = new Rectangle(0, 0, width, height);
        background.setFill(Color.LIGHTCORAL);

        // Oblik igraca (trougao)
        double r = 50; // poluprecnik
        Polygon fastShape = new Polygon(
                0, -r,
                -r, r,
                r, r
        );
        fastShape.setFill(Color.WHITE);
        fastShape.setStroke(Color.BLACK);
        fastShape.setStrokeWidth(3);

        // Centriraj oblik u sredini
        fastShape.setLayoutX(width / 2);
        fastShape.setLayoutY(height / 2 - 50);

        // Dugme ispod oblika
        Button selectBtn = new Button("Izaberi FAST");
        selectBtn.setLayoutX(width / 2 - 50);
        selectBtn.setLayoutY(height - 80);
        selectBtn.setPrefWidth(100);

        // Akcija dugmeta
        selectBtn.setOnAction(e -> {
            // Pokreni igru sa FAST tipom
            new Main().startGame(stage, Player.PlayerType.FAST);
        });

        this.getChildren().addAll(background, fastShape, selectBtn);
    }
}
