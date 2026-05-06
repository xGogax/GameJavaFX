package gridrunner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start ( Stage stage ) {
        Group root = new Group ( );

        Input input = new Input();

        Level level = new Level (
                Constants.MAP,
                Constants.TILE_SIZE,
                Constants.WALL_FILL_COLOR,
                Constants.WALL_STROKE_COLOR,
                Constants.START_COLOR,
                Constants.GOAL_COLOR
        );
        root.getChildren ( ).add ( level );

        Player player = new Player (
               Constants.PLAYER_RADIUS,
               level.getStartX ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
               level.getStartY ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
               Constants.PLAYER_FILL_COLOR,
               Constants.PLAYER_STROKE_COLOR
        );
        root.getChildren ( ).add ( player );

        Scene scene = new Scene ( root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
        scene.setFill(new ImagePattern(new Image(Main.class.getResourceAsStream("ground.jpg"))));

        scene.setOnKeyPressed ( input::keyPressed );
        scene.setOnKeyReleased ( input::keyReleased );

        AnimationTimer timer = new AnimationTimer ( ) {
            private double last;
            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }

                double dt = ( now - this.last ) / 10e8;
                this.last = now;

                if ( player.touches ( level.getGoal ( ) ) ) {
                    return;
                }

                player.update ( dt, Constants.PLAYER_SPEED, input, level.getWalls ( ) );
            }
        };
        timer.start ( );

        stage.setTitle ( "Do cilja" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ( );
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}