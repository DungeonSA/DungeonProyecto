package com.dungeonsa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Pantallas.PantallaMenuPrincipal;
import com.dungeonsa.Pantallas.Pantalla;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {

	public static final String TITULO="Dungeon";
	public static final String ICONO="icono.png";
	public static final int ANCHO=800;
	public static final int ALTO=480;
	private int ancho,alto;
	private OrthographicCamera camara;
	private SpriteBatch sb;
	private AssetManager am;
	public BitmapFont font;
	private FitViewport vista;
	//Archivo de puntuaciones
//	Preferences puntuacion = Gdx.app.getPreferences("My Preferences");

	@Override
	public void create() {
		camara=new OrthographicCamera();
		camara.setToOrtho(false,ANCHO,ALTO);
		ancho= Gdx.graphics.getWidth();
		alto=Gdx.graphics.getHeight();
		sb=new SpriteBatch();
		am=new AssetManager();
		font= new BitmapFont();
		font.setColor(Color.BLACK);
		vista=new FitViewport(ancho,alto,camara);
		cambiarPantalla(null, new PantallaMenuPrincipal());
		//lee Puntuacion

	}

	@Override
	public void resize(int width, int height) {
		vista.update(width,height,true);
	}

	@Override
	public void dispose() {
		super.dispose();
		sb.dispose();
		am.dispose();
	}

	//----------------------------GETTERS---------------------------
	public OrthographicCamera getCamara(){ return camara; }

	public FitViewport getVista() {
		return vista;
	}

	public SpriteBatch getSb() {
		return sb;
	}

	public AssetManager getAm() { return am; }

	public BitmapFont getFont() {
		return font;
	}

	public int getAncho() { return ancho; }

	public int getAlto() { return alto; }

	public void cambiarPantalla(Pantalla antigua, Pantalla nueva){
		if(antigua!=null){
			antigua.dispose();
		}
		setScreen(nueva);
	}
}