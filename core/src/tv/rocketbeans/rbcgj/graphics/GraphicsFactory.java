package tv.rocketbeans.rbcgj.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public final class GraphicsFactory {

    /**
     * Creates a new texture of the given color and size
     *
     * @param width width of the texture
     * @param height height of the texture
     * @param color color of the texture
     * @return new texture object
     */
    public static Texture createTexture(int width, int height, Color color) {
        Pixmap map = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        map.setColor(color);
        map.fill();
        Texture texture = new Texture(map);
        map.dispose();
        return texture;
    }
}