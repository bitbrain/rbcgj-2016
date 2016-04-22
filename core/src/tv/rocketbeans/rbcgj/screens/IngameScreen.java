package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectType;
import tv.rocketbeans.rbcgj.core.controller.WASDMovementController;
import tv.rocketbeans.rbcgj.graphics.SpriteRenderer;
import tv.rocketbeans.rbcgj.util.Colors;

public class IngameScreen extends AbstractScreen {

    private OrthogonalTiledMapRenderer mapRenderer;
    private MapLayers layers;

    // Lighting
    private World boxWorld;
    private RayHandler rayHandler;

    public IngameScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        boxWorld = new World(new Vector2(), false);
        RayHandler.useDiffuseLight(true);
        boxWorld.createBody(new BodyDef());
        rayHandler = new RayHandler(boxWorld);
        rayHandler.setAmbientLight(0.1f, 0.1f, 0.4f, 1.0f);
        new PointLight(rayHandler, 50, new Color(1,1,1,1), 250, 250f, 250f);
        setBackgroundColor(Colors.BG_LEVEL_1);
        GameObject eddy = world.addObject();
        eddy.setPosition(0f, 0f);
        eddy.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
        eddy.setType(GameObjectType.EDDY);
        world.setCameraTracking(eddy);
        world.registerRenderer(GameObjectType.EDDY, new SpriteRenderer(Assets.Textures.EDDY));
        world.setController(eddy, new WASDMovementController());

        TiledMap map = new TmxMapLoader().load("maps/level_1.tmx");
        layers = map.getLayers();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    protected void beforeWorldRender(Batch batch, float delta) {
        super.beforeWorldRender(batch, delta);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.getBatch().begin();
        mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(0));
        mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(1));
        mapRenderer.getBatch().end();
        batch.begin();
    }

    @Override
    protected void afterWorldRender(Batch batch, float delta) {
        super.afterWorldRender(batch, delta);
        batch.end();
        mapRenderer.getBatch().begin();
        mapRenderer.setView(camera);
        mapRenderer.renderTileLayer((TiledMapTileLayer) layers.get(2));
        mapRenderer.getBatch().end();
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
        batch.begin();
    }
}
