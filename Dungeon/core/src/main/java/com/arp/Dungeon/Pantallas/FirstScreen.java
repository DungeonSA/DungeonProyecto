package com.dungeonsa.Pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dungeonsa.Personajes.Jugador;
import com.badlogic.gdx.physics.box2d.World;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends Pantalla {
	public static final int LADO_LOSA=32;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderizador;
	//Fisicas
	private World mundo;


	public FirstScreen() {
		super();
		mapa=am.get("");
		renderizador=new OrthogonalTiledMapRenderer(mapa, 1.0f/LADO_LOSA);

		//Fisicas
		mundo=new World(new Vector2(0,-3),true);

		//Procesa la capa 2 del mapa, la que tiene los "objetos"
	}

	@Override
	public void leerEntrada(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			Jugador.moverIzquierda();
		}if(Gdx.input.isKeyPressed(Input.Keys.W)){
			Jugador.moverArriba();
		}if(Gdx.input.isKeyPressed(Input.Keys.S)){
			Jugador.moverAbajo();
		}if(Gdx.input.isKeyPressed(Input.Keys.D)){
			Jugador.moverDerecha();
		}
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