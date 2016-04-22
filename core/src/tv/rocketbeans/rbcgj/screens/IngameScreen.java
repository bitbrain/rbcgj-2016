package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectType;
import tv.rocketbeans.rbcgj.core.controller.WASDMovementController;
import tv.rocketbeans.rbcgj.graphics.SpriteRenderer;

public class IngameScreen extends AbstractScreen {

    private OrthogonalTiledMapRenderer mapRenderer;

    public IngameScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        super.onCreateStage(stage, width, height);
        GameObject eddy = world.addObject();
        eddy.setPosition(0f, 0f);
        eddy.setDimensions(GameConfig.CELL_SCALE, GameConfig.CELL_SCALE);
        eddy.setType(GameObjectType.EDDY);
        world.registerRenderer(GameObjectType.EDDY, new SpriteRenderer(Assets.Textures.EDDY));
        world.setController(eddy, new WASDMovementController());

        TiledMap map = new TmxMapLoader().load("maps/level_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    protected void beforeWorldRender(Batch batch, float delta) {
        super.beforeWorldRender(batch, delta);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
    }
}
