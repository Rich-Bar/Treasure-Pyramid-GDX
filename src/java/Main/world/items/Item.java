package main.world.items;

import org.newdawn.slick.Image;

import main.world.effects.Effect;
import main.world.entitys.Entity;

public abstract class Item extends Entity{
	
	public int count = 0;
	protected Effect effect;
	protected String name;
	protected Image image;
	
	public Item(String name, int count, Image img, Effect effect) {
		this.name = name;
		this.count = count;
		this.image = img;
		this.effect = effect;
	}
	
	public Effect getEffect(){
		return effect;
	}
	
	public Image getImage(){
		return image;
	}
	
	public String getName(){
		return name;
	}
	
}