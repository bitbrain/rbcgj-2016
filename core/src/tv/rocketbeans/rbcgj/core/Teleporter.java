package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.graphics.FX;
import tv.rocketbeans.rbcgj.screens.AbstractScreen;
import tv.rocketbeans.rbcgj.screens.GameOverScreen;

public class Teleporter implements MapActionHandler.MapActionListener {

    private LevelManager levelManager;

    private AbstractScreen screen;

    private NutGame game;

    public Teleporter(LevelManager levelManager, AbstractScreen screen, NutGame game) {
        this.levelManager = levelManager;
        this.screen = screen;
        this.game = game;
    }

    @Override
    public void onObjectEnter(GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        // Portal found!
        if (properties != null && properties.get("portal") != null) {
            String portalId = (String)properties.get("portal");
            MapObject portal = api.getPortalById(portalId);
            if (portal != null) {
                float x = (Float)portal.getProperties().get("x");
                float y = (Float)portal.getProperties().get("y");
                x = (float) (Math.floor(x / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE);
                y = (float) (Math.floor(y / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE);
                object.setPosition(x , y);
                FX.getInstance().fadeIn(0.5f);
                AssetManager.getSound(Assets.Sounds.PORTAL).play();
            } else if (portalId.contains("@level/")) {
                try {
                    portalId = portalId.replace("@level/", "");
                    Levels levels = Levels.valueOf(portalId.toUpperCase());
                    AssetManager.getSound(Assets.Sounds.PORTAL).play();
                    levelManager.loadLevel(levels, object);
                } catch (IllegalArgumentException e) {
                    // noOp
                    System.out.println("No level with id " + portalId);
                }
            } else if (portalId.contains("@gameover")) {
                screen.setScreen(new GameOverScreen(game));
            } else {
                System.out.println("No portal with id " + portalId);
            }
        }
    }
}
