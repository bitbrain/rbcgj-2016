package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import java.util.logging.Level;

public class QuestHandler implements MapActionHandler.MapActionListener {

    private PlayerManager playerManager;

    private LevelManager levelManager;

    public QuestHandler(PlayerManager playerManager, LevelManager levelManager) {
        this.playerManager = playerManager;
        this.levelManager = levelManager;
    }
    @Override
    public void onObjectEnter(GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        MapObject mapObject = api.getObjectInfront(object);
        if (mapObject != null) {
            GameObject infrontObject = levelManager.getGameObjectByMapObject(mapObject);
            if (infrontObject != null && infrontObject.getType() != GameObjectType.CRUMB) {
                playerManager.addCollectible(infrontObject.getType());
            }
        }

    }
}
