package richbar.com.github.treasurepyramid.block;

import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Created by Rich Y on 04.08.2017.
 */
public class GenericBlock extends Block {
	private Model model;
	
	@Override
	public String getId() {
		return "GenericBlock";
	}
}