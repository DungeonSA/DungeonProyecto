package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Juego;

public class PantallaMenuPrincipal extends Pantalla{

	private Texture fondo,boton1,boton2,boton3,boton4;
	private Vector2 Pboton1,Pboton2,Pboton3,Pboton4;
	private Rectangle Aboton1,Aboton2,Aboton3,Aboton4;

	private Label puntuaciones;
	private Label nivel1;
	private Label nivel2;
	private Label nivel3;

	Preferences prefs= Gdx.app.getPreferences("preferences");

	public PantallaMenuPrincipal() {
		super();
		//PANTALLA
		camara.setToOrtho(false,juego.ANCHO,juego.ALTO);
		vista=new FitViewport(juego.ANCHO,juego.ALTO,camara);
		vista.setScreenBounds(0,0,juego.ANCHO,juego.ALTO);


		Label.LabelStyle estiloLabel =new Label.LabelStyle();
		estiloLabel.font = juego.font;

		puntuaciones=new Label("PUNTUACIONES",estiloLabel);
		nivel1=new Label(prefs.getString("Mazmorra Alvaro","Nivel Alvaro: No completado"),estiloLabel);
		nivel2=new Label(prefs.getString("Mazmorra Raul","Nivel Raul: No completado"),estiloLabel);
		nivel3=new Label(prefs.getString("Mazmorra Basilio","Nivel Basilio: No completado"),estiloLabel);
		puntuaciones.setSize(1,1);
		puntuaciones.setPosition(10,340);
		nivel1.setPosition(10,270);
		nivel2.setPosition(10,230);
		nivel3.setPosition(10,190);
		//Asset Manager
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
		//Ubicar botones
		Pboton1=new Vector2(juego.ANCHO/2-boton1.getWidth()/2,juego.ALTO/11*5-boton1.getHeight()/2);
		Pboton2=new Vector2(juego.ANCHO/2-boton2.getWidth()/2,juego.ALTO/11*4-boton2.getHeight()/2);
		Pboton3=new Vector2(juego.ANCHO/2-boton3.getWidth()/2,juego.ALTO/11*3-boton3.getHeight()/2);
		Pboton4=new Vector2(juego.ANCHO/2-boton4.getWidth()/2,juego.ALTO/11*2-boton4.getHeight()/2);
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
		sb.setProjectionMatrix(camara.combined);
		vista.apply();
		sb.begin();
		sb.draw(fondo,0,0,juego.ANCHO,juego.ALTO);
		sb.draw(boton1,Pboton1.x,Pboton1.y);
		sb.draw(boton2,Pboton2.x,Pboton2.y);
		sb.draw(boton3,Pboton3.x,Pboton3.y);
		sb.draw(boton4,Pboton4.x,Pboton4.y);
		puntuaciones.draw(sb,100);
		nivel1.draw(sb,100);
		nivel2.draw(sb,100);
		nivel3.draw(sb,100);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
//		vista.update(width,height,true);
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