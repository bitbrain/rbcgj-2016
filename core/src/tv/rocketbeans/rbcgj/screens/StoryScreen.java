package tv.rocketbeans.rbcgj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import javax.swing.GroupLayout;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import tv.rocketbeans.rbcgj.NutGame;
import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.story.StoryTeller;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.ui.Styles;
import tv.rocketbeans.rbcgj.util.Colors;

public class StoryScreen extends AbstractScreen {

    static {
        Tween.registerAccessor(Actor.class, new ActorTween());
    }

    private Label label, action;

    private StoryTeller teller;

    public StoryScreen(NutGame game) {
        super(game);
        setBackgroundColor(Colors.BACKGROUND);
    }

    @Override
    protected void onCreateStage(Stage stage, int width, int height) {
        super.onCreateStage(stage, width, height);

        teller = new StoryTeller();

        AssetManager.getMusic(Assets.Musics.STORY_SCREEN).setLooping(true);
        AssetManager.getMusic(Assets.Musics.STORY_SCREEN).play();
        fx.setFadeColor(Color.BLACK);
        fx.fadeIn(2f);

        Table layout = new Table();
        layout.setFillParent(true);

        label = new Label(teller.getNextStoryPoint(), Styles.STORY);
        label.setWrap(true);
        label.setAlignment(Align.center);
        layout.center().add(label).width(600f).padBottom(200f).row();
        action = new Label("Press any KEY to continue", Styles.CREDITS);
        action.setColor(Color.WHITE);
        action.getColor().a = 0.5f;
        Tween.to(action, ActorTween.ALPHA, 1f).target(0.2f)
                .ease(TweenEquations.easeInOutCubic)
                .repeatYoyo(Tween.INFINITY, 0f)
                .start(tweenManager);
        layout.center().add(action).row();

        stage.addActor(layout);
    }

    @Override
    protected void beforeWorldRender(Batch batch, float delta) {
        super.beforeWorldRender(batch, delta);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            AssetManager.getMusic(Assets.Musics.STORY_SCREEN).stop();
            setScreen(new IngameScreen(game));
            AssetManager.getSound(Assets.Sounds.START_GAME).play();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            if (teller.hasNextStoryPoint()) {
                tweenManager.killTarget(label);
                Tween.to(label, ActorTween.ALPHA, 0.5f)
                        .target(0f)
                        .ease(TweenEquations.easeOutQuad)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                label.setText(teller.getNextStoryPoint());
                                Tween.to(label, ActorTween.ALPHA, 1f)
                                        .target(1f)
                                        .ease(TweenEquations.easeInOutQuad)
                                        .start(tweenManager);
                            }
                        })
                        .setCallbackTriggers(TweenCallback.COMPLETE)
                        .start(tweenManager);
            } else {
                AssetManager.getMusic(Assets.Musics.STORY_SCREEN).stop();
                setScreen(new IngameScreen(game));
                AssetManager.getSound(Assets.Sounds.START_GAME).play();
            }
        }
    }
}
