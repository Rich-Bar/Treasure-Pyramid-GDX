package richbar.com.github.treasurepyramid;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import richbar.com.github.treasurepyramid.block.Block;

import java.util.Map;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class World {
	
	public Map<Vector3, Block> blocks;
	BoundingBox loaded = new BoundingBox(new Vector3(-20, -20, -20), new Vector3(20, 20, 20));
	
	
	WorldFragment getViewportFragment(){
		return new WorldFragment();
	}
	
	public class WorldFragment {
		Map<Vector3, Block> blocks;
	}
}
