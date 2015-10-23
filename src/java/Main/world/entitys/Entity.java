package main.world.entitys;

import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

public interface Entity {

	static UUID uuid = UUID.randomUUID();
	
	Vector3f getVelocity();
	Vector3f getLocation();
	
}
