package richbar.com.github.treasurepyramid.world;

import com.badlogic.gdx.math.Vector3;
import richbar.com.github.treasurepyramid.block.Block;

import java.util.Map;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class World {
	
	Map<Vector3, Block> blocks;
	
	
	
	WorldFragment getViewportFragment(){
		return new WorldFragment();
	}
	
	public class WorldFragment {
		Map<Vector3, Block> blocks;
	}
}
