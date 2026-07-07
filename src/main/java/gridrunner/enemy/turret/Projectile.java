package gridrunner.enemy.turret;

import gridrunner.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Projectile extends Circle {
    private double centerX, centerY;
    private final double dx, dy;
    private final double speed;
    private boolean destroyed = false;

    public Projectile(double centerX, double centerY, double radius,
                      double dx, double dy, double speed,
                      Color fill, Color stroke) {
        super(radius);

        this.centerX = centerX;
        this.centerY = centerY;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;

        this.setFill(fill);
        this.setStroke(stroke);
        this.setStrokeWidth(radius * 0.15);

        this.setCenterX(centerX);
        this.setCenterY(centerY);
    }

    public void update(double dt, List<Rectangle> walls) {
        this.centerX += this.dx * this.speed * dt;
        this.centerY += this.dy * this.speed * dt;

        this.setCenterX(this.centerX);
        this.setCenterY(this.centerY);

        if (hitsWall(walls)) {
            this.destroyed = true;
        }
    }

    private boolean hitsWall(List<Rectangle> walls) {
        for (Rectangle wall : walls) {
            double left   = wall.getBoundsInParent().getMinX();
            double right  = left + wall.getWidth();
            double top    = wall.getBoundsInParent().getMinY();
            double bottom = top + wall.getHeight();

            double closestX = Math.max(left, Math.min(this.centerX, right));
            double closestY = Math.max(top, Math.min(this.centerY, bottom));

            double diffX = this.centerX - closestX;
            double diffY = this.centerY - closestY;
            double r = this.getRadius();

            if (diffX * diffX + diffY * diffY <= r * r) return true;
        }
        return false;
    }

    public boolean isDestroyed() { return this.destroyed; }

    public boolean overlapsPlayer(Player player) {
        double dx = player.getCenterX() - this.centerX;
        double dy = player.getCenterY() - this.centerY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist <= player.getRadius() + this.getRadius();
    }
}
