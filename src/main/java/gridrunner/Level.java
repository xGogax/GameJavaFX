package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends Group {

    private List<Rectangle> walls;
    private List<BlinkingWall> blinkingWalls;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Spinner> spinners = new ArrayList<>();
    private Rectangle goal;
    public double startX, startY;
    private Rectangle start;

    public Level ( String map[], double tileSize, Color wallFillColor, Color wallStrokeColor, Color startColor, Color goalColor ) {
        this.walls = new ArrayList<> ( );
        this.blinkingWalls = new ArrayList<>();

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
    }

    public List<Rectangle> getWalls ( ) { return Collections.unmodifiableList ( this.walls ); }
    public List<BlinkingWall> getBlinkingWalls () { return Collections.unmodifiableList ( this.blinkingWalls ); }
    public List<Enemy> getEnemies() { return Collections.unmodifiableList(this.enemies); }
    public List<Spinner> getSpinners() { return Collections.unmodifiableList(this.spinners); }

    public Rectangle getGoal ( ) { return this.goal; }

    public double getStartX ( ) { return this.startX; }
    public double getStartY ( ) { return this.startY; }
}