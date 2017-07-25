package main.events;

import main.world.levels.Level;

public interface LevelEvents extends Event{

	void loadedLevel(Level level);

	void loadLevel(Level level);
	
	void unloadLevel(Level level);
}
