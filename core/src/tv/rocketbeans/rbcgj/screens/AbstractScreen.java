package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.TweenManager;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.core.GameWorld;
import tv.rocketbeans.rbcgj.graphics.FX;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;
import tv.rocketbeans.rbcgj.ui.Tooltip;

public class AbstractScreen implements Screen {

    protected NutGame game;

    protected GameWorld world;

    protected OrthographicCamera camera;

    private Color backgroundColor = Color.BLACK.cpy();

    private Batch batch;

    private Stage stage;

    public AbstractScreen(NutGame game) {
        this.game = game;
    }

    public NutGame getGame() {
        return game;
    }

    protected TweenManager tweenManager = SharedTweenManager.getInstance();

    protected FX fx = FX.getInstance();

    protected Tooltip tooltip = Tooltip.getInstance();

    protected InputMultiplexer input;

    @Override
    public final void show() {
        camera = new OrthographicCamera();
        world = new GameWorld(camera);
        batch = new SpriteBatch();
        input = new InputMultiplexer();
        fx.init(tweenManager, camera);
    }

    @Override
    public final void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);
        fx.begin();
        camera.update();
        stage.act(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        beforeWorldRender(batch, delta);
        world.updateAndRender(batch, delta);
        afterWorldRender(batch, delta);
        fx.render(batch, delta);
        batch.end();
        stage.draw();
        fx.end();
    }

    @Override
    public final void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage(getViewport(width, height));
            input.addProcessor(stage);
            tooltip.init(stage, camera);
            onCreateStage(stage, width, height);
            Gdx.input.setInputProcessor(input);
        } else {
            stage.getViewport().update(width, height);
        }
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    protected void beforeWorldRender(Batch batch, float delta) {

    }

    protected void afterWorldRender(Batch batch, float delta) {

    }

    protected void onCreateStage(Stage stage, int width, int height) {

    }

    protected Viewport getViewport(int width, int height) {
        return new ScreenViewport();
    }

    @Override
    public void dispose() {
        world.reset();
        stage.dispose();
        input.clear();
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }
}
