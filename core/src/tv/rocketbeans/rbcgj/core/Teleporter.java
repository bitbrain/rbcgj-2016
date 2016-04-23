package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import tv.rocketbeans.rbcgj.GameConfig;

public class Teleporter implements MapActionHandler.MapActionListener {

    private LevelManager levelManager;

    public Teleporter(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    @Override
    public void onObjectEnter(GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        // Portal found!
        if (properties.get("portal-id") != null) {
            String portalId = (String)properties.get("portal-id");
            MapObject portal = api.getPortalById(portalId);
            if (portal != null) {
                float x = (Float)properties.get("x");
                float y = (Float)properties.get("y");
                x = (float) (Math.floor(x / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE);
                y = (float) (Math.floor(y / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE);
                object.setPosition(x , y);
            } else {
                try {
                    Levels levels = Levels.valueOf(portalId.toUpperCase());
                    levelManager.loadLevel(levels, object);
                } catch (IllegalArgumentException e) {
                    // noOp
                }
            }
        }
    }
}
