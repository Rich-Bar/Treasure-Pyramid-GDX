package main.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.types.Point3D;
import main.world.blocks.Block;
import main.world.entitys.Player;



public final class World {

	private Map<Point3D, List<Block>> solids = new HashMap<>();
	private Player player = new Player();
	
	public Block getBlockAt(int x, int y, int z){
		return solids.get(new Point3D(x, y, z)).get(0);
	}
	
	public Block[] getBlocksAt(int x, int y, int z){
		List<Block> list = solids.get(new Point3D(x, y, z)); 
		return (Block[]) list.toArray(new Block[list.size()]);
	}
	
	public void update(){
		player.update();
	}
	
	public Player getPlayer(){
		return player;
	}
	
}
