package Entorno;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Muro extends Sprite {
    protected Body cuerpo;
    protected TextureRegion aspecto;

    public Muro(World mundo, TextureAtlas atlas, int x, int y) {
        super(atlas.findRegion("nada"));

        //Cuerpo físico
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.type= BodyDef.BodyType.StaticBody;
        defCuerpo.position.x=x+0.5f;
        defCuerpo.position.y=y+0.5f;
        cuerpo=mundo.createBody(defCuerpo);
        //El cuerpo es un contenedor para uno o más componentes (fixtures)
        FixtureDef defComponente=new FixtureDef();
        PolygonShape forma= new PolygonShape();
        forma.setAsBox(.5f,.5f);
        defComponente.shape=forma;
        cuerpo.createFixture(defComponente);
    }
}
