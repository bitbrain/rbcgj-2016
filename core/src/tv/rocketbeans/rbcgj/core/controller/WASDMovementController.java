package tv.rocketbeans.rbcgj.core.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import tv.rocketbeans.rbcgj.core.CollisionDetector;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectController;
import tv.rocketbeans.rbcgj.core.GameObjectMover;
import tv.rocketbeans.rbcgj.core.MapActionHandler;

public class WASDMovementController implements GameObjectController {

    private GameObjectMover mover;

    private CollisionDetector collisions;

    private MapActionHandler handler;

    public WASDMovementController(CollisionDetector collisions, MapActionHandler handler) {
        this.collisions = collisions;
        this.handler = handler;
    }

    @Override
    public void act(GameObject object, float delta) {
        if (mover == null) {
            mover = new GameObjectMover(object, collisions, handler);
        }
        mover.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            mover.moveUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            mover.moveDown();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            mover.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            mover.moveRight();
        }
    }
}
