package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.core.Direction;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.MapActionHandler;
import tv.rocketbeans.rbcgj.core.tmx.Tmx;
import tv.rocketbeans.rbcgj.util.Colors;

public class TooltipHandler implements MapActionHandler.MapActionListener {
    
    @Override
    public void onObjectEnter(final GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        if (properties != null && properties.containsKey("text")) {
            String text = (String)properties.get("text");
            Tooltip.getInstance().create(object.getLeft(), object.getTop() + object.getHeight() * 3f, Styles.STORY, text);
        } else {
            Vector2 lookAt = directionToVector(object.getDirection());
            int indexX = (int)Math.floor(object.getLeft() / GameConfig.CELL_SCALE) + (int)lookAt.x;
            int indexY = (int)Math.floor(object.getTop() / GameConfig.CELL_SCALE) + (int)lookAt.y;
            MapObject o = api.getObjectAt(indexX, indexY);
            if (o != null) {
                final MapProperties p = o.getProperties();
                if (p.get(Tmx.INFO) != null) {
                    String text = (String) p.get(Tmx.INFO);
                    Tooltip.getInstance().create(object.getLeft(), object.getTop() + object.getHeight() * 3f, Styles.STORY, text, Colors.INFO, new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            if (p.get(Tmx.ANSWER) != null) {
                                String answer = (String) p.get(Tmx.ANSWER);
                                float x = (Float)p.get("x");
                                float y = (Float)p.get("y");
                                Tooltip.getInstance().create(x, y + object.getHeight() * 3, Styles.STORY, answer, Colors.ANSWER, null);
                            }
                        }
                    });
                } else if (p.get(Tmx.ANSWER) != null) {
                    String answer = (String) p.get(Tmx.ANSWER);
                    float x = (Float)p.get("x");
                    float y = (Float)p.get("y");
                    Tooltip.getInstance().create(x, y + object.getHeight() * 3, Styles.STORY, answer, Colors.ANSWER, null);
                }
            }
        }
    }



    private Vector2 directionToVector(int direction) {
        Vector2 v = new Vector2();
        if (direction == Direction.LEFT) {
            v.set(-1, 0);
        } else if (direction == Direction.RIGHT) {
            v.set(1, 0);
        } else if (direction == Direction.UP) {
            v.set(0, 1);
        } else if (direction == Direction.DOWN) {
            v.set(0, -1);
        }
        return v;
    }

}
