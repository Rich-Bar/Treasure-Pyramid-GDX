package main.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import main.Game;
import main.world.blocks.Block;
import main.world.effects.Effect;
import main.world.entitys.Entity;



public final class World {


	private Effect activeEffect = new Effect();
	
	private Map<Point, List<Block>> solids = new HashMap<>();
	private List<Entity> entitys = new ArrayList<>();	
	
	public List<List<Block>> getBlocks(){
		return new ArrayList<List<Block>>(solids.values());
	}
	
	public List<Block> getBlockAt(int x, int y){
		return solids.get(new Point(x, y));
	}
	
	public List<Entity> getEntitys(){
		return entitys;
	}
	
	public Entity getEntityAt(int x, int y, int z){
		for(Entity e : entitys){
			if (((int)e.getLocation().getX()) == x &&
				((int)e.getLocation().getY()) == y &&
				((int)e.getLocation().getZ()) == z) return e;
		}
		return null;
	}
	
	public Entity[] getEntitysAt(int x, int y, int z){
		List<Entity> res = new ArrayList<>();
		for(Entity e : entitys){
			if (((int)e.getLocation().getX()) == x &&
				((int)e.getLocation().getY()) == y &&
				((int)e.getLocation().getZ()) == z) res.add(e);
		}
		return (Entity[]) res.toArray();
	}
	
	public void update(){
		Game.inst().player.update();
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		activeEffect.renderBlocks(this, gc, sbg, g);
		activeEffect.renderEntitys(this, gc, sbg, g);
		activeEffect.renderPlayer(this, gc, sbg, g);
		activeEffect.renderPostFX(this, gc, sbg, g);
	}

	/**
	 * @return the activeEffect
	 */
	public void resetEffect() {
		this.activeEffect = new Effect();
	}

	public void setEffect(Effect activeEffect) {
		this.activeEffect = activeEffect;
	}
	
	public void load(){
		for(int i = 3; i < 13; i++){
			for(int j = 3; j < 10; j++){
			//	solids.put(new Point(i, j), new FloorBlock(new Point(i, j)));
			}	
		}
	}
	
}
