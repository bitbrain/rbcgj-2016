package tv.rocketbeans.rbcgj.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import aurelienribon.tweenengine.TweenManager;
import box2dLight.RayHandler;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;
import tv.rocketbeans.rbcgj.tweens.TweenUtils;

public class LightingManager {

    private RayHandler handler;

    private TweenManager tweenManager = SharedTweenManager.getInstance();

    private Color ambientLight;

    public LightingManager() {
        RayHandler.useDiffuseLight(true);
        handler = new RayHandler(new World(new Vector2(), false));
        handler.setAmbientLight(0f, 0f, 0f, 1f);
        ambientLight = new Color(0.6f, 0.6f, 0.6f, 1f);
    }

    public void fadeToAmbientLight(float r, float g, float b, float a) {
        TweenUtils.toColor(ambientLight, new Color(r, g, b, a), 1f);
    }

    public void setAmbientLight(Color color) {
        this.ambientLight = color;
    }

    public void updateAndRender(OrthographicCamera camera) {
        handler.setCombinedMatrix(camera);
        handler.setAmbientLight(ambientLight.r, ambientLight.g, ambientLight.b, ambientLight.a);
        handler.updateAndRender();
    }
}
