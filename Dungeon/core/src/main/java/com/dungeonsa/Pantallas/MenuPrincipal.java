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
import com.dungeonsa.Dificultad;
import com.dungeonsa.Juego;
import com.dungeonsa.Utiles;

public class MenuPrincipal {
    private final Juego juego;
    private final FitViewport vista;
    private final Stage escenario;
    private Skin skin;
    private Preferences prefs= Gdx.app.getPreferences(Utiles.prefer);
    private Label nivel1, nivel2, nivel3;
    private TextButton boton1, boton2, boton3, boton4;

    public MenuPrincipal(SpriteBatch sb,PantallaMenuPrincipal pantallaMenu){
        juego= (Juego)(Gdx.app.getApplicationListener());
        vista=new FitViewport(juego.ANCHO,juego.ALTO,new OrthographicCamera());
        escenario=new Stage(vista,sb);

        //==================INFO DE PANTALLA======================//

        Label.LabelStyle estiloLabel =new Label.LabelStyle();
        estiloLabel.font = juego.font;

        nivel1=new Label(prefs.getString("Mazmorra Rome","Nivel Alvaro: No completado"),estiloLabel);
        nivel2=new Label(prefs.getString("Mazmorra Raul","Nivel Raul: No completado"),estiloLabel);
        nivel3=new Label(prefs.getString("Mazmorra Basilio","Nivel Basilio: No completado"),estiloLabel);

        Table puntuacion=new Table();
        puntuacion.left();
        puntuacion.setFillParent(true);
        puntuacion.add(nivel1);
        puntuacion.row();
        puntuacion.add(nivel2);
        puntuacion.row();
        puntuacion.add(nivel3);

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

        boton1= new TextButton("Mazmorra Alvaro",estiloBoton);
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.cambiarPantalla(pantallaMenu,new PantallaAccion(Utiles.dungRomeF,Utiles.dungRomeN, Dificultad.FACIL));
            }
        });
        boton2= new TextButton("Mazmorra Raul",estiloBoton);
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.cambiarPantalla(pantallaMenu,new PantallaAccion(Utiles.dungRaulF,Utiles.dungRaulN, Dificultad.FACIL));
            }
        });
        boton3= new TextButton("Mazmorra Basilio",estiloBoton);
        boton3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.cambiarPantalla(pantallaMenu,new PantallaAccion(Utiles.dungBasilioF,Utiles.dungBasilioN, Dificultad.FACIL));
            }
        });
        boton4= new TextButton("Salir",estiloBoton);
        boton4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        Table tabla=new Table();
        tabla.right();
        tabla.setFillParent(true);
        tabla.add(boton1).size(500,50).padRight(100);
        tabla.row();
        tabla.add(boton2).size(500,50).padRight(100);
        tabla.row();
        tabla.add(boton3).size(500,50).padRight(100);
        tabla.row();
        tabla.add(boton4).size(500,50).padRight(100);

        escenario.addActor(puntuacion);
        escenario.addActor(tabla);


        Gdx.input.setInputProcessor(escenario);
    }

    public void actualizar(float delta, int saltos){
        escenario.act(delta);
    }

    //-------------------------------------------------------------
    public FitViewport getVista() {
        return vista;
    }

    public Stage getEscenario() {
        return escenario;
    }
}
