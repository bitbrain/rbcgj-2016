package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import tv.rocketbeans.rbcgj.core.GameObjectType;
import tv.rocketbeans.rbcgj.graphics.TextureResolver;
import tv.rocketbeans.rbcgj.i18n.Bundle;
import tv.rocketbeans.rbcgj.i18n.Messages;

public class AchievementWidget extends Actor {

    private static final float ICON_SIZE = 100f;
    private static final float PADDING = 20f;

    private Sprite icon;

    private Label text;

    public AchievementWidget(int type) {
        Texture texture = TextureResolver.resolveTexture(type, true);
        icon = new Sprite(texture);
        String contents = Bundle.general.get(resolveTextKey(type));
        text = new Label(contents, Styles.ACHIEVEMENT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setPosition(Gdx.graphics.getWidth() / 2f - (ICON_SIZE + PADDING + text.getPrefWidth()) / 2f,
                Gdx.graphics.getHeight() - 200f);
        icon.setColor(getColor());
        icon.setPosition(getX(), getY() - ICON_SIZE / 3f);
        icon.setSize(ICON_SIZE, ICON_SIZE);
        text.setPosition(getX() + ICON_SIZE + PADDING, getY());
        text.getColor().a = getColor().a;
        icon.draw(batch, parentAlpha);
        text.draw(batch, parentAlpha);
    }

    private String resolveTextKey(int type) {
        switch (type) {
            case GameObjectType.CRUMB:
                return Messages.ACHIEVEMENT_CRUMBS;
            case GameObjectType.CASHEW:
                return Messages.ACHIEVEMENT_CASHEW;
            case GameObjectType.RUISIN:
                return Messages.ACHIEVEMENT_RUISIN;
            case GameObjectType.ALMOND:
                return Messages.ACHIEVEMENT_ALMOND;
            case GameObjectType.BRAZIL:
                return Messages.ACHIEVEMENT_BRAZIL;
        }
        return Messages.ACHIEVEMENT;
    }
}
