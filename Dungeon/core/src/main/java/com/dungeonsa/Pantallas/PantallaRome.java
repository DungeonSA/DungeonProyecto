package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeonsa.Entorno.*;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dungeonsa.Personajes.Jugador;
import com.dungeonsa.Personajes.Personaje;

import java.util.ArrayList;

public class PantallaRome extends Pantalla {
	public static final String TIPO="tipo";
	public static final String JUGADOR="jugador";
	public static final String MURO="muro";
	public static final String ENEMIGO="enemigo";
	public static final String COFRE="cofre";
	public static final int LADO_LOSA=16;
	private TiledMap mapa;
	private TiledMapTileLayer capa;
	private float relacionAspecto;
	private OrthogonalTiledMapRenderer renderizador;

	private ArrayList<Muro> listaMuros;
	private ArrayList<Cofre> listaCofres;

	public static final Vector2 PASO_ARRIBA=new Vector2(0,1f);
	public static final Vector2 PASO_ABAJO=new Vector2(0,-1f);
	public static final Vector2 PASO_DERECHA=new Vector2(1f,0);
	public static final Vector2 PASO_IZQUIERDA=new Vector2(-1f,0);
	public static float VEL_MAX=3.0f;

	private Personaje jugador=null;
	private Body cuerpoJugador=null;
	private Vector2 posicionJugador=null;

//    Fisicas
	private World mundo;
	private Box2DDebugRenderer depurador;


    public PantallaRome() {
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


					}
				}
			}
		}
//chequear areas de interaccion
//		mundo.setContactListener(new ContactListener() {
//			@Override
//			public void beginContact(Contact contact) {
//				Fixture compA= contact.getFixtureA();
//				Fixture compB= contact.getFixtureB();
//				if(!compA.getUserData().equals("area_interacciones") && !compB.getUserData().equals("area")){
//
//					return;
//				}
//				if(compA.getUserData().equals("area_interacciones") && (compB.getUserData() instanceof Cofre)){
//					System.out.println("cofre");
//
//				}else if(compB.getUserData().equals("area_interacciones") && (compA.getUserData() instanceof Cofre)){
//					System.out.println("cofre");
//				}
//			}
//
//			@Override
//			public void endContact(Contact contact) {
//
//			}
//
//			@Override
//			public void preSolve(Contact contact, Manifold oldManifold) {
//
//			}
//
//			@Override
//			public void postSolve(Contact contact, ContactImpulse impulse) {
//
//			}
//		});
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
    }

    @Override
    public void actualizar(float delta) {
        renderizador.setView(camara);
        jugador.actualizar(delta);
		for(Cofre i:listaCofres)i.actualizar(delta);

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
		jugador.draw(sb);
        sb.end();

        depurador.render(mundo, camara.combined); //dibuja las lineas del debuger
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

    @Override
    public void dispose() {

    }
}
