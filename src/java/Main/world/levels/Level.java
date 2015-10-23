package main.world.levels;

import main.Game;
import main.events.LevelEvents;
import main.types.Location;
import main.world.blocks.Block;

public abstract class Level implements LevelEvents {

	protected Game mainGame;
	
	public Level(Game game) {
		mainGame = game;
		mainGame.eventHandler.addListener(this);
	}
	abstract Location getGoal();
	
	abstract Block[][] getLevel();
}
