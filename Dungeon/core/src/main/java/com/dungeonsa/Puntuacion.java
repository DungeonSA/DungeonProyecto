package com.dungeonsa;

public class Puntuacion {
    private int contador = 0;
    // Datos
    private String Nombre;
    private int intento;
    private float tiempo;

    public Puntuacion(String nombre,float segundos){
        Nombre = nombre;
        intento = ++contador;
        tiempo = segundos;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return Nombre+intento;
    }
}
