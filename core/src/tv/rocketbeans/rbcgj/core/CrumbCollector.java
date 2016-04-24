package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import tv.rocketbeans.rbcgj.GameConfig;

public class CrumbCollector implements MapActionHandler.MapActionListener {

    private LevelManager levelManager;

    private PlayerManager playerManager;

    private GameWorld world;

    public CrumbCollector(LevelManager levelManager, GameWorld world, PlayerManager playerManager) {
        this.levelManager = levelManager;
        this.playerManager = playerManager;
        this.world = world;
    }

    @Override
    public void onObjectEnter(GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        int indexX = (int)Math.floor(object.getLeft() / GameConfig.CELL_SCALE);
        int indexY = (int)Math.floor(object.getTop() / GameConfig.CELL_SCALE);
        MapObject o = api.getObjectAt(indexX, indexY);
        if (o != null) {
            GameObject gameObject = levelManager.getGameObjectByMapObject(o);
            if (gameObject != null) {
                playerManager.addCrumb();
                api.removeObjectAt(indexX, indexY);
                world.remove(gameObject);
            }
        }
    }
}
