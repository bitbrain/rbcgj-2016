package tv.rocketbeans.rbcgj.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.Direction;
import tv.rocketbeans.rbcgj.core.GameObject;

public class DirectionalSpriteRenderer implements RenderManager.Renderer {

    protected Sprite[] sprites;

    private Texture texture;

    private Sprite shadow;

    public DirectionalSpriteRenderer(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void init() {
        sprites = new Sprite[4];
        sprites[Direction.DOWN] = new Sprite(new TextureRegion(texture, 0, 0, 16, 16));
        sprites[Direction.UP] = new Sprite(new TextureRegion(texture, 0, 16, 16, 16));
        sprites[Direction.RIGHT] = new Sprite(new TextureRegion(texture, 0, 32, 16, 16));
        sprites[Direction.LEFT] = new Sprite(new TextureRegion(texture, 0, 48, 16, 16));
        shadow = new Sprite(AssetManager.getTexture(Assets.Textures.SHADOW));
    }

    @Override
    public void render(GameObject object, Batch batch, float delta) {
        shadow.setPosition(object.getLeft() + object.getOffset().x, object.getTop() + object.getOffset().y - 2);
        shadow.setAlpha(0.2f);
        shadow.setSize(object.getWidth(), object.getHeight() / 2f);
        shadow.draw(batch, 1f);
        Sprite sprite = sprites[object.getDirection()];
        sprite.setPosition(object.getLeft() + object.getOffset().x, object.getTop() + object.getOffset().y);
        sprite.setSize(object.getWidth(), object.getHeight());
        sprite.setColor(object.getColor());
        sprite.setScale(object.getScale().x, object.getScale().y);
        sprite.draw(batch, 1f);
    }
}
