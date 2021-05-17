package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Muro1 extends Muro{
    public Muro1(World mundo, int x, int y, TextureRegion textureRegion) {
        super(mundo, x, y,textureRegion);
    }
}
