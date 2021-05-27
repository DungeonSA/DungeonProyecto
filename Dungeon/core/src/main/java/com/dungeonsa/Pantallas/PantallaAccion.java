package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Dificultad;
import com.dungeonsa.Entorno.Cofre;
import com.dungeonsa.Entorno.Interactuables;
import com.dungeonsa.Entorno.Muro;
import com.dungeonsa.Entorno.MuroDungeon;
import com.dungeonsa.Juego;
import com.dungeonsa.Personajes.Enemigo;
import com.dungeonsa.Personajes.Esqueleto;
import com.dungeonsa.Personajes.Jugador;
import com.dungeonsa.Personajes.Personaje;
import com.dungeonsa.Utiles;

import java.util.ArrayList;
import java.util.Random;

public abstract class PantallaAccion extends Pantalla {
    //pantalla
    protected float relacionAspecto;
    protected Label labelCofres;
    protected Label vida;
    protected FitViewport hud;

    //variables mapa
    private TiledMap mapa;
    private TiledMapTileLayer capa;
    private OrthogonalTiledMapRenderer renderizador;
    Preferences prefs= Gdx.app.getPreferences("preferences");
    private float tiempo=0;

    //variables nivel
    protected static String archivoNivel = "pruevaRome.tmx";
    protected static String nombreNivel = "Mazmorra Default";
    protected static Dificultad dificultad = Dificultad.FACIL;
    protected static ArrayList<Muro> listaMuros;
    protected static ArrayList<Cofre> listaCofres;
    protected static ArrayList<Enemigo> listaEnemigos;
    protected static ArrayList<Body> listanegra;

    //variables jugador
    protected Personaje jugador = null;
    protected Body cuerpoJugador = null;
    protected Vector2 posicionJugador = null;

    //Objetivo nivel
    private static int cofresRecogidos=0;
    private static int cofresTotales=0;

    //Fisicas
    protected World mundo;
    protected Box2DDebugRenderer depurador;

    public PantallaAccion() {
        super();
        am.load("Dungeon_character_2.png", Texture.class);
        am.load("Dungeon_Tileset.png", Texture.class);
        am.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        am.load(archivoNivel, TiledMap.class);
        am.load("DungeonFont.fnt", BitmapFont.class);
        am.finishLoading();
        mapa = am.get(archivoNivel);

        //Pantalla
        renderizador = new OrthogonalTiledMapRenderer(mapa, 1.0f / Utiles.LADO_LOSA);
        relacionAspecto = (float) juego.ANCHO / juego.ALTO;
//        camara.setToOrtho(false, 10 * relacionAspecto, 10);
        vista=new FitViewport(10*relacionAspecto,10,camara);
        vista.setScreenBounds(0,0,juego.ANCHO,juego.ALTO);

        hud=new FitViewport(juego.ANCHO*10,juego.ALTO*10,camara);
        hud.setScreenBounds(0,0,100,100);

        //Hud
        Label.LabelStyle estiloLabel =new Label.LabelStyle();
        estiloLabel.font = juego.font;
        labelCofres=new Label("Cofres: "+cofresRecogidos+"/"+cofresTotales,estiloLabel);
        vida=new Label("hp:",estiloLabel);

        vida.setSize(1,1);
        vida.setPosition(1,1,1);
        labelCofres.setSize(1,1);
        labelCofres.setPosition(1,1,1);

        //Físicas
        mundo = new World(new Vector2(0, 0), true);
        depurador = new Box2DDebugRenderer();

        //arrays para ojetos del mapa
        listaMuros = new ArrayList<>();
        listaCofres = new ArrayList<>();
        listaEnemigos = new ArrayList<>();
        listanegra = new ArrayList<>();

        //genera objetos estaticos
        capa = (TiledMapTileLayer) mapa.getLayers().get(1);
        for (int x = 0; x < capa.getWidth(); x++) {
            for (int y = 0; y < capa.getHeight(); y++) {
                TiledMapTileLayer.Cell celda = capa.getCell(x, y);
                if (celda == null) continue;
                MapProperties propiedades = celda.getTile().getProperties();
                if (propiedades.containsKey(Utiles.TIPO)) {
                    switch ((String) propiedades.get(Utiles.TIPO)) {

                        case Utiles.MURO:
                            listaMuros.add(new MuroDungeon(mundo, x, y, celda.getTile().getTextureRegion()));
                            break;
                    }
                }
            }
        }
        //genera objetos inteligentes
        capa = (TiledMapTileLayer) mapa.getLayers().get(2);
        for (int x = 0; x < capa.getWidth(); x++) {
            for (int y = 0; y < capa.getHeight(); y++) {
                TiledMapTileLayer.Cell celda = capa.getCell(x, y);
                if (celda == null) continue;
                MapProperties propiedades = celda.getTile().getProperties();
                if (propiedades.containsKey(Utiles.TIPO)) {
                    switch ((String) propiedades.get(Utiles.TIPO)) {
                        case Utiles.JUGADOR:
                            jugador = new Personaje(mundo, x, y, celda.getTile().getTextureRegion());
                            cuerpoJugador = jugador.getCuerpo();
                            break;
                        case Utiles.COFRE:
                            listaCofres.add(new Cofre(mundo, x, y, celda.getTile().getTextureRegion(), this));
                            cofresTotales++;
                            break;
                        case Utiles.ESQUELETO:
                            Random r = new Random();
                            int VidaEnemigo = r.nextInt(dificultad.getVidaMax() - dificultad.getVidaMin()) + dificultad.getVidaMin();
                            listaEnemigos.add(new Esqueleto(mundo, x, y, celda.getTile().getTextureRegion(), VidaEnemigo, dificultad.getDamageEnemigo(), this));
                            break;

                    }
                }
            }
        }
        //chequear areas de interaccion
        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture compA = contact.getFixtureA();
                Fixture compB = contact.getFixtureB();
                if (!compA.getUserData().equals("area_interacciones")&&!compB.getUserData().equals("area_interacciones") &&

                        !compA.getUserData().equals("area_ataque")&&!compB.getUserData().equals("area_ataque")&&

                        !compA.getUserData().equals("vision_enemiga")&&!compB.getUserData().equals("vision_enemiga")&&

                        !compA.getUserData().equals("alcance_e")&&!compB.getUserData().equals("alcance_e")) {

                    return;
                }
                if (compA.getUserData().equals("area_interacciones") && (compB.getUserData() instanceof Cofre)) {
                    ((Cofre) compB.getUserData()).empezar_interactuar();

                } else if (compB.getUserData().equals("area_interacciones") && (compA.getUserData() instanceof Cofre)) {
                    ((Cofre) compA.getUserData()).empezar_interactuar();

                }

                if (compA.getUserData().equals("area_ataque") && (compB.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compB.getUserData()).empezar_interactuar();

                } else if (compB.getUserData().equals("area_ataque") && (compA.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compA.getUserData()).empezar_interactuar();

                }

                if (compA.getUserData().equals("vision_enemiga") && (compB.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compB.getUserData()).setEstadoAlerta(true);



                } else if (compB.getUserData().equals("vision_enemiga") && (compA.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compA.getUserData()).setEstadoAlerta(true);

                }

                if (compA.getUserData().equals("alcance_e") && (compB.getUserData() instanceof Enemigo)) {
                    int a= ((Enemigo)compB.getUserData()).getDp();
                    jugador.setPuede_recivir_ataque(true);
                    jugador.setDaño_recivido(a);


                } else if (compB.getUserData().equals("alcance_e") && (compA.getUserData() instanceof Enemigo)) {
                    int a= ((Enemigo)compA.getUserData()).getDp();
                    jugador.setPuede_recivir_ataque(true);
                    jugador.setDaño_recivido(a);


                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture compA = contact.getFixtureA();
                Fixture compB = contact.getFixtureB();
                if (
                        !compA.getUserData().equals("area_interacciones")&&!compB.getUserData().equals("area_interacciones") &&

                        !compA.getUserData().equals("area_ataque")&&!compB.getUserData().equals("area_ataque")&&

                        !compA.getUserData().equals("vision_enemiga")&&!compB.getUserData().equals("vision_enemiga")&&

                        !compA.getUserData().equals("alcance_e")&&!compB.getUserData().equals("alcance_e")
                ) {
                    return;
                }


                if (compA.getUserData().equals("area_interacciones") && (compB.getUserData() instanceof Cofre)) {
                    ((Cofre) compB.getUserData()).dejar_interactuar();

                } else if (compB.getUserData().equals("area_interacciones") && (compA.getUserData() instanceof Cofre)) {
                    ((Cofre) compA.getUserData()).dejar_interactuar();

                }


                if (compA.getUserData().equals("area_ataque") && (compB.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compB.getUserData()).dejar_interactuar();

                } else if (compB.getUserData().equals("area_ataque") && (compA.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compA.getUserData()).dejar_interactuar();

                }
                if (compA.getUserData().equals("alcance_e") && (compB.getUserData() instanceof Enemigo)) {
                    jugador.setPuede_recivir_ataque(false);
                    jugador.setDaño_recivido(0);


                } else if (compB.getUserData().equals("alcance_e") && (compA.getUserData() instanceof Enemigo)) {
                    jugador.setPuede_recivir_ataque(false);
                    jugador.setDaño_recivido(0);


                }
                if (compA.getUserData().equals("vision_enemiga") && (compB.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compB.getUserData()).setEstadoAlerta(false);




                } else if (compB.getUserData().equals("vision_enemiga") && (compA.getUserData() instanceof Enemigo)) {
                    ((Enemigo) compA.getUserData()).setEstadoAlerta(false);

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
        posicionJugador = cuerpoJugador.getPosition();

        //controles de teclado
        if (!Gdx.input.isKeyPressed(Input.Keys.A) && cuerpoJugador.getLinearVelocity().x < 0) {
            cuerpoJugador.setLinearVelocity(0f, cuerpoJugador.getLinearVelocity().y);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.D) && cuerpoJugador.getLinearVelocity().x > 0) {
            cuerpoJugador.setLinearVelocity(0f, cuerpoJugador.getLinearVelocity().y);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && cuerpoJugador.getLinearVelocity().y > 0) {
            cuerpoJugador.setLinearVelocity(cuerpoJugador.getLinearVelocity().x, 0f);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.S) && cuerpoJugador.getLinearVelocity().y < 0) {
            cuerpoJugador.setLinearVelocity(cuerpoJugador.getLinearVelocity().x, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) &&
                cuerpoJugador.getLinearVelocity().x > -Utiles.VEL_MAX) {
            cuerpoJugador.applyLinearImpulse(Utiles.PASO_IZQUIERDA, posicionJugador, true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) &&
                cuerpoJugador.getLinearVelocity().y < Utiles.VEL_MAX) {
            cuerpoJugador.applyLinearImpulse(Utiles.PASO_ARRIBA, posicionJugador, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) &&
                cuerpoJugador.getLinearVelocity().y > -Utiles.VEL_MAX) {
            cuerpoJugador.applyLinearImpulse(Utiles.PASO_ABAJO, posicionJugador, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) &&
                cuerpoJugador.getLinearVelocity().x < Utiles.VEL_MAX) {
            cuerpoJugador.applyLinearImpulse(Utiles.PASO_DERECHA, posicionJugador, true);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            juego.cambiarPantalla(this,new PantallaMenuPrincipal());
        }

        //controles de raton
        if (Gdx.input.justTouched()) {
            Vector2 puntoClick = juego.getVista().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            for (int i = 0; i < listaCofres.size(); i++) {
                if (listaCofres.get(i).getAreaClick().contains(puntoClick.x, puntoClick.y)) {
                    listaCofres.get(i).usar();
                }

            }
            for (int i = 0; i < listaEnemigos.size(); i++) {
                if (listaEnemigos.get(i).getAreaClick().contains(puntoClick.x, puntoClick.y)) {
                    if (jugador.isPuede_atacar()) {
                        listaEnemigos.get(i).recivirAtaque(jugador.getDp());
                        listaEnemigos.get(i).cambiar_color();
                        jugador.consumir_ataque();
                    }
                }

            }
        }
    }

    @Override
    public void actualizar(float delta) {
        tiempo += delta;
        if (cofresRecogidos>=cofresTotales){
            prefs.putString(nombreNivel,nombreNivel+" conseguido en "+tiempo+" segundos");
            prefs.flush();

            juego.cambiarPantalla(this,new PantallaMenuPrincipal());
        }
        if (jugador.getHp()<=0){
            juego.cambiarPantalla(this,new PantallaMenuPrincipal());
        }
        renderizador.setView(camara);
        jugador.actualizar(delta,jugador.getCuerpo().getWorldCenter());

        //actualizar estado de objetos
        for (int i = 0; i < listaCofres.size(); i++) listaCofres.get(i).actualizar(delta);
        for (int i = 0; i < listaEnemigos.size(); i++) {
            Vector2 pos= listaEnemigos.get(i).getCuerpo().getWorldCenter();
            listaEnemigos.get(i).actualizar(delta,pos);
        }

        //camara sigue jugador
        camara.position.x = MathUtils.clamp(cuerpoJugador.getPosition().x, 5 * relacionAspecto, capa.getWidth() - 5 * relacionAspecto);
        camara.position.y = MathUtils.clamp(cuerpoJugador.getPosition().y, 5, capa.getHeight() - 5);
        camara.update();
        //objetivo de nivel
        labelCofres.setText("Cofres: "+cofresRecogidos+"/"+cofresTotales);
    }

    @Override
    public void dibujar(float delta) {
        //dibujar mapa fondo
        vista.apply();
        int[] capas = {0};
        renderizador.render(capas);
        sb.setProjectionMatrix(camara.combined);

        //dibujar objetos
        sb.begin();
        for (Muro b : listaMuros) b.draw(sb);
        for (Cofre i : listaCofres) i.draw(sb);
        for (Enemigo i : listaEnemigos) i.draw(sb);
        jugador.draw(sb);
        sb.end();
//Hu




        //dibujar depurador (debug de colisiones)
        //depurador.render(mundo, camara.combined); //dibuja las lineas del debuger
        mundo.step(.02f, 6, 2);

        //borrar objetos destruidos
        for (Body cuerpo : listanegra) {
            mundo.destroyBody(cuerpo);
        }
        listanegra.clear();
    }

    public static void eliminarcofre(Interactuables i) {
        listaCofres.remove(i);
        listanegra.add(i.getCuerpo());
        cofresRecogidos++;
    }

    public static void eliminarEnemigo(Enemigo e) {
        listaEnemigos.remove(e);
        listanegra.add(e.getCuerpo());
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
//        vista.update(width,height,true);
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
    public Personaje personajeref(){
        return this.jugador;
    }
}
