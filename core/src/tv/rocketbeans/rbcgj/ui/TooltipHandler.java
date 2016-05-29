package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.MissingResourceException;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.audio.SoundPoolLooper;
import tv.rocketbeans.rbcgj.core.Direction;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.MapActionHandler;
import tv.rocketbeans.rbcgj.core.tmx.Tmx;
import tv.rocketbeans.rbcgj.i18n.Bundle;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.util.Colors;

public class TooltipHandler implements MapActionHandler.MapActionListener {

    private SoundPoolLooper looper;

    static {
        Tween.registerAccessor(Actor.class, new ActorTween());
    }

    public TooltipHandler() {
        looper = new SoundPoolLooper(Assets.Sounds.EDDY_BLA_1, Assets.Sounds.EDDY_BLA_2, Assets.Sounds.EDDY_BLA_3, Assets.Sounds.EDDY_BLA_4, Assets.Sounds.EDDY_BLA_5, Assets.Sounds.EDDY_BLA_6);
        looper.setInterval(0.4f);
        looper.update(2f);
    }
    
    @Override
    public void onObjectEnter(final GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        if (properties != null && (properties.containsKey(Tmx.TEXT_KEY) || properties.containsKey(Tmx.TEXT))) {
            looper.play();
            looper.update(2f);
            String text = extractText(properties, Tmx.TEXT_KEY, Tmx.TEXT);
            Tooltip.getInstance().create(object.getLeft(), object.getTop() + object.getHeight() * 3f, Styles.STORY, text);
        } else {
            MapObject o = api.getObjectInfront(object);
            if (o != null) {
                final MapProperties p = o.getProperties();
                if (p.containsKey(Tmx.INFO_KEY) || p.containsKey(Tmx.INFO)) {
                    looper.play();
                    looper.update(2f);
                    String text = extractText(p, Tmx.INFO_KEY, Tmx.INFO);
                    Tooltip.getInstance().create(object.getLeft(), object.getTop() + object.getHeight() * 3f, Styles.STORY, text, Colors.INFO, new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            String answer = extractText(p, Tmx.ANSWER_KEY, Tmx.ANSWER);
                            if (answer != null) {
                                float x = (Float) p.get("x");
                                float y = (Float) p.get("y");
                                Tooltip.getInstance().create(x, y + object.getHeight() * 3, Styles.STORY, answer, Colors.ANSWER, null);
                            }
                        }
                    });
                } else if (p.get(Tmx.ANSWER) != null) {
                    String answer = extractText(p, Tmx.ANSWER_KEY, Tmx.ANSWER);
                    if (answer != null) {
                        float x = (Float) p.get("x");
                        float y = (Float) p.get("y");
                        Tooltip.getInstance().create(x, y + object.getHeight() * 3, Styles.STORY, answer, Colors.ANSWER, null);
                    }
                }
            }
        }
    }

    private String extractText(MapProperties p, String key, String text) {
        if (p.containsKey(key) || p.containsKey(text)) {
            if (p.containsKey(key)) {
                try {
                    return Bundle.general.get((String) p.get(key));
                } catch (MissingResourceException e) {
                    if (p.containsKey(text)) {
                        return (String) p.get(text);
                    } else {
                        // Nothing to do here. Flee, you fools!
                        return null;
                    }
                }
            } else {
               return (String) p.get(text);
            }
        } else {
            return null;
        }
    }
}
