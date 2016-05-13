package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.Tween;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.PlayerManager;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;
import tv.rocketbeans.rbcgj.ui.Styles;

public class CrumbUI extends Actor implements PlayerManager.PlayerListener {

    private static final float DEFAULT_ALPHA = 0.4f;

    private PlayerManager playerManager;

    private Sprite sprite;

    private Label label;

    public CrumbUI(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.playerManager.addListener(this);
        setHeight(80f);
        sprite = new Sprite(AssetManager.getTexture(Assets.Textures.CRUMB));
        label = new Label("0", Styles.PLAYER_INFO);
        getColor().a = DEFAULT_ALPHA;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setSize(getHeight(), getHeight());
        sprite.setAlpha(getColor().a);
        sprite.draw(batch, parentAlpha);
        label.setPosition(getX() + getHeight(), getY() + 25f);
        label.getColor().a = getColor().a;
        label.draw(batch, parentAlpha);
    }

    @Override
    public void onAddCollectible(PlayerManager.Collectible collectible) {
        label.setText(String.valueOf(collectible.getCurrentAmount()));
        getColor().a = 1f;
        Tween.to(this, ActorTween.ALPHA, 1f).target(DEFAULT_ALPHA).start(SharedTweenManager.getInstance());
    }
}
