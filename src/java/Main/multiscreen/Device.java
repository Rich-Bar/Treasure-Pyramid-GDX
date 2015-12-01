package main.multiscreen;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.Serializable;

import main.Game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.AppGameContainer;

public class Device{

	private GraphicsDevice gd;
	private AppGameContainer appgc;
	private Window type;
	private float offset;
	private double factor;
	private boolean lrColumns = true;
	
	public Device(GraphicsDevice gDevice) {
		this.gd = gDevice;
	}

	public void dial(){
	////Handle GameContainer
			appgc.setShowFPS(Game.inst().config.settings.debug);
			appgc.setTargetFrameRate(Game.inst().config.maxFPS -1);	//-1 Fixes the 1 more FPS bug
			appgc.setVSync(Game.inst().config.settings.vSync);
			////Handle Canvas and Bounds
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			
			Rectangle internalResolution = gd.getDefaultConfiguration().getBounds();
			//add black columns if necessary
			factor = 0;
			if((double)internalResolution.getWidth() / internalResolution.getHeight() < (double)Game.pixelartResolution.getWidth() / Game.pixelartResolution.getHeight()){
				factor = (double)Game.pixelartResolution.getHeight() / internalResolution.getHeight();
				offset = (int) ((Game.pixelartResolution.getWidth() - internalResolution.getWidth() * factor) /2);
				lrColumns = true;
			}else if((double)internalResolution.getWidth() / internalResolution.getHeight() > (double)Game.pixelartResolution.getWidth() / Game.pixelartResolution.getHeight()){
				factor = (double)Game.pixelartResolution.getWidth() / internalResolution.getWidth();
				offset = (int) ((Game.pixelartResolution.getHeight() - internalResolution.getHeight() * factor) /2);
				lrColumns = false;
			}
			System.out.println("offset: " + offset + " - lr: " + lrColumns);
			
			//apply columns
			if(lrColumns)GL11.glOrtho(-offset ,internalResolution.getWidth() + offset, internalResolution.getHeight(), 0, 100, -100);
			else GL11.glOrtho(0 ,internalResolution.getWidth(), internalResolution.getHeight() + offset, - offset, 100, -100);
			
				
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			 
			appgc.setMouseGrabbed(true);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}

	/**
	 * @return the appgc
	 */
	public AppGameContainer getAppgc() {
		return appgc;
	}

	/**
	 * @param appgc the appgc to set
	 */
	public void setAppgc(AppGameContainer appgc) {
		this.appgc = appgc;
		if(Window.GameScreen.isInstance(appgc)) type = Window.GameScreen;
		else if(Window.BlackScreen.isInstance(appgc)) type = Window.BlackScreen;
		else if(Window.InventoryScreen.isInstance(appgc)) type = Window.InventoryScreen;
		else type = Window.None;
	}
	
	public SerialDevice getSerialDevice(){
		return new SerialDevice(gd.getIDstring(), type.name());
	}

	@SuppressWarnings("serial")
	public class SerialDevice implements Serializable{
		public String deviceName;
		public String container;
		
		public SerialDevice(String deviceName, String container) {
			this.deviceName = deviceName;
			this.container = container;
		}
	}
	
}
