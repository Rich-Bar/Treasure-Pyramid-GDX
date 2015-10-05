package main.states;

import main.Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameScreen extends BaseState {
	
	private int ID;
	
	public GameScreen(int ID){
		super();
		this.ID = ID;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadState(GameState S) {
		Game.getInstance().eventHandler.loadedState(S);
		
	}

	@Override
	public void unloadState(GameState S) {
		Game.getInstance().eventHandler.unloadedState(S);
		
	}

}
