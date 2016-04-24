package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import box2dLight.PointLight;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.CollisionDetector;
import tv.rocketbeans.rbcgj.core.CrumbCollector;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectType;
import tv.rocketbeans.rbcgj.core.LevelManager;
import tv.rocketbeans.rbcgj.core.Levels;
import tv.rocketbeans.rbcgj.core.MapActionHandler;
import tv.rocketbeans.rbcgj.core.PlayerManager;
import tv.rocketbeans.rbcgj.core.Teleporter;
import tv.rocketbeans.rbcgj.core.controller.WASDMovementController;
import tv.rocketbeans.rbcgj.graphics.DirectionalSpriteRenderer;
import tv.rocketbeans.rbcgj.graphics.LightingManager;
import tv.rocketbeans.rbcgj.graphics.ShadowSpriteRenderer;
import tv.rocketbeans.rbcgj.graphics.SpriteRenderer;
import tv.rocketbeans.rbcgj.ui.Styles;
import tv.rocketbeans.rbcgj.ui.TooltipHandler;
import tv.rocketbeans.rbcgj.util.Colors;

public class IngameScreen extends AbstractScreen {

    // Lighting
    private LightingManager lightingManager;

    private GameObject eddy;

    private PointLight lantern;

    private LevelManager levelManager;

    private CollisionDetector collisions;

    private MapActionHandler handler;

    private Teleporter teleporter;

    private PlayerManager playerManager;

    public IngameScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        initRenderers();
        setBackgroundColor(Colors.BACKGROUND);

        playerManager = new PlayerManager();

        eddy = world.addObject();
        eddy.setPosition(0f, 0f);
        eddy.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
        eddy.setType(GameObjectType.PEANUT);

        handler = new MapActionHandler();
        lightingManager = new LightingManager();
        lightingManager.setAmbientLight(new Color(0f, 0.1f, 0.2f, 0.37f));
        lantern = lightingManager.addPointLight(250f, new Color(1f, 0.4f, 0.2f, 1f), eddy.getLeft(), eddy.getTop());

        collisions = new CollisionDetector();
        levelManager = new LevelManager(lightingManager, world, handler, collisions);
        world.setController(eddy, new WASDMovementController(collisions, handler));
        levelManager.loadLevel(Levels.LEVEL_1, eddy);
        world.setCameraTracking(eddy);

        teleporter = new Teleporter(levelManager);
        handler.addListener(teleporter);
        handler.addListener(new TooltipHandler());
        handler.addListener(new CrumbCollector(levelManager, world, playerManager));


        PlayerUI ui = new PlayerUI(playerManager);
        ui.setPosition(20f, 20f);
        stage.addActor(ui);
    }

    @Override
    protected void beforeWorldRender(Batch batch, float delta) {
        if (levelManager.isNowInitialized()) {
            camera.position.x = eddy.getLeft() + eddy.getWidth() / 2f;
            camera.position.y = eddy.getTop() + eddy.getHeight() / 2f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            tooltip.create(eddy.getLeft(), eddy.getTop() + eddy.getHeight() * 3f, Styles.STORY, "Das sieht wirklich sehr interessant aus!");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        lantern.setPosition(eddy.getOffset().x + eddy.getLeft() + eddy.getWidth() / 2f, eddy.getOffset().y + eddy.getTop() + eddy.getHeight() / 2f);
        super.beforeWorldRender(batch, delta);
        batch.end();
        levelManager.renderBackground(camera);
        batch.begin();
    }

    @Override
    protected void afterWorldRender(Batch batch, float delta) {
        super.afterWorldRender(batch, delta);
        batch.end();
        levelManager.renderForeground(camera);
        lightingManager.updateAndRender(camera);
        batch.begin();
    }

    private void initRenderers() {
        world.registerRenderer(GameObjectType.PEANUT, new DirectionalSpriteRenderer(Assets.Textures.EDDY));
        world.registerRenderer(GameObjectType.ALMOND, new SpriteRenderer(Assets.Textures.ALMOND_DEAD));
        world.registerRenderer(GameObjectType.RUISIN, new SpriteRenderer(Assets.Textures.RUISIN_DEAD));
        world.registerRenderer(GameObjectType.CASHEW, new SpriteRenderer(Assets.Textures.CASHEW_DEAD));
        world.registerRenderer(GameObjectType.BRAZIL, new SpriteRenderer(Assets.Textures.BRAZILNUT_DEAD));
        world.registerRenderer(GameObjectType.CRUMB, new ShadowSpriteRenderer(Assets.Textures.CRUMB));
    }
}
