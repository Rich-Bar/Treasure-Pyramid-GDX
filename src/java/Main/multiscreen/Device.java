package main.multiscreen;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.Serializable;

import main.Game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.AppGameContainer;

public class Device implements Runnable{

	
	private DisplayManager dm;
	private GraphicsDevice gd;
	
	private AppGameContainer appgc;
	private org.newdawn.slick.Game sbg;
	private boolean isGame = false;
	private Window type;
	
	private int interval = 0;
	private boolean updateInvisible = false;
	private String title = "";
		
	private float offset;
	private double factor;
	private boolean lrColumns = true;
	
	public Device(DisplayManager dm, GraphicsDevice gDevice, Window type) {
		this.dm = dm;
		this.gd = gDevice;
		this.type = type;
	}

	public void dial(){
		System.out.println("Dialing Screen["+ type.name() +"]: " + title);
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

	public void run(){
		try{
			if(type == Window.GameScreen){
				sbg = new Game(dm);
				appgc = new AppGameContainer(sbg);
				isGame = true;
				interval = 240;
				title = "Treasure Pyramid - Game";
			}
			else if(type == Window.InventoryScreen){
				sbg = new InventoryScreen();
				appgc = new AppGameContainer(sbg);
				updateInvisible = true;
				interval = 240;
				title = "Treasure Pyramid - Inventory";
			}
			else if(type == Window.BlackScreen){
				sbg = new BlackScreen();
				title = "Treasure Pyramid - Black";
				appgc = new AppGameContainer(sbg);
				Display.setLocation(1920, 1080);
				interval = 240;
			}
			
			System.out.println("Initializing Screen["+ type.name() +"]: " + title);
			
			appgc.setMaximumLogicUpdateInterval(interval);
			appgc.setUpdateOnlyWhenVisible(updateInvisible);
			appgc.setTitle(title);
			appgc.setDisplayMode(Game.pixelartResolution.width * Game.scale, Game.pixelartResolution.height * Game.scale, false);
			DisplayMode dm = gd.getDisplayMode();
			System.out.println(gd.getDefaultConfiguration().getBounds().x + " x " + gd.getDefaultConfiguration().getBounds().y);
			appgc.setDisplayMode(dm.getWidth(), dm.getHeight(), true);
			
	
			System.out.println("Starting Screen["+ type.name() +"]: " + title);
			appgc.start();
		}catch(Exception e){
			System.out.println("Unable to start screen!!");
		}
		
	}
	
	public org.newdawn.slick.Game getGame(){
		return sbg;
	}
	
	public boolean isGame(){
		return isGame;
	}
	
	public GraphicsDevice getDevice(){
		return gd;
	}
	
	
	/**
	 * @return the appgc
	 */
	public AppGameContainer getAppgc() {
		return appgc;
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
