package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.i18n.Bundle;
import tv.rocketbeans.rbcgj.i18n.Messages;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.tweens.SpriteTween;
import tv.rocketbeans.rbcgj.ui.Styles;
import tv.rocketbeans.rbcgj.util.Colors;

public class MenuScreen extends AbstractScreen {

    static {
        Tween.registerAccessor(Sprite.class, new SpriteTween());
    }

    private Sprite logo;

    public MenuScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        super.onCreateStage(stage, width, height);
        setBackgroundColor(Colors.BACKGROUND);
        fx.setFadeColor(Color.BLACK);
        fx.fadeIn(3f);
        Table layout = new Table();
        layout.setFillParent(true);

        logo = new Sprite(AssetManager.getTexture(Assets.Textures.LOGO));
        logo.setScale(0.65f);
        Tween.to(logo, SpriteTween.SCALE, 4f).target(0.8f).ease(TweenEquations.easeOutCubic).repeatYoyo(Tween.INFINITY, 0f).start(tweenManager);
        logo.getColor().a = 0f;
        Tween.to(logo, SpriteTween.ALPHA, 2f).target(1f).ease(TweenEquations.easeInCubic).start(tweenManager);

        TextButton playButton = new TextButton(Bundle.general.get(Messages.MENU_START), Styles.MENU_BUTTON);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetManager.getMusic(Assets.Musics.TITLE_SCREEN).stop();
                setScreen(new StoryScreen(game));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (fromActor == null) {
                    AssetManager.getSound(Assets.Sounds.MENU_CHOOSE).play(1f, 1f, 0f);
                }
            }
        });
        layout.center().add(playButton).height(70).width(320f).padBottom(20f).padTop(180f).row();
        TextButton closeButton = new TextButton(Bundle.general.get(Messages.MENU_CLOSE), Styles.MENU_BUTTON);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetManager.getMusic(Assets.Musics.TITLE_SCREEN).stop();
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (fromActor == null) {
                    AssetManager.getSound(Assets.Sounds.MENU_CHOOSE).play(1f, 1f, 0f);
                }
            }
        });
        layout.center().add(closeButton).height(70).width(320).row();

        Label credits = new Label("game by Miguel Gonzalez and Simon Janssen", Styles.STORY);
        layout.center().add(credits).padTop(70f).row();

        Label version = new Label("Version " + GameConfig.GAME_VERSION, Styles.STORY);
        version.getColor().a = 0.7f;
        layout.center().add(version).padTop(20f).row();

        stage.addActor(layout);
    }

    @Override
    protected void afterWorldRender(Batch batch, float delta) {
        super.afterWorldRender(batch, delta);
        logo.setPosition(Gdx.graphics.getWidth() / 2f - logo.getWidth() / 2f, Gdx.graphics.getHeight() / 1.6f);
        logo.draw(batch);
    }

    @Override
    protected Viewport getViewport(int width, int height) {
        return new FitViewport(width, height);
    }
}
