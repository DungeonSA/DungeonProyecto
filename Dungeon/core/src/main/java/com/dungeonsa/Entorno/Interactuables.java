package com.dungeonsa.Entorno;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Pantallas.Pantalla;
import com.dungeonsa.Pantallas.PantallaAccion;
import com.dungeonsa.Pantallas.PantallaRome;

public abstract class Interactuables extends Sprite {
    protected Body cuerpo;
    protected TextureRegion aspecto;
    protected Fixture componente;
    protected String nombre;
    protected boolean puedepulsar = false;
    protected PantallaAccion refPantalla;

    public Interactuables(World mundo, int x, int y, TextureRegion textureRegion, PantallaAccion refPantalla) {
        super();
        this.refPantalla = refPantalla;
        //Cuerpo físico

        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.StaticBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        cuerpo=mundo.createBody(defCuerpo);
        //contenedor de componentes
        FixtureDef defComponente= new FixtureDef();
        CircleShape forma=new CircleShape();
        forma.setRadius(0.5f);
        defComponente.shape= forma;
        componente=cuerpo.createFixture(defComponente);
        //darle aspecto
        aspecto=new TextureRegion(textureRegion,0,0,
                PantallaAccion.LADO_LOSA,PantallaAccion.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
    }

    public void empezar_interactuar() {
        puedepulsar = true;

    }
    public void dejar_interactuar(){
        puedepulsar = false;
    }

    public void usar(){

    }

    public void actualizar(float delta){


    }

    public Body getCuerpo() {
        return cuerpo;
    }

}

