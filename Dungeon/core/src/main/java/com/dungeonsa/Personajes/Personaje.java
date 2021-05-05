package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class Personaje {
    protected Vector2 posicion;
    protected static Texture h;
    protected String nombre;
    protected int vidaMax, vidaActual;

    public Personaje(int x, int y) {
        posicion = new Vector2(x,y);
    }

    public void moverDerecha() {
        posicion.add(10,0);
    }

    public static void moverArriba() {

    }

    public static void moverAbajo() {

    }

    public static void moverIzquierda() {

    }
}
