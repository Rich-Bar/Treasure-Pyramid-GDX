package main.multiscreen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.opengl.renderer.ImmediateModeOGLRenderer;
import org.newdawn.slick.util.Log;

import main.Game;
import main.components.SheetFont;
import main.multiscreen.Device.SerialDevice;

public class DisplayManager extends ImmediateModeOGLRenderer{
	
	private List<Device> devices = new ArrayList<>();
	
	//Resolution dependent settings
	public double factor;
	
	public DisplayManager(AppGameContainer appgc) {
		System.out.println("Current Devices:");
		for(GraphicsDevice gDevice : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()){
			if(gDevice.isFullScreenSupported()){
				devices.add(new Device(gDevice));
				System.out.println(" - Device " + gDevice.getIDstring());
			}
		}
		if(devices.size() > 0)devices.get(0).setAppgc(appgc);
	}
	
	public void init(){
	}

	public SerialDevice[] getSerialDevices(){
		SerialDevice[] sd = new SerialDevice[devices.size()];
		for(Device d : devices){
			sd[sd.length] = d.getSerialDevice();
		}
		return sd;
	}
	
	public void setDevices(Device... device){
		//TODO: Add reading mechanism!
	}
	
	public void resetResolution() {
		for(Device d : devices){
			d.dial();
		}
		/*
		_________________
		|-- - FONTS - --|
		|_______________|
		*/
		Game.inst().font = new SheetFont();		
	}
	
	@Override
	public void initDisplay(int width, int height) {
		System.out.println("OVERRIDEN DISPLAY INITIATION");
		Log.info("OVERRIDEN DISPLAY INITIATION2");
	}
}
