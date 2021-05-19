package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Pantallas.Pantalladev;

public class Esqueleto extends Enemigo {
    public Esqueleto(World mundo, int x, int y, TextureRegion textureRegion, int hp, int dp) {
        super(mundo, x, y, textureRegion, hp, dp);
    }

    @Override
    public void recivirAtaque(int daño) {
        super.recivirAtaque(daño);
        if(PuedeSerAtacado){
            hp-=daño;
    }
    }
}
