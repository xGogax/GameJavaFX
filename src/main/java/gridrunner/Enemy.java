package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Enemy extends Rectangle {
    private double centerX, centerY;
    private double speed;
    private double direction = 1; // 1 - down, -1 = up

    public Enemy(double x, double y, double size, double speed,
                 Color fill, Color stroke){

        super(size, size);

        this.setFill(fill);
        this.setStroke(stroke);
        this.setStrokeWidth(size * 0.04);

        this.centerX = x;
        this.centerY = y;
        this.speed = speed;

        this.setX(x);
        this.setY(y);
    }

    public void update(double dt, List<Rectangle> walls) {
        double dy = this.direction * this.speed * dt;

        this.centerY += dy;

        if (hitsWall(walls)) {
            this.centerY -= dy;
            this.direction *= -1;
        }

        this.setY(this.centerY);
    }

    private boolean hitsWall(List<Rectangle> walls) {
        double left   = this.centerX;
        double right  = this.centerX + this.getWidth();
        double top    = this.centerY;
        double bottom = this.centerY + this.getHeight();

        for (Rectangle wall : walls) {
            double wLeft   = wall.getBoundsInParent().getMinX();
            double wRight  = wLeft + wall.getWidth();
            double wTop    = wall.getBoundsInParent().getMinY();
            double wBottom = wTop + wall.getHeight();

            boolean overlapX = left < wRight  && right  > wLeft;
            boolean overlapY = top  < wBottom && bottom > wTop;

            if (overlapX && overlapY) return true;
        }
        return false;
    }

    public boolean overlapsPlayer(Player player) {
        double left  = this.getBoundsInParent().getMinX();
        double right = left + this.getWidth();
        double up    = this.getBoundsInParent().getMinY();
        double down  = up + this.getHeight();

        double cx = player.getCenterX();
        double cy = player.getCenterY();
        double r  = player.getRadius();

        double closestX = Math.max(left,  Math.min(cx, right));
        double closestY = Math.max(up,    Math.min(cy, down));

        double dx = cx - closestX;
        double dy = cy - closestY;

        return dx * dx + dy * dy <= r * r;
    }
}
