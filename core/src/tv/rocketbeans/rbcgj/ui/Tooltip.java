package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import tv.rocketbeans.rbcgj.tweens.ActorTween;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;

public class Tooltip {

    public interface TooltipFactory {
        Actor create();
    }

    private static final Tooltip instance = new Tooltip();

    private TweenManager tweenManager = SharedTweenManager.getInstance();

    private Stage stage;

    private Camera camera;

    private Set<Actor> tooltips = new HashSet<Actor>();

    private TweenEquation equation;

    private float duration;

    private float scale;

    private Actor lastTooltip;

    private Tooltip() {
        setTweenEquation(TweenEquations.easeOutCubic);
        duration = 2.5f;
        scale = 1.0f;
    }

    public static Tooltip getInstance() {
        return instance;
    }

    public void create(float x, float y, Label.LabelStyle style, String text) {
        create(x, y, style, text, Color.WHITE, null);
    }

    public void create(float x, float y, final Label.LabelStyle style, final String text, Color color, final TweenCallback callback) {
        create(x, y, style, text, color, callback, new TooltipFactory() {
            @Override
            public Actor create() {
                final Label tooltip = new Label(text, style) {
                    @Override
                    public float getX() {
                        return super.getX() - camera.position.x + camera.viewportWidth / 2f - this.getWidth() / 2f;
                    }

                    @Override
                    public float getY() {
                        return super.getY() - camera.position.y + camera.viewportHeight / 2f - this.getHeight() / 2f;
                    }

                    @Override
                    public float getOriginX() {
                        return super.getOriginX() + this.getWidth() / 2f;
                    }

                    @Override
                    public float getOriginY() {
                        return super.getOriginY() + this.getHeight() / 2f;
                    }
                };
                tooltip.setWrap(true);
                tooltip.setWidth(400f);
                return tooltip;
            }
        });
    }

    public void create(float x, float y, Label.LabelStyle style, String text, Color color, final TweenCallback callback, TooltipFactory factory) {
        if (lastTooltip != null) {
            final Actor tooltip = lastTooltip;
            tweenManager.killTarget(lastTooltip);
            Tween.to(tooltip, ActorTween.ALPHA, 1f).target(0f).setCallbackTriggers(TweenCallback.COMPLETE)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            stage.getActors().removeValue(tooltip, true);
                            lastTooltip = null;
                        }
                    }).ease(equation).start(tweenManager);
        }
        final Actor tooltip = factory.create();
        tooltip.setColor(color);
        tooltip.setPosition(x, y);
        stage.addActor(tooltip);
        tooltips.add(tooltip);
        Tween.to(tooltip, ActorTween.ALPHA, this.duration).delay(2.8f).target(0f).setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        if (callback != null) {
                            callback.onEvent(type, source);
                        }
                        lastTooltip = null;
                        stage.getActors().removeValue(tooltip, true);
                    }
                }).ease(equation).start(tweenManager);
        Tween.to(tooltip, ActorTween.SCALE, this.duration).target(scale).ease(equation).start(tweenManager);
        if (lastTooltip != tooltip) {
            lastTooltip = tooltip;
        }
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setTweenEquation(TweenEquation equation) {
        this.equation = equation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void clear() {
        for (Actor a : tooltips) {
            tweenManager.killTarget(a);
            stage.getActors().removeValue(a, true);
        }
        tooltips.clear();
    }

    public void init(Stage stage, Camera camera) {
        this.stage = stage;
        this.camera = camera;
    }

}