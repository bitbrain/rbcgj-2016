package tv.rocketbeans.rbcgj.core;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import tv.rocketbeans.rbcgj.GameConfig;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.audio.SoundPoolLooper;
import tv.rocketbeans.rbcgj.tweens.GameObjectTween;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;
import tv.rocketbeans.rbcgj.util.DeltaTimer;

public class GameObjectMover {

    static {
        Tween.registerAccessor(GameObject.class, new GameObjectTween());
    }

    private GameObject object;

    private CollisionDetector collisions;

    private TweenManager tweenManager = SharedTweenManager.getInstance();

    private DeltaTimer timer = new DeltaTimer();

    private MapActionHandler handler;

    private SoundPoolLooper movementSoundLooper;

    public GameObjectMover(GameObject object, CollisionDetector collisions, MapActionHandler handler) {
        this.object = object;
        this.collisions = collisions;
        this.handler = handler;
        timer.update(GameConfig.MOVEMENT_TIME);
        movementSoundLooper = new SoundPoolLooper(Assets.Sounds.STEP_1, Assets.Sounds.STEP_2, Assets.Sounds.STEP_3);
    }

    public void update(float delta) {
        movementSoundLooper.update(delta);
        timer.update(delta);
    }

    public void moveLeft() {
        movementSoundLooper.play();
        if (isReadyToMove() && canMoveLeft()) {
            timer.reset();
            object.move(-GameConfig.CELL_SCALE, 0f);
            object.setOffset(GameConfig.CELL_SCALE, 0f);
            object.setDirection(Direction.LEFT);
            Tween.to(object, GameObjectTween.OFFSET_X, GameConfig.MOVEMENT_TIME)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            handler.enter(object, object.getLeft(), object.getTop());
                        }
                    }).setCallbackTriggers(TweenCallback.COMPLETE)
                    .ease(TweenEquations.easeNone)
                    .target(0f).start(tweenManager);
            animateMovement(object);
        } else if (isReadyToMove()) {
            object.setDirection(Direction.LEFT);
        }
    }

    public void moveRight() {
        movementSoundLooper.play();
        if (isReadyToMove() && canMoveRight()) {
            timer.reset();
            object.move(GameConfig.CELL_SCALE, 0f);
            object.setOffset(-GameConfig.CELL_SCALE, 0f);
            object.setDirection(Direction.RIGHT);
            Tween.to(object, GameObjectTween.OFFSET_X, GameConfig.MOVEMENT_TIME)
                    .ease(TweenEquations.easeNone)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            handler.enter(object, object.getLeft(), object.getTop());
                        }
                    }).setCallbackTriggers(TweenCallback.COMPLETE)
                    .target(0f).start(tweenManager);
            animateMovement(object);
        } else if (isReadyToMove()) {
            object.setDirection(Direction.RIGHT);
        }
    }

    public void moveUp() {
        movementSoundLooper.play();
        if (isReadyToMove() && canMoveUp()) {
            timer.reset();
            object.move(0f, GameConfig.CELL_SCALE);
            object.setOffset(0f, -GameConfig.CELL_SCALE);
            object.setDirection(Direction.UP);
            Tween.to(object, GameObjectTween.OFFSET_Y, GameConfig.MOVEMENT_TIME)
                    .ease(TweenEquations.easeNone)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            handler.enter(object, object.getLeft(), object.getTop());
                        }
                    }).setCallbackTriggers(TweenCallback.COMPLETE)
                    .target(0f).start(tweenManager);
            animateMovement(object);
        } else if (isReadyToMove()) {
            object.setDirection(Direction.UP);
        }
    }

    public void moveDown() {
        movementSoundLooper.play();
        if (isReadyToMove() && canMoveDown()) {
            timer.reset();
            object.move(0f, -GameConfig.CELL_SCALE);
            object.setOffset(0f, GameConfig.CELL_SCALE);
            object.setDirection(Direction.DOWN);
            Tween.to(object, GameObjectTween.OFFSET_Y, GameConfig.MOVEMENT_TIME)
                    .ease(TweenEquations.easeNone)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            handler.enter(object, object.getLeft(), object.getTop());
                        }
                    }).setCallbackTriggers(TweenCallback.COMPLETE)
                    .target(0f).start(tweenManager);
            animateMovement(object);
        } else if (isReadyToMove()) {
            object.setDirection(Direction.DOWN);
        }
    }

    private boolean isReadyToMove() {
        return timer.reached(GameConfig.MOVEMENT_TIME);
    }

    private boolean canMoveLeft() {
        return !collisions.isCollision(object.getLeft() - GameConfig.CELL_SCALE, object.getTop());
    }

    private boolean canMoveRight() {
        return !collisions.isCollision(object.getLeft() + GameConfig.CELL_SCALE, object.getTop());
    }

    private boolean canMoveDown() {
        return !collisions.isCollision(object.getLeft(), object.getTop() - GameConfig.CELL_SCALE);
    }

    private boolean canMoveUp() {
        return !collisions.isCollision(object.getLeft(), object.getTop() + GameConfig.CELL_SCALE);
    }

    private void animateMovement(final GameObject object) {
        Tween.to(object, GameObjectTween.SCALE_X, GameConfig.MOVEMENT_TIME / 2f)
                .ease(TweenEquations.easeInOutQuad)
                .target(0.9f)
                .repeatYoyo(1, 0f)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Tween.to(object, GameObjectTween.SCALE_X, GameConfig.MOVEMENT_TIME / 2f)
                            .target(1.15f)
                            .repeatYoyo(1, 0f)
                            .ease(TweenEquations.easeInOutQuad)
                            .start(tweenManager);
                    }
                })
                .start(tweenManager);
        Tween.to(object, GameObjectTween.SCALE_Y, GameConfig.MOVEMENT_TIME)
                .ease(TweenEquations.easeInOutQuad)
                .target(0.85f)
                .repeatYoyo(1, 0f)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Tween.to(object, GameObjectTween.SCALE_Y, GameConfig.MOVEMENT_TIME)
                                .target(1.05f)
                                .repeatYoyo(1, 0f)
                                .ease(TweenEquations.easeInOutQuad)
                                .start(tweenManager);
                    }
                })
                .start(tweenManager);
    }
}

