package com.dungeonsa.Personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Personaje {
    protected static Vector2 posicion;
    protected static Texture h;
    protected String nombre;
    protected int vidaMax, vidaActual;

    public Personaje(float x, float y) {
        posicion = new Vector2(x,y);
    }

    public static void moverDerecha() {
        posicion.add(10,0);
    }

    public static void moverArriba() {
        posicion.add(0,10);
    }

    public static void moverAbajo() {
        posicion.add(0,-10);
    }

    public static void moverIzquierda() {
        posicion.add(-10,0);
    }
}
