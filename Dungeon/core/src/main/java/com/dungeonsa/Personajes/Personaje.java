package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Entorno.Interactuables;
import com.dungeonsa.Pantallas.PantallaRome;

public class Personaje extends Sprite {
    protected int hp=100;
    protected int maxhp=100;
    protected int exp;
    protected int dp;
    protected Body cuerpo;
    protected TextureRegion aspecto;

    public Personaje(World mundo, int x, int y, TextureRegion textureRegion){

        super();

        //Cuerpo físico
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.DynamicBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        FixtureDef defComponente= new FixtureDef();
        FixtureDef componenteinteraccion=new FixtureDef();
        FixtureDef componenteAtacar=new FixtureDef();
        cuerpo=mundo.createBody(defCuerpo);

        //Sensor del cuerpo fisico
        CircleShape forma=new CircleShape();
        forma.setRadius(0.5f);

        defComponente.shape= forma;
        defComponente.friction=0;
        cuerpo.createFixture(defComponente).setUserData("jugador");

        //Sensor de Interaccion
        forma.setRadius(1.5f);
        componenteinteraccion.isSensor=true;
        componenteinteraccion.shape=forma;
        cuerpo.createFixture(componenteinteraccion).setUserData("area_interacciones");
        //Sensor de ataque
        forma.setRadius(2f);
        componenteAtacar.isSensor=true;
        componenteAtacar.shape=forma;
        cuerpo.createFixture(componenteAtacar).setUserData("area_ataque");
        //dar aspecto al personaje
        aspecto=new TextureRegion(textureRegion,0,0,
                PantallaRome.LADO_LOSA, PantallaRome.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public void actualizar(float delta) {
        setRegion(aspecto);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);

    }

//    public Interactuables
}
