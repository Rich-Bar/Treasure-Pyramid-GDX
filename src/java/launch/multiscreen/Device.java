package launch.multiscreen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import javax.swing.JFrame;

import main.Game;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.CanvasGameContainer;

import launch.Launcher;

public class Device{

	private static GraphicsDevice gd;
	public static Window type;
	public static String gameDir;
	
	private CanvasGameContainer appgc;
	private org.newdawn.slick.Game sbg;
	public JFrame gFrame;
	public boolean isGame = false;
	public String title = "";
		
	public float offset;
	public double factor;
	public boolean lrColumns = true;
	
	public static void main(String[] args) throws Exception {
		if(args.length != 2) return;
		if(	!(args[0].toLowerCase().startsWith("-gdevice:") || args[0].toLowerCase().startsWith("-type:")) ||
			!(args[1].toLowerCase().startsWith("-gdevice:") || args[1].toLowerCase().startsWith("-type:"))) return;
		
		int k = 1;
		if(args[0].toLowerCase().startsWith("-type:")) k = 0;
		
		type = Window.valueOf(args[k].replace("-type:", "").trim());
		k = k == 1? 0 : 1;
		String gdString = args[k].replace("-gdevice:", "").trim().replaceAll("\\\\", "/");
		
		Launcher.setDeviceLog(gdString);
		
		System.out.println("Started Device with " + args.length + " args: " + Arrays.toString(args));
		gameDir = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
		System.out.println("In gameDir: " + gameDir);
		
		Launcher.setLibraryPath();
		
		boolean foundDevice = false;
		GraphicsDevice[] systemDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for(GraphicsDevice gDevice : systemDevices){
			String cDeviceID = gDevice.getIDstring().replaceAll("\\\\", "/");
			if(cDeviceID.equals(gdString)){
				System.out.println("Found Corresponding gDevice!");
				foundDevice = true;
				gd = gDevice;
			}
		}
		if(!foundDevice){
			System.out.println("Unable to find corresponding gDevice to gID: " + gdString);
			gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		}
		
		Device d = new Device();
		d.run();
	}
	
	public void run() throws Exception{
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
			e.printStackTrace();
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
