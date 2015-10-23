package main.events;

import main.world.levels.Level;

public interface LevelEvents extends Event{

	void loadedLevel(Level L);

	void loadLevel(Level L);
	
	void unloadLevel(Level L);
}
