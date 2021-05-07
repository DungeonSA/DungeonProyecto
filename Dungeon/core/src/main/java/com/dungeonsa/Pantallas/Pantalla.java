package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeonsa.Juego;

public abstract class Pantalla implements Screen {
    protected Juego juego;
    protected SpriteBatch sb;

    public Pantalla() {
        this.juego=(Juego)Gdx.app.getApplicationListener();
        this.sb= juego.getSb();
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

    public abstract void dibujar(float delta);
}
