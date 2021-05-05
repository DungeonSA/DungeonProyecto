package com.dungeonsa.Pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.dungeonsa.Personajes.Jugador;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends Pantalla {
	private Texture fondo;


	public FirstScreen() {
		super();
	}

	@Override
	public void leerEntrada(float delta) {
		if(Gdx.input.isButtonPressed(Input.Keys.A)){
			Jugador.moverIzquierda();
		}if(Gdx.input.isButtonPressed(Input.Keys.W)){
			Jugador.moverArriba();
		}if(Gdx.input.isButtonPressed(Input.Keys.S)){
			Jugador.moverAbajo();
		}if(Gdx.input.isButtonPressed(Input.Keys.D)){
			Jugador.moverDerecha();
		}
	}

	@Override
	public void actualizar(float delta) {

	}

	@Override
	public void dibujar(float delta) {

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