package main.managers;

import java.io.*;

import main.Game;
import main.components.Settings;
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
		settings.debug = (false);
		settings.vSync = (false);
		settings.masterVol = (1f);
		settings.musicVol = (1f);
		settings.soundVol = (1f);
		settings.language = (LANGUAGES.ENGLISH_EN);
		settings.sDevice = Game.inst().displayManager.getSerialDevices();
		write(settings);
		read(); //prevent recursive resetting of the config - this would be recursive if the standart values couldn't be read!
	}
	
	public void write(Settings settings){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(config);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(settings);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized Settings were Saved");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}

	public void read(){
		try
	      {
	         FileInputStream fileIn = new FileInputStream(config);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         settings = (Settings) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException | ClassNotFoundException i)
	      {
	    	  i.printStackTrace();
	      }
	}
}	