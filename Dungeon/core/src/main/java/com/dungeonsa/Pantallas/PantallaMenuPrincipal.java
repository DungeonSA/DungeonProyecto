package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Juego;

public class PantallaMenuPrincipal extends Pantalla{
	private FitViewport vista;
	private int ancho,alto;

	private Texture fondo,boton1,boton2,boton3,boton4;
	private Vector2 Pboton1,Pboton2,Pboton3,Pboton4;
	private Rectangle Aboton1,Aboton2,Aboton3,Aboton4;

	public PantallaMenuPrincipal() {
		super();
		am.load("boton1.png",Texture.class);
		am.load("boton2.png",Texture.class);
		am.load("boton3.png",Texture.class);
		am.load("boton4.png",Texture.class);
		am.load("Menu.png",Texture.class);
		am.finishLoading();
		fondo=am.get("Menu.png");
		boton1=am.get("boton1.png");
		boton2=am.get("boton2.png");
		boton3=am.get("boton3.png");
		boton4=am.get("boton4.png");
		//PANTALLA
		camara.setToOrtho(false,juego.getAncho(),juego.getAlto());
		ancho= Gdx.graphics.getWidth();
		alto=Gdx.graphics.getHeight();
		vista=new FitViewport(ancho,alto,camara);
		//Ubicar botones
		Pboton1=new Vector2(Juego.ANCHO/2-boton1.getWidth()/2,Juego.ALTO/11*5-boton1.getHeight()/2);
		Pboton2=new Vector2(Juego.ANCHO/2-boton2.getWidth()/2,Juego.ALTO/11*4-boton2.getHeight()/2);
		Pboton3=new Vector2(Juego.ANCHO/2-boton3.getWidth()/2,Juego.ALTO/11*3-boton3.getHeight()/2);
		Pboton4=new Vector2(Juego.ANCHO/2-boton4.getWidth()/2,Juego.ALTO/11*2-boton4.getHeight()/2);
		//Definir areas de interaccion
		Aboton1=new Rectangle((int)Pboton1.x,(int)Pboton1.y,boton1.getWidth(),boton1.getHeight());
		Aboton2=new Rectangle((int)Pboton2.x,(int)Pboton2.y,boton2.getWidth(),boton2.getHeight());
		Aboton3=new Rectangle((int)Pboton3.x,(int)Pboton3.y,boton3.getWidth(),boton3.getHeight());
		Aboton4=new Rectangle((int)Pboton4.x,(int)Pboton4.y,boton4.getWidth(),boton4.getHeight());
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
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
			juego.cambiarPantalla(this,new PantallaBasilio());
		}else if(Gdx.input.justTouched()){
			Vector2 puntoClick=juego.getVista().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
			if(Aboton1.contains(puntoClick.x, puntoClick.y)){
				juego.cambiarPantalla(this,new PantallaRome());
			}else if(Aboton2.contains(puntoClick.x, puntoClick.y)){
				juego.cambiarPantalla(this,new Pantalladev());
			}else if(Aboton3.contains(puntoClick.x, puntoClick.y)){
				juego.cambiarPantalla(this,new PantallaBasilio());
			}else if(Aboton4.contains(puntoClick.x,puntoClick.y)){
				Gdx.app.exit();
			}
		}
	}
	@Override
	public void actualizar(float delta) {
		camara.update();
	}

	@Override
	public void dibujar(float delta) {
		sb.begin();
		sb.draw(fondo,0,0,juego.getAncho(),juego.getAlto());
		sb.draw(boton1,Pboton1.x,Pboton1.y);
		sb.draw(boton2,Pboton2.x,Pboton2.y);
		sb.draw(boton3,Pboton3.x,Pboton3.y);
		sb.draw(boton4,Pboton4.x,Pboton4.y);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		vista.update(width,height,true);
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