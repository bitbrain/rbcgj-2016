package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.core.tmx.Tmx;

public class CollisionDetector {

    private boolean[][] collisions;

    private int width, height;

    public CollisionDetector() {
    }

    public void updateCollisions(TiledMap map) {
        MapProperties properties = map.getProperties();
        width = (Integer) properties.get(Tmx.WIDTH);
        height = (Integer) properties.get(Tmx.HEIGHT);
        if (collisions != null) {
            collisions = null;
        }
        collisions = new boolean[width][height];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                collisions[x][y] = getCollision(map, x, y);
            }
        }
    }

    public boolean isCollision(int indexX, int indexY) {
        if (isValidIndex(indexX, indexY)) {
            return collisions[indexX][indexY];
        } else {
            // Out of bounds should be a collision as well
            return true;
        }
    }

    public boolean isCollision(float x, float y) {
        int indexX = (int)Math.floor(x / GameConfig.CELL_SCALE);
        int indexY = (int)Math.floor(y / GameConfig.CELL_SCALE);
        return isCollision(indexX, indexY);
    }

    private boolean isValidIndex(int indexX, int indexY) {
        return indexX >= 0 && indexY >= 0 && indexX < width && indexY < height;
    }

    private boolean getCollision(TiledMap map, int x, int y) {
        for (MapLayer layer : map.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    MapProperties properties = cell.getTile().getProperties();
                    boolean collision = Boolean.valueOf(properties.get(Tmx.COLLISION, "true", String.class));
                    if (collision) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
