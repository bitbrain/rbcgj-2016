package tv.rocketbeans.rbcgj.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.GameObject;

public class ShadowSpriteRenderer extends SpriteRenderer {

    private Sprite shadow;

    public ShadowSpriteRenderer(Assets.Textures textureId) {
        super(textureId);
        shadow = new Sprite(AssetManager.getTexture(Assets.Textures.SHADOW));
    }

    @Override
    public void render(GameObject object, Batch batch, float delta) {
        shadow.setPosition(object.getLeft() + object.getWidth() / 4f, object.getTop() + 6);
        shadow.setAlpha(0.2f);
        shadow.setSize(object.getWidth() / 2f, object.getHeight() / 2f);
        shadow.draw(batch, 1f);
        super.render(object, batch, delta);
    }
}
