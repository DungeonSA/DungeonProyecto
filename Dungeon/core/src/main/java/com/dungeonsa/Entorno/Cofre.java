package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Pantallas.PantallaRome;
import com.dungeonsa.Pantallas.Pantalladev;

public class Cofre extends Interactuables{
    protected TextureRegion aspecto;
    public Cofre(World mundo, int x, int y, TextureRegion textureRegion) {
        super(mundo, x, y,textureRegion);
        componente.setUserData(this);

    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);
//        setRegion(aspecto);
//        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
        if(Puedepulsar){
            Pantalladev.eliminarcofre(this);

        }
    }


}
