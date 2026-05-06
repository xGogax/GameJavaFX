package gridrunner;

import javafx.scene.paint.Color;

public class Constants {
    public static final String[] MAP = {
            "####################",
            "#S........B........#",
            "#.........B........#",
            "#...########.......#",
            "#...#..............#",
            "#...#..............#",
            "#...########.......#",
            "#..................#",
            "#..................#",
            "#.......########...#",
            "#.......#..........#",
            "#.......#..........#",
            "#.......########...#",
            "#................G.#",
            "####################"
    };

    public static final int TILE_SIZE = 40;

    public static final int WINDOW_WIDTH  = Constants.MAP[0].length ( ) * Constants.TILE_SIZE;
    public static final int WINDOW_HEIGHT = Constants.MAP.length * Constants.TILE_SIZE;

    public static final Color BACKGROUND_COLOR    = Color.web ( "#c8c8c8" );
    public static final Color START_COLOR         = Color.web ( "#88c8ff" );
    public static final Color GOAL_COLOR          = Color.web ( "#44dd88" );
    public static final Color WALL_FILL_COLOR     = Color.web ( "#334466" );
    public static final Color WALL_STROKE_COLOR   = Color.web ( "#223355" );
    public static final Color PLAYER_FILL_COLOR   = Color.web ( "#2255cc" );
    public static final Color PLAYER_STROKE_COLOR = Color.web ( "#1133aa" );

    // Blinking wall settings
    public static final Color  BLINK_WALL_FILL_COLOR   = Color.web("#aa3333");
    public static final Color  BLINK_WALL_STROKE_COLOR = Color.web("#881111");
    public static final double BLINK_VISIBLE_SECONDS   = 2.5;
    public static final double BLINK_HIDDEN_SECONDS    = 1.5;

    public static final double PLAYER_RADIUS = Constants.TILE_SIZE * 0.75 / 2;
    public static final double PLAYER_SPEED  = 180; // pixels per second
}
