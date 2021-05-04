package com.dungeonsa.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeonsa.Juego;

public abstract class Pantalla implements Screen {
    protected Juego juego;
    protected SpriteBatch sb;

    public Pantalla() {
        this.juego=Juego.getInstance();
        this.sb= juego.getSb();
    }

    @Override
    public void render(float delta) {

    }
}
