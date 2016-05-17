package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import box2dLight.PointLight;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.core.tmx.Tmx;
import tv.rocketbeans.rbcgj.graphics.FX;
import tv.rocketbeans.rbcgj.graphics.LightingManager;
import tv.rocketbeans.rbcgj.tweens.GameObjectTween;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;

public class LevelManager {

    static {
        Tween.registerAccessor(GameObject.class, new GameObjectTween());
    }

    private OrthogonalTiledMapRenderer mapRenderer;

    private MapLayers layers;

    private LightingManager lightingManager;

    private List<PointLight> staticLights;

    private CollisionDetector collisions;

    private Vector2 spawn = new Vector2();

    private Music music;

    private MapActionHandler actionHandler;

    private boolean initialized;

    private Set<GameObject> gameObjects;

    private GameWorld world;

    private Map<MapObject, GameObject> mapping;

    private int crumbCount = 0;

    private PlayerManager playerManager;

    private GameWorld.GameWorldListener gameWorldListener = new GameWorld.GameWorldListener() {
        @Override
        public void onRemoveObject(GameObject removal) {
            gameObjects.remove(removal);
            for (Map.Entry<MapObject, GameObject> entry : mapping.entrySet()) {
                if (entry.getValue().equals(removal)) {
                    mapping.remove(entry.getKey());
                    break;
                }
            }
        }
    };

    public LevelManager(LightingManager lightingManager, GameWorld world, MapActionHandler handler, CollisionDetector collisions, PlayerManager playerManager) {
        this.lightingManager = lightingManager;
        this.playerManager = playerManager;
        staticLights = new ArrayList<PointLight>();
        this.collisions = collisions;
        this.actionHandler = handler;
        this.world = world;
        this.world.setListener(gameWorldListener);
        mapping = new HashMap<MapObject, GameObject>();
        this.gameObjects = new HashSet<GameObject>();
    }

    public void loadLevel(Levels levels, GameObject player) {
        initialized = true;
        System.out.println("Load level!");
        for (PointLight light : staticLights) {
            light.remove(true);
        }
        world.remove(true, gameObjects.toArray(new GameObject[gameObjects.size()]));
        mapping.clear();
        gameObjects.clear();
        staticLights.clear();
        lightingManager.setAmbientLight(levels.getAmbientColor());
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
        TiledMap map = AssetManager.getMap(levels.getMaps());
        collisions.updateCollisions(map);
        layers = map.getLayers();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        updateObjects();
        actionHandler.load(map);
        player.setPosition(spawn.x, spawn.y);
        if (music != null) {
            music.setLooping(false);
            music.stop();
        }
        music = AssetManager.getMusic(levels.getMusics());
        music.setLooping(true);
        music.play();
        FX.getInstance().setFadeColor(Color.BLACK);
        FX.getInstance().fadeIn(2.5f);
    }

    public boolean isNowInitialized() {
        return initialized;
    }

    public void renderForeground(OrthographicCamera camera) {
        if (mapRenderer != null) {
            mapRenderer.getBatch().begin();
            mapRenderer.setView(camera);
            mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(1));
            mapRenderer.getBatch().end();
        }
        initialized = false;
    }

    public GameObject getGameObjectByMapObject(MapObject object) {
        if (mapping.containsKey(object)) {
            return mapping.get(object);
        } else {
            return null;
        }
    }

    public void renderBackground(OrthographicCamera camera) {
        if (mapRenderer != null) {
            mapRenderer.setView(camera);
            mapRenderer.getBatch().begin();
            mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(0));
            mapRenderer.getBatch().end();
        }
    }

    private void updateObjects() {
        TiledMap map = mapRenderer.getMap();
        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                MapProperties properties = object.getProperties();
                if (properties.get(Tmx.TYPE) == null) {
                    continue;
                }
                if (properties.get(Tmx.TYPE).equals(Tmx.LIGHT)) {
                    Float r = Float.valueOf((String)properties.get(Tmx.RED));
                    Float g = Float.valueOf((String)properties.get(Tmx.GREEN));
                    Float b = Float.valueOf((String)properties.get(Tmx.BLUE));
                    Float a = Float.valueOf((String)properties.get(Tmx.ALPHA));
                    Float radius = Float.valueOf((String)properties.get(Tmx.RADIUS));
                    Float x = (Float)properties.get(Tmx.X) + GameConfig.CELL_SCALE / 2f;
                    Float y = (Float)properties.get(Tmx.Y) + GameConfig.CELL_SCALE / 2f;
                    PointLight light = lightingManager.addPointLight(radius, new Color(r, g, b, a), x, y);
                    staticLights.add(light);
                } else if (properties.get(Tmx.TYPE).equals(Tmx.SPAWN)) {
                    float x = (Float)properties.get(Tmx.X);
                    float y = (Float)properties.get(Tmx.Y);
                    // Normalize spawn position
                    int xIndex = (int)Math.floor(x / GameConfig.CELL_SCALE);
                    int yIndex = (int)Math.floor(y / GameConfig.CELL_SCALE);
                    spawn.x = xIndex * GameConfig.CELL_SCALE;
                    spawn.y = yIndex * GameConfig.CELL_SCALE;
                } else if (properties.get(Tmx.TYPE).equals(Tmx.NPC)) {
                    float x = (Float)properties.get(Tmx.X);
                    float y = (Float)properties.get(Tmx.Y);
                    String type = (String)properties.get(Tmx.NUT);
                    // Normalize spawn position
                    x = (int)Math.floor(x / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE;
                    y = (int)Math.floor(y / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE;
                    GameObject npc = world.addObject();
                    npc.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
                    npc.setPosition(x, y);
                    npc.setType(getNPCType(type));
                    System.out.println("Load NPC with type " + getNPCType(type) + "(" + npc + ")");
                    gameObjects.add(npc);
                    mapping.put(object, npc);
                } else if (properties.get(Tmx.TYPE).equals(Tmx.CRUMB)) {
                    crumbCount++;
                    playerManager.setMaxAmount(GameObjectType.CRUMB, crumbCount);
                    float x = (Float)properties.get(Tmx.X);
                    float y = (Float)properties.get(Tmx.Y);
                    // Normalize spawn position
                    x = (int)Math.floor(x / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE;
                    y = (int)Math.floor(y / GameConfig.CELL_SCALE) * GameConfig.CELL_SCALE;
                    GameObject crumb = world.addObject();
                    crumb.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
                    crumb.setPosition(x, y);
                    crumb.setType(GameObjectType.CRUMB);
                    gameObjects.add(crumb);
                    // Animate crumbs
                    Tween.to(crumb, GameObjectTween.OFFSET_Y, 0.5f).delay(1f * (float)Math.random()).ease(TweenEquations.easeOutQuad).target(GameConfig.CELL_SCALE / 4f)
                            .repeatYoyo(Tween.INFINITY, 0.5f).start(SharedTweenManager.getInstance());
                    mapping.put(object, crumb);
                }
            }
        }
    }

    private int getNPCType(String type) {
        if (type.equals(Tmx.RAISIN)) {
            return GameObjectType.RUISIN;
        } else if (type.equals(Tmx.ALMOND)) {
            return GameObjectType.ALMOND;
        } else if (type.equals(Tmx.BRAZIL)) {
            return GameObjectType.BRAZIL;
        } else if (type.equals(Tmx.CASHEW)) {
            return GameObjectType.CASHEW;
        }
        return -1;
    }
}
