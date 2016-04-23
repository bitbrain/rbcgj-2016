package tv.rocketbeans.rbcgj.util;

public class DeltaTimer {

    private float time;

    public float getTicks() {
        return time;
    }

    public void update(float delta) {
        time += delta;
    }

    public void reset() {
        time = 0;
    }

    public boolean reached(float millis) {
        return time >= millis;
    }
}