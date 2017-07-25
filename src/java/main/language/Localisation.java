package main.language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.Game;

public final class Localisation{

	public Translation newGame = new Translation(translate("new_game"));
	public Translation options = new Translation(translate("options"));
	public Translation credits = new Translation(translate("credits"));
	public Translation exit = new Translation(translate("exit"));
	public Translation language = new Translation(translate("language"));
	public Translation masterVol = new Translation(translate("master_volume"));
	public Translation musicVol = new Translation(translate("music_volume"));
	public Translation soundVol = new Translation(translate("sound_volume"));
	public Translation axis = new Translation(translate("axis"));
	public Translation directAxis = new Translation(translate("axis_direct"));
	public Translation isometricAxis = new Translation(translate("axis_isometric"));
	public Translation save = new Translation(translate("save"));
	public Translation cancel = new Translation(translate("cancel"));
	public Translation vSync = new Translation(translate("vsync"));
	public Translation debug = new Translation(translate("debug"));
	public Translation confirmTitle = new Translation(translate("confirm_title"));
	public Translation confirmMessage = new Translation(translate("confirm_message"));
	
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
