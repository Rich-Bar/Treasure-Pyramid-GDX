package main.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import main.Game;
import main.types.Point3D;
import main.world.blocks.Block;
import main.world.effects.Effect;
import main.world.entitys.Entity;



public final class World {


	private Effect activeEffect = new Effect();
	
	private Map<Point3D, List<Block>> solids = new HashMap<>();
	private List<Entity> entitys = new ArrayList<>();	
	
	public Block[] getBlocks(){
		@SuppressWarnings("unchecked")
		List<Block>[] tmp = (List<Block>[]) solids.values().toArray();
		List<Block> res = new ArrayList<>();
		for(List<Block> list : Arrays.asList(tmp)){
			for(Block block : list){
				res.add(block);
			}
		}
		return (Block[]) res.toArray();
	}
	
	public Block getBlockAt(int x, int y, int z){
		return solids.get(new Point3D(x, y, z)).get(0);
	}
	
	public Block[] getBlocksAt(int x, int y, int z){
		List<Block> list = solids.get(new Point3D(x, y, z)); 
		return (Block[]) list.toArray(new Block[list.size()]);
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
	
}
