package main.elements.levels;

import main.Game;
import main.elements.blocks.Block;
import main.types.Location;

public class IntroLevel extends Level{

	public IntroLevel(Game game) {
		super(game);
	}

	@Override
	public Block[][] getLevel() {
		return null;
	}
	
	@Override
	public void loadedLevel(Level L) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadLevel(Level L) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadLevel(Level L) {
		// TODO Auto-generated method stub
		
	}

	@Override
	Location getGoal() {
		return null;
	}

}
