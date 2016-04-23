package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.maps.MapProperties;

import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.MapActionHandler;

public class TooltipHandler implements MapActionHandler.MapActionListener {
    
    @Override
    public void onObjectEnter(GameObject object, MapProperties properties, MapActionHandler.MapAPI api) {
        if (properties != null && properties.containsKey("text")) {
            String text = (String)properties.get("text");
            Tooltip.getInstance().create(object.getLeft(), object.getTop() + object.getHeight() * 3f, Styles.STORY, text);
        }
    }
}
