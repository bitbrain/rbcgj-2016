package tv.rocketbeans.rbcgj.audio;

import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.util.DeltaTimer;

public class SoundPoolLooper {

    private static final float DEFAULT_INTERVAL = 0.3f;

    private DeltaTimer timer;

    private Assets.Sounds[] sounds;

    private float interval = DEFAULT_INTERVAL;

    private Random random;

    public SoundPoolLooper(Assets.Sounds... sounds) {
        this.timer = new DeltaTimer();
        this.sounds = sounds;
        this.random = new Random();
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public void play() {
        if (timer.reached(interval)) {
            playRandomSound();
            timer.reset();
        }
    }

    public void update(float delta) {
        timer.update(delta);
    }

    private void playRandomSound() {
        int randomIndex = random.nextInt(sounds.length);
        Sound sound = AssetManager.getSound(sounds[randomIndex]);
        sound.play(0.7f - random.nextFloat() * 0.2f, 1f - random.nextFloat() * 0.2f, 0f);
    }
}
