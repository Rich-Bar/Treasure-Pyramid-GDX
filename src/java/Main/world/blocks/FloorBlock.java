package main.world.blocks;

import main.types.Point3D;
import main.world.effects.Effect;
import main.world.effects.GanjaEffect;

import org.newdawn.slick.Image;

public class FloorBlock implements Block{

	private Point3D location = new Point3D();
	
	public FloorBlock(Point3D loc) {
		location = loc;
	}
	
	@Override
	public boolean isInteractable() {
		return false;
	}

	@Override
	public double getPassthroughSpeed() {
		return 1;
	}

	@Override
	public Image getTexture() {
		return null;
	}

	@Override
	public void interact() {
	}

	@Override
	public void passthrough() {
	}

	@Override
	public void applyLight(Image frame) {
	}

	@Override
	public boolean isSeethrough() {
		return true;
	}

	@Override
	public Point3D getPosition() {
		return location;
	}

	@Override
	public boolean isEffected(Effect e) {
		if(e instanceof GanjaEffect)return true;
		else return false;
	}

}
