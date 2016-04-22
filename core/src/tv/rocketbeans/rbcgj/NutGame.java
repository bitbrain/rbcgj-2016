package tv.rocketbeans.rbcgj;

import com.badlogic.gdx.Game;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.screens.IngameScreen;

public class NutGame extends Game {

    @Override
    public void create() {
        AssetManager.init();
        setScreen(new IngameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
