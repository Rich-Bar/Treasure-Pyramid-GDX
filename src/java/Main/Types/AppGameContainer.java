package main.types;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import main.multiscreen.DisplayManager;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.InternalTextureLoader;

/**
 * A game container that will display the game as an stand alone 
 * application.
 *
 * @author kevin
 */
public class AppGameContainer extends org.newdawn.slick.AppGameContainer {

	protected DisplayMode targetMode;
	
	
	public AppGameContainer(Game game) throws SlickException {
		super(game);
	}
	
	public void setDisplayMode(GraphicsDevice device, int width, int height, boolean fullscreen) throws SlickException {
		if ((this.width == width) && (this.height == height) && (isFullscreen() == fullscreen)) {
			return;
		}
		
		try {
			targetMode = null;
			if (fullscreen) {
				
				List<DisplayMode> modes = new ArrayList<>();
				for(java.awt.DisplayMode mode : device.getDisplayModes()){
					modes.add(DisplayManager.toLWJGLDisplayMode(mode));
				}
				int freq = 0;
				
				for (DisplayMode mode: modes) {
					DisplayMode current = mode;
					
					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetMode == null) || (current.getFrequency() >= freq)) {
							if ((targetMode == null) || (current.getBitsPerPixel() > targetMode.getBitsPerPixel())) {
								targetMode = current;
								freq = targetMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == originalDisplayMode.getBitsPerPixel()) &&
						    (current.getFrequency() == originalDisplayMode.getFrequency())) {
							targetMode = current;
							break;
						}
					}
				}
			} else {
				targetMode = new DisplayMode(width,height);
			}
			
			if (targetMode == null) {
				throw new SlickException("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
			}
			
			this.width = width;
			this.height = height; 

			Display.setDisplayMode(targetMode.toDisplayMode());
			Display.setFullscreen(fullscreen);

			if (Display.isCreated()) {
				initGL();
				enterOrtho();
			} 
			
			if (targetMode.getBitsPerPixel() == 16) {
				InternalTextureLoader.get().set16BitMode();
			}
		} catch (LWJGLException e) {
			throw new SlickException("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen, e);
		}
		
		getDelta();
	}
	
	@Override
	public void setDisplayMode(int width, int height, boolean fullscreen) throws SlickException {
		setDisplayMode(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), width, height, fullscreen);
	}
}
