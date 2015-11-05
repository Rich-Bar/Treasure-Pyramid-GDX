package main.world.items;

import main.world.effects.GanjaEffect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Kush extends Item{

	public Kush() throws SlickException {
		super("Kush", 2, new Image("src/assets/Textures/Cursor.png"), new GanjaEffect());
	}


}
