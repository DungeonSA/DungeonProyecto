package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Pantallas.Pantalla;
import com.dungeonsa.Pantallas.PantallaAccion;
import com.dungeonsa.Pantallas.PantallaRome;
import com.dungeonsa.Pantallas.Pantalladev;

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
//        setRegion(aspecto);
//        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);

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
