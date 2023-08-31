package com.mygdx.villa.race.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.villa.race.VillaRace;
import com.mygdx.villa.race.utiles.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Villa Race";
		config.width = Config.ANCHO;
		config.height = Config.ALTO;
		config.fullscreen= false;
		//config.resizable = false;
		new LwjglApplication(new VillaRace(), config);
	}
}
