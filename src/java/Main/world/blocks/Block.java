package main.world.blocks;

import main.types.Point3D;
import main.world.effects.Effect;

import org.newdawn.slick.Image;

public interface Block {
	
	Point3D getPosition();
	
	boolean isInteractable();
	
	boolean isSeethrough();
	
	double getPassthroughSpeed();
	
	Image getTexture();
	
	void interact();
	
	void passthrough();

	void applyLight(Image frame);
	
	boolean isEffected(Effect e);
}
