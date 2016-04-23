package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;

import tv.rocketbeans.rbcgj.GameConfig;

/**
 * Created by miguel on 23.04.16.
 */
public final class ScreenHandler {

    public static void setFull() {
        if (GameConfig.AUTO_FULLSCREEN) {
            Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

    }
}