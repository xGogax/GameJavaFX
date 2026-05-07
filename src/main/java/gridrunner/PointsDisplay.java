package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PointsDisplay extends Group {
    private Text pointsText;

    public PointsDisplay() {
        this.pointsText = new Text(20, 30, "Points: 0");

        this.pointsText.setFill(Color.GOLD);
        this.pointsText.setFont(Font.font(24));

        this.getChildren().add(this.pointsText);
    }

    public void update(int points) {
        this.pointsText.setText("Points: " + points);
    }
}
