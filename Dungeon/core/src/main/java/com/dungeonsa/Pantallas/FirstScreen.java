package com.dungeonsa.Pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends Pantalla {
	private Texture fondo;

	public FirstScreen() {
		super();
		am.load("Menu.png", Texture.class);
		am.finishLoading();
		fondo=am.get("Menu.png");

	}

	@Override
	public void show() {

	}

	@Override
	public void leerEntrada(float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
			juego.cambiarPantalla(this,new Pantalladev());
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
			juego.cambiarPantalla(this,new PantallaRome());
		}
	}

	@Override
	public void actualizar(float delta) {
	}

	@Override
	public void dibujar(float delta) {
		sb.begin();
		sb.draw(fondo,0,0,juego.getAncho(),juego.getAlto());
		sb.end();
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