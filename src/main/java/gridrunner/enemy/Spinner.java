package gridrunner.enemy;

import gridrunner.Constants;
import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Spinner extends Group {

    private Rectangle blade;
    private Polygon diamond;
    private Rotate rotate;

    private double centerX, centerY;
    private double bladeWidth, bladeLength;
    private double rotationSpeed;

    public Spinner(double x, double y, double tileSize,
                   Color fill, Color stroke){
        this.centerX = x + tileSize / 2;
        this.centerY = y + tileSize / 2;

        this.bladeWidth = tileSize * 0.18;
        this.bladeLength = tileSize * 4;
        this.rotationSpeed = Constants.SPINNER_ROTATION_SPEED;

        // Elisa
        this.blade = new Rectangle(
                -bladeWidth / 2,
                -bladeLength / 2,
                bladeWidth,
                bladeLength
        );
        this.blade.setFill(fill);
        this.blade.setStroke(stroke);
        this.blade.setStrokeWidth(tileSize * 0.02);

        // Dijamant
        double d = tileSize * 0.28;
        this.diamond = new Polygon(
                0, -d,
                d, 0,
                0, d,
                -d, 0
        );
        this.diamond.setFill(fill);
        this.diamond.setStroke(stroke);
        this.diamond.setStrokeWidth(tileSize * 0.02);

        //rotacija oko centra
        this.rotate = new Rotate(0, 0, 0);

        this.getChildren().addAll(this.blade, this.diamond);
        this.getTransforms().addAll(
                new Translate(this.centerX, this.centerY),
                this.rotate
        );
    }

    public void update(double dt){
        this.rotate.setAngle(this.rotate.getAngle() + this.rotationSpeed * dt);
    }

    public boolean overlapsPlayer(Player player) {
        double cx = player.getCenterX();
        double cy = player.getCenterY();
        double r  = player.getRadius();

        // Transformisi igraca u lokalni koordinatni sistem spinnera
        double localX = cx - this.centerX;
        double localY = cy - this.centerY;

        // Primeni inverznu rotaciju
        double angle = Math.toRadians(-this.rotate.getAngle());
        double cos   = Math.cos(angle);
        double sin   = Math.sin(angle);

        double rotatedX = localX * cos - localY * sin;
        double rotatedY = localX * sin + localY * cos;

        // Proveri koliziju sa nerotiranom elisom (centrisanom na 0,0)
        double closestX = Math.max(-bladeWidth / 2,  Math.min(rotatedX, bladeWidth / 2));
        double closestY = Math.max(-bladeLength / 2, Math.min(rotatedY, bladeLength / 2));

        double dx = rotatedX - closestX;
        double dy = rotatedY - closestY;

        return dx * dx + dy * dy <= r * r;
    }
}
