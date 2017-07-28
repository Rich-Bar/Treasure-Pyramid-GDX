package richbar.com.github.treasurepyramid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import richbar.com.github.treasurepyramid.block.Block;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class IntroScreen extends BasicScreen{
	private IntroScreen screen;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose() {
		screen = null;
	}
	
	@Override
	public boolean stayUpdated() {
		return false;
	}
	
	@Override
	public void create() {
		screen = new IntroScreen();
		screen.show();
		Block block = new Block() {
			@Override
			public String getId() {
				return "GenericBlock";
			}
		};
	}
}
