package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import tv.rocketbeans.rbcgj.core.GameObjectType;
import tv.rocketbeans.rbcgj.core.PlayerManager;

public class CrumbUI extends Actor {

    private PlayerManager playerManager;

    private List<CollectibleWidget> widgets = new ArrayList<CollectibleWidget>();

    public CrumbUI(PlayerManager playerManager) {
        this.playerManager = playerManager;
        addCollectibleWidget(GameObjectType.CASHEW);
        addCollectibleWidget(GameObjectType.ALMOND);
        addCollectibleWidget(GameObjectType.BRAZIL);
        addCollectibleWidget(GameObjectType.RUISIN);
        addCollectibleWidget(GameObjectType.CRUMB).setDefaultAlpha(0.5f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < widgets.size(); ++i) {
            CollectibleWidget w = widgets.get(i);
            w.setPosition(getX(), getY() + w.getHeight() / 1.7f * i);
            w.draw(batch, parentAlpha);
        }

    }

    private CollectibleWidget addCollectibleWidget(int type) {
        CollectibleWidget widget = new CollectibleWidget(type);
        widget.setHeight(64f);
        playerManager.addListener(widget);
        widgets.add(widget);
        return widget;
    }
}
