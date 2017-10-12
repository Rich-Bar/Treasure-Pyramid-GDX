package richbar.com.github.treasurepyramid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

/**
 * Created by Rich Y on 01.08.2017.
 */
public class DisplayManager {
	
	public DisplayManager(){
		
	}
	
	public void resetToDefault(){
		for(Graphics.Monitor screen : Gdx.graphics.getMonitors()) {
			Graphics.DisplayMode[] modes = Gdx.graphics.getDisplayModes(screen);
			for (Graphics.DisplayMode mode : modes) {
				
			}
		}
	}
}
