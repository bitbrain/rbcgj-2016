package tv.rocketbeans.rbcgj.assets;

public final class Assets {

    public enum Fonts {

        TEST("");

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

        TEST("");

        private String path;

        Textures(String path) {
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

        TEST("");

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