package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Pantallas.PantallaRome;

public class Cofre extends Muro{
    protected TextureRegion aspecto;
    public Cofre(World mundo, int x, int y, TextureRegion textureRegion) {
        super(mundo, x, y);
        aspecto=new TextureRegion(textureRegion, 0, 0,
                PantallaRome.LADO_LOSA,PantallaRome.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);
    }
    public void actualizar(float delta){
        setRegion(aspecto);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
    }
}
