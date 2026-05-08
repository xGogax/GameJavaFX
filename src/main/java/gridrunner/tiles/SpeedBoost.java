package gridrunner.tiles;

import gridrunner.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpeedBoost extends Rectangle {
    private double centerX, centerY;
    public SpeedBoost(double x, double y, double size,
                 Color fill, Color stroke){

        super(size, size);

        this.setFill(fill);
        this.setStroke(stroke);
        this.setStrokeWidth(size * 0.04);

        this.centerX = x;
        this.centerY = y;

        this.setX(x);
        this.setY(y);
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
