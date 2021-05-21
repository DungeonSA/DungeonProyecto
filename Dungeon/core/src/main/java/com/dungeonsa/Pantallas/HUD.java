package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Juego;

public class HUD {
    private Juego juego;
    private FitViewport vista;
    private Stage escenario;
    private Label etiquetaTiempo, valorTiempo, etiquetaSaltos, valorSaltos;
    private float tiempo;
    private TextButton boton;
    private boolean mapaVisible;

    public HUD(SpriteBatch sb){
        juego= (Juego)(Gdx.app.getApplicationListener());
        vista=new FitViewport(PantallaAccion.ANCHO_PANEL*1.5f,
                PantallaAccion.ALTO_HUD,new OrthographicCamera());

        escenario=new Stage(vista,sb);

        BitmapFont miFuente=juego.getAm().get("DungeonFont.fnt");
        Label.LabelStyle miEstilo=new Label.LabelStyle(miFuente, Color.LIME);
        etiquetaTiempo=new Label("SEGUNDOS",miEstilo);
        valorTiempo=new Label("0",miEstilo);
        etiquetaSaltos=new Label("SALTOS",miEstilo);
        valorSaltos=new Label("0",miEstilo);

        TextButton.TextButtonStyle estiloBoton=new TextButton.TextButtonStyle();
        estiloBoton.font=miFuente;
        estiloBoton.fontColor=Color.WHITE;
        estiloBoton.overFontColor=Color.CYAN;
        estiloBoton.downFontColor=Color.ORANGE;
        boton= new TextButton("MAPA",estiloBoton);
        boton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mapaVisible=!mapaVisible;
            }
        });

        Table tabla=new Table();
        tabla.top();
        tabla.setFillParent(true);
        tabla.add(etiquetaTiempo).padTop(PantallaAccion.ALTO_HUD/10);
        tabla.row();
        tabla.add(valorTiempo);
        tabla.row();
        tabla.add(etiquetaSaltos).padTop(PantallaAccion.ALTO_HUD/10);
        tabla.row();
        tabla.add(valorSaltos);
        tabla.row();
        tabla.add(boton).padTop(PantallaAccion.ALTO_HUD/5);

        escenario.addActor(tabla);

        tiempo=0;
        mapaVisible=false;

        Gdx.input.setInputProcessor(escenario);
    }

    public void actualizar(float delta, int saltos){
        tiempo+=delta;
        valorTiempo.setText((int)tiempo);
        valorSaltos.setText(saltos);

        escenario.act(delta);
    }

    //-------------------------------------------------------------
    public FitViewport getVista() {
        return vista;
    }

    public Stage getEscenario() {
        return escenario;
    }

    public boolean isMapaVisible() {
        return mapaVisible;
    }
}
