package tv.rocketbeans.rbcgj.core;

import com.badlogic.gdx.audio.Music;

import tv.rocketbeans.rbcgj.assets.Assets;

public enum Levels {

    LEVEL_1(Assets.Maps.DEFAULT, Assets.Musics.LEVEL_1);

    private Assets.Maps maps;

    private Assets.Musics musics;

    Levels(Assets.Maps maps, Assets.Musics musics) {
        this.maps = maps;
        this.musics = musics;
    }

    public Assets.Maps getMaps() {
        return maps;
    }

    public Assets.Musics getMusics() {
        return musics;
    }
}
