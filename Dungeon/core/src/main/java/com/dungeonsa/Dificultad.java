package com.dungeonsa;

public enum Dificultad {
    FACIL("facil",20,30,5),
    NORMAL("normal", 30, 40,10),
    DIFICIL("dificil", 40, 50,15),
    INFIERNO("infierno", 50, 60,20);

    private final String TIPO;
    private final int VIDA_ENEMIGA_MIN;
    private final int VIDA_ENEMIGA_MAX;
    private final int DAMAGE_ENEMIGO;

    Dificultad(String tipo, int vida_enemiga_min, int vida_enemiga_max, int damage_enemigo) {
        TIPO = tipo;
        VIDA_ENEMIGA_MIN = vida_enemiga_min;
        VIDA_ENEMIGA_MAX = vida_enemiga_max;
        DAMAGE_ENEMIGO = damage_enemigo;
    }

    public String getTIPO() {
        return TIPO;
    }

    public int getVidaMin() {
        return VIDA_ENEMIGA_MIN;
    }

    public int getVidaMax() {
        return VIDA_ENEMIGA_MAX;
    }

    public int getDamageEnemigo() {
        return DAMAGE_ENEMIGO;
    }
}
