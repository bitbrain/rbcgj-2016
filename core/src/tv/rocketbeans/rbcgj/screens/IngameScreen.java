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
import tv.rocketbeans.rbcgj.core.QuestHandler;
import tv.rocketbeans.rbcgj.core.Teleporter;
import tv.rocketbeans.rbcgj.core.controller.WASDMovementController;
import tv.rocketbeans.rbcgj.graphics.DirectionalSpriteRenderer;
import tv.rocketbeans.rbcgj.graphics.LightingManager;
import tv.rocketbeans.rbcgj.graphics.ShadowSpriteRenderer;
import tv.rocketbeans.rbcgj.graphics.SpriteRenderer;
import tv.rocketbeans.rbcgj.ui.*;
import tv.rocketbeans.rbcgj.util.Colors;

import static tv.rocketbeans.rbcgj.core.GameObjectType.*;
import static tv.rocketbeans.rbcgj.graphics.TextureResolver.resolveTexture;

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

        playerManager.setMaxAmount(GameObjectType.BRAZIL, 1);
        playerManager.setMaxAmount(GameObjectType.RUISIN, 1);
        playerManager.setMaxAmount(GameObjectType.ALMOND, 1);
        playerManager.setMaxAmount(GameObjectType.CASHEW, 1);

        eddy = world.addObject();
        eddy.setPosition(0f, 0f);
        eddy.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
        eddy.setType(GameObjectType.PEANUT);

        handler = new MapActionHandler();
        lightingManager = new LightingManager();
        lightingManager.setAmbientLight(new Color(0f, 0.1f, 0.2f, 0.37f));
        lantern = lightingManager.addPointLight(250f, new Color(1f, 0.4f, 0.2f, 1f), eddy.getLeft(), eddy.getTop());

        collisions = new CollisionDetector();
        levelManager = new LevelManager(lightingManager, world, handler, collisions, playerManager);
        world.setController(eddy, new WASDMovementController(collisions, handler));
        Levels level = Levels.LEVEL_1;
        levelManager.loadLevel(level, eddy);
        world.setCameraTracking(eddy);

        teleporter = new Teleporter(levelManager, this, game);
        handler.addListener(teleporter);
        handler.addListener(new TooltipHandler());
        handler.addListener(new CrumbCollector(levelManager, world, playerManager));
        handler.addListener(new QuestHandler(playerManager, levelManager));

        CrumbUI ui = new CrumbUI(playerManager);
        ui.setPosition(Gdx.graphics.getWidth() - 150f, Gdx.graphics.getHeight() - 220f);
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
        world.registerRenderer(PEANUT, new DirectionalSpriteRenderer(resolveTexture(PEANUT, false)));
        world.registerRenderer(ALMOND, new SpriteRenderer(resolveTexture(ALMOND, true)));
        world.registerRenderer(RUISIN, new SpriteRenderer(resolveTexture(RUISIN, true)));
        world.registerRenderer(CASHEW, new SpriteRenderer(resolveTexture(CASHEW, true)));
        world.registerRenderer(BRAZIL, new SpriteRenderer(resolveTexture(BRAZIL, true)));
        world.registerRenderer(CRUMB, new ShadowSpriteRenderer(resolveTexture(CRUMB, true)));
    }
}
