package main.events;

import org.newdawn.slick.state.GameState;

public interface StateEvents extends Event{

	void loadedState(GameState S);

	void loadState(GameState S);
	
	void unloadedState(GameState S);
	
	void unloadState(GameState S);
}
