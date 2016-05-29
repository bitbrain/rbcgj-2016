package tv.rocketbeans.rbcgj;

public interface GameConfig {

    String GAME_NAME = "The Legend of Studentenfutter";
    String GAME_VERSION = "1.3.2";

    // Scale of cells
    int CELL_SCALE = 32;

    // Time to take in order to
    // move to the next cell
    float MOVEMENT_TIME = 0.23f;

    // Auto fullscreen enabled
    boolean AUTO_FULLSCREEN = true;

    // Debug mode
    boolean DEV_MODE = false;
}
