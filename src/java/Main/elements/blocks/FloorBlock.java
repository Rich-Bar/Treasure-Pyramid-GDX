package main.elements.blocks;

import org.newdawn.slick.Image;

public class FloorBlock implements Block{

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

}
