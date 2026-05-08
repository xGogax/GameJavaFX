package gridrunner.displays;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimeDisplay extends Group {

    private Text timeText;
    private double time = 0;

    public TimeDisplay(double windowWidth) {

        this.timeText = new Text(windowWidth / 2.0 - 40, 30, "Time: 0.0");

        this.timeText.setFill(Color.WHITE);
        this.timeText.setFont(Font.font(24));

        this.getChildren().add(timeText);
    }

    public void update(double dt) {
        this.time += dt;
        this.timeText.setText(String.format("Time: %.1f", this.time));
    }
}