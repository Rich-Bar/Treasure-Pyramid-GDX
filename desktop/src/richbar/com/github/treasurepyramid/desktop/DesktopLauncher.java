package richbar.com.github.treasurepyramid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import richbar.com.github.treasurepyramid.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Treasure Pyramid";
		config.vSyncEnabled = false;
		config.resizable = false;
		config.initialBackgroundColor = com.badlogic.gdx.graphics.Color.BLACK;
		Main main = new Main();
		
		new LwjglApplication(main, config);
	}
}
