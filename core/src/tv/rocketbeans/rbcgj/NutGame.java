package tv.rocketbeans.rbcgj;

import com.badlogic.gdx.Game;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.screens.IngameScreen;
import tv.rocketbeans.rbcgj.screens.LoadingScreen;
import tv.rocketbeans.rbcgj.screens.MenuScreen;
import tv.rocketbeans.rbcgj.screens.ScreenHandler;
import tv.rocketbeans.rbcgj.screens.StoryScreen;
import tv.rocketbeans.rbcgj.ui.Styles;

public class NutGame extends Game {

    @Override
    public void create() {
        AssetManager.init();
        Styles.init();
        ScreenHandler.setFull();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
