package gridrunner;

import gridrunner.displays.GameOverlay;
import gridrunner.displays.HeartsDisplay;
import gridrunner.displays.PointsDisplay;
import gridrunner.displays.TimeDisplay;
import gridrunner.enemy.Enemy;
import gridrunner.enemy.Spinner;
import gridrunner.menu.GroupBalanced;
import gridrunner.menu.GroupFast;
import gridrunner.menu.GroupTank;
import gridrunner.menu.LevelSelect;
import gridrunner.powerup.Coin;
import gridrunner.tiles.SlowBoost;
import gridrunner.tiles.SpeedBoost;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Group levelRoot = new Group();
        levelRoot.getChildren().add(new LevelSelect(Constants.MENU_WIDTH, Constants.MENU_HEIGHT, stage));

        Scene levelScene = new Scene(levelRoot, Constants.MENU_WIDTH, Constants.MENU_HEIGHT);

        stage.setScene(levelScene);
        stage.setTitle("Izaberi mapu");
        stage.setResizable(false);
        stage.show();
    }

    public void showClassSelect(Stage stage) {
        Group menuRoot = new Group();
        Scene menuScene = new Scene(menuRoot, Constants.MENU_WIDTH, Constants.MENU_HEIGHT);

        double buttonHeight = Constants.MENU_HEIGHT;
        double buttonWidth = Constants.MENU_WIDTH / 3.0;

        GroupFast fastGroup = new GroupFast(buttonWidth, buttonHeight, stage);
        fastGroup.setLayoutX(0);

        GroupBalanced balancedGroup = new GroupBalanced(buttonWidth, buttonHeight, stage);
        balancedGroup.setLayoutX(buttonWidth);

        GroupTank tankGroup = new GroupTank(buttonWidth, buttonHeight, stage);
        tankGroup.setLayoutX(2 * buttonWidth);

        menuRoot.getChildren().addAll(fastGroup, balancedGroup, tankGroup);

        stage.setScene(menuScene);
        stage.setTitle("Izaberi igrača");
        stage.setResizable(false);
        stage.show();
    }

    public void startGame(Stage stage, Player.PlayerType type) {
        Group root = new Group();
        Input input = new Input();

        Level level = new Level(
                Constants.MAP,
                Constants.TILE_SIZE,
                Constants.WALL_FILL_COLOR,
                Constants.WALL_STROKE_COLOR,
                Constants.START_COLOR,
                Constants.GOAL_COLOR
        );
        root.getChildren().add(level);

        Player player = new Player(
                Constants.PLAYER_RADIUS,
                level.getStartX() + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                level.getStartY() + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                type
        );
        root.getChildren().add(player);

        HeartsDisplay hearts = new HeartsDisplay(Constants.WINDOW_WIDTH, player.getLives());
        root.getChildren().add(hearts);

        Scene scene = new Scene ( root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
        scene.setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ground.jpg")))));

        scene.setOnKeyPressed ( input::keyPressed );
        scene.setOnKeyReleased ( input::keyReleased );

        PointsDisplay pointsDisplay = new PointsDisplay();
        root.getChildren().add(pointsDisplay);

        TimeDisplay timeDisplay = new TimeDisplay(Constants.WINDOW_WIDTH);
        root.getChildren().add(timeDisplay);

        gridrunner.powerup.HeartSpawner heartSpawner = new gridrunner.powerup.HeartSpawner(root, Constants.MAP, Constants.TILE_SIZE);
        gridrunner.powerup.ShieldSpawner shieldSpawner = new gridrunner.powerup.ShieldSpawner(root, Constants.MAP, Constants.TILE_SIZE);

        AnimationTimer timer = new AnimationTimer ( ) {
            private double last;
            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }

                double dt = ( now - this.last ) / 10e8;
                this.last = now;

                // Game over provera
                if (!player.isAlive()) {
                    this.stop();
                    root.getChildren().add(new GameOverlay(
                            "Game Over",
                            Color.web("#ee2244"),
                            Constants.WINDOW_WIDTH,
                            Constants.WINDOW_HEIGHT
                    ));
                    return;
                }

                // Win provera
                if (player.touches(level.getGoal())) {
                    this.stop();
                    root.getChildren().add(new GameOverlay(
                            "You Win!",
                            Color.web("#44dd88"),
                            Constants.WINDOW_WIDTH,
                            Constants.WINDOW_HEIGHT
                    ));
                    return;
                }

                // Provera da li je igrac vec na cilju
                if (player.touches(level.getGoal())) return;

                player.update ( dt, Constants.PLAYER_SPEED, input, level.getWalls(), level.getBlinkingWalls());

                // Provera sudara sa neprijateljima
                for (Enemy enemy : level.getEnemies()) {
                    enemy.update(dt, level.getWalls());
                    if (enemy.overlapsPlayer(player) && !player.isShielded()) {
                        player.resetPosition();
                        player.loseLife();
                    }
                }

                // Provera sudara sa spinnerima
                for (Spinner spinner : level.getSpinners()) {
                    spinner.update(dt);
                    if (spinner.overlapsPlayer(player) && !player.isShielded()) {
                        player.resetPosition();
                        player.loseLife();
                    }
                }

                // Coins
                for (Coin coin : level.getCoins()) {
                    if (coin.isCollected()) {
                        continue;
                    }

                    if (coin.overlapsPlayer(player)) {
                        coin.collect();
                        player.addGamePoints(1);
                        pointsDisplay.update(player.getGamePoints());
                    }
                }

                double multiplier = 1.0;
                //Provera da li udara SpeedBoost
                for (SpeedBoost boost : level.getSpeedBoosters()) {
                    if (boost.overlapsPlayer(player)) {
                        multiplier = 1.5;
                        break;
                    }
                }
                //Provera da li udara SlowBoost
                for (SlowBoost boost : level.getSlowBoosters()) {
                    if (boost.overlapsPlayer(player)) {
                        multiplier = 0.5;
                        break;
                    }
                }

                player.setTileSpeed(multiplier);

                // Heart Spawner
                heartSpawner.update(dt, player);

                // Shield Spawner
                shieldSpawner.update(dt, player);

                // Timer
                timeDisplay.update(dt);

                hearts.update(player.getLives());
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