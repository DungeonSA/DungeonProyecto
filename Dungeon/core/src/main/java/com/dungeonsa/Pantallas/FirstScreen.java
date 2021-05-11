package com.dungeonsa.Pantallas;


import com.dungeonsa.Entorno.Muro;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dungeonsa.Personajes.Jugador;
import com.badlogic.gdx.physics.box2d.World;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends Pantalla {
	public static final String TIPO="tipo";
	public static final String JUGADOR="mainchar";
	public static final String MURO="nada";
	public static final int LADO_LOSA=16;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderizador;

	public static final Vector2 PASO_ARRIBA=new Vector2(0,.1f);
	public static final Vector2 PASO_ABAJO=new Vector2(0,-.1f);
	public static final Vector2 PASO_DERECHA=new Vector2(.1f,0);
	public static final Vector2 PASO_IZQUIERDA=new Vector2(-.1f,0);
	public static float VEL_MAX=2.0f;
	private Jugador jugador=null;
	private Body cuerpoJugador=null;
	private Vector2 posicionJugador=null;

	TextureAtlas atlas;
	//Fisicas
	private World mundo;
	private Box2DDebugRenderer depurador;


	public FirstScreen() {
//		super();
//		mapa=am.get("");
//		renderizador=new OrthogonalTiledMapRenderer(mapa, 1.0f/LADO_LOSA);
//
//		//Fisicas
//		mundo=new World(new Vector2(0,-3),true);
//
//		//Procesa la capa 2 del mapa, la que tiene los "objetos"
		super();
//		atlas=am.get("Graficos.atlas");
		mapa=am.get("mapadev.tmx");
		renderizador=new OrthogonalTiledMapRenderer(mapa,1.0f/LADO_LOSA);
		float relacionAspecto= (float)juego.getAncho()/juego.getAlto();
		camara.setToOrtho(false,10*relacionAspecto,10);

		//Físicas
		mundo=new World(new Vector2(0,0),true);
		depurador=new Box2DDebugRenderer();
		//Procesa la capa 2 del mapa, la que tiene los "objetos"
		TiledMapTileLayer capa=(TiledMapTileLayer)mapa.getLayers().get(2);
		for(int x=0;x<capa.getWidth();x++){
			for(int y=0;y<capa.getHeight();y++){
				TiledMapTileLayer.Cell celda=capa.getCell(x,y);
				if(celda==null) continue;
				MapProperties propiedades=celda.getTile().getProperties();
				if(propiedades.containsKey(TIPO)){
					switch((String)propiedades.get(TIPO)){
						case JUGADOR:
							jugador=new Jugador(mundo,atlas,x,y);
							cuerpoJugador=jugador.getCuerpo();
							break;
						case MURO:
							new Muro(mundo,atlas,x,y);
							break;
					}
				}
			}
		}
	}

	@Override
	public void leerEntrada(float delta) {
		posicionJugador=cuerpoJugador.getPosition();
//		if(Gdx.input.isKeyPressed(Input.Keys.A)){
//			Jugador.moverIzquierda();
//		}if(Gdx.input.isKeyPressed(Input.Keys.W)){
//			Jugador.moverArriba();
//		}if(Gdx.input.isKeyPressed(Input.Keys.S)){
//			Jugador.moverAbajo();
//		}if(Gdx.input.isKeyPressed(Input.Keys.D)){
//			Jugador.moverDerecha();
//		}
	}

	@Override
	public void actualizar(float delta) {
		renderizador.setView(camara);
		camara.update();
	}

	@Override
	public void dibujar(float delta) {
		int[] capas={0,1};
		renderizador.render(capas);

		sb.setProjectionMatrix(camara.combined);
		sb.begin();

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

	@Override
	public void dispose() {

	}
}