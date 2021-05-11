package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Jugador extends Sprite {
    protected Body cuerpo;
    protected TextureRegion aspecto;

    public Jugador(World mundo, TextureAtlas atlas, int x, int y){
        super(atlas.findRegion("mainchar"));

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
    }

    public Body getCuerpo() {
        return cuerpo;
    }
}
