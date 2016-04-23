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
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectType;
import tv.rocketbeans.rbcgj.core.LevelManager;
import tv.rocketbeans.rbcgj.core.Levels;
import tv.rocketbeans.rbcgj.core.controller.WASDMovementController;
import tv.rocketbeans.rbcgj.graphics.DirectionalSpriteRenderer;
import tv.rocketbeans.rbcgj.graphics.LightingManager;
import tv.rocketbeans.rbcgj.util.Colors;

public class IngameScreen extends AbstractScreen {

    // Lighting
    private LightingManager lightingManager;

    private GameObject eddy;

    private PointLight lantern;

    private LevelManager levelManager;

    private CollisionDetector collisions;

    private boolean camPositionFix = false;

    public IngameScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        setBackgroundColor(Colors.BACKGROUND);
        eddy = world.addObject();
        eddy.setPosition(0f, 0f);
        eddy.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
        eddy.setType(GameObjectType.EDDY);
        world.registerRenderer(GameObjectType.EDDY, new DirectionalSpriteRenderer(Assets.Textures.EDDY));
        lightingManager = new LightingManager();
        lightingManager.setAmbientLight(new Color(0f, 0.1f, 0.2f, 0.37f));
        lantern = lightingManager.addPointLight(250f, new Color(1f, 0.4f, 0.2f, 1f), eddy.getLeft(), eddy.getTop());
        collisions = new CollisionDetector();
        levelManager = new LevelManager(lightingManager, collisions);
        world.setController(eddy, new WASDMovementController(collisions));
        levelManager.loadLevel(Levels.LEVEL_1, eddy);
        world.setCameraTracking(eddy);
    }

    @Override
    protected void beforeWorldRender(Batch batch, float delta) {
        if (!camPositionFix) {
            camera.position.x = eddy.getLeft() + eddy.getWidth() / 2f;
            camera.position.y = eddy.getTop() + eddy.getHeight() / 2f;
            camPositionFix = true;
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
}
