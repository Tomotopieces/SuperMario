package io.tomoto.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.tomoto.game.SuperMario;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("SuperMario");
		config.setWindowedMode(SuperMario.V_WIDTH * 2, SuperMario.V_HEIGHT * 2);
		new Lwjgl3Application(new SuperMario(), config);
	}
}
