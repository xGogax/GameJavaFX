package gridrunner.menu;

import gridrunner.Main;
import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GroupBalanced extends Group {

    public GroupBalanced(double width, double height, Stage stage) {
        // Pozadinski rectangle
        Rectangle background = new Rectangle(0, 0, width, height);
        background.setFill(Color.LIGHTBLUE);

        // Oblik igraca (osmerougao)
        double r = 50; // poluprecnik
        double[] pts = new double[16];
        for (int i = 0; i < 8; i++) {
            double angle = -Math.PI / 2 + i * (Math.PI / 4);
            pts[i * 2]     = r * Math.cos(angle);
            pts[i * 2 + 1] = r * Math.sin(angle);
        }
        Polygon balancedShape = new Polygon(pts);
        balancedShape.setFill(Color.WHITE);
        balancedShape.setStroke(Color.BLACK);
        balancedShape.setStrokeWidth(3);

        // Centriraj oblik u sredini
        balancedShape.setLayoutX(width / 2);
        balancedShape.setLayoutY(height / 2 - 50);

        // Dugme ispod oblika
        Button selectBtn = new Button("Izaberi BALANCED");
        selectBtn.setLayoutX(width / 2 - 60);
        selectBtn.setLayoutY(height - 80);
        selectBtn.setPrefWidth(120);

        // Akcija dugmeta
        selectBtn.setOnAction(e -> {
            new Main().startGame(stage, Player.PlayerType.BALANCED);
        });

        this.getChildren().addAll(background, balancedShape, selectBtn);
    }
}
