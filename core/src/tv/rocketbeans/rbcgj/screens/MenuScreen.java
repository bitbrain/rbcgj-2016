package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.ui.Styles;
import tv.rocketbeans.rbcgj.util.Colors;

public class MenuScreen extends AbstractScreen {

    static {
        Tween.registerAccessor(Actor.class, new ActorTween());
    }

    public MenuScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        super.onCreateStage(stage, width, height);
        AssetManager.getMusic(Assets.Musics.TITLE_SCREEN).play();
        setBackgroundColor(Colors.BACKGROUND);
        fx.setFadeColor(Color.BLACK);
        fx.fadeIn(6f);
        Table layout = new Table();
        layout.setFillParent(true);

        Image logo = new Image(new Sprite(AssetManager.getTexture(Assets.Textures.LOGO)));
        layout.add(logo).center().padBottom(90f).row();
        logo.getColor().a = 0f;
        Tween.to(logo, ActorTween.ALPHA, 6f).delay(1f).target(1f).ease(TweenEquations.easeInCubic).start(tweenManager);

        TextButton playButton = new TextButton("Starten", Styles.MENU_BUTTON);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new IngameScreen(game));
            }
        });
        layout.center().add(playButton).height(70).width(270f).padBottom(20f).row();
        TextButton closeButton = new TextButton("Beenden", Styles.MENU_BUTTON);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        layout.center().add(closeButton).height(70).width(270).row();

        Label credits = new Label("game by Miguel Gonzalez and Simon Janssen", Styles.CREDITS);
        credits.setFontScale(0.3f);
        layout.center().add(credits).padTop(90f).row();

        stage.addActor(layout);
    }

    @Override
    protected Viewport getViewport(int width, int height) {
        return new FitViewport(width, height);
    }
}
