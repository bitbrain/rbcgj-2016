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

    private Assets.Textures textureId;

    public DirectionalSpriteRenderer(Assets.Textures textureId) {
        this.textureId = textureId;
    }

    @Override
    public void init() {
        Texture texture = AssetManager.getTexture(textureId);
        sprites = new Sprite[4];
        sprites[Direction.DOWN] = new Sprite(new TextureRegion(texture, 0, 0, 16, 16));
        sprites[Direction.UP] = new Sprite(new TextureRegion(texture, 0, 16, 16, 16));
        sprites[Direction.RIGHT] = new Sprite(new TextureRegion(texture, 0, 32, 16, 16));
        sprites[Direction.LEFT] = new Sprite(new TextureRegion(texture, 0, 48, 16, 16));
    }

    @Override
    public void render(GameObject object, Batch batch, float delta) {
        Sprite sprite = sprites[object.getDirection()];
        sprite.setPosition(object.getLeft() + object.getOffset().x, object.getTop() + object.getOffset().y);
        sprite.setSize(object.getWidth(), object.getHeight());
        sprite.setColor(object.getColor());
        sprite.setScale(object.getScale().x, object.getScale().y);
        sprite.draw(batch, 1f);
    }
}
