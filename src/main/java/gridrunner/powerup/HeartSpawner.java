package gridrunner;

import gridrunner.powerup.HeartPickup;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HeartSpawner {

    private final Group root;
    private final List<String> freeTiles = new ArrayList<>();
    private final double tileSize;
    private final List<HeartPickup> hearts = new ArrayList<>();
    private final Random random = new Random();

    private double timer     = 0;
    private double nextSpawn;

    public HeartSpawner(Group root, String[] map, double tileSize) {
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
        List<HeartPickup> toRemove = new ArrayList<>();
        for (HeartPickup h : this.hearts) {
            if (h.overlapsPlayer(player)) {
                h.collect();
                toRemove.add(h);
                player.addLife();
            }
        }
        for (HeartPickup h : toRemove) {
            this.root.getChildren().remove(h);
            this.hearts.remove(h);
        }

        this.timer += dt;
        if (this.timer >= this.nextSpawn) {
            this.timer     = 0;
            this.nextSpawn = randomInterval();
            spawnHeart();
        }
    }

    private void spawnHeart() {
        if (this.freeTiles.isEmpty()) return;

        Collections.shuffle(this.freeTiles, this.random);
        String[] parts = this.freeTiles.get(0).split(",");
        double x = Double.parseDouble(parts[0]) * this.tileSize;
        double y = Double.parseDouble(parts[1]) * this.tileSize;

        HeartPickup heart = new HeartPickup(x, y, this.tileSize);
        this.hearts.add(heart);
        this.root.getChildren().add(heart);
    }

    private double randomInterval() {
        return 10 + this.random.nextDouble() * 20; // 10 to 30 seconds
    }
}