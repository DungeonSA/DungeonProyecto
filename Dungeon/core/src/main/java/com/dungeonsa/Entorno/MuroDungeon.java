package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class MuroDungeon extends Muro{
    public MuroDungeon(World mundo, int x, int y, TextureRegion textureRegion) {
        super(mundo, x, y, textureRegion);
    }

}
