package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Victoria extends Pantalla{

	private Texture fondo;

	private OrthographicCamera CamaraMenu;
	private MenuVictoria hudMenuVictoria = new MenuVictoria(sb,this);

	public Victoria() {
		super();
		//PANTALLA
		CamaraMenu=new OrthographicCamera();
		CamaraMenu.setToOrtho(false,juego.ANCHO,juego.ALTO);
		vista=new FitViewport(juego.ANCHO,juego.ALTO,CamaraMenu);
		vista.setScreenBounds(0,0,juego.ANCHO,juego.ALTO);
		//Asset Manager
		am.load("Victoria.png",Texture.class);
		am.finishLoading();
		fondo=am.get("Victoria.png");
	}

	@Override
	public void show() {
	}

	@Override
	public void leerEntrada(float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}
	@Override
	public void actualizar(float delta) {
		camara.update();
	}

	@Override
	public void dibujar(float delta) {
		sb.setProjectionMatrix(CamaraMenu.combined);
		vista.apply();
		sb.begin();
		sb.draw(fondo,0,0,juego.ANCHO,juego.ALTO);
		sb.end();

		hudMenuVictoria.getVista().apply();
		hudMenuVictoria.getEscenario().draw();
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
}