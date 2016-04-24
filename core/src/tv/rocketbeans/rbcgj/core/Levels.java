package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.graphics.Color;

import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.util.Colors;

public enum Levels {


    LEVEL_1(Assets.Maps.BOSS, Assets.Musics.LEVEL_1, Colors.AMBIENT_LEVEL_1),
    LEVEL_2(Assets.Maps.LEVEL2, Assets.Musics.LEVEL_2, Colors.AMBIENT_LEVEL_2),
    BOSS(Assets.Maps.BOSS, Assets.Musics.LEVEL_4, Colors.AMBIENT_LEVEL_BOSS);

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
