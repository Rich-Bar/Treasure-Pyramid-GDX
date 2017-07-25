package main.states;

import main.Game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.events.StateEvents;

public abstract class BaseState implements GameState, StateEvents{

	protected int id;
	protected boolean unloadRequest = false;
	
	public BaseState() {
		Game.inst().eventHandler.addListener(this);
	}
	
	@Override
	public int getID() {
		return id;
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
	public void setInput(Input arg0) {}

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
	public void enter(GameContainer arg0, StateBasedGame arg1)throws SlickException {}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1) throws SlickException {}
	
	@Override
	public void loadedState(GameState state) {}
	
	@Override
	public void unloadedState(GameState state) {}
	
	public void pause() {}
	
	public void unpause() {}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {}
	
	public abstract boolean isAcceptingInput();
	
	public abstract void keyPressed(int arg0, char arg1);

	public abstract void keyReleased(int arg0, char arg1);
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {}
	}

	public abstract void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException;
}
