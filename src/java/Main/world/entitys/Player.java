package main.world.entitys;

import main.world.World;
import main.world.items.Inventory;

import org.lwjgl.util.vector.Vector3f;

public final class Player extends Entity{

	public Vector3f escapePointOffset;
	
	private Inventory inv = new Inventory();
	
	public void consume(World w){
		if(inv.getCurrentItem().count > 1){
			inv.getCurrentItem().count -= 1;
			w.setEffect(inv.getCurrentItem().getEffect());
		}
	}
	
	public void move(Vector3f dir){
		location.translate(dir.x, dir.y, dir.z);
	}
	
	public void update(){
		location = location.translate(velocity.x,velocity.y, velocity.z);
		velocity.set(velocity.x * 0.98f, velocity.x * 0.98f, velocity.x * 0.98f);
	}
}
