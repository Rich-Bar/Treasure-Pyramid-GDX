package main.states;

import main.Game;
import main.Game.Screens;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.types.Sound;

public class CreditsMenu extends BaseState{
	
	private boolean isPaused;
	private Image credits;
	private Image creditsOverlay;
	private float musicPos;
	private Sound music;
	private float creditsPos;
	
	public CreditsMenu(int id){
		super();
		this.id = id;
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
		
		if(creditsPos < credits.getHeight() - 240){
			creditsPos = music.getSound().getPosition() * 10; // 10px/s
		}
		credits.draw(0, 0, 360 * Game.inst().scale, 240 * Game.inst().scale, 0, creditsPos, 360, creditsPos + 240);
		creditsOverlay.draw(0, 0, Game.inst().scale);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
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
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentState() instanceof CreditsMenu) unpause();
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentState() instanceof CreditsMenu && !unloadRequest) pause();
	}

	public void switchScreen(){
		Game.inst().eventHandler.loadState(Game.inst().getState(Screens.MAIN.getID()));
	}

	@Override
	public void loadState(GameState state) {
		if(state instanceof CreditsMenu){			
			try {
				music = new Sound("Sound/This Will Destroy You - The Mighty Rio Grande.ogg");
				credits = new Image("Textures/Credits.png");
				creditsOverlay = new Image("Textures/Overlays/Credits.png");
				credits.setFilter(Image.FILTER_NEAREST);
				creditsPos = 0;
				musicPos = 0;
			} catch (SlickException e) {
				e.printStackTrace();
			}
			Game.inst().eventHandler.loadedState(state);
		}
	}

	@Override
	public void unloadState(GameState state) {
		if(state instanceof CreditsMenu){			
			unloadRequest = true;	
			music = null;
			credits = null;
			creditsOverlay = null;
			Game.inst().eventHandler.unloadedState(state);
		}
	}
}
