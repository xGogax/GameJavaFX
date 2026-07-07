package gridrunner;

import gridrunner.enemy.Enemy;
import gridrunner.enemy.Spinner;
import gridrunner.enemy.turret.Turret;
import gridrunner.powerup.Coin;
import gridrunner.tiles.BlinkingWall;
import gridrunner.tiles.SlowBoost;
import gridrunner.tiles.SpeedBoost;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends Group {

    private List<Coin> coins;                       // spawning randomly
    private List<Rectangle> walls;                  // #
    private List<SpeedBoost> speedBoosters;         // X
    private List<SlowBoost> slowBoosters;              // Y
    private List<BlinkingWall> blinkingWalls;       // B
    private List<Enemy> enemies;                    // E
    private List<Spinner> spinners;                 // R
    private List<Turret> turrets;                   // T
    private Rectangle goal;                         // G
    public double startX, startY;
    private Rectangle start;                        // S

    public Level ( String map[], double tileSize, Color wallFillColor, Color wallStrokeColor, Color startColor, Color goalColor ) {
        this.walls = new ArrayList<> ( );
        this.blinkingWalls = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.spinners = new ArrayList<>();
        this.speedBoosters = new ArrayList<>();
        this.slowBoosters = new ArrayList<>();
        this.turrets = new ArrayList<>();

        for ( int row = 0; row < map.length; row++ ) {
            for ( int column = 0; column < map[row].length ( ); column++ ) {
                double positionX = column * tileSize;
                double positionY = row * tileSize;

                switch ( map[row].charAt( column ) ) {
                    case '#': {
                        Rectangle wall = new Rectangle ( tileSize, tileSize );
                        wall.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        wall.setFill ( wallFillColor );
                        wall.setStroke ( wallStrokeColor );
                        wall.setStrokeWidth ( tileSize * 0.04 );

                        this.walls.add ( wall );

                        super.getChildren ( ).add ( wall );

                        break;
                    }

                    case 'X': {
                        SpeedBoost boost = new SpeedBoost(
                                positionX, positionY, tileSize,
                                Constants.SPEED_BOOSTER_FILL_COLOR, Constants.SPEED_BOOSTER_STROKE_COLOR
                        );
                        this.speedBoosters.add(boost);
                        super.getChildren().add(boost);
                        break;
                    }

                    case 'Y': {
                        SlowBoost boost = new SlowBoost(
                                positionX, positionY, tileSize,
                                Constants.SLOW_DOWN_FILL_COLOR, Constants.SLOW_DOWN_STROKE_COLOR
                        );
                        this.slowBoosters.add(boost);
                        super.getChildren().add(boost);
                        break;
                    }

                    case 'B': {
                        BlinkingWall bw = new BlinkingWall(
                                positionX, positionY, tileSize,
                                Constants.BLINK_WALL_FILL_COLOR, Constants.BLINK_WALL_STROKE_COLOR,
                                Constants.BLINK_VISIBLE_SECONDS, Constants.BLINK_HIDDEN_SECONDS
                        );
                        this.blinkingWalls.add(bw);
                        super.getChildren().add(bw);

                        break;
                    }

                    case 'E': {
                        Enemy enemy = new Enemy(
                                positionX + (Constants.TILE_SIZE - Constants.ENEMY_SIZE) / 2,
                                positionY + (Constants.TILE_SIZE - Constants.ENEMY_SIZE) / 2,
                                Constants.ENEMY_SIZE,
                                Constants.ENEMY_SPEED,
                                Constants.ENEMY_FILL_COLOR,
                                Constants.ENEMY_STROKE_COLOR
                        );
                        this.enemies.add(enemy);
                        super.getChildren().add(enemy);
                        break;
                    }

                    case 'R': {
                        Spinner spinner = new Spinner(
                                positionX, positionY,
                                tileSize,
                                Constants.SPINNER_FILL_COLOR,
                                Constants.SPINNER_STROKE_COLOR
                        );
                        this.spinners.add(spinner);
                        super.getChildren().add(spinner);
                        break;
                    }

                    case 'S': {
                        this.start = new Rectangle ( tileSize, tileSize );
                        this.start.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        this.start.setFill ( startColor );

                        super.getChildren ( ).add ( this.start );

                        this.startX = positionX;
                        this.startY = positionY;

                        break;
                    }

                    case 'T': {
                        Turret.Direction dir = determineTurretDirection(map, row, column);

                        Turret turret = new Turret(
                                positionX, positionY, tileSize,
                                Constants.TURRET_FILL_COLOR, Constants.TURRET_STROKE_COLOR,
                                dir,
                                Constants.TURRET_FIRE_INTERVAL,
                                Constants.PROJECTILE_SPEED,
                                Constants.PROJECTILE_FILL_COLOR, Constants.PROJECTILE_STROKE_COLOR,
                                this
                        );

                        this.turrets.add(turret);
                        this.walls.add(turret); // ponasa se kao solidan zid za igraca

                        super.getChildren().add(turret);

                        break;
                    }

                    case 'G':{
                        this.goal = new Rectangle ( tileSize, tileSize );
                        this.goal.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        this.goal.setFill ( goalColor );

                        super.getChildren ( ).add ( this.goal );

                        break;
                    }
                }
            }
        }

        for (Enemy enemy : enemies) {
            enemy.toFront();
        }
        for (Spinner spinner : spinners) {
            spinner.toFront();
        }

        for (int i = 0; i < Constants.NUMBER_OF_COINS; i++) {

            int x = (int) (Math.random() * map[0].length());
            int y = (int) (Math.random() * map.length);

            while (map[y].charAt(x) != '.') {
                x = (int) (Math.random() * map[0].length());
                y = (int) (Math.random() * map.length);
            }

            double coinX = x * tileSize + tileSize / 2.0;
            double coinY = y * tileSize + tileSize / 2.0;

            Coin coin = new Coin(
                    coinX,
                    coinY,
                    tileSize * 0.2,
                    Constants.COIN_FILL_COLOR,
                    Constants.COIN_STROKE_COLOR
            );

            this.coins.add(coin);
            super.getChildren().add(coin);
        }
    }

    private Turret.Direction determineTurretDirection(String[] map, int row, int col) {
        if (col + 1 < map[row].length() && map[row].charAt(col + 1) == '.') return Turret.Direction.RIGHT;
        if (col - 1 >= 0 && map[row].charAt(col - 1) == '.') return Turret.Direction.LEFT;
        if (row + 1 < map.length && col < map[row + 1].length() && map[row + 1].charAt(col) == '.') return Turret.Direction.DOWN;
        if (row - 1 >= 0 && col < map[row - 1].length() && map[row - 1].charAt(col) == '.') return Turret.Direction.UP;

        return Turret.Direction.RIGHT;
    }

    public List<Rectangle> getWalls ( ) { return Collections.unmodifiableList ( this.walls ); }
    public List<BlinkingWall> getBlinkingWalls () { return Collections.unmodifiableList ( this.blinkingWalls ); }
    public List<Enemy> getEnemies() { return Collections.unmodifiableList(this.enemies); }
    public List<Spinner> getSpinners() { return Collections.unmodifiableList(this.spinners); }
    public List<Coin> getCoins() { return Collections.unmodifiableList(this.coins); }
    public List<SpeedBoost> getSpeedBoosters() { return Collections.unmodifiableList(this.speedBoosters); }
    public List<SlowBoost> getSlowBoosters() { return Collections.unmodifiableList(this.slowBoosters); }
    public List<Turret> getTurrets() { return Collections.unmodifiableList(this.turrets); }

    public Rectangle getGoal ( ) { return this.goal; }

    public double getStartX ( ) { return this.startX; }
    public double getStartY ( ) { return this.startY; }
}