package main.events;

import org.newdawn.slick.state.GameState;

public interface StateEvents extends Event{

	void loadedState(GameState state);

	void loadState(GameState state);
	
	void unloadedState(GameState state);
	
	void unloadState(GameState state);
}
