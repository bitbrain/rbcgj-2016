package tv.rocketbeans.rbcgj.graphics;

import com.badlogic.gdx.graphics.Texture;

import tv.rocketbeans.rbcgj.assets.Assets;

import static tv.rocketbeans.rbcgj.core.GameObjectType.*;
import static tv.rocketbeans.rbcgj.assets.AssetManager.*;

/**
 * Retrieves textures for certain game object types
 */
public final class TextureResolver {

    public static Texture resolveTexture(int type, boolean inactive) {
        switch (type) {
            case CRUMB:
                return getTexture(Assets.Textures.CRUMB);
            case PEANUT:
                return getTexture(Assets.Textures.PEANUT);
            case RUISIN:
                return getTexture(inactive ? Assets.Textures.RUISIN_DEAD : Assets.Textures.RUISIN);
            case ALMOND:
                return getTexture(inactive ? Assets.Textures.ALMOND_DEAD : Assets.Textures.ALMOND);
            case BRAZIL:
                return getTexture(inactive ? Assets.Textures.BRAZIL_DEAD : Assets.Textures.BRAZIL);
            case CASHEW:
                return getTexture(inactive ? Assets.Textures.CASHEW_DEAD : Assets.Textures.CASHEW);
            default:
                return null;
        }
    }
}
