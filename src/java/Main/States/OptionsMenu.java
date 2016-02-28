package main.states;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import main.Game;
import main.Game.Screens;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.components.Popup;
import main.components.Settings;
import main.components.SheetFont;
import main.components.Slider;
import main.components.Toggle;
import main.language.LANGUAGES;
import main.language.Localisation;
import main.managers.ConfigManager;

public class OptionsMenu extends BaseState{
	
	private int selected = 0;
	
	private Slider mainVolume;
	private Slider musicVolume;
	private Slider soundVolume;
	private LANGUAGES selectedLang;
	private Toggle vSync;
	private Toggle debug;
	private Popup confirm;
	private boolean popup;
	
	private SheetFont font;
	
	public OptionsMenu(int ID){
		super();
		this.ID = ID;
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		Game.inst().keyManager.keyPressed(arg0, this);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		Game.inst().keyManager.keyReleased(arg0);
		
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		super.render(arg0, arg1, arg2);
		
		font.drawStringAlignMiddle(160, 10, Game.inst().lang.Options.getTranslation(),Color.white, 75);
		font.drawStringAlignMiddle(160, 10, "____________",Color.white, 75);
		
		font.drawString(282, 50, (selectedLang + "").substring(0, (selectedLang + "").length() - 3), Color.decode("0x999999"), 35);
		mainVolume.draw(300 * Game.scale, 70  * Game.scale, 50, Color.decode("0x999999"));
		musicVolume.draw(300 * Game.scale, 85  * Game.scale, 50, Color.decode("0x999999"));
		soundVolume.draw(300 * Game.scale, 100  * Game.scale, 50, Color.decode("0x999999"));
		vSync.draw(300 * Game.scale, 120  * Game.scale, 50, Color.decode("0x999999"));
		debug.draw(300 * Game.scale, 140  * Game.scale, 50, Color.decode("0x999999"));
		
		font.drawString(20, 50, Game.inst().lang.Language.getTranslation() + ":", Color.decode("0x999999"), 35);
		font.drawString(20, 70, Game.inst().lang.MasterVol.getTranslation() + ":", Color.decode("0x999999"), 35);
		font.drawString(30, 85, Game.inst().lang.MusicVol.getTranslation() + ":", Color.decode("0x999999"), 35);
		font.drawString(30, 100, Game.inst().lang.SoundVol.getTranslation() + ":", Color.decode("0x999999"), 35);
		font.drawString(20, 120, Game.inst().lang.VSync.getTranslation() + ":", Color.decode("0x999999"), 35);
		font.drawString(20, 140, Game.inst().lang.Debug.getTranslation() + ":", Color.decode("0x999999"), 35);
		font.drawString(100, 210, Game.inst().lang.Save.getTranslation(), Color.decode("0x999999"), 35);
		font.drawString(200, 210, Game.inst().lang.Cancel.getTranslation(), Color.decode("0x999999"), 35);
		
		if(selected == 0){
			font.drawString(282, 50, (selectedLang + "").substring(0, (selectedLang + "").length() - 3), 35);
			font.drawString(20, 50, Game.inst().lang.Language.getTranslation() + ":",  35);
		}
		else if(selected == 1){
			mainVolume.draw(300 * Game.scale, 70  * Game.scale, 50);
			font.drawString(20, 70, Game.inst().lang.MasterVol.getTranslation() + ":", 35);
		}
		else if(selected == 2){
			musicVolume.draw(300 * Game.scale, 85  * Game.scale, 50);
			font.drawString(30, 85, Game.inst().lang.MusicVol.getTranslation() + ":", 35);
		}
		else if(selected == 3){
			soundVolume.draw(300 * Game.scale, 100  * Game.scale, 50);
			font.drawString(30, 100, Game.inst().lang.SoundVol.getTranslation() + ":", 35);
		}
		else if(selected == 4){
			vSync.draw(300 * Game.scale, 120  * Game.scale, 50);
			font.drawString(20, 120, Game.inst().lang.VSync.getTranslation() + ":", 35);
		}
		else if(selected == 5){
			debug.draw(300 * Game.scale, 140  * Game.scale, 50);
		}
		else if(selected == 6){
			font.drawString(100, 210, Game.inst().lang.Save.getTranslation(), 35);
		}
		else if(selected == 7){
			font.drawString(200, 210, Game.inst().lang.Cancel.getTranslation(), 35);
		}
		
		if(popup)confirm.render(new Dimension(160, 72), 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Game.inst().keyManager.update(this, delta);
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		if(this.selected - selected == 1 && this.selected == 7) selected = 5;
		else if(selected > 6) selected = 0;
		else if (selected < 0) selected = 6;
		this.selected = selected;
	}
	
	public void switchCancel() {
		if(selected == 6)this.selected = 7;
		else if(selected == 7)selected = 6;
	}
	
	public void pressedEnter() {
		if(selected == 4){
			vSync.value =! vSync.value;
		}else if(selected == 5){
			debug.value =! debug.value;
		}else if(selected == 6){
			if(popup){

				ConfigManager conf = Game.inst().config;
				Settings newSet = Game.inst().config.settings;
				newSet.masterVol = (mainVolume.getValue());
				newSet.musicVol  = (musicVolume.getValue());
				newSet.soundVol = (soundVolume.getValue());
				newSet.vSync = (vSync.value);
				newSet.debug = (debug.value);
				newSet.language = (selectedLang);
				conf.write(newSet);
				
				Game.inst().lang = new Localisation();
				popup = false;
				Game.inst().eventHandler.loadState(Game.inst().getState(Screens.MAIN.getID()));
			}else{
				popup = true;
			}
		}else if(selected == 7){
			Game.inst().eventHandler.loadState(Game.inst().getState(Screens.MAIN.getID()));
		}
		
	}
	
	public void pressedEscape(){
		if(popup){
			popup = false;
		}else{
			Game.inst().eventHandler.loadState(Game.inst().getState(Screens.MAIN.getID()));
		}
	}
	
	public void changeSlider(int change){
		if(selected == 0){
			if(change > 0) change = 1;
			else if(change < 0) change = -1;
			List<LANGUAGES> languagelist = Arrays.asList(LANGUAGES.values());
			if(languagelist.indexOf(selectedLang) + change > languagelist.size() - 1) selectedLang = languagelist.get(0);
			else if(languagelist.indexOf(selectedLang) + change < 0) selectedLang = languagelist.get(languagelist.size() -1);
			else selectedLang = languagelist.get(languagelist.indexOf(selectedLang) + change);
		}else if(selected == 1){
			mainVolume.setValue(mainVolume.getValue() + change);
		}else if(selected == 2){
			musicVolume.setValue(musicVolume.getValue() + change);
		}else if(selected == 3){
			soundVolume.setValue(soundVolume.getValue() + change);
		}
	}

	@Override
	public void loadState(GameState S) {
		if(S instanceof OptionsMenu){
			font = Game.inst().font;
			ConfigManager config = Game.inst().config;
			selectedLang = config.settings.language;
			mainVolume = new Slider((int)(config.settings.masterVol*100));
			musicVolume = new Slider((int)(config.settings.musicVol*100));
			soundVolume = new Slider((int)(config.settings.soundVol*100));
			vSync = new Toggle(config.settings.vSync);
			debug = new Toggle(config.settings.debug);
			confirm = new Popup(Game.inst().lang.ConfirmTitle.getTranslation(), Game.inst().lang.ConfirmMessage.getTranslation());
			Game.inst().eventHandler.loadedState(S);
			selected = 0;
		}
	}

	@Override
	public void unloadState(GameState S) {
		if(S instanceof OptionsMenu){		
			unloadRequest = true;
			mainVolume = null;
			musicVolume = null;
			soundVolume = null;
			vSync = null;
			debug = null;
			Game.inst().eventHandler.unloadedState(S);
		}
	}
}
