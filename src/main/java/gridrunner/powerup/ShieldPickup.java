package gridrunner.powerup;

import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ShieldPickup extends Group {
    private double centerX, centerY;
    private double radius;
    private boolean collected = false;
    private boolean finished = false;

    private final Polygon hexagon;
    private Circle indicator;

    public ShieldPickup(double x, double y, double tileSize) {
        this.centerX = x + tileSize / 2.0;
        this.centerY = y + tileSize / 2.0;
        this.radius = tileSize * 0.35;

        double[] points = new double[12];
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 90);
            points[i * 2]     = this.centerX + this.radius * Math.cos(angle);
            points[i * 2 + 1] = this.centerY + this.radius * Math.sin(angle);
        }

        this.hexagon = new Polygon(points);
        this.hexagon.setFill(Color.web("#3399ff"));
        this.hexagon.setStroke(Color.web("#1166cc"));
        this.hexagon.setStrokeWidth(2);

        this.getChildren().add(this.hexagon);
    }

    public boolean isCollected() { return this.collected; }
    public boolean isFinished() { return this.finished; }

    // Poziva se kad igrac pokupi stit
    public void collect(Player player) {
        this.collected = true;

        this.getChildren().remove(this.hexagon);

        this.indicator = new Circle(0, 0, player.getRadius() * 1.5);
        this.indicator.setFill(Color.TRANSPARENT);
        this.indicator.setStroke(Color.web("#3399ff"));
        this.indicator.setStrokeWidth(3);

        this.getChildren().add(this.indicator);
    }

    // krug prati igraca
    public void updateIndicator(Player player) {
        if (!this.collected || this.finished) return;

        this.indicator.setCenterX(player.getCenterX());
        this.indicator.setCenterY(player.getCenterY());

        if (!player.isShielded()) {
            this.finished = true;
        }
    }

    public boolean overlapsPlayer(Player player) {
        double dx = player.getCenterX() - this.centerX;
        double dy = player.getCenterY() - this.centerY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist <= player.getRadius() + this.radius;
    }
}