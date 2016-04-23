package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.tweens.SpriteTween;
import tv.rocketbeans.rbcgj.util.Colors;

public class LoadingScreen extends AbstractScreen {

    private Sprite logo;

    public LoadingScreen(NutGame game) {
        super(game);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        super.onCreateStage(stage, width, height);

        AssetManager.getMusic(Assets.Musics.TITLE_SCREEN).setVolume(0.6f);
        AssetManager.getMusic(Assets.Musics.TITLE_SCREEN).play();
        setBackgroundColor(Colors.BACKGROUND);
        logo = new Sprite(AssetManager.getTexture(Assets.Textures.RBCGJ));
        logo.setScale(0.1f);
        fx.fadeIn(2f);
        fx.setFadeColor(Color.BLACK);
        Tween.to(logo, SpriteTween.SCALE, 2f)
                .target(0.7f)
                .ease(TweenEquations.easeInOutCubic)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        LoadingScreen.this.setScreen(new MenuScreen(game));
                    }
                }).start(tweenManager);
    }

    @Override
    protected void afterWorldRender(Batch batch, float delta) {
        logo.setPosition(Gdx.graphics.getWidth() / 2f - logo.getWidth() / 2f,
                         Gdx.graphics.getHeight() / 2f - logo.getHeight() / 2f);
        logo.draw(batch);
    }
}
