package com.dungeonsa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Pantallas.FirstScreen;
import com.dungeonsa.Pantallas.Pantalla;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {
	public static final int ANCHO=450;
	public static final int ALTO=800;
	public static final String TITULO="Dungeon";
	public static final String ICONO="icono.png";
	private int ancho,alto;

	private OrthographicCamera camara;
	private FitViewport vista;
	private SpriteBatch sb;
	private AssetManager am;
	private Pantalla actual;

	@Override
	public void create() {
		camara=new OrthographicCamera();
		ancho= Gdx.graphics.getWidth();
		alto=Gdx.graphics.getHeight();
		vista=new FitViewport(ancho,alto,camara);
		sb=new SpriteBatch();
		am=new AssetManager();
		am.setLoader(TiledMap.class,
				new TmxMapLoader(new InternalFileHandleResolver()));
		am.load("mapadev.tmx", TiledMap.class);
		am.load("Graficos.atlas", TextureAtlas.class);

		cambiarPantalla(null, new FirstScreen());
	}

	@Override
	public void resize(int width, int height) {
		vista.update(width,height,true);
	}

	@Override
	public void dispose() {
		super.dispose();
//		sb.dispose();
//		am.dispose();
	}

	//----------------------------GETTERS---------------------------
	public OrthographicCamera getCamara(){ return camara; }

	public FitViewport getVista() {
		return vista;
	}

	public SpriteBatch getSb() {
		return sb;
	}

	public Pantalla getActual() {
		return actual;
	}

	public AssetManager getAm() { return am; }

	public int getAncho() { return ancho; }

	public int getAlto() { return alto; }

	public void cambiarPantalla(Pantalla antigua, Pantalla nueva){
		if(antigua!=null){
			antigua.dispose();
		}
		setScreen(nueva);
		actual=nueva;
	}
}