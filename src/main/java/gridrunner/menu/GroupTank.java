package gridrunner.menu;

import gridrunner.Main;
import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GroupTank extends Group {

    public GroupTank(double width, double height, Stage stage) {
        // Pozadinski rectangle
        Rectangle background = new Rectangle(0, 0, width, height);
        background.setFill(Color.LIGHTGREEN);

        // Oblik igraca (kvadrat)
        double r = 50; // poluprecnik
        double[] pts = new double[]{
                -r, -r,
                r, -r,
                r, r,
                -r, r
        };
        Polygon tankShape = new Polygon(pts);
        tankShape.setFill(Color.WHITE);
        tankShape.setStroke(Color.BLACK);
        tankShape.setStrokeWidth(3);

        // Centriraj oblik u sredini
        tankShape.setLayoutX(width / 2);
        tankShape.setLayoutY(height / 2 - 50);

        // Dugme ispod oblika
        Button selectBtn = new Button("Izaberi TANK");
        selectBtn.setLayoutX(width / 2 - 50);
        selectBtn.setLayoutY(height - 80);
        selectBtn.setPrefWidth(100);

        // Akcija dugmeta
        selectBtn.setOnAction(e -> {
            new Main().startGame(stage, Player.PlayerType.TANK);
        });

        this.getChildren().addAll(background, tankShape, selectBtn);
    }
}
