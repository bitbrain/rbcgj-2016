package tv.rocketbeans.rbcgj.assets;

public final class Assets {

    public enum Fonts {

        MEDIUM("fonts/medium.fnt"),
        SMALL("fonts/small.fnt"),
        STORY("fonts/story.fnt");

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
        BACKGROUND("textures/background.png"),
        ALMOND_DEAD("textures/almond-dead.png"),
        CASHEW_DEAD("textures/cashew-dead.png"),
        BRAZILNUT_DEAD("textures/brazilnut-dead.png"),
        RUISIN_DEAD("textures/ruisin-dead.png"),
        CRUMB("textures/crumb.png");

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
        LEVEL2("maps/level2.tmx"),
        BOSS("maps/boss.tmx");

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
        PAUSE_SCREEN("music/Pause_Screen.ogg"),
        STORY_SCREEN("music/story_screen.ogg");

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
        PORTAL("sound/portal.ogg"),
        EDDY_BLA_1("sound/eddy_bla_1.ogg"),
        EDDY_BLA_2("sound/eddy_bla_2.ogg"),
        EDDY_BLA_3("sound/eddy_bla_3.ogg"),
        EDDY_BLA_4("sound/eddy_bla_4.ogg"),
        EDDY_BLA_5("sound/eddy_bla_5.ogg"),
        EDDY_BLA_6("sound/eddy_bla_6.ogg"),
        COLLECT_NUT("sound/collect_nut.ogg");

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