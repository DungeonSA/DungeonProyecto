package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dungeonsa.Juego;

public abstract class Pantalla implements Screen {
    protected Juego juego;
    protected SpriteBatch sb;
    protected AssetManager am;
    protected FitViewport vista;
    protected OrthographicCamera camara;

    public Pantalla() {
        this.juego=(Juego)Gdx.app.getApplicationListener();
        this.sb= juego.getSb();
        this.am=juego.getAm();
        this.vista=juego.getVista();
        this.camara=juego.getCamara();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerEntrada(delta);
        actualizar(delta);
        dibujar(delta);
    }

    public abstract void leerEntrada(float delta);

    public abstract void actualizar(float delta);

    @Override
    public void resize(int width, int height) {
//        vista.update(width,height,false);
    }

    public abstract void dibujar(float delta);

    @Override
    public void dispose() {
    }
}
