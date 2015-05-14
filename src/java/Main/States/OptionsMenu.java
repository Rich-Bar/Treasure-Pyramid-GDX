package main.states;

import java.awt.Dimension;

import main.Game;
import main.Game.Screens;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.managers.ConfigManager;
import main.managers.ConfigManager.Settings;
import main.types.Popup;
import main.types.Slider;
import main.types.Toggle;

public class OptionsMenu extends BaseState{
	
	private int ID;
	private Game mainGame;
	
	private int selected = 0;
	
	private Slider mainVolume;
	private Slider musicVolume;
	private Slider soundVolume;
	private Toggle vSync;
	private Toggle debug;
	private Toggle keyAxis;
	private Popup confirm;
	private boolean popup;
	
	
	public OptionsMenu(int ID, Game game){
		super(game);
		this.ID = ID;
		mainGame = game;
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		mainGame.keyManager.keyPressed(arg0, this);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		mainGame.keyManager.keyReleased(arg0);
		
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		String keyAxisText = "Isometric";
		if(keyAxis.value){
			keyAxisText = "Direct";
		}
		
		
		mainGame.font.drawStringAlignMiddle(160, 10, "Options",Color.white, 75);
		mainGame.font.drawStringAlignMiddle(160, 10, "____________",Color.white, 75);
		
		mainVolume.draw(300 * Game.scale, 50  * Game.scale, 50, Color.decode("0x999999"));
		musicVolume.draw(300 * Game.scale, 65  * Game.scale, 50, Color.decode("0x999999"));
		soundVolume.draw(300 * Game.scale, 80  * Game.scale, 50, Color.decode("0x999999"));
		vSync.draw(300 * Game.scale, 100  * Game.scale, 50, Color.decode("0x999999"));
		mainGame.font.drawString(282, 120, keyAxisText, Color.decode("0x999999"), 35);
		debug.draw(300 * Game.scale, 140  * Game.scale, 50, Color.decode("0x999999"));
		
		mainGame.font.drawString(20, 50, "Main Volume:", Color.decode("0x999999"), 35);
		mainGame.font.drawString(30, 65, "Music Volume:", Color.decode("0x999999"), 35);
		mainGame.font.drawString(30, 80, "Sound Volume:", Color.decode("0x999999"), 35);
		mainGame.font.drawString(20, 100, "VSync:", Color.decode("0x999999"), 35);
		mainGame.font.drawString(20, 120, "Key Axis:", Color.decode("0x999999"), 35);
		mainGame.font.drawString(20, 140, "Debug:", Color.decode("0x999999"), 35);

		if(selected == 0){
			mainVolume.draw(300 * Game.scale, 50  * Game.scale, 50);
			mainGame.font.drawString(20, 50, "Main Volume:", 35);
		}
		else if(selected == 1){
			musicVolume.draw(300 * Game.scale, 65  * Game.scale, 50);
			mainGame.font.drawString(30, 65, "Music Volume:", 35);
		}
		else if(selected == 2){
			soundVolume.draw(300 * Game.scale, 80  * Game.scale, 50);
			mainGame.font.drawString(30, 80, "Sound Volume:", 35);
		}
		else if(selected == 3){
			vSync.draw(300 * Game.scale, 100  * Game.scale, 50);
			mainGame.font.drawString(20, 100, "VSync:", 35);
		}
		else if(selected == 4){
			mainGame.font.drawString(20, 120, "Key Axis:", 35);
			mainGame.font.drawString(282, 120, keyAxisText, 35);
		}
		else if(selected == 5){
			debug.draw(300 * Game.scale, 140  * Game.scale, 50);
			mainGame.font.drawString(20, 140, "Debug:", 35);
		}
		
		if(popup)confirm.render(new Dimension(160, 72), 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		mainGame.keyManager.update(this, delta);
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		if(selected > 6) selected = 0;
		else if (selected < 0) selected = 6;
		this.selected = selected;
	}
	
	public void pressedEnter() {
		if(selected == 3){
			vSync.value =! vSync.value;
		}else if(selected == 4){
			keyAxis.value =! keyAxis.value;
		}else if(selected == 5){
			debug.value =! debug.value;
		}else if(selected == 6){
			if(popup){

				ConfigManager conf = mainGame.config;
				Settings newSet = mainGame.config.settings;
				newSet.setMasterVol(mainVolume.getValue());
				newSet.setMusicVol(musicVolume.getValue());
				newSet.setSoundVol(soundVolume.getValue());
				newSet.setvSync(vSync.value);
				newSet.setKeyAxis(keyAxis.value);
				newSet.setDebug(debug.value);
				conf.write(newSet);
				
				mainGame.resetResolution(mainGame.getContainer());
				popup = false;
				mainGame.eventHandler.loadState(mainGame.getState(Screens.MAIN.getID()));
			}else{
				popup = true;
			}
		}
	}
	
	public void pressedEscape(){
		if(popup){
			popup = false;
		}else{
			mainGame.eventHandler.loadState(mainGame.getState(Screens.MAIN.getID()));
		}
	}
	
	public void changeSlider(int change){
		if(selected == 0){
			mainVolume.setValue(mainVolume.getValue() + change);
		}else if(selected == 1){
			musicVolume.setValue(musicVolume.getValue() + change);
		}else if(selected == 2){
			soundVolume.setValue(soundVolume.getValue() + change);
		}
	}

	@Override
	public void loadState(GameState S) {
		if(S instanceof OptionsMenu){
			mainVolume = new Slider((int)( mainGame.config.settings.getMasterVol()*100));
			musicVolume = new Slider((int)( mainGame.config.settings.getMusicVol()*100));
			soundVolume = new Slider((int)( mainGame.config.settings.getSoundVol()*100));
			vSync = new Toggle(mainGame.config.settings.isvSync());
			debug = new Toggle(mainGame.config.settings.isDebug());
			keyAxis = new Toggle(mainGame.config.settings.getKeyAxis());
			confirm = new Popup("Confirm?", "to confirm your changes press\n[ENTER] or [SPACE]\n\nTo abort press anything...", mainGame);
			mainGame.eventHandler.loadedState(S);
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
			keyAxis = null;
			mainGame.eventHandler.unloadedState(S);
		}
	}
}
