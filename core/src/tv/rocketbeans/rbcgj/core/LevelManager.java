package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

import box2dLight.PointLight;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.tmx.TmxObjectType;
import tv.rocketbeans.rbcgj.graphics.LightingManager;

public class LevelManager {

    private OrthogonalTiledMapRenderer mapRenderer;

    private MapLayers layers;

    private LightingManager lightingManager;

    private List<PointLight> staticLights;

    public LevelManager(LightingManager lightingManager) {
        this.lightingManager = lightingManager;
        staticLights = new ArrayList<PointLight>();
    }

    public void loadMap(Assets.Maps maps) {
        for (PointLight light : staticLights) {
            light.remove(true);
        }
        staticLights.clear();
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
        TiledMap map = AssetManager.getMap(maps);
        layers = map.getLayers();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        updateLights();
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

    private void updateLights() {
        TiledMap map = mapRenderer.getMap();
        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                MapProperties properties = object.getProperties();
                if (properties.get("type").equals(TmxObjectType.LIGHT)) {
                    Float r = Float.valueOf((String)properties.get("r"));
                    Float g = Float.valueOf((String)properties.get("g"));
                    Float b = Float.valueOf((String)properties.get("b"));
                    Float a = Float.valueOf((String)properties.get("a"));
                    Float radius = Float.valueOf((String)properties.get("radius"));
                    Float x = (Float)properties.get("x") + GameConfig.CELL_SCALE / 2f;
                    Float y = (Float)properties.get("y") + GameConfig.CELL_SCALE / 2f;
                    PointLight light = lightingManager.addPointLight(radius, new Color(r, g, b, a), x, y);
                    staticLights.add(light);
                }
            }
        }
    }
}
