package gridrunner.enemy.turret;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class Turret extends Rectangle {
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private final Group sceneRoot;
    private final List<Projectile> projectiles = new ArrayList<>();

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
        super(tileSize, tileSize);
        this.getTransforms().add(new Translate(x, y));
        this.setFill(fillColor);
        this.setStroke(strokeColor);
        this.setStrokeWidth(tileSize * 0.04);

        this.tileSize = tileSize;
        this.centerX = x + tileSize / 2.0;
        this.centerY = y + tileSize / 2.0;
        this.direction = direction;
        this.fireInterval = fireInterval;
        this.projectileSpeed = projectileSpeed;
        this.projectileFill = projectileFill;
        this.projectileStroke = projectileStroke;
        this.sceneRoot = sceneRoot;
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
