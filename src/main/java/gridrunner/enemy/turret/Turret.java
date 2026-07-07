package gridrunner.enemy.turret;

import gridrunner.Constants;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turret extends Group {
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private final Group sceneRoot;
    private final List<Projectile> projectiles = new ArrayList<>();

    private final Rectangle hitbox; // wall

    private final double tileSize;
    private final double centerX, centerY;
    private final Direction direction;
    private final double fireInterval;
    private final double projectileSpeed;
    private final Color projectileFill;
    private final Color projectileStroke;

    private double timer = 0;

    public Turret(double x, double y, double tileSize, Color fillColor, Color strokeColor,
                  Direction direction, double fireInterval, double projectileSpeed,
                  Color projectileFill, Color projectileStroke, Group sceneRoot) {

        this.getTransforms().add(new Translate(x, y));

        this.tileSize = tileSize;
        this.centerX = x + tileSize / 2.0;
        this.centerY = y + tileSize / 2.0;
        this.direction = direction;
        this.fireInterval = fireInterval;
        this.projectileSpeed = projectileSpeed;
        this.projectileFill = projectileFill;
        this.projectileStroke = projectileStroke;
        this.sceneRoot = sceneRoot;

        this.hitbox = new Rectangle(tileSize, tileSize);
        this.hitbox.getTransforms().add(new Translate(x, y));
        this.hitbox.setFill(Color.TRANSPARENT);
        this.hitbox.setStroke(Color.TRANSPARENT);

        buildVisuals(fillColor, strokeColor);
    }

    private void buildVisuals(Color fillColor, Color strokeColor) {
        Rectangle background = new Rectangle(tileSize, tileSize);
        background.setFill(Constants.WALL_FILL_COLOR);
        background.setStroke(Constants.WALL_STROKE_COLOR);
        background.setStrokeWidth(tileSize*0.04);

        Circle body = new Circle(tileSize / 2.0, tileSize / 2.0, tileSize * 0.32);
        body.setFill(fillColor);
        body.setStroke(strokeColor);
        body.setStrokeWidth(tileSize * 0.04);

        double barrelLength = tileSize * 0.55;
        double barrelWidth = tileSize * 0.22;

        Rectangle barrel = new Rectangle(barrelLength, barrelWidth);
        barrel.setX(tileSize / 2.0);
        barrel.setY(tileSize / 2.0 - barrelWidth / 2.0);
        barrel.setFill(strokeColor);
        barrel.setStroke(strokeColor.darker());
        barrel.setStrokeWidth(tileSize * 0.02);

        double angle = switch (direction) {
            case UP -> -90;
            case DOWN -> 90;
            case LEFT -> 180;
            case RIGHT -> 0;
        };
        barrel.getTransforms().add(new Rotate(angle, tileSize / 2.0, tileSize / 2.0));

        this.getChildren().addAll(background, barrel, body);
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }

    public void update(double dt, List<Rectangle> walls) {
        this.timer += dt;
        if (this.timer >= this.fireInterval) {
            this.timer = 0;
            fire();
        }

        List<Projectile> toRemove = new ArrayList<>();
        for (Projectile p : this.projectiles) {
            p.update(dt, walls);
            if (p.isDestroyed()) {
                toRemove.add(p);
            }
        }
        for (Projectile p : toRemove) {
            this.sceneRoot.getChildren().remove(p);
            this.projectiles.remove(p);
        }
    }

    private void fire() {
        double dx = 0, dy = 0;
        switch (this.direction) {
            case UP:    dy = -1; break;
            case DOWN:  dy = 1;  break;
            case LEFT:  dx = -1; break;
            case RIGHT: dx = 1;  break;
        }

        double radius = this.tileSize * 0.15;
        double startX = this.centerX + dx * (this.tileSize / 2.0 + radius);
        double startY = this.centerY + dy * (this.tileSize / 2.0 + radius);

        Projectile projectile = new Projectile(
                startX, startY, radius, dx, dy, this.projectileSpeed,
                this.projectileFill, this.projectileStroke
        );

        this.projectiles.add(projectile);
        this.sceneRoot.getChildren().add(projectile);
    }

    public List<Projectile> getProjectiles() {
        return Collections.unmodifiableList(this.projectiles);
    }
}