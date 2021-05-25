package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Pantallas.Pantalla;
import com.dungeonsa.Pantallas.PantallaAccion;
import com.dungeonsa.Pantallas.PantallaRome;
import com.dungeonsa.Pantallas.Pantalladev;
import com.dungeonsa.Utiles;

import java.awt.*;
import java.util.Collection;

public  class Enemigo extends Sprite {
    protected int hp;
    protected int dp;
    protected float Intervalo_color;
    protected float contador_color;
    protected float velocidad = 100;
    protected boolean PuedeSerAtacado = false;
    protected boolean invencible = false;
    protected Body cuerpo;
    protected Rectangle areaClick;
    protected TextureRegion aspecto;
    protected PantallaAccion refPantalla;
    protected Vector2 posicion;

    public Enemigo(World mundo, int x, int y, TextureRegion textureRegion, int hp, int dp, PantallaAccion refPantalla){
        super();
        Intervalo_color=0.5f;
        this.refPantalla = refPantalla;

        contador_color=Intervalo_color;
        areaClick = new Rectangle(x,y,1,1);
        this.hp=hp;
        this.dp=dp;
        //Cuerpo físico
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.DynamicBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        FixtureDef defComponente= new FixtureDef();
//        FixtureDef componenteVision=new FixtureDef();
//        FixtureDef componenteAtacar=new FixtureDef();
        cuerpo=mundo.createBody(defCuerpo);

        //Sensor del cuerpo fisico
        CircleShape forma=new CircleShape();
        forma.setRadius(0.4f);
        defComponente.shape= forma;
        defComponente.friction=0;
        cuerpo.createFixture(defComponente).setUserData(this);

//        //sensor vision
//        forma.setRadius(2.f);
//        componenteVision.isSensor=true;
//        componenteVision.shape=forma;
//        cuerpo.createFixture(componenteVision).setUserData("vision_enemiga");

        //dar aspecto al personaje
        aspecto=new TextureRegion(textureRegion,0,0,
                Utiles.LADO_LOSA, Utiles.LADO_LOSA);
        setRegion(aspecto);
        setBounds(0,0,1,1);

    }

    public Body getCuerpo() {
        return cuerpo;
    }



    public void actualizar(float delta, Vector2 posicion) {
        setRegion(aspecto);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
        areaClick.setLocation((int)cuerpo.getPosition().x,(int)cuerpo.getPosition().y);
        if(contador_color<Intervalo_color){
            contador_color+=delta;
        }
        if(contador_color>=Intervalo_color)this.setColor(1f,1f,1f,1f);
        if (hp<0){
            refPantalla.eliminarEnemigo(this);
        }
        this.posicion = posicion;

    }
    public void empezar_interactuar() {
        PuedeSerAtacado = true;
        System.out.println("hola");

    }
    public void dejar_interactuar(){
        PuedeSerAtacado = false;
        System.out.println("adios");
    }
    public void recivirAtaque(int daño) {
        if(PuedeSerAtacado){
            System.out.println(hp);
            hp=hp-daño;
            this.setColor(1f,0f,0f,1f);
            System.out.println(hp);
            System.out.printf("hay");

        }
    }

    public void moverHacia(Vector2 objetivo) {
        cuerpo.applyForce(calculateVelocity(objetivo),objetivo,true);
//        cuerpo.setLinearVelocity(calculateVelocity(objetivo));
    }

    public Vector2 calculateVelocity(Vector2 target) {
        Vector2 direction = new Vector2(target.x - cuerpo.getPosition().x, target.y - cuerpo.getPosition().y ).nor();
        return new Vector2( velocidad * direction.x, velocidad * direction.y );
    }

    public int getDp() {
        return dp;
    }

    public void cambiar_color(){
        if (PuedeSerAtacado){
            contador_color = 0f;
            this.setColor(1f, 0f, 0f, 1f);
        }
    }
    public Rectangle getAreaClick() {
        return areaClick;
    }
}

