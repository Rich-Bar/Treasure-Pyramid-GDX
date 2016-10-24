package main.world.blocks;

import main.world.effects.Effect;

import org.lwjgl.util.Point;
import org.newdawn.slick.Image;

public class FloorBlock extends Block{

	Image img;
	
	public FloorBlock(Point coord) {
		super(coord);
		try{
			String basePath = "Textures/Blocks/";
			img = new Image(basePath + "sandstone_top.png");
		}catch(Exception e){
		}
	}

	@Override
	public Image getImage() {
		return img;
	}

	@Override
	public void interact() {
		
	}

	@Override
	public boolean isEffected(Effect e) {
		return false;
	}

}
