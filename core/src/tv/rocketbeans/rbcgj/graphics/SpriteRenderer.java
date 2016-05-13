package tv.rocketbeans.rbcgj.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.GameObject;

public class SpriteRenderer implements RenderManager.Renderer {

    protected Sprite sprite;

    private Texture texture;

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void init() {
        sprite = new Sprite(texture);
    }

    @Override
    public void render(GameObject object, Batch batch, float delta) {
        sprite.setPosition(object.getLeft() + object.getOffset().x, object.getTop() + object.getOffset().y);
        sprite.setSize(object.getWidth(), object.getHeight());
        sprite.setColor(object.getColor());
        sprite.setScale(object.getScale().x, object.getScale().y);
        sprite.draw(batch, 1f);
    }
}