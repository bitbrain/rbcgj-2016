package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import tv.rocketbeans.rbcgj.core.PlayerManager;
import tv.rocketbeans.rbcgj.graphics.TextureResolver;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;
import tv.rocketbeans.rbcgj.util.Colors;

/**
 * Widget to display a certain collectible identified by GameObject id
 */
public class CollectibleWidget extends Actor implements PlayerManager.PlayerListener {

    private static final float DEFAULT_ALPHA = 0.35f;

    private final int type;

    private Sprite icon;

    private Label text;

    private float defaultAlpha = DEFAULT_ALPHA;

    public CollectibleWidget(int gameObjectType) {
        type = gameObjectType;
        icon = new Sprite(TextureResolver.resolveTexture(gameObjectType, false));
        text = new Label("0", Styles.STORY);
        getColor().a = defaultAlpha;
    }

    public void setDefaultAlpha(float alpha) {
        defaultAlpha = alpha;
        TweenManager mgr = SharedTweenManager.getInstance();
        mgr.killTarget(this);
        getColor().a = defaultAlpha;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        icon.setPosition(getX(), getY());
        icon.setSize(getHeight(), getHeight());
        icon.setColor(getColor());
        icon.draw(batch, parentAlpha * getColor().a);
        text.setColor(getColor());
        text.setPosition(getX() + icon.getWidth(), getY() + icon.getHeight() / 2f - text.getHeight() / 2f - 3f);
        text.draw(batch, parentAlpha * getColor().a);
    }

    @Override
    public void onAddCollectible(PlayerManager.Collectible collectible) {
        if (collectible.getType() == type) {
            if (collectible.getMaxAmount() > 0) {
                text.setText(collectible.getCurrentAmount() + "/" + collectible.getMaxAmount());
                if (collectible.getCurrentAmount() == collectible.getMaxAmount()) {
                    setColor(Colors.SUCCESS);
                }
            } else {
                text.setText(String.valueOf(collectible.getCurrentAmount()));
            }
            TweenManager mgr = SharedTweenManager.getInstance();
            mgr.killTarget(this);
            getColor().a = 1f;
            if (collectible.getCurrentAmount() != collectible.getMaxAmount()) {
                Tween.to(this, ActorTween.ALPHA, .7f).target(defaultAlpha).start(mgr);
            }
        }
    }
}
