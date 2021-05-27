package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Juego;

public class Hud {
    private Juego juego;
    private FitViewport vista;
    private Stage escenario;
    private Label cofres, vida, tiempo;
    private float segundos;

    public Hud(SpriteBatch sb){
        juego= (Juego)(Gdx.app.getApplicationListener());
        vista=new FitViewport(juego.ANCHO,juego.ALTO,new OrthographicCamera());

        escenario=new Stage(vista,sb);

        Label.LabelStyle estiloLabel =new Label.LabelStyle();
        estiloLabel.font = juego.font;
        cofres=new Label("Cofres: 0/0",estiloLabel);
        vida=new Label("hp: 0",estiloLabel);
        tiempo=new Label("0",estiloLabel);

        Table tabla=new Table();
        tabla.top();
        tabla.setFillParent(true);
        tabla.add(cofres).left();
        tabla.row();
        tabla.add(vida).padLeft(1);
        tabla.row();
        tabla.add(tiempo).padLeft(1);

        escenario.addActor(tabla);
    }

    public void actualizar(float delta, int cofresActuales, int cofresRestantes, int vidaJugador){
        segundos+=delta;
        cofres.setText("Cofres: "+cofresActuales+"/"+cofresRestantes);
        vida.setText("hp:"+vidaJugador);
        tiempo.setText((int)segundos);
    }

    //-------------------------------------------------------------
    public FitViewport getVista() {
        return vista;
    }

    public float getSegundos() {
        return segundos;
    }

    public Stage getEscenario() {
        return escenario;
    }
}
