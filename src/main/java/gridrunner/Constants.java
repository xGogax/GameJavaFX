package gridrunner;

import javafx.scene.paint.Color;

import java.util.Map;

public class Constants {
    public static void updateWindowSize() {
        WINDOW_WIDTH  = MAP[0].length() * TILE_SIZE;
        WINDOW_HEIGHT = MAP.length * TILE_SIZE;
    }

    public static String[] MAP;

    public static final int TILE_SIZE = 40;

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    public static final int MENU_WIDTH = 900;
    public static final int MENU_HEIGHT = 600;

    public static final Color BACKGROUND_COLOR    = Color.web ( "#c8c8c8" );
    public static final Color START_COLOR         = Color.web ( "#88c8ff" );
    public static final Color GOAL_COLOR          = Color.web ( "#44dd88" );
    public static final Color WALL_FILL_COLOR     = Color.web ( "#334466" );
    public static final Color WALL_STROKE_COLOR   = Color.web ( "#223355" );
    public static final Color PLAYER_FILL_COLOR   = Color.web ( "#2255cc" );
    public static final Color PLAYER_STROKE_COLOR = Color.web ( "#1133aa" );

    // Blinking wall settings
    public static final Color  BLINK_WALL_FILL_COLOR   = Color.web("#cc6600");
    public static final Color  BLINK_WALL_STROKE_COLOR = Color.web("#aa4400");
    public static final double BLINK_VISIBLE_SECONDS   = 2.5;
    public static final double BLINK_HIDDEN_SECONDS    = 1.5;

    // Enemy settings
    public static final Color  ENEMY_FILL_COLOR   = Color.web("#cc4400");
    public static final Color  ENEMY_STROKE_COLOR = Color.web("#aa2200");
    public static final double ENEMY_SPEED        = 120;
    public static final double ENEMY_SIZE         = Constants.TILE_SIZE * 0.65;

    // Spinner settings
    public static final Color  SPINNER_FILL_COLOR     = Color.web("#aa44cc");
    public static final Color  SPINNER_STROKE_COLOR   = Color.web("#882299");
    public static final double SPINNER_ROTATION_SPEED = 120; // stepeni po sekundi

    // Coin settings
    public static final Color COIN_FILL_COLOR   = Color.web("#f5c542");
    public static final Color COIN_STROKE_COLOR = Color.web("#d89b00");
    public static final double NUMBER_OF_COINS = 5;

    // Speed Booster settings
    public static final Color  SPEED_BOOSTER_FILL_COLOR   = Color.web("#44ccaa");
    public static final Color  SPEED_BOOSTER_STROKE_COLOR = Color.web("#229977");

    // Slow Booster settings
    public static final Color  SLOW_DOWN_FILL_COLOR   = Color.web("#cc4444");
    public static final Color  SLOW_DOWN_STROKE_COLOR = Color.web("#992222");

    // Turret settings
    public static final Color  TURRET_FILL_COLOR      = Color.web("#555555");
    public static final Color  TURRET_STROKE_COLOR     = Color.web("#333333");
    public static final Color  PROJECTILE_FILL_COLOR   = Color.web("#ff3333");
    public static final Color  PROJECTILE_STROKE_COLOR = Color.web("#aa0000");
    public static final double TURRET_FIRE_INTERVAL    = 2.5;  // sekundi izmedju hitaca
    public static final double PROJECTILE_SPEED        = 200;  // pixels per second

    public static final double PLAYER_RADIUS = Constants.TILE_SIZE * 0.75 / 2;
    public static final double PLAYER_SPEED  = 180; // pixels per second
}
