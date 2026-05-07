package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

public class HeartsDisplay extends Group {

    private static final int MAX_LIVES = 3;
    private SVGPath[] hearts;

    public HeartsDisplay(double windowWidth) {
        this.hearts = new SVGPath[MAX_LIVES];

        for (int i = 0; i < MAX_LIVES; i++) {
            SVGPath heart = new SVGPath();
            heart.setContent("M 10 30 A 20 20 0 0 1 50 30 A 20 20 0 0 1 90 30 Q 90 60 50 90 Q 10 60 10 30 Z");
            heart.setFill(Color.web("#ee2244"));
            heart.setStroke(Color.web("#cc0022"));
            heart.setStrokeWidth(2);
            heart.setScaleX(0.35);
            heart.setScaleY(0.35);

            // Poravnaj desno — ide od desna na levo
            heart.setTranslateX(windowWidth - (i + 2) * 42 - 8);
            heart.setTranslateY(-Constants.TILE_SIZE * 0.75);

            this.hearts[i] = heart;
            this.getChildren().add(heart);
        }
    }

    public void update(int lives) {
        for (int i = 0; i < MAX_LIVES; i++) {
            // Srca se gase zdesna nalevo
            if (i < lives) {
                this.hearts[i].setFill(Color.web("#ee2244"));
                this.hearts[i].setOpacity(1.0);
            } else {
                this.hearts[i].setFill(Color.web("#111111"));
                this.hearts[i].setOpacity(0.4);
            }
        }
    }
}