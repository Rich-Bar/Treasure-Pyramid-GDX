package main.states;

import main.Game;
import main.events.StateEvents;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingScreen implements GameState, StateEvents{

	private Image screen;
	private int newTarget;
	private int oldTarget;
	private boolean rendered = false;
	private boolean notified = false;
	
	public LoadingScreen() {
	}
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseWheelMoved(int arg0) {}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {
		return false;
	}

	@Override
	public void setInput(Input arg0) {}

	@Override
	public void keyPressed(int arg0, char arg1) {}

	@Override
	public void keyReleased(int arg0, char arg1) {}

	@Override
	public void controllerButtonPressed(int arg0, int arg1) {}

	@Override
	public void controllerButtonReleased(int arg0, int arg1) {}

	@Override
	public void controllerDownPressed(int arg0) {}

	@Override
	public void controllerDownReleased(int arg0) {}

	@Override
	public void controllerLeftPressed(int arg0) {}

	@Override
	public void controllerLeftReleased(int arg0) {}

	@Override
	public void controllerRightPressed(int arg0) {}

	@Override
	public void controllerRightReleased(int arg0) {}

	@Override
	public void controllerUpPressed(int arg0) {}

	@Override
	public void controllerUpReleased(int arg0) {}

	@Override
	public void loadedState(GameState s) {}

	@Override
	public void loadState(GameState s) {}

	@Override
	public void unloadedState(GameState s) {}

	@Override
	public void unloadState(GameState s) {}

	public void setTarget(int newID, int oldID){
		newTarget = newID;
		oldTarget = oldID;
		rendered = false;
		notified = false;
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentStateID() == newTarget){
			unloadState(arg1.getState(oldTarget));
		}
	}

	@Override
	public int getID() {
		return Game.Screens.LOADING.getID();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {}
	
	public void init(){
		try {
			screen = new Image("Textures/Overlays/Loading.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)throws SlickException {	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {}
		
		screen.draw(0,0, Game.inst().scale);
		if(rendered == false){
			rendered = true;
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)throws SlickException {
		if(rendered && !notified){
			notified = true;
			Game.inst().keyManager.clearKeys();
			Game.inst().eventHandler.notifyLoad(Game.inst().getState(newTarget));
		}
	}
}
