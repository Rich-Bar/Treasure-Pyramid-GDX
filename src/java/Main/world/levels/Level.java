package main.world.levels;

import main.Game;
import main.events.LevelEvents;
import main.types.Point3D;
import main.world.blocks.Block;

public abstract class Level implements LevelEvents {

	protected Game mainGame;
	
	public Level(Game game) {
		mainGame = game;
		mainGame.eventHandler.addListener(this);
	}
	abstract Point3D getGoal();
	
	abstract Block[][] getLevel();
}
