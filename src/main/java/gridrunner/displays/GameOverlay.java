package gridrunner.displays;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameOverlay extends Group {

    public GameOverlay(String message, Color color, double windowWidth, double windowHeight) {
        Rectangle bg = new Rectangle(windowWidth, windowHeight);
        bg.setFill(Color.color(0, 0, 0, 0.55));

        Text text = new Text(message);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 52));
        text.setFill(color);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setX(windowWidth / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setY(windowHeight / 2);

        this.getChildren().addAll(bg, text);
    }
}