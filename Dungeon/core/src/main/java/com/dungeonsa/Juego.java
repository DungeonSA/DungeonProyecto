package com.dungeonsa;

import com.badlogic.gdx.Game;
import com.dungeonsa.FirstScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {
	@Override
	public void create() {
		setScreen(new FirstScreen());
	}
}