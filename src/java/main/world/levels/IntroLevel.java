package main.world.levels;

import main.Game;
import main.types.Point3D;
import main.world.blocks.Block;

public class IntroLevel extends Level{

	public IntroLevel(Game game) {
		super(game);
	}

	@Override
	public Block[][] getLevel() {
		return null;
	}
	
	@Override
	public void loadedLevel(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadLevel(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadLevel(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	Point3D getGoal() {
		return null;
	}

}
