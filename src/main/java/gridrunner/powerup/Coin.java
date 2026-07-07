package gridrunner.powerup;

import gridrunner.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

public class Coin extends Circle {

    private Translate position;
    private double centerX, centerY;
    private boolean collected = false;

    public Coin(double centerX, double centerY, double radius,
                Color fillColor, Color strokeColor) {
        super(radius);

        this.centerX = centerX;
        this.centerY = centerY;

        this.setFill(fillColor);
        this.setStroke(strokeColor);

        this.position = new Translate(centerX, centerY);
        this.getTransforms().add(position);
    }

    public boolean isCollected() { return this.collected; }

    public void collect() {
        this.collected = true;
        this.setVisible(false);
    }

    public boolean overlapsPlayer(Player player) {

        double dx = player.getCenterX() - this.centerX;
        double dy = player.getCenterY() - this.centerY;

        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = player.getRadius() + this.getRadius();

        return distanceSquared <= radiusSum * radiusSum;
    }
}
