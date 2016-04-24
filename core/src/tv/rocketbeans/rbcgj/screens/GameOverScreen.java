package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.core.Levels;
import tv.rocketbeans.rbcgj.ui.Styles;
import tv.rocketbeans.rbcgj.ui.Tooltip;
import tv.rocketbeans.rbcgj.util.Colors;

public class GameOverScreen extends AbstractScreen {

    public GameOverScreen(NutGame game) {
        super(game);
        setBackgroundColor(Colors.BACKGROUND);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        super.onCreateStage(stage, width, height);
        fx.fadeIn(5f);
        Table layout = new Table();
        layout.setFillParent(true);
        Label label = new Label("To be continued", Styles.PLAYER_INFO);
        layout.center().add(label);
        stage.addActor(layout);
        Tooltip.getInstance().clear();
    }

    @Override
    protected void beforeWorldRender(Batch batch, float delta) {
        super.beforeWorldRender(batch, delta);
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            for (Levels levels : Levels.values()) {
                AssetManager.getMusic(levels.getMusics()).setLooping(false);
                AssetManager.getMusic(levels.getMusics()).stop();
            }
            setScreen(new MenuScreen(game));
        }
    }
}
