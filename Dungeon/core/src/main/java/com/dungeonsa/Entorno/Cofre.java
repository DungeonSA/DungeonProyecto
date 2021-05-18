package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Pantallas.PantallaRome;

public class Cofre extends Interactuables{
    protected TextureRegion aspecto;
    public Cofre(World mundo, int x, int y, TextureRegion textureRegion) {
        super(mundo, x, y,textureRegion);
        //componentes cofre
        FixtureDef defComponente= new FixtureDef();
        CircleShape forma=new CircleShape();
        forma.setRadius(0.5f);
        defComponente.shape= forma;
        defComponente.friction=0;
        cuerpo.createFixture(defComponente).setUserData("cofre");
        aspecto=new TextureRegion(textureRegion, 0, 0,
                PantallaRome.LADO_LOSA,PantallaRome.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);
    }
    public void actualizar(float delta){
        setRegion(aspecto);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
    }

    @Override
    public void interactuar() {
        super.interactuar();

    }
}
