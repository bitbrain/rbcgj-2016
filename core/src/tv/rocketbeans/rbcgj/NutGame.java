package tv.rocketbeans.rbcgj;

import com.badlogic.gdx.Game;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.screens.IngameScreen;
import tv.rocketbeans.rbcgj.screens.LoadingScreen;
import tv.rocketbeans.rbcgj.screens.ScreenHandler;

public class NutGame extends Game {

    @Override
    public void create() {
        AssetManager.init();
        ScreenHandler.setFull();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
