package main.language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.Game;

public final class Localisation{

	public Translation NewGame = new Translation(translate("new_game"));
	public Translation Options = new Translation(translate("options"));
	public Translation Credits = new Translation(translate("credits"));
	public Translation Exit = new Translation(translate("exit"));
	public Translation Language = new Translation(translate("language"));
	public Translation MasterVol = new Translation(translate("master_volume"));
	public Translation MusicVol = new Translation(translate("music_volume"));
	public Translation SoundVol = new Translation(translate("sound_volume"));
	public Translation Axis = new Translation(translate("axis"));
	public Translation DirectAxis = new Translation(translate("axis_direct"));
	public Translation IsometricAxis = new Translation(translate("axis_isometric"));
	public Translation Save = new Translation(translate("save"));
	public Translation Cancel = new Translation(translate("cancel"));
	public Translation VSync = new Translation(translate("vsync"));
	public Translation Debug = new Translation(translate("debug"));
	public Translation ConfirmTitle = new Translation(translate("confirm_title"));
	public Translation ConfirmMessage = new Translation(translate("confirm_message"));
	
	public class Translation{
		
		private String translation;
		
		public Translation(String translation){
			this.translation = translation;
		}
		
		public String getTranslation(){
			return translation;
		}
	}
	
	/**
	 * @param text - Text to translate (in english)
	 * @return translated text
	 */
	@SuppressWarnings("resource")
	public String translate(String text){ 
		LANGUAGES standart = Game.inst().config.settings.language;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(standart.getFile()));
	        String line;
			while((line = br.readLine()) != null) {
				if(line.toLowerCase().matches(text.toLowerCase() + ".*"))
					text = line.substring(text.length() + 2, line.length());
			}
		} catch (IOException  e1) {}
		return text.replaceAll("&n", "\n");
	}
}
