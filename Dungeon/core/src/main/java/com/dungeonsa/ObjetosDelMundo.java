package com.dungeonsa;

public enum ObjetosDelMundo {
    JUGADOR("jugador"),MURO("muro"),ESQUELETO("esqueleton"),COFRE("cofre");

    private final String TIPO;

    ObjetosDelMundo(String tipo) {
        TIPO = tipo;
    }

    public String getTIPO() {
        return TIPO;
    }
}
