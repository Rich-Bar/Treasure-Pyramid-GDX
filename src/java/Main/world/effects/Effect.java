package main.world.effects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import main.world.World;
import main.world.blocks.Block;

public class Effect {
	
	private static int blockSize = 80;

	public void renderPlayer(World w, GameContainer gc, StateBasedGame sbg, Graphics g){
		
	}
	
	public void renderBlocks(World w, GameContainer gc, StateBasedGame sbg, Graphics g){
		for(Block b : w.getBlocks().get(0)){
			if(b == null) break;
			Image img = b.getImage();
			g.drawImage(img, b.getPosition().getX() * blockSize,b.getPosition().getY() * blockSize, b.getPosition().getX() * blockSize + blockSize, b.getPosition().getY() * blockSize + blockSize, 0, 0, img.getWidth(), img.getHeight());
		}
	}
	
	public void renderEntitys(World w, GameContainer gc, StateBasedGame sbg, Graphics g){
		
	}
	
	public void renderPostFX(World w, GameContainer gc, StateBasedGame sbg, Graphics g){
		
	}
	
}
