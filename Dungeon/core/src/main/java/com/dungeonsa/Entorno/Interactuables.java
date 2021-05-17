package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Pantallas.PantallaRome;

public class Interactuables extends Sprite {
    protected Body cuerpo;
    protected TextureRegion aspecto;
    protected boolean usable= false;

    public Interactuables(World mundo, int x, int y, TextureRegion textureRegion) {
        super();

        //Cuerpo físico
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.DynamicBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        cuerpo=mundo.createBody(defCuerpo);

        //Componentes dentro del cuerpo
        FixtureDef defComponente= new FixtureDef();
        FixtureDef componenteinteraccion=new FixtureDef();
        CircleShape forma=new CircleShape();
        forma.setRadius(0.5f);
        defComponente.shape= forma;
        defComponente.friction=0;
        cuerpo.createFixture(defComponente);

        //darle aspecto
        aspecto=new TextureRegion(textureRegion,0,0,
                PantallaRome.LADO_LOSA,PantallaRome.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
    }

    public void interactuar(){

    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }
}

