package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.graphics.FX;

public class Teleporter implements MapActionHandler.MapActionListener {

    private LevelManager levelManager;

    public Teleporter(LevelManager levelManager) {
        this.levelManager = levelManager;
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
            } else if (portalId.contains("@level/")) {
                try {
                    portalId = portalId.replace("@level/", "");
                    Levels levels = Levels.valueOf(portalId.toUpperCase());
                    levelManager.loadLevel(levels, object);
                } catch (IllegalArgumentException e) {
                    // noOp
                }
            }
        }
    }
}
