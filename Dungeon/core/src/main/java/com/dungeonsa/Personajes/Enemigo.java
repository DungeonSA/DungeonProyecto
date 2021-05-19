package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Pantallas.PantallaRome;
import com.dungeonsa.Pantallas.Pantalladev;

import java.awt.*;

public abstract class Enemigo extends Sprite {
    protected boolean PuedeSerAtacado = false;
    protected Rectangle areaClick;
    protected int hp;
    protected int exp;
    protected int dp;
    protected Body cuerpo;
    protected TextureRegion aspecto;

    public Enemigo(World mundo, int x, int y, TextureRegion textureRegion,int hp,int dp){
        super();
        this.hp=hp;
        this.dp=dp;
        areaClick = new Rectangle(x,y,16,16);
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
        cuerpo.createFixture(defComponente).setUserData(this);
        //dar aspecto al personaje
        aspecto=new TextureRegion(textureRegion,0,0,
                PantallaRome.LADO_LOSA, PantallaRome.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public Rectangle getAreaClick() {
        return areaClick;
    }

    public void actualizar(float delta) {
        setRegion(aspecto);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);

    }
    public void recivirAtaque(int daño) {

        }
    }

