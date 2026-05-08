package gridrunner.powerup;

import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class HeartPickup extends Group {
    private double centerX, centerY;
    private double radius;
    private boolean collected = false;

    public HeartPickup(double x, double y, double tileSize) {
        this.centerX = x + tileSize / 2.0;
        this.centerY = y + tileSize / 2.0;
        this.radius = tileSize * 0.3;

        SVGPath heart = new SVGPath();
        heart.setContent("M 10 30 A 20 20 0 0 1 50 30 A 20 20 0 0 1 90 30 Q 90 60 50 90 Q 10 60 10 30 Z");
        heart.setFill(Color.web("#ee2244"));
        heart.setStroke(Color.web("#cc0022"));
        heart.setStrokeWidth(2);

        this.getTransforms().addAll(
                new Translate(this.centerX, this.centerY),
                new Scale(0.35, 0.35, 0, 0)
        );

        heart.setTranslateX(-50);
        heart.setTranslateY(-45);

        this.getChildren().add(heart);
    }

    public boolean isCollected() { return this.collected; }

    public void collect() { this.collected = true; }

    public boolean overlapsPlayer(Player player) {
        double dx = player.getCenterX() - this.centerX;
        double dy = player.getCenterY() - this.centerY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist <= player.getRadius() + this.radius;
    }
}
