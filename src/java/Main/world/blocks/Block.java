package main.world.blocks;

import main.world.effects.Effect;

import org.lwjgl.util.Point;
import org.newdawn.slick.Image;

public abstract class Block {
	
	private Point location = new Point();
	
	public Block(Point coord) {
		location = coord;
	}
	
	public Point getPosition(){
		return location;
	}
	
	public abstract Image getImage();
	
	public abstract void interact();
	
	public abstract boolean isEffected(Effect e);
}
