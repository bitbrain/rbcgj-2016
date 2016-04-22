package tv.rocketbeans.rbcgj.tweens;

import aurelienribon.tweenengine.TweenManager;

public class SharedTweenManager {

    private static TweenManager instance = new TweenManager();

    private SharedTweenManager() {

    }

    public static TweenManager getInstance() {
        return instance;
    }
}