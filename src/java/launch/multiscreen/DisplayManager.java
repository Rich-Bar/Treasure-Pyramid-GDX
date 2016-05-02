package launch.multiscreen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import launch.Launcher;

public class DisplayManager{
	
	private List<Process> processes = new ArrayList<>();
	private List<ProcessBuilder> processBuilders = new ArrayList<>();
	
	private String path;
	public boolean init;
	
	public DisplayManager() throws UnsupportedEncodingException {
		String abstPath = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = URLDecoder.decode(abstPath, "UTF-8");
		//path = path.substring(1, path.length());
		
		System.out.println(path);
		
		boolean first = true;
		System.out.println("Current Devices:");
		GraphicsDevice[] systemDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for(GraphicsDevice gDevice : systemDevices){
			if(gDevice.isFullScreenSupported()){
				String gName = gDevice.getIDstring().replaceAll("\\\\", "/");
				System.out.println(gName);
				if(first){
					String[] pbArgs =  {"java", "-cp", path, Device.class.getCanonicalName(), '"' + "-gdevice:" + gName + " -type:" + Window.GameScreen + '"'}; 
					processBuilders.add(new ProcessBuilder(pbArgs));
					first = false;
				}else{
					String[] pbArgs = {"java", "-cp", path, Device.class.getCanonicalName(), '"' + "-gdevice:" + gName + " -type:" + Window.BlackScreen + '"'}; 
					processBuilders.add(new ProcessBuilder(pbArgs));
				}
			}
		}
		try {
			start();
		} catch (IOException e) {
			System.out.println("Couldn't start Processes!");
			e.printStackTrace();
		}
	}
	
	private void start() throws IOException{
		for(ProcessBuilder pb : processBuilders){
			Process p = pb.start();
			processes.add(p);
		}
		init = true;
	}
	
	public void waitForIt(){
		try {
			for(Process p : processes){
				p.waitFor();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
