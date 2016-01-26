package main.multiscreen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;


import main.Game;
import main.multiscreen.Device.SerialDevice;

public class DisplayManager{
	
	private List<Device> devices = new ArrayList<>();
	private List<Thread> threads = new ArrayList<>();
	
	//Resolution dependent settings
	public double factor;
	
	public DisplayManager() {
		boolean first = true;
		System.out.println("Current Devices:");
		GraphicsDevice[] systemDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for(GraphicsDevice gDevice : systemDevices){
			if(gDevice.isFullScreenSupported()){
				if(first){
					devices.add(new Device(this, gDevice, Window.GameScreen));
					first = false;
				}else{
					devices.add(new Device(this, gDevice,  Window.BlackScreen));
				}
				System.out.println(" - Device " + gDevice.getIDstring());
			}
		}
		for(Device d : devices){
			Thread dThread = new Thread(d);
			threads.add(dThread);
			dThread.start();
			
		}
	}

	public List<Device> getDevices(){
		return devices;
	}
	
	public SerialDevice[] getSerialDevices(){
		SerialDevice[] sd = new SerialDevice[devices.size()];
		int i = 0;
		for(Device d : devices){
			sd[i] = d.getSerialDevice();
			i++;
		}
		return sd;
	}
	
	public Game getGame(){
		Game game = null;
		for(Device d : devices){
			if(d.isGame()) game = (Game) d.getGame();
		}
		return game;
	}
	
	public void gameResize(){
		for(Device d : devices){
			if(d.isGame()) d.dial();
		}
	}
}
