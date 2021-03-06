package com.dungeonsa.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.dungeonsa.Juego;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new Juego(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle(Juego.TITULO);
		configuration.setWindowedMode(Juego.ANCHO, Juego.ALTO);
//		configuration.setFullscreenMode(configuration.getDisplayMode());
		configuration.setResizable(false);
		configuration.setWindowIcon(Juego.ICONO);
		return configuration;
	}
}