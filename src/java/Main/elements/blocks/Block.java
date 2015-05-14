package main.elements.blocks;

import org.newdawn.slick.Image;

public interface Block {

	boolean isInteractable();
	
	boolean isSeethrough();
	
	double getPassthroughSpeed();
	
	Image getTexture();
	
	void interact();
	
	void passthrough();

	void applyLight(Image frame);
	
}
