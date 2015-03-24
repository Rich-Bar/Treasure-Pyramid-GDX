package Main.Managers;

import java.io.*;
import java.awt.GraphicsDevice;

import Main.Game;

public class ConfigManager {

	public final int maxFPS = 120;
	public Settings settings = new Settings();
	private File config;
	private String OS = (System.getProperty("os.name")).toUpperCase();
	private Game mainGame;
	
	public ConfigManager(Game game) {
		mainGame = game;
		
		String fileLocation;
		if (OS.contains("WIN")){
			fileLocation = System.getenv("AppData") + "/TreasurePyramid/conf.ig";
		}else{
			fileLocation = System.getProperty("user.home");
			fileLocation += "/Library/Application Support/TreasurePyramid/conf.ig";
		}
		
		config = new File(fileLocation);
		
		if(!config.exists() || config.length() == 0){
			config.getParentFile().mkdirs();
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			resetConfig();
			System.out.println("Created new ConfigFile!");
		}
	}
	
	
	private void resetConfig(){
		Settings settings = new Settings();
		settings.setDebug(false);
		settings.setKeyAxis(false);
		settings.setvSync(false);
		settings.setMasterVol(1f);
		settings.setMusicVol(1f);
		settings.setSoundVol(1f);
		write(settings);
		read(true); //prevent recursive resetting of the config - this would be recursive if the standart values couldn't be read!
	}
	
	public void write(Settings settings){
		if(this.settings == settings) return;
		this.settings = settings; 
		String settingscomp = settings.masterVol + "\n" + settings.masterVol+ "\n" + settings.musicVol+ "\n" + settings.soundVol+ "\n" + settings.keyAxis+ "\n" + settings.vSync+ "\n" + settings.debug;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(config));
			writer.write(settingscomp);
			writer.close();
			
		} catch (IOException e) {
			System.err.println("Configfile not found!");
		}
	}

	public void read(){
		read(false);
	}
	
	public void read(boolean recursive){
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(config));
	        String line;
	        int i = 0;
	        while((line = br.readLine()) != null) {
	            switch(i){
		            case 0:{
		            	settings.masterVol = Float.parseFloat(line);
		            	break;
		            }
		            case 1:{
		            	settings.musicVol = Float.parseFloat(line);
		            	break;
		            }
		            case 2:{
		            	settings.soundVol = Float.parseFloat(line);
		            	break;
		            }
		            case 3:{
		            	settings.keyAxis = Boolean.parseBoolean(line);
		            	break;
		            }
		            case 4:{
		            	settings.vSync = Boolean.parseBoolean(line);
		            	break;
		            }
		            case 5:{
		            	settings.debug = Boolean.parseBoolean(line);
		            	break;
		            }
		            
	            }
	        	i++;
	        }
		} catch (NumberFormatException | IOException e) {
			if(!recursive)resetConfig();
			System.err.println("Configfile is corrupted -> replaced it!");
		}finally{
			mainGame.resetResolution(mainGame.getContainer());
		}
		
	}

	public class Settings {
		private float masterVol = 1f;
		private float musicVol = 1f;
		private float soundVol = 1f;
		private boolean keyAxis = false;
		private boolean vSync = false;
		private GraphicsDevice monitor;
		private boolean debug = false;
		
		private float percent(float a){
			if(a>1f){
				return 1f;
			}else if(a<0f){
				return 0f;
			}
			return a;
		}
		
		public float getMasterVol() {
			return masterVol;
		}
		
		public void setMasterVol(float masterVol) {
			this.masterVol = percent(masterVol);
		}
		
		public float getMusicVol() {
			return musicVol;
		}
		
		public void setMusicVol(float musicVol) {
			this.musicVol = percent(musicVol);
		}
		
		public float getSoundVol() {
			return soundVol;
		}
		
		public void setSoundVol(float soundVol) {
			this.soundVol = percent(soundVol);
		}
		
		public boolean getKeyAxis() {
			return keyAxis;
		}
		
		public void setKeyAxis(boolean keyAxis) {
			this.keyAxis = keyAxis;
		}
		
		public boolean isvSync() {
			return vSync;
		}
		
		public void setvSync(boolean vSync) {
			this.vSync = vSync;
		}
		
		public GraphicsDevice getMonitor() {
			return monitor;
		}
		
		public void setMonitor(GraphicsDevice monitor) {
			this.monitor = monitor;
		}
		
		public boolean isDebug() {
			return debug;
		}
		
		public void setDebug(boolean debug) {
			this.debug = debug;
		}
	}
}	