package tv.rocketbeans.rbcgj.tweens;

import com.badlogic.gdx.graphics.Color;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public final class TweenUtils {

    private static TweenManager tweenManager = SharedTweenManager.getInstance();

    /**
     * Fades the source color object into the target color in the given time
     *
     * @param sourceColor the source color
     * @param targetColor the target color
     * @param time given decimal time (in seconds)
     * @param equation tween equation
     */
    public static void toColor(Color sourceColor, Color targetColor, float time, TweenEquation equation) {
        tweenManager.killTarget(sourceColor, ColorTween.R);
        tweenManager.killTarget(sourceColor, ColorTween.G);
        tweenManager.killTarget(sourceColor, ColorTween.B);
        Tween.to(sourceColor, ColorTween.R, time).ease(equation).target(targetColor.r).start(tweenManager);
        Tween.to(sourceColor, ColorTween.G, time).ease(equation).target(targetColor.g).start(tweenManager);
        Tween.to(sourceColor, ColorTween.B, time).ease(equation).target(targetColor.b).start(tweenManager);
    }

    /**
     * Fades the source color object into the target color in the given time
     *
     * @param sourceColor the source color
     * @param targetColor the target color
     * @param time given decimal time (in seconds)
     */
    public static void toColor(Color sourceColor, Color targetColor, float time) {
        toColor(sourceColor, targetColor, time, TweenEquations.easeNone);
    }

    /**
     * Fades the source color object into the target color in 1 second
     *
     * @param sourceColor the source color
     * @param targetColor the target color
     */
    public static void toColor(Color sourceColor, Color targetColor) {
        toColor(sourceColor, targetColor, 1f, TweenEquations.easeNone);
    }
}