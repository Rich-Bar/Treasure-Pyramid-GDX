package main.elements.levels;

import main.Game;
import main.elements.blocks.Block;
import main.events.LevelEvents;
import main.types.Location;

public abstract class Level implements LevelEvents {

	protected Game mainGame;
	
	public Level(Game game) {
		mainGame = game;
		mainGame.eventHandler.addListener(this);
	}
	abstract Location getGoal();
	
	abstract Block[][] getLevel();
}
