package gridrunner.powerup;

import gridrunner.Player;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShieldSpawner {
    private final Group root;
    private final List<String> freeTiles = new ArrayList<>();
    private final double tileSize;
    private final List<ShieldPickup> shields = new ArrayList<>();
    private final Random random = new Random();

    private double timer     = 0;
    private double nextSpawn;

    public ShieldSpawner(Group root, String[] map, double tileSize) {
        this.root     = root;
        this.tileSize = tileSize;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length(); col++) {
                if (map[row].charAt(col) == '.') {
                    freeTiles.add(col + "," + row);
                }
            }
        }

        this.nextSpawn = randomInterval();
    }

    public void update(double dt, Player player) {
        List<ShieldPickup> toRemove = new ArrayList<>();

        for (ShieldPickup s : this.shields) {
            if (!s.isCollected() && s.overlapsPlayer(player)) {
                player.activateShield();
                s.collect(player);
            }

            if (s.isCollected()) {
                s.updateIndicator(player);
                if (s.isFinished()) {
                    toRemove.add(s);
                }
            }
        }

        for (ShieldPickup s : toRemove) {
            this.root.getChildren().remove(s);
            this.shields.remove(s);
        }

        this.timer += dt;
        if (this.timer >= this.nextSpawn) {
            this.timer     = 0;
            this.nextSpawn = randomInterval();
            spawnShield();
        }
    }

    private void spawnShield() {
        if (this.freeTiles.isEmpty()) return;

        Collections.shuffle(this.freeTiles, this.random);
        String[] parts = this.freeTiles.get(0).split(",");
        double x = Double.parseDouble(parts[0]) * this.tileSize;
        double y = Double.parseDouble(parts[1]) * this.tileSize;

        ShieldPickup shield = new ShieldPickup(x, y, this.tileSize);
        this.shields.add(shield);
        this.root.getChildren().add(shield);
    }

    private double randomInterval() {
        return 25 + this.random.nextDouble() * 35; // 25 do 60 sekundi
    }
}