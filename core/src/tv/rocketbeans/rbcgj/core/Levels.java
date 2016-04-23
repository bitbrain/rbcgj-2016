package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.graphics.Color;

import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.util.Colors;

public enum Levels {


    LEVEL_1(Assets.Maps.LEVEL1, Assets.Musics.LEVEL_1, Colors.BACKGROUND),
    DEMO(Assets.Maps.DEFAULT, Assets.Musics.LEVEL_1, Colors.BACKGROUND);

    private Assets.Maps maps;

    private Assets.Musics musics;

    private Color ambientColor;

    Levels(Assets.Maps maps, Assets.Musics musics, Color color) {
        this.maps = maps;
        this.musics = musics;
        this.ambientColor = color;
    }

    public Color getAmbientColor() { return ambientColor; }

    public Assets.Maps getMaps() {
        return maps;
    }

    public Assets.Musics getMusics() {
        return musics;
    }
}
