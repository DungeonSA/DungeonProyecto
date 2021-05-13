package com.dungeonsa.Pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends Pantalla {
	private Texture fondo;

	public FirstScreen() {
		super();
<<<<<<< HEAD
		atlas=am.get("Graficos.atlas");
		mapa=am.get("mapadev.tmx");
		renderizador=new OrthogonalTiledMapRenderer(mapa,1.0f/LADO_LOSA);
		float relacionAspecto= (float)juego.getAncho()/juego.getAlto();
		camara.setToOrtho(false,10*relacionAspecto,10);

		//Físicas
		mundo=new World(new Vector2(0,0),true);
		depurador=new Box2DDebugRenderer();
		//Procesa la capa 2 del mapa, la que tiene los "objetos"
		TiledMapTileLayer capa=(TiledMapTileLayer)mapa.getLayers().get(2);
		for(int x=0;x<capa.getWidth();x++){
			for(int y=0;y<capa.getHeight();y++){
				TiledMapTileLayer.Cell celda=capa.getCell(x,y);
				if(celda==null) continue;
				MapProperties propiedades=celda.getTile().getProperties();
				if(propiedades.containsKey(TIPO)){
					switch((String)propiedades.get(TIPO)){
						case JUGADOR:
							jugador=new Jugador(mundo,atlas,x,y);
							cuerpoJugador=jugador.getCuerpo();
							break;
						case MURO:
							new Muro(mundo,atlas,x,y);
							break;
					}
				}
			}
		}
=======
		am.load("Menu.png", Texture.class);
		am.finishLoading();
		fondo=am.get("Menu.png");

	}

	@Override
	public void show() {

>>>>>>> 67b8a983cd7e3f53b8a670575a8eff9aab2edb73
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