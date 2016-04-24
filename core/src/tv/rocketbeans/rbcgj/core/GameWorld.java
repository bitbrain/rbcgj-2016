package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.rocketbeans.rbcgj.graphics.CameraTracker;
import tv.rocketbeans.rbcgj.graphics.RenderManager;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;
import tv.rocketbeans.rbcgj.util.ZIndexComparator;

public class GameWorld {

    /** the default cache size this world uses */
    public static final int DEFAULT_CACHE_SIZE = 100;

    public static interface WorldBounds {
        boolean isInBounds(GameObject object, OrthographicCamera camera);
    }

    private final List<GameObject> removals = new ArrayList<GameObject>();

    private final List<GameObject> objects = new ArrayList<GameObject>();

    private final Map<GameObject, GameObjectController> controllers = new HashMap<GameObject, GameObjectController>();

    private final Pool<GameObject> pool;

    private WorldBounds bounds = new WorldBounds() {

        @Override
        public boolean isInBounds(GameObject object, OrthographicCamera camera) {
            return true;
        }
    };

    private OrthographicCamera camera;

    private RenderManager renderManager;

    private CameraTracker tracker;

    private Comparator<GameObject> comparator = new ZIndexComparator();

    public GameWorld(OrthographicCamera camera) {
        this(camera, DEFAULT_CACHE_SIZE);
    }

    public GameWorld(OrthographicCamera camera, int cacheSize) {
        this.camera = camera;
        renderManager = new RenderManager();
        tracker = new CameraTracker(camera);
        pool = new Pool<GameObject>(cacheSize) {
            @Override
            protected GameObject newObject() {
                return new GameObject();
            }
        };
    }

    /**
     * Sets the bounds of the world. By default, everything is in bounds.
     *
     * @param bounds the new bounds implementation
     */
    public void setBounds(WorldBounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Adds a new game object to the game world and provides it.
     *
     * @return newly created game object
     */
    public GameObject addObject() {
        GameObject object = pool.obtain();
        objects.add(object);
        return object;
    }

    /**
     * Registers a renderer for an existing game object of the given type (id)
     *
     * @param gameObjectId type/id of the game object
     * @param renderer instance of the renderer
     */
    public void registerRenderer(Integer gameObjectId, RenderManager.Renderer renderer) {
        renderManager.register(gameObjectId, renderer);
    }

    public void setController(GameObject object, GameObjectController controller) {
        controllers.put(object, controller);
    }

    /**
     * Enables camera tracking for the given object. Tracking can be disabled by providing null.
     *
     * @param object game object which should be tracked.
     */
    public void setCameraTracking(GameObject object) {
        tracker.setTarget(object);
    }

    /**
     * Sets the speed the camera should follow its target
     *
     * @param speed camera tracking speed
     */
    public void setTrackingSpeed(float speed) {
        tracker.setSpeed(speed);
    }

    /**
     * Sets the zoom scale the camera should have while following its target
     *
     * @param scale scale factor of the camera while tracking a target
     */
    public void setTrackingZoomScale(float scale) {
        tracker.setZoomScale(scale);
    }

    /**
     * Updates and renders this world
     *
     * @param batch the batch
     * @param delta frame delta
     */
    public void updateAndRender(Batch batch, float delta) {
        Collections.sort(objects, comparator);
        for (GameObject object : objects) {
            if (!bounds.isInBounds(object, camera)) {
                removals.add(object);
                continue;
            }
            GameObjectController c = controllers.get(object);
            if (c != null) {
                c.act(object, delta);
            }
            renderManager.render(object, batch, delta);
        }
        tracker.update(delta);
        for (GameObject removal : removals) {
            objects.remove(removal);
            controllers.remove(removal);
            pool.free(removal);
            SharedTweenManager.getInstance().killTarget(removal);
        }
        removals.clear();
    }

    /**
     * Number of active objects in the world
     *
     * @return
     */
    public int size() {
        return objects.size();
    }

    /**
     * Resets this world object
     */
    public void reset() {
        pool.clear();
        objects.clear();
        removals.clear();
    }

    /**
     * Removes the given game objects from this world
     *
     * @param objects
     */
    public void remove(GameObject... objects) {
        for (GameObject object : objects) {
            removals.add(object);
        }
    }
}