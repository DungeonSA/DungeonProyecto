package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Pantallas.PantallaRome;

public class Jugador extends Sprite {
    protected Body cuerpo;
    protected TextureRegion aspecto;

    public Jugador(World mundo, int x, int y){
        super();

        //Cuerpo físico
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.DynamicBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        cuerpo=mundo.createBody(defCuerpo);
        //Componentes dentro del cuerpo
        FixtureDef defComponente= new FixtureDef();
        CircleShape forma=new CircleShape();
        forma.setRadius(0.5f);
        defComponente.shape= forma;
        defComponente.friction=0;
        cuerpo.createFixture(defComponente);

//        aspecto=new TextureRegion(getTexture(),getRegionX(),getRegionY(),
//                PantallaRome.LADO_LOSA,PantallaRome.LADO_LOSA);
//        setRegion(aspecto);
        setBounds(0,0,1,1);
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public void actualizar(float delta){
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
    }
}
