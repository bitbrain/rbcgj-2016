package tv.rocketbeans.rbcgj.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import tv.rocketbeans.rbcgj.assets.AssetManager;
import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.graphics.GraphicsFactory;
import tv.rocketbeans.rbcgj.util.Colors;

public class Styles {

    public static final TextButton.TextButtonStyle MENU_BUTTON = new TextButton.TextButtonStyle();

    public static final Label.LabelStyle CREDITS = new Label.LabelStyle();
    public static final Label.LabelStyle STORY = new Label.LabelStyle();
    public static final Label.LabelStyle PLAYER_INFO = new Label.LabelStyle();

    public static void init() {
        MENU_BUTTON.font = AssetManager.getFont(Assets.Fonts.MEDIUM);
        MENU_BUTTON.fontColor = Colors.UI.cpy();
        MENU_BUTTON.overFontColor = Colors.UI_HIGHLIGHTED;
        MENU_BUTTON.checkedFontColor = Colors.UI_HIGHLIGHTED;
        MENU_BUTTON.up = GraphicsFactory.createDrawable(Assets.Textures.BUTTON_9, Colors.UI);
        MENU_BUTTON.checked = GraphicsFactory.createDrawable(Assets.Textures.BUTTON_9, Colors.UI_HIGHLIGHTED);
        MENU_BUTTON.over = GraphicsFactory.createDrawable(Assets.Textures.BUTTON_9, Colors.UI_HIGHLIGHTED);

        CREDITS.font = AssetManager.getFont(Assets.Fonts.SMALL);
        CREDITS.fontColor = Colors.UI_HIGHLIGHTED.cpy();

        STORY.font = AssetManager.getFont(Assets.Fonts.STORY);
        STORY.fontColor = Colors.UI_HIGHLIGHTED.cpy();

        PLAYER_INFO.font = AssetManager.getFont(Assets.Fonts.MEDIUM);
        PLAYER_INFO.fontColor = Colors.UI_HIGHLIGHTED.cpy();

    }
}
