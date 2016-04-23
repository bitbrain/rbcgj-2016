package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;

public class LevelManager {

    private OrthogonalTiledMapRenderer mapRenderer;

    private MapLayers layers;

    public LevelManager() {
    }

    public void loadMap(Assets.Maps maps) {
        TiledMap map = AssetManager.getMap(maps);
        layers = map.getLayers();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void renderForeground(OrthographicCamera camera) {
        if (mapRenderer != null) {
            mapRenderer.getBatch().begin();
            mapRenderer.setView(camera);
            mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(2));
            mapRenderer.getBatch().end();
        }
    }

    public void renderBackground(OrthographicCamera camera) {
        if (mapRenderer != null) {
            mapRenderer.setView(camera);
            mapRenderer.getBatch().begin();
            mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(0));
            mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(1));
            mapRenderer.getBatch().end();
        }
    }
}
