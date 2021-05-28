package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Juego;
import com.dungeonsa.Utiles;

public class MenuVictoria {
    private final Juego juego;
    private final FitViewport vista;
    private final Stage escenario;
    private Skin skin;
    private Preferences prefs= Gdx.app.getPreferences(Utiles.prefer);
    private Label nivel1, nivel2, nivel3;
    private TextButton boton1, boton2, boton3, boton4;

    public MenuVictoria(SpriteBatch sb, Victoria pantallaVictoria){
        juego= (Juego)(Gdx.app.getApplicationListener());
        vista=new FitViewport(juego.ANCHO,juego.ALTO,new OrthographicCamera());
        escenario=new Stage(vista,sb);

        //==================INFO DE PANTALLA======================//

        Label.LabelStyle estiloLabel =new Label.LabelStyle();
        estiloLabel.font = juego.font;





        //===============BOTONES DE INTERACCION====================//

        TextButton.TextButtonStyle estiloBoton=new TextButton.TextButtonStyle();
        skin=new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("botonTex.atlas")));
        estiloBoton.font=juego.font;
        estiloBoton.up = skin.getDrawable("BotonMenu");
        estiloBoton.down = skin.getDrawable("BotonMenu");
        estiloBoton.checked = skin.getDrawable("BotonMenu");
        estiloBoton.fontColor=Color.WHITE;
        estiloBoton.overFontColor=Color.GRAY;
        estiloBoton.downFontColor=Color.ORANGE;

        boton1= new TextButton("Menu",estiloBoton);
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.cambiarPantalla(pantallaVictoria,new PantallaMenuPrincipal());
            }
        });
        boton2= new TextButton("Salir",estiloBoton);
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        Table tabla=new Table();
        tabla.center();
        tabla.setFillParent(true);
        tabla.add(boton1).size(500,50).padTop(100);
        tabla.row();
        tabla.add(boton2).size(500,50).padTop(100);


        escenario.addActor(tabla);


        Gdx.input.setInputProcessor(escenario);
    }

    //-------------------------------------------------------------
    public FitViewport getVista() {
        return vista;
    }

    public Stage getEscenario() {
        return escenario;
    }
}
