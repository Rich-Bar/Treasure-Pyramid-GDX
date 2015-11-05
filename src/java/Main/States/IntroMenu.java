package main.states;

import main.Game;
import main.Game.Screens;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.types.MenuButton;
import main.types.Sound;

public class IntroMenu extends BaseState {
	
	private Sound music;
	private Image logo;
	private MenuButton skipProlog;
	private boolean skipable = false;
	private float musicPos = 0;
	private float musicLength = 200f;
	private boolean isPaused;
	private float scrollPos;
	private Image scroll;
	private Image scrollOverlay;
	
	public IntroMenu(int ID){
		super();
		this.ID = ID;
	}

	public void switchScreen(){
		music.stop();
		Game.inst().eventHandler.loadState(Game.inst().getState(Screens.MAIN.getID()));
	}

	@Override
	public boolean isAcceptingInput() {
		return skipable;
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
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentStateID() == Screens.INTRO.getID()){
			unpause();
		}
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentStateID() == Screens.INTRO.getID() && !unloadRequest){
			pause();
		}
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		if(music.getSound().getPosition() > 2f && music.getSound().getPosition() < 198){
			if(scrollPos < scroll.getHeight() + 100){
				scrollPos = (float)((Math.pow((double)music.getSound().getPosition() * 0.05, 2.301029995665)) / musicLength * scroll.getHeight());
			}
			scroll.draw(0, 0, 360 * Game.scale, 240 * Game.scale, 0, scrollPos, 360, scrollPos + 240);
			scrollOverlay.draw(0, 0, Game.scale);
			skipProlog.draw(skipProlog.getMiddle(Game.pixelartResolution.width, true) * Game.scale, 200 * Game.scale);
		}else if(music.getSound().getPosition() > 198){
			switchScreen();
		}
		logo.draw(0, 0, Game.scale * 2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		float musicPos =  music.getSound().getPosition();
		if(musicPos > 0.3f && musicPos < 4f){
			logo.setAlpha((music.getSound().getPosition()-0.3f));
		}else if(musicPos >= 4f && musicPos < 8f){
			if(!skipable) skipable = true;
			logo.setAlpha((8f-music.getSound().getPosition()) * 0.65f );
		}else{
			logo.setAlpha(0f);
		}
		
		if(!arg0.hasFocus() && !isPaused){
			isPaused = true;
			pause();
		}else if(arg0.hasFocus() && isPaused){
			unpause();
			isPaused = false;
		}
	}
	
	@Override
	public void pause(){
		musicPos = music.getSound().getPosition();
		music.stop();
	}
	
	@Override
	public void unpause(){
		music.playAt(musicPos);
	}

	@Override
	public void loadState(GameState S) {
		if(S instanceof IntroMenu){	
			try {
				logo = new Image("src/assets/Textures/RichyEntertainment_LowRes.png");
				skipProlog = new MenuButton("src/assets/Textures/PrologSkip.png", 1);
				scroll = new Image("src/assets/Textures/PrologScroll.png");
				scrollOverlay = new Image("src/assets/Textures/Overlays/Credits.png");
				
				logo.setAlpha(0f);
				
				logo.setFilter(Image.FILTER_NEAREST);
				scroll.setFilter(Image.FILTER_NEAREST);
				
				music = new Sound("src/assets/Sound/SeminararbeitProlog-Intro.ogg");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			Game.inst().eventHandler.loadedState(this);
		}
	}

	@Override
	public void unloadState(GameState S) {
		if(S instanceof IntroMenu){		
			unloadRequest = true;
			music = null;
			logo = null;
			skipProlog = null;
			scroll = null;
			scrollOverlay = null;
			Game.inst().eventHandler.unloadedState(S);
		}
	}
}
