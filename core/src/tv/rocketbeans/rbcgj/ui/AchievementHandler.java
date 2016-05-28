package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.core.PlayerManager;
import tv.rocketbeans.rbcgj.tweens.SharedTweenManager;

public class AchievementHandler implements PlayerManager.PlayerListener {

    @Override
    public void onAddCollectible(final PlayerManager.Collectible collectible) {
        Tooltip.TooltipFactory factory = new Tooltip.TooltipFactory() {
            @Override
            public Actor create() {
                return new AchievementWidget(collectible.getType());
            }
        };
        if (collectible.isJustAchieved()) {
            AssetManager.getSound(Assets.Sounds.ACHIEVEMENT_UNLOCKED).play();
            Tooltip.getInstance().create(null, 2.5f, factory, false);
        }
    }
}
