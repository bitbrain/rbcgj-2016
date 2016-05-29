package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.ui.Styles;
import tv.rocketbeans.rbcgj.ui.Tooltip;
import tv.rocketbeans.rbcgj.util.Colors;

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
                playerManager.addCollectible(GameObjectType.CRUMB);
                api.removeObjectAt(indexX, indexY);
                world.remove(gameObject);
                AssetManager.getSound(Assets.Sounds.COLLECT_NUT).play();
                Tooltip.getInstance().create(object.getLeft() + object.getWidth() * 2f, object.getTop() + object.getHeight() * 3f, Styles.STORY, "nom..", Colors.INFO, null);
            }
        }
    }
}
