package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Entorno.Interactuables;
import com.dungeonsa.Pantallas.PantallaRome;
import com.dungeonsa.Utiles;

import java.util.ArrayList;

public class Personaje extends Sprite {
    protected int hp=100;
    protected int maxhp=100;
    int daño_recivido;
    protected int exp;
    protected int dp;
    protected Body cuerpo;
    protected TextureRegion aspecto;
    protected boolean Puede_atacar;
    protected boolean Puede_recivir_ataque;
    protected static float Intervalo_recivir_ataque=0.5f;
    protected static float Intervalo_atacar=0.7f;
    protected float contador_atacar;
    protected float contador_recivir;
    protected Vector2 posicion;

    protected ArrayList<Interactuables> enRangoDeUso;

    public Personaje(World mundo, int x, int y, TextureRegion textureRegion){
        super();
        daño_recivido=0;
        Puede_recivir_ataque=true;
        dp=10;
        Puede_atacar=true;
        contador_atacar=Intervalo_atacar;
        contador_recivir=Intervalo_recivir_ataque;
        enRangoDeUso = new ArrayList<>();
        //Cuerpo físico
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.DynamicBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        FixtureDef defComponente= new FixtureDef();
        FixtureDef componenteinteraccion=new FixtureDef();
        FixtureDef componenteAtacar=new FixtureDef();
        FixtureDef componenteVision=new FixtureDef();
        cuerpo=mundo.createBody(defCuerpo);

        //Sensor del cuerpo fisico
        CircleShape forma=new CircleShape();
        forma.setRadius(0.4f);
        defComponente.shape= forma;
        defComponente.friction=0;
        cuerpo.createFixture(defComponente).setUserData(this);

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
        //sensor vision
        forma.setRadius(2.f);
        componenteVision.isSensor=true;
        componenteVision.shape=forma;
        cuerpo.createFixture(componenteVision).setUserData("vision_enemiga");
//        sensor daño_enemigo
        forma.setRadius(0.8f);
        componenteAtacar.isSensor=true;
        componenteAtacar.shape=forma;
        cuerpo.createFixture(componenteAtacar).setUserData("alcance_e");
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
//        System.out.println(contador);
        this.posicion=posicion;
//        System.out.println(posicion);
        setRegion(aspecto);
        setPosition(cuerpo.getPosition().x-.5f,cuerpo.getPosition().y-.5f);
        if(!Puede_atacar&&contador_atacar<Intervalo_atacar){
            contador_atacar+=delta;
        }
        if(contador_atacar>=Intervalo_atacar)Puede_atacar=true;
        if(Puede_recivir_ataque&&contador_recivir<Intervalo_recivir_ataque || !Puede_recivir_ataque&&contador_recivir<Intervalo_recivir_ataque){
            contador_recivir+=delta;
        }else if(Puede_recivir_ataque&&contador_recivir>=Intervalo_recivir_ataque){

            hp-=daño_recivido;
            contador_recivir=0f;

        }
        decidirAspecto(delta);

//        System.out.println(this.hp);
    }

    public boolean isPuede_atacar() {
        return Puede_atacar;
    }

    public void setPuede_recivir_ataque(boolean puede_recivir_ataque) {
        Puede_recivir_ataque = puede_recivir_ataque;
    }

    public int getDp() {
        return dp;
    }
    public void consumir_ataque(){
        contador_atacar=0.f;
        Puede_atacar=false;

    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setDaño_recivido(int daño_recivido) {
        this.daño_recivido = daño_recivido;
        aspecto.flip(true,false);
    }
    public TextureRegion decidirAspecto(float delta) {
        Vector2 velocidad = cuerpo.getLinearVelocity();
        if ((velocidad.x < 0 && !aspecto.isFlipX()) || (velocidad.x > 0 && aspecto.isFlipX()))
            aspecto.flip(true, false);

        return aspecto;
    }

//
}
