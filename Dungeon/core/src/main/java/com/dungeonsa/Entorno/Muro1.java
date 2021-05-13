package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

public class Muro1 extends Muro{
    public Muro1(World mundo, TextureAtlas atlas, int x, int y) {
        super(mundo, atlas.findRegion("muro"), x, y);
    }
}
