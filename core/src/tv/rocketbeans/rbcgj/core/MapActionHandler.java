package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.core.tmx.Tmx;

public class MapActionHandler {

    private int width, height;

    public interface MapActionListener {
        void onObjectEnter(GameObject object, MapProperties properties, MapAPI api);
    }

    public interface MapAPI {
        MapObject getPortalById(String id);
        MapObject getObjectAt(int indexX, int indexY);
        void removeObjectAt(int indexX, int indexY);
        MapObject getObjectInfront(GameObject object);
    }

    private MapObject[][] objects;

    private Set<MapActionListener> listeners;

    private Map<String, MapObject> portals;

    private MapAPI api = new MapAPI() {
        @Override
        public MapObject getPortalById(String id) {
            return portals.get(id);
        }

        @Override
        public MapObject getObjectAt(int indexX, int indexY) {
            if (indexX >= 0 && indexX < width && indexY >= 0 && indexY < height) {
                return objects[indexX][indexY];
            } else {
                return null;
            }
        }

        @Override
        public void removeObjectAt(int indexX, int indexY) {
            if (indexX >= 0 && indexX < width && indexY >= 0 && indexY < height) {
                objects[indexX][indexY] = null;
            }
        }

        @Override
        public MapObject getObjectInfront(GameObject other) {
            Vector2 lookAt = directionToVector(other.getDirection());
            int indexX = (int)Math.floor(other.getLeft() / GameConfig.CELL_SCALE) + (int)lookAt.x;
            int indexY = (int)Math.floor(other.getTop() / GameConfig.CELL_SCALE) + (int)lookAt.y;
            return getObjectAt(indexX, indexY);
        }
    };

    public MapActionHandler() {
        listeners = new HashSet<MapActionListener>();
        portals = new HashMap<String, MapObject>();
    }

    public void enter(GameObject object, float x, float y) {
        int indexX = (int)Math.floor(x / GameConfig.CELL_SCALE);
        int indexY = (int)Math.floor(y / GameConfig.CELL_SCALE);
        MapObject o = null;
        if (indexX >=0 && indexY >=0 && indexX < width && indexY < height) {
            o = objects[indexX][indexY];
        }
        for (MapActionListener l : listeners) {
            l.onObjectEnter(object, o != null ? o.getProperties() : null, api);
        }
    }

    public void addListener(MapActionListener listener) {
        this.listeners.add(listener);
    }

    public void load(TiledMap map) {
        MapProperties properties = map.getProperties();
        width = (Integer) properties.get(Tmx.WIDTH);
        height = (Integer) properties.get(Tmx.HEIGHT);
        objects = new MapObject[width][height];
        portals.clear();

        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                if (object.getProperties().get("portal-id") != null) {
                    portals.put((String) object.getProperties().get("portal-id"), object);
                }
                MapProperties objectProperties = object.getProperties();
                float x = (Float) objectProperties.get("x");
                float y = (Float) objectProperties.get("y");
                int indexX = (int) Math.floor(x / GameConfig.CELL_SCALE);
                int indexY = (int) Math.floor(y / GameConfig.CELL_SCALE);
                if (indexX >= 0 && indexY >= 0 && indexX < width && indexY < height) {
                    objects[indexX][indexY] = object;
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
