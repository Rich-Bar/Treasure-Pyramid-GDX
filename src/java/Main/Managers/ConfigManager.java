package main.managers;

import java.io.*;
import java.awt.GraphicsDevice;

import main.Game;
import main.language.LANGUAGES;

public class ConfigManager {

	public final int maxFPS = 120;
	public Settings settings = new Settings();
	private File config;
	
	public ConfigManager() {
		
		config = new File(OSManagement.getAppdataPath() + "conf.ig");
		
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
		settings.setLanguage(LANGUAGES.ENGLISH_EN);
		write(settings);
		read(true); //prevent recursive resetting of the config - this would be recursive if the standart values couldn't be read!
	}
	
	public void write(Settings settings){
		//if(this.settings. == settings) return;
		this.settings = settings; 
		String settingscomp = settings.masterVol+ "\n" + settings.musicVol+ "\n" + settings.soundVol+ "\n" + settings.keyAxis+ "\n" + settings.vSync+ "\n" + settings.debug+ "\n" + settings.language;
		
		try {
            FileWriter f2 = new FileWriter(config, false);
            f2.write(settingscomp);
            f2.close();
            System.out.println("Wrote:\n" + settingscomp);
        } catch (IOException e) {
            e.printStackTrace();
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
		            case 6:{
		            	settings.language = LANGUAGES.valueOf(line);
		            	System.out.println(settings.language);
		            	break;
		            }
		            
	            }
	        	i++;
	        }
		} catch (NumberFormatException | IOException e) {
			if(!recursive)resetConfig();
			System.err.println("Configfile is corrupted -> replaced it!");
		}finally{
			Game.inst().displayManager.resetResolution(Game.inst().getContainer());
		}
		
	}

	public class Settings {
		private LANGUAGES language;
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
		
		public void setLanguage(LANGUAGES lang) {
			language = lang;
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

		public LANGUAGES getLanguage() {
			return language;
		}
	}
}	