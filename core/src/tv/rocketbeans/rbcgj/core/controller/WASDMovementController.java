package tv.rocketbeans.rbcgj.core.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import aurelienribon.tweenengine.TweenManager;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectController;
import tv.rocketbeans.rbcgj.core.GameObjectMover;

public class WASDMovementController implements GameObjectController {

    private GameObjectMover mover;

    @Override
    public void act(GameObject object, float delta) {
        if (mover == null) {
            mover = new GameObjectMover(object);
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
