package tv.rocketbeans.rbcgj.assets;

public final class Assets {

    public enum Fonts {

        MEDIUM("fonts/medium.fnt");

        private String path;

        Fonts(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains texture definitions
     */
    public enum Textures {

        EDDY("textures/nut-eddy.png"),
        SHADOW("textures/shadow.png"),
        RBCGJ("textures/rbcgj.png"),
        LOGO("textures/logo.png"),
        BUTTON_9("textures/button.9.png"),
        BACKGROUND("textures/background.png");

        private String path;

        Textures(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains map definitions
     */
    public enum Maps {
        DEFAULT("maps/default.tmx"),
        LEVEL1("maps/level1.tmx"),
        LEVEL2("maps/level2.tmx");

        private String path;

        Maps(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains music definitions
     */
    public enum Musics {

        LEVEL_1("music/Level_1.ogg"),
        LEVEL_2("music/Level_2.ogg"),
        LEVEL_3("music/Level_3.ogg"),
        LEVEL_4("music/Level_4.ogg"),
        TITLE_SCREEN("music/Title_Screen.ogg"),
        PAUSE_SCREEN("music/Pause_Screen.ogg");

        private String path;

        Musics(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains sound definitions
     */
    public enum Sounds {

        STEP_1("sound/steps_1.ogg"),
        STEP_2("sound/steps_2.ogg"),
        STEP_3("sound/steps_3.ogg"),
        MENU_CHOOSE("sound/menu_choose.ogg"),
        START_GAME("sound/start_new_game.ogg"),
        PORTAL("sound/portal.ogg");

        private String path;

        Sounds(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains particle effect definitions
     */
    public enum ParticleEffects {
        TEST("");

        private String path;

        ParticleEffects(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}