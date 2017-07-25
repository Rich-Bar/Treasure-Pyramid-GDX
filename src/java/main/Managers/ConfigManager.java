package main.managers;

import java.io.*;

import main.components.Settings;
import main.language.LANGUAGES;

public class ConfigManager {

	public final int maxFPS = 120;
	public Settings settings = new Settings();
	private File config;
	
	public ConfigManager() {
		
		String fileLocation = OSManagement.getAppdataPath() + "conf.ig";
		
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
		settings.debug = false;
		settings.vSync = false;
		settings.masterVol = 1f;
		settings.musicVol = 1f;
		settings.soundVol = 1f;
		settings.language = LANGUAGES.ENGLISH_EN;
		write(settings);
		read(true); //prevent recursive resetting of the config - this would be recursive if the standart values couldn't be read!
	}
	
	public void write(Settings settings){
		this.settings = settings; 
		String settingscomp = settings.masterVol+ "\n" + settings.musicVol+ "\n" + settings.soundVol+ "\n" + settings.vSync+ "\n" + settings.debug+ "\n" + settings.language;
		
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
		            	settings.vSync = Boolean.parseBoolean(line);
		            	break;
		            }
		            case 4:{
		            	settings.debug = Boolean.parseBoolean(line);
		            	break;
		            }
		            case 5:{
		            	settings.language = LANGUAGES.valueOf(line);
		            	break;
		            }
		            
	            }
	        	i++;
	        }
		} catch (NumberFormatException | IOException e) {
			if(!recursive)resetConfig();
			System.err.println("Configfile is corrupted -> replaced it!");
		}
	}
}	