package main.world.items;

import main.world.effects.GanjaEffect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Mushroom extends Item{

	public Mushroom() throws SlickException {
		super("Schroom", 2, new Image("src/assets/Textures/Cursor.png"), new GanjaEffect());
	}
	
	@Override
	public String getName() {
		if(count == 1)
			return name;
		else
			return name + "s";
	}

}
