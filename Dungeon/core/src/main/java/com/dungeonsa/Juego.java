package com.dungeonsa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Pantallas.FirstScreen;
import com.dungeonsa.Pantallas.Pantalla;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {
	public static final int ANCHO=450;
	public static final int ALTO=800;
	public static final String TITULO="Pajarraco";
	public static final String ICONO="pepe.png";

	private OrthographicCamera camara;
	private FitViewport vista;
	private SpriteBatch sb;
	private AssetManager am;
	private Pantalla actual;
	private Music musica;

	@Override
	public void create() {
		camara=new OrthographicCamera();
		vista=new FitViewport(ANCHO,ALTO,camara);
		sb=new SpriteBatch();

		cambiarPantalla(null, new FirstScreen());
	}

	@Override
	public void resize(int width, int height) {
		vista.update(width,height,true);
	}

	public FitViewport getVista() {
		return vista;
	}

	public SpriteBatch getSb() {
		return sb;
	}

	public Pantalla getActual() {
		return actual;
	}

	public void cambiarPantalla(Pantalla antigua, Pantalla nueva){
		if(antigua!=null){
			antigua.dispose();
		}
		setScreen(nueva);
		actual=nueva;
	}
}