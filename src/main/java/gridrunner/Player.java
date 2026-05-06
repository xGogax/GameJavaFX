package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.List;

public class Player extends Polygon {

    private Translate position;
    private double centerX, centerY, radius;
    private double startX, startY;

    public Player(double radius, double positionX, double positionY,
                  Color fillColor, Color strokeColor) {

        super(octagonPoints(radius));

        this.setFill(fillColor);
        this.setStroke(strokeColor);
        this.setStrokeWidth(radius * 0.03);

        this.centerX = positionX + radius;
        this.centerY = positionY + radius;
        this.startX = this.centerX; this.startY = this.centerY;
        this.radius  = radius;

        this.position = new Translate(this.centerX, this.centerY);
        this.getTransforms().addAll(this.position);
    }

    private static double[] octagonPoints(double radius) {
        double[] pts = new double[16];
        for (int i = 0; i < 8; i++) {
            double angle = -Math.PI / 2 + i * (Math.PI / 4);
            pts[i * 2]     = radius * Math.cos(angle);
            pts[i * 2 + 1] = radius * Math.sin(angle);
        }
        return pts;
    }

    public void update(double dt, double speed, Input input, List<Rectangle> walls, List<BlinkingWall> blinkingWalls) {
        double dx = 0;
        double dy = 0;

        if ( input.left ( ) )  { dx -= speed * dt; }
        if ( input.right ( ) ) { dx += speed * dt; }
        if ( input.up ( ) )    { dy -= speed * dt; }
        if ( input.down ( ) )  { dy += speed * dt; }
        if ( input.R ( ) )     { this.centerX = this.startX; this.centerY = this.startY; }

        // Keep consistent speed on diagonals
        if ( dx != 0 && dy != 0 ) {
            double factor = 1.0 / Math.sqrt(2.0);
            dx *= factor;
            dy *= factor;
        }

        // Resolve each axis independently to allow sliding along walls
        this.moveAndResolve ( dx, 0, walls, blinkingWalls);
        this.moveAndResolve ( 0, dy, walls, blinkingWalls);
    }

    private void moveAndResolve ( double dx, double dy, List<Rectangle> walls, List<BlinkingWall> blinkingWalls) {
        this.centerX += dx;
        this.centerY += dy;
        boolean clear = true;
        for ( Rectangle wall : walls ) {
            if ( !overlaps ( wall ) ) {
                continue;
            }

            clear = false;
        }

        for (BlinkingWall bwall : blinkingWalls) {
            if (!bwall.isSolid() || !overlaps (bwall)){
                continue;
            }

            clear = false;
        }

        if ( !clear ) {
            this.centerX -= dx;
            this.centerY -= dy;
        }

        this.position.setX ( this.centerX );
        this.position.setY ( this.centerY );
    }

    private boolean overlaps ( Rectangle rectangle ) {
        double wallMinX = rectangle.getBoundsInParent ( ).getMinX ( );
        double wallMinY = rectangle.getBoundsInParent ( ).getMinY ( );

        double left  = wallMinX;
        double right = wallMinX + rectangle.getWidth ( );
        double up    = wallMinY;
        double down  = wallMinY + rectangle.getHeight ( );

        double closestX = Math.max ( left, Math.min ( this.centerX, right ) );
        double closestY = Math.max ( up, Math.min ( this.centerY, down ) );

        double dx = this.centerX - closestX;
        double dy = this.centerY - closestY;

        return dx * dx + dy * dy <= this.radius * this.radius;
    }

    public boolean touches ( Rectangle goal ) {
        double goalMinX = goal.getBoundsInParent ( ).getMinX ( );
        double goalMinY = goal.getBoundsInParent ( ).getMinY ( );

        double left  = goalMinX;
        double right = goalMinX + goal.getWidth ( );
        double up    = goalMinY;
        double down  = goalMinY + goal.getHeight ( );

        boolean horizontal = ( this.centerX - this.radius ) >= left && ( this.centerX + radius ) <= right;
        boolean vertical   = ( this.centerY - this.radius ) >= up && ( this.centerY + radius ) <= down;

        return horizontal && vertical;
    }
}