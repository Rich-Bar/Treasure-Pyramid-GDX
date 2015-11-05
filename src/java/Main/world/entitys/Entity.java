package main.world.entitys;

import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

public abstract class Entity {

	static UUID uuid = UUID.randomUUID();
	
	protected Vector3f velocity;
	protected Vector3f location;
	
	public Vector3f getVelocity(){
		return velocity;
	}
	
	public Vector3f getLocation(){
		return location;
	}
	
}
