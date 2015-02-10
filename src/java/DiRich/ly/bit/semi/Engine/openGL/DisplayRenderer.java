package DiRich.ly.bit.semi.Engine.openGL;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class DisplayRenderer {
	
	private GraphicsDevice gd;
	private Dimension screenDimension = new Dimension(0,0);
	
	public DisplayRenderer(Dimension screenDimension){
		this.screenDimension = screenDimension;
	}
	
	public void start(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		setDisplay(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight(), true);
		
		while (!Display.isCloseRequested()) {
			// Clear the screen and depth buffer
		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
	 
		    // set the color of the quad (R,G,B,A)
		    GL11.glColor4f(1.5f,0.5f,1.0f, 1.0f);
	 
		    // draw quad
		    GL11.glBegin(GL11.GL_QUADS);
		        GL11.glVertex2f(0,0);
				GL11.glVertex2f(0,screenDimension.height);
				GL11.glVertex2f(screenDimension.width,screenDimension.height);
				GL11.glVertex2f(screenDimension.width,0);
		    GL11.glEnd();

		    Display.update();
		}
	 
		Display.destroy();
		
	}
	
	
	public void setDisplay(int width, int height, boolean fullscreen){
		Display.destroy();
		try {
			setDisplayMode(width, height, fullscreen);
		    Display.create();
		    
		} catch (LWJGLException e) {
			e.printStackTrace();
		    System.exit(0);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		//TODO: schwarze Balken einfügen xD
		
		GL11.glOrtho(0 ,screenDimension.width, screenDimension.height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	//http://ninjacave.com/
	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	private void setDisplayMode(int width, int height, boolean fullscreen) {
	 
	    // return if requested DisplayMode is already set
	    if ((Display.getDisplayMode().getWidth() == width) && 
	        (Display.getDisplayMode().getHeight() == height) && 
	    (Display.isFullscreen() == fullscreen)) {
	        return;
	    }
	 
	    try {
	        DisplayMode targetDisplayMode = null;
	         
	    if (fullscreen) {
	        DisplayMode[] modes = Display.getAvailableDisplayModes();
	        int freq = 0;
	                 
	        for (int i=0;i<modes.length;i++) {
	            DisplayMode current = modes[i];
	                     
	        if ((current.getWidth() == width) && (current.getHeight() == height)) {
	            if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
	                if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
	                targetDisplayMode = current;
	                freq = targetDisplayMode.getFrequency();
	                        }
	                    }
	 
	            // if we've found a match for bpp and frequence against the 
	            // original display mode then it's probably best to go for this one
	            // since it's most likely compatible with the monitor
	            if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
	                        (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
	                            targetDisplayMode = current;
	                            break;
	                    }
	                }
	            }
	        } else {
	            targetDisplayMode = new DisplayMode(width,height);
	        }
	 
	        if (targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }
	 
	        Display.setDisplayMode(targetDisplayMode);
	        Display.setFullscreen(fullscreen);
	             
	    } catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}

}
