package com.dungeonsa;

import com.badlogic.gdx.math.Vector2;

public abstract class Utiles {
    public static final int LADO_LOSA = 16;
    public static final String TIPO = "tipo";
    public static final String JUGADOR = "jugador";
    public static final String MURO = "muro";
    public static final String ESQUELETO = "esqueleton";
    public static final String COFRE = "cofre";
    public static final Vector2 PASO_ARRIBA = new Vector2(0, 1f);
    public static final Vector2 PASO_ABAJO = new Vector2(0, -1f);
    public static final Vector2 PASO_DERECHA = new Vector2(1f, 0);
    public static final Vector2 PASO_IZQUIERDA = new Vector2(-1f, 0);
    public static final float VEL_MAX = 3.0f;
}
