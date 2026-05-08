package gridrunner.tiles;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BlinkingWall extends Rectangle {
    private boolean solid = true;

    public BlinkingWall(double x, double y, double size,
                        Color fill, Color stroke,
                        double visibleSeconds, double hiddenSeconds){
        super(size, size);

        this.setX(x);
        this.setY(y);
        this.setFill(fill);
        this.setStroke(stroke);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> this.solid = false);

        PauseTransition stayHidden = new PauseTransition(Duration.seconds(hiddenSeconds));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.4), this);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setOnFinished(e -> this.solid = true);

        PauseTransition stayVisible = new PauseTransition(Duration.seconds(visibleSeconds));

        SequentialTransition cycle = new SequentialTransition(
                stayVisible, fadeOut, stayHidden, fadeIn
        );
        cycle.setCycleCount(SequentialTransition.INDEFINITE);
        cycle.play();
    }

    public boolean isSolid() {
        return this.solid;
    }
}
