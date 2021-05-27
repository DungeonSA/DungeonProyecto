package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Pantallas.PantallaAccion;

import java.awt.*;

public class Cofre extends Interactuables{
    protected Rectangle areaClick;

    public Cofre(World mundo, int x, int y, TextureRegion textureRegion, PantallaAccion refPantalla) {
        super(mundo, x, y,textureRegion,refPantalla);
        componente.setUserData(this);
        nombre="Cofre";
        areaClick = new Rectangle(x,y,1,1);
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);
    }

    public Rectangle getAreaClick() {
        return areaClick;
    }

    @Override
    public void usar() {
        super.usar();
        if(puedepulsar){
            refPantalla.eliminarcofre(this);
        }
    }
}
