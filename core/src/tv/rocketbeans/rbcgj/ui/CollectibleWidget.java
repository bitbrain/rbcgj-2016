package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;

import tv.rocketbeans.rbcgj.core.PlayerManager;

/**
 * Widget to display a certain collectible identified by GameObject id
 */
public class CollectibleWidget extends Actor implements PlayerManager.PlayerListener {

    private final int type;

    public CollectibleWidget(int gameObjectType) {
        type = gameObjectType;
    }

    @Override
    public void onAddCollectible(PlayerManager.Collectible collectible) {

    }
}
