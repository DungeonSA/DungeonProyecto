package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.dungeonsa.Entorno.Muro;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dungeonsa.Entorno.Muro1;
import com.dungeonsa.Personajes.Jugador;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class PantallaRome extends Pantalla {
	public static final String TIPO="tipo";
	public static final String JUGADOR="jugador";
	public static final String MURO="nada";
	public static final int LADO_LOSA=16;
	private TiledMap mapa;
	private TiledMapTileLayer capa;
	private float relacionAspecto;
	private OrthogonalTiledMapRenderer renderizador;

	private ArrayList<Muro> listaMuros;

	public static final Vector2 PASO_ARRIBA=new Vector2(0,1f);
	public static final Vector2 PASO_ABAJO=new Vector2(0,-1f);
	public static final Vector2 PASO_DERECHA=new Vector2(1f,0);
	public static final Vector2 PASO_IZQUIERDA=new Vector2(-1f,0);
	public static float VEL_MAX=3.0f;

	private Jugador jugador=null;
	private Body cuerpoJugador=null;
	private Vector2 posicionJugador=null;

	private Vector2 inputVector;
	private Vector2 velocidad;

	TextureAtlas atlas;
//    Fisicas
	private World mundo;
	private Box2DDebugRenderer depurador;


    public PantallaRome() {
		super();
		am.load("0x72_16x16DungeonTileset.v4.png", Texture.class);
		am.setLoader(TiledMap.class,
				new TmxMapLoader(new InternalFileHandleResolver()));
		am.load("pruevasolo.tmx", TiledMap.class);
		am.finishLoading();
		mapa=am.get("pruevasolo.tmx");

		renderizador=new OrthogonalTiledMapRenderer(mapa,1.0f/LADO_LOSA);
		relacionAspecto= (float)juego.getAncho()/juego.getAlto();
		camara.setToOrtho(false,10*relacionAspecto,10);

		//Físicas
		mundo=new World(new Vector2(0,0),true);
		depurador=new Box2DDebugRenderer();
		//Procesa la capa 2 del mapa, la que tiene los "objetos"
		listaMuros=new ArrayList<>();
		capa=(TiledMapTileLayer)mapa.getLayers().get(0);
		for(int x=0;x<capa.getWidth();x++){
			for(int y=0;y<capa.getHeight();y++){
				TiledMapTileLayer.Cell celda=capa.getCell(x,y);
				if(celda==null) continue;
				MapProperties propiedades=celda.getTile().getProperties();
				if(propiedades.containsKey(TIPO)){
					switch((String)propiedades.get(TIPO)){
						case MURO:
							listaMuros.add(new Muro1(mundo,x,y));
							break;
					}
				}
			}
		}
		capa=(TiledMapTileLayer)mapa.getLayers().get(1);
		for(int x=0;x<capa.getWidth();x++) {
			for (int y = 0; y < capa.getHeight(); y++) {
				TiledMapTileLayer.Cell celda = capa.getCell(x, y);
				if (celda == null) continue;
				MapProperties propiedades = celda.getTile().getProperties();
				if (propiedades.containsKey(TIPO)) {
					switch ((String) propiedades.get(TIPO)) {
						case JUGADOR:
							jugador = new Jugador(mundo, x, y);
							cuerpoJugador = jugador.getCuerpo();
							break;
					}
				}
			}
		}
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

        //La camara sigue al jugador
		camara.position.x=MathUtils.clamp(cuerpoJugador.getPosition().x,5*relacionAspecto,capa.getWidth()-5*relacionAspecto);
		camara.position.y=MathUtils.clamp(cuerpoJugador.getPosition().y,5,capa.getHeight()-5);
        camara.update();
    }

    @Override
    public void dibujar(float delta) {
    	//render basico para capas de fondo
        int[] capas={0,1};
        renderizador.render(capas);

        sb.setProjectionMatrix(camara.combined);
        sb.begin();
//		for(Muro b:listaMuros) b.draw(sb);
//		jugador.draw(sb);
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
