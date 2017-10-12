package richbar.com.github.treasurepyramid.screen;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class OptionsScreen  extends BasicScreen{
	private OptionsScreen screen;
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
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
		screen = new OptionsScreen();
		screen.show();
	}
}
