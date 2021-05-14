package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeonsa.Entorno.Muro1;
import com.dungeonsa.Pantallas.Pantalla;
import com.dungeonsa.Personajes.Jugador;


public class Pantalladev extends Pantalla {
    TiledMapTileLayer capa;
    public static final String TIPO="tipo";
    public static final String JUGADOR="jugador";
    public static final String DURO="nada";
    //    public static final String BLANDO="blando";
    public static final int LADO_LOSA=16;
    private TiledMap mapa;
    private float relacionAspecto;
    private OrthogonalTiledMapRenderer renderizador;

    public static final Vector2 PASO_ARRIBA=new Vector2(0,5.0f);
    public static final Vector2 PASO_ABAJO=new Vector2(0,-5.0f);
    public static final Vector2 PASO_DERECHA=new Vector2(5.0f,0);
    public static final Vector2 PASO_IZQUIERDA=new Vector2(-5.0f,0);
    public static float VEL_MAX=5.0f;
    private Jugador jugador=null;
    private Body cuerpoJugador=null;
    private Vector2 posicionJugador=null;


    //Físicas
    private World mundo;
    private Box2DDebugRenderer depurador;

    public Pantalladev() {
        super();
        am.load("0x72_16x16DungeonTileset.v4.png", Texture.class);
        am.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        am.load("pruevasolo.tmx", TiledMap.class);
        am.finishLoading();

//        atlas=am.get("Graficos.atlas");
        mapa=am.get("pruevasolo.tmx");
        renderizador=new OrthogonalTiledMapRenderer(mapa,1.0f/LADO_LOSA);
        relacionAspecto= (float)juego.getAncho()/juego.getAlto();
        camara.setToOrtho(false,10*relacionAspecto,10);


        //Físicas
        mundo=new World(new Vector2(0,0),true);
        depurador=new Box2DDebugRenderer();
        //Procesar capas--------------------------
        capa=(TiledMapTileLayer)mapa.getLayers().get(0);
        for(int x=0;x<capa.getWidth();x++){
            for(int y=0;y<capa.getHeight();y++){
                TiledMapTileLayer.Cell celda=capa.getCell(x,y);
                if(celda==null) continue;
                MapProperties propiedades=celda.getTile().getProperties();
                if(propiedades.containsKey(TIPO)){
                    switch((String)propiedades.get(TIPO)){

                        case DURO:
                            new Muro1(mundo,x,y);
                            break;


                    }
                }
            }
        }
        capa=(TiledMapTileLayer)mapa.getLayers().get(1);
        for(int x=0;x<capa.getWidth();x++){
            for(int y=0;y<capa.getHeight();y++){
                TiledMapTileLayer.Cell celda=capa.getCell(x,y);
                if(celda==null) continue;
                MapProperties propiedades=celda.getTile().getProperties();
                if(propiedades.containsKey(TIPO)){
                    switch((String)propiedades.get(TIPO)){


                        case JUGADOR:
                            jugador=new Jugador(mundo,x,y,celda.getTile().getTextureRegion());
                            cuerpoJugador=jugador.getCuerpo();

                            break;

                    }
                }
            }
        }

        //----------------------------------------

    }

    @Override
    public void leerEntrada(float delta) {
        posicionJugador=cuerpoJugador.getPosition();
        if(!Gdx.input.isKeyPressed(Input.Keys.A)  && cuerpoJugador.getLinearVelocity().x < 0){
            cuerpoJugador.setLinearVelocity(0f,cuerpoJugador.getLinearVelocity().y);
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.D)  && cuerpoJugador.getLinearVelocity().x > 0){
            cuerpoJugador.setLinearVelocity(0f,cuerpoJugador.getLinearVelocity().y);
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.W)  && cuerpoJugador.getLinearVelocity().y > 0){
            cuerpoJugador.setLinearVelocity(cuerpoJugador.getLinearVelocity().x,0f);
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.S)  && cuerpoJugador.getLinearVelocity().y < 0){
            cuerpoJugador.setLinearVelocity(cuerpoJugador.getLinearVelocity().x,0f);
        }










        if(Gdx.input.isKeyPressed(Input.Keys.A) &&
                cuerpoJugador.getLinearVelocity().x>-VEL_MAX){
            cuerpoJugador.applyLinearImpulse(PASO_IZQUIERDA,posicionJugador,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) &&
                cuerpoJugador.getLinearVelocity().y<VEL_MAX){
            cuerpoJugador.applyLinearImpulse(PASO_ARRIBA,posicionJugador,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) &&
                cuerpoJugador.getLinearVelocity().y>-VEL_MAX){
            cuerpoJugador.applyLinearImpulse(PASO_ABAJO,posicionJugador,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) &&
                cuerpoJugador.getLinearVelocity().x<VEL_MAX){
            cuerpoJugador.applyLinearImpulse(PASO_DERECHA,posicionJugador,true);
        }
    }


    @Override
    public void actualizar(float delta) {
        renderizador.setView(camara);
        jugador.actualizar(delta);
        camara.position.x= MathUtils.clamp(cuerpoJugador.getPosition().x,5*relacionAspecto,capa.getWidth()-5*relacionAspecto);
        camara.position.y=MathUtils.clamp(cuerpoJugador.getPosition().y,5,capa.getHeight()-5);
        camara.update();

    }

    @Override
    public void dibujar(float delta) {
        int[] capas={0};
        renderizador.render(capas);

        sb.setProjectionMatrix(camara.combined);
        sb.begin();
        jugador.draw(sb);
        sb.end();

        depurador.render(mundo, camara.combined);
        mundo.step(.02f,6,2);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}