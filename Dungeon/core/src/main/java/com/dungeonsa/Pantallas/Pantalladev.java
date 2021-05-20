package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Entorno.*;
import com.dungeonsa.ObjetosDelMundo;
import com.dungeonsa.Pantallas.Pantalla;
import com.dungeonsa.Personajes.Enemigo;
import com.dungeonsa.Personajes.Esqueleto;
import com.dungeonsa.Personajes.Jugador;
import com.dungeonsa.Personajes.Personaje;


import java.util.ArrayList;
import java.util.Random;


public class Pantalladev extends Pantalla  {
    public static final int VIDA_MAX_ENEMIGO=100;
    public static final int VIDA_MIN_ENEMIGO=50;
    public static final String TIPO="tipo";
    public static final String JUGADOR="jugador";
    public static final String MURO="muro";
    public static final String ESQUELETO="esqueleton";
    public static final String COFRE="cofre";
    public static final int LADO_LOSA=16;
    private TiledMap mapa;
    private TiledMapTileLayer capa;
    private float relacionAspecto;
    private OrthogonalTiledMapRenderer renderizador;
    private ArrayList<Muro> listaMuros;
    private static ArrayList<Cofre> listaCofres;
    private static ArrayList<Enemigo> listaEnemigos;
    private static ArrayList<Body> listanegra;
    public static final Vector2 PASO_ARRIBA=new Vector2(0,1f);
    public static final Vector2 PASO_ABAJO=new Vector2(0,-1f);
    public static final Vector2 PASO_DERECHA=new Vector2(1f,0);
    public static final Vector2 PASO_IZQUIERDA=new Vector2(-1f,0);
    public static float VEL_MAX=3.0f;

    private Personaje jugador=null;
    private Body cuerpoJugador=null;
    private Vector2 posicionJugador=null;
    private Vector2 puntoClick;

    //    Fisicas
    private World mundo;
    private Box2DDebugRenderer depurador;


    public Pantalladev() {
        super();
        am.load("Dungeon_character_2.png", Texture.class);
        am.load("Dungeon_Tileset.png", Texture.class);
        am.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        am.load("pruevaRome.tmx", TiledMap.class);
        am.finishLoading();
        mapa=am.get("pruevaRome.tmx");

        renderizador=new OrthogonalTiledMapRenderer(mapa,1.0f/LADO_LOSA);
        relacionAspecto= (float)juego.getAncho()/juego.getAlto();
        camara.setToOrtho(false,10*relacionAspecto,10);

        //Físicas
        mundo=new World(new Vector2(0,0),true);
        depurador=new Box2DDebugRenderer();
        //-----------------------LEE MURO DE MAPA-------------------------//
        listaMuros=new ArrayList<>();
        listaCofres=new ArrayList<>();
        listaEnemigos=new ArrayList<>();
        listanegra=new ArrayList<>();

        capa=(TiledMapTileLayer)mapa.getLayers().get(1);
        for(int x=0;x<capa.getWidth();x++){
            for(int y=0;y<capa.getHeight();y++){
                TiledMapTileLayer.Cell celda=capa.getCell(x,y);
                if(celda==null) continue;
                MapProperties propiedades=celda.getTile().getProperties();
                if(propiedades.containsKey(TIPO)){
                    switch((String)propiedades.get(TIPO)){

                        case MURO:
                            listaMuros.add(new MuroDungeon(mundo,x,y, celda.getTile().getTextureRegion()));
                            break;
                    }
                }
            }
        }
        //-----------------------LEE LOGICA DE MAPA-------------------------//
        capa=(TiledMapTileLayer)mapa.getLayers().get(2);
        for(int x=0;x<capa.getWidth();x++) {
            for (int y = 0; y < capa.getHeight(); y++) {
                TiledMapTileLayer.Cell celda = capa.getCell(x, y);
                if (celda == null) continue;
                MapProperties propiedades = celda.getTile().getProperties();
                if (propiedades.containsKey(TIPO)) {
                    switch ((String) propiedades.get(TIPO)) {
                        case JUGADOR:
                            jugador = new Personaje(mundo, x, y, celda.getTile().getTextureRegion());
                            cuerpoJugador = jugador.getCuerpo();
                            break;
                        case COFRE:
                            listaCofres.add(new Cofre(mundo,x,y,celda.getTile().getTextureRegion()));
                            break;
                        case ESQUELETO:
                            Random r = new Random();
                            int VidaEnemigo = r.nextInt(VIDA_MAX_ENEMIGO-VIDA_MIN_ENEMIGO) + VIDA_MIN_ENEMIGO;
                            listaEnemigos.add(new Esqueleto(mundo,x,y,celda.getTile().getTextureRegion(),VidaEnemigo,12));
                            break;

                    }
                }
            }
        }
//chequear areas de interaccion


        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture compA= contact.getFixtureA();
                Fixture compB= contact.getFixtureB();
                if(!compA.getUserData().equals("area_interacciones") && !compB.getUserData().equals("area_interacciones")&&!compA.getUserData().equals("area_ataque") && !compB.getUserData().equals("area_ataque")){

                    return;
                }
                if(compA.getUserData().equals("area_interacciones") && (compB.getUserData() instanceof Cofre)){
                    ((Cofre)compB.getUserData()).empezar_interactuar();
                    System.out.printf("cofre");
                }
                else if(compB.getUserData().equals("area_interacciones") && (compA.getUserData() instanceof Cofre)){
                    ((Cofre)compA.getUserData()).empezar_interactuar();
                    System.out.printf("cofre");
                }


                if(compA.getUserData().equals("area_ataque") && (compB.getUserData() instanceof Enemigo)){
                    ((Enemigo)compB.getUserData()).empezar_interactuar();

                }
                else if(compB.getUserData().equals("area_ataque") && (compA.getUserData() instanceof Enemigo)){
                    ((Enemigo)compA.getUserData()).empezar_interactuar();

                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture compA= contact.getFixtureA();
                Fixture compB= contact.getFixtureB();
                if(!compA.getUserData().equals("area_interacciones") && !compB.getUserData().equals("area_interacciones")&&!compA.getUserData().equals("area_ataque") && !compB.getUserData().equals("area_ataque")){
                    return;
                }
                if(compA.getUserData().equals("area_interacciones") && (compB.getUserData() instanceof Cofre)){
                    ((Cofre)compB.getUserData()).dejar_interactuar();
                    System.out.printf("NOcofre");
                }else if(compB.getUserData().equals("area_interacciones") && (compA.getUserData() instanceof Cofre)){
                    ((Cofre)compA.getUserData()).dejar_interactuar();
                    System.out.printf("NOcofre");
                }
                if(compA.getUserData().equals("area_ataque") && (compB.getUserData() instanceof Enemigo)){
                    ((Enemigo)compB.getUserData()).dejar_interactuar();

                }
                else if(compB.getUserData().equals("area_ataque") && (compA.getUserData() instanceof Enemigo)){
                    ((Enemigo)compA.getUserData()).dejar_interactuar();

                }


            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });


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
        }if(Gdx.input.isKeyPressed(Input.Keys.W) &&
                cuerpoJugador.getLinearVelocity().y<VEL_MAX){
            cuerpoJugador.applyLinearImpulse(PASO_ARRIBA,posicionJugador,true);
        }if(Gdx.input.isKeyPressed(Input.Keys.S) &&
                cuerpoJugador.getLinearVelocity().y>-VEL_MAX){
            cuerpoJugador.applyLinearImpulse(PASO_ABAJO,posicionJugador,true);
        }if(Gdx.input.isKeyPressed(Input.Keys.D) &&
                cuerpoJugador.getLinearVelocity().x<VEL_MAX) {
            cuerpoJugador.applyLinearImpulse(PASO_DERECHA, posicionJugador, true);
        }

        if(Gdx.input.justTouched()){
            Vector2 puntoClick=juego.getVista().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
            for(int i=0;i<listaCofres.size();i++){
                if(listaCofres.get(i).getAreaClick().contains(puntoClick.x, puntoClick.y)){
                    listaCofres.get(i).usar();
                }

            }
            for(int i=0;i<listaEnemigos.size();i++){
                if(listaEnemigos.get(i).getAreaClick().contains(puntoClick.x, puntoClick.y)){
                    if(jugador.isPuede_atacar()){
                        listaEnemigos.get(i).recivirAtaque(jugador.getDp());
                        listaEnemigos.get(i).cambiar_color();
                    jugador.consumir_ataque();}
                }

            }
        }
    }

    @Override
    public void actualizar(float delta) {
        renderizador.setView(camara);
        jugador.actualizar(delta);
        for(int i=0;i<listaCofres.size();i++)listaCofres.get(i).actualizar(delta);
        for(int i=0;i<listaEnemigos.size();i++){listaEnemigos.get(i).actualizar(delta);}

        //La camara sigue al jugador
        camara.position.x=MathUtils.clamp(cuerpoJugador.getPosition().x,5*relacionAspecto,capa.getWidth()-5*relacionAspecto);
        camara.position.y=MathUtils.clamp(cuerpoJugador.getPosition().y,5,capa.getHeight()-5);
        camara.update();
    }

    @Override
    public void dibujar(float delta) {
        //render basico para capas de fondo
        int[] capas={0};
        renderizador.render(capas);

        sb.setProjectionMatrix(camara.combined);
        sb.begin();
        for(Muro b: listaMuros)b.draw(sb);
        for(Cofre i:listaCofres)i.draw(sb);
        for(Enemigo i:listaEnemigos)i.draw(sb);

        jugador.draw(sb);
        sb.end();

        depurador.render(mundo, camara.combined); //dibuja las lineas del debuger
        mundo.step(.02f,6,2);
        for(Body cuerpo:listanegra){

            mundo.destroyBody(cuerpo);
        }
        listanegra.clear();
    }

    public static void eliminarcofre(Interactuables i){
        listaCofres.remove(i);
        listanegra.add(i.getCuerpo());
    }
    public static void eliminarEnemigo(Enemigo e){
        listaEnemigos.remove(e);
        listanegra.add(e.getCuerpo());
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

    @Override
    public void dispose() {

    }
}