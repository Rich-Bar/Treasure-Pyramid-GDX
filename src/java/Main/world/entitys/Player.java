package main.world.entitys;

import org.lwjgl.util.vector.Vector3f;

public class Player implements Entity{

	private Vector3f velocity;
	private Vector3f location;
	public Vector3f escapePointOffset;
	
	@Override
	public Vector3f getVelocity() {
		return velocity;
	}

	@Override
	public Vector3f getLocation() {
		return location;
	}
	
	public void move(Vector3f dir){
		location.translate(dir.x, dir.y, dir.z);
	}
	
	public void update(){
		location = location.translate(velocity.x,velocity.y, velocity.z);
		velocity.set(velocity.x * 0.98f, velocity.x * 0.98f, velocity.x * 0.98f);
	}

}
