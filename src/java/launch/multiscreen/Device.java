package launch.multiscreen;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.JFrame;

import main.Game;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.CanvasGameContainer;

public class Device implements Runnable{

	private GraphicsDevice gd;
	
	private CanvasGameContainer appgc;
	private org.newdawn.slick.Game sbg;
	public JFrame gFrame;
	public boolean isGame = false;
	public Window type;
	public String title = "";
		
	public float offset;
	public double factor;
	public boolean lrColumns = true;
	
	public Device(GraphicsDevice gDevice, Window type) {
		this.gd = gDevice;
		this.type = type;
	}

	public void run(){
		try{
			if(type == Window.GameScreen){
				sbg = new Game(this);
				appgc = new CanvasGameContainer(sbg);
				isGame = true;
				title = "Treasure Pyramid - Game";
			}
			else if(type == Window.InventoryScreen){
				sbg = new InventoryScreen();
				appgc = new CanvasGameContainer(sbg);
				title = "Treasure Pyramid - Inventory";
			}
			else if(type == Window.BlackScreen){
				sbg = new BlackScreen();
				title = "Treasure Pyramid - Black";
				appgc = new CanvasGameContainer(sbg);
				Display.setLocation(1920, 1080);
			}
			
			System.out.println("Initializing Screen["+ type.name() +"]: " + title);
			
			gFrame = new JFrame(title);
			gFrame.setUndecorated(true);
			Rectangle bounds = gd.getDefaultConfiguration().getBounds();
			System.out.println(bounds);
			appgc.setSize(bounds.width, bounds.height);
			gFrame.setLocation(bounds.x, bounds.y);
			gFrame.setSize(bounds.width, bounds.height);
			gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			gFrame.add(appgc);
			gFrame.setVisible(true);
	
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
	public CanvasGameContainer getAppgc() {
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
