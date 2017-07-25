package main.states;

import main.Game;
//import main.world.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameScreen extends BaseState {
	
	//private World world = new World();
	
	public GameScreen(int id){
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
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadState(GameState state) {
		if(state instanceof GameScreen){
			//world.load();
			Game.inst().eventHandler.loadedState(state);
		}
		
	}

	@Override
	public void unloadState(GameState state) {
		Game.inst().eventHandler.unloadedState(state);
		
	}

}
