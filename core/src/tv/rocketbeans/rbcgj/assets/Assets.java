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
        SHADOW("textures/shadow.png");

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
        DEFAULT("maps/default.tmx");

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

        TEST("");

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