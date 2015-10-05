package main.multiscreen;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import main.Game;
import main.types.DisplayMode;
import main.types.SheetFont;

public class DisplayManager {
	
	private static List<GraphicsDevice> devices = new ArrayList<>();
	private static GraphicsDevice mainDevice;
	
	private List<AppGameContainer> blankContainers = new ArrayList<>();
	private AppGameContainer inventoryContainer;
	
	
	//Resolution dependent settings
	private static int offsetX = 0;
	private static int offsetY = 0;
	public double factor;	
	private boolean isMultiscreen;
	private Dimension mainResolution;
	private Dimension internalResolution;
	
	public static AppGameContainer init(AppGameContainer appgc) throws SlickException{
		resetDevices();
		appgc.setMaximumLogicUpdateInterval(240);
		appgc.setUpdateOnlyWhenVisible(false);
		appgc.setTitle(Game.TITLE);
		appgc.setDisplayMode(mainDevice.getDisplayMode().getWidth(), mainDevice.getDisplayMode().getHeight(), mainDevice.isFullScreenSupported());		
		
		Game.mainResolution = new Dimension(mainDevice.getDisplayMode().getWidth(), mainDevice.getDisplayMode().getHeight());
		
		return appgc;
	}
	
	
	public static DisplayMode toLWJGLDisplayMode(java.awt.DisplayMode awt){
		DisplayMode lwjgl = new DisplayMode(awt.getWidth(), awt.getHeight(), awt.getBitDepth(), awt.getRefreshRate());
		return lwjgl;
		
	}
	
	public Dimension getMainResolution(){
		return mainResolution;
	}
	
	public static void resetDevices(){
		devices.addAll(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()));
		GraphicsDevice toRemove = null;
		for(GraphicsDevice device : devices){
			if(device == GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()){
				toRemove = device;
				mainDevice = device;
			}
		}
		devices.remove(toRemove);
	}
	
	public void setMultiscreen(){setMultiscreen(isMultiscreen, 0);}
	
	public void setMultiscreen(int inventoryScreen){setMultiscreen(isMultiscreen, inventoryScreen);}
	
	public void setMultiscreen(boolean isMulti){setMultiscreen(isMulti, 0);}
	
	public void setMultiscreen(boolean isMulti, int inventoryScreen){
		try {
			isMultiscreen = isMulti;
			if(isMulti){
				if(devices.size() + 1 < inventoryScreen) inventoryScreen = 0;
				if(inventoryScreen <= 0) Game.getInstance().renderInventory = true;
				for(GraphicsDevice device : devices){
					if(devices.indexOf(device) + 1 == inventoryScreen){
						inventoryContainer = new AppGameContainer(new InventoryScreen());
						inventoryContainer.setMaximumLogicUpdateInterval(120);
						inventoryContainer.setUpdateOnlyWhenVisible(false);
						inventoryContainer.setTitle(Game.TITLE + " - Inventory");
						inventoryContainer.setDisplayMode(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight(), device.isFullScreenSupported());
						inventoryContainer.start();
					}else{
						AppGameContainer blankContainer = new AppGameContainer(new BlackScreen());;
						blankContainer.setMaximumLogicUpdateInterval(1);
						blankContainer.setUpdateOnlyWhenVisible(false);
						blankContainer.setTitle("");
						blankContainer.setDisplayMode(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight(), device.isFullScreenSupported());
						blankContainer.start();
						blankContainers.add(blankContainer);
					}
				}
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setGameResolution(Dimension newResolution){
		mainResolution = newResolution;
	}

	public void resetResolution(GameContainer container) {
		internalResolution = Game.getInstance().getInternalResolution();
		////Handle GameContainer
		container.setShowFPS(Game.getInstance().config.settings.isDebug());
		container.setTargetFrameRate(Game.getInstance().config.maxFPS -1);	//-1 Fixes the 1 more FPS bug
		container.setVSync(Game.getInstance().config.settings.isvSync());
		////Handle Canvas and Bounds
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		//add black columns if necessary
		factor = 0;
		if((double)internalResolution.getWidth() / internalResolution.getHeight() < (double)mainResolution.getWidth() / mainResolution.getHeight()){
			factor = (double)mainResolution.getHeight() / internalResolution.getHeight();
			offsetX = (int) ((mainResolution.getWidth() - internalResolution.getWidth() * factor) /2);
		}else if((double)internalResolution.getWidth() / internalResolution.getHeight() > (double)mainResolution.getWidth() / mainResolution.getHeight()){
			factor = (double)mainResolution.getWidth() / internalResolution.getWidth();
			offsetY = (int) ((mainResolution.getHeight() - internalResolution.getHeight() * factor) /2);
		}
		System.out.println("offsetX: " + offsetX + " - offsetY: " + offsetY);
		
		//apply columns
		GL11.glOrtho(-offsetX ,internalResolution.getWidth() + offsetX, internalResolution.getHeight() + offsetY, - offsetY, 100, -100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		 
		container.setMouseGrabbed(true);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		/*
		___________________
		|-- - SHADERS - --|
		|_________________|
		crtshader uniforms:
			uniform vec2 rubyInputSize;
			uniform vec2 rubyTextureSize;
			uniform vec2 rubyOutputSize;
		baseshader uniforms: -
		*/
			
		/*try {
			crtshader = Shader.makeShader("src/assets/Shaders/crt-geom.vsh", "src/assets/Shaders/crt-geom.fsh");
			crtshader.setUniformVariable("rubyInputSize", internalResolution.width, internalResolution.height);
			crtshader.setUniformVariable("rubyTextureSize", internalResolution.width, internalResolution.height);
			crtshader.setUniformVariable("rubyOutputSize", internalResolution.width- 20, internalResolution.height- 20);
			//crtshader.startShader();
			//crtshader.forceFixedShader();
			baseshader = Shader.makeShader("src/assets/Shaders/vignette.vsh", "src/assets/Shaders/vignette.fsh");
			baseshader.setUniformVariable("u_sampler2D", 1);
			baseshader.setUniformVariable("u_resolution", nativeResolution.width, nativeResolution.height);
			/*float[] fb = {0f, 0f, 0f, 0f};
			FloatBuffer buff = BufferUtils.createFloatBuffer(4);
			buff.put(fb);
			baseshader.setUniformMatrix4("u_projTrans", true, buff);
			//baseshader.startShader();
		} catch (SlickException e) {
			e.printStackTrace();
		}*/
		
		/*
		_________________
		|-- - FONTS - --|
		|_______________|
		*/
		Game.getInstance().font = new SheetFont();
		
		
	}
}
