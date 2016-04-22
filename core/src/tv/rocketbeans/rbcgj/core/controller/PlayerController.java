package tv.rocketbeans.rbcgj.core.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.core.GameObject;
import tv.rocketbeans.rbcgj.core.GameObjectController;

public class PlayerController implements GameObjectController {

    @Override
    public void act(GameObject object, float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            object.move(0f, GameConfig.CELL_SCALE);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            object.move(0f, -GameConfig.CELL_SCALE);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            object.move(-GameConfig.CELL_SCALE, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            object.move(GameConfig.CELL_SCALE, 0);
        }
    }
}
