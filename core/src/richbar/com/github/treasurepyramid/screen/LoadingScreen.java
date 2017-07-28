package richbar.com.github.treasurepyramid.screen;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class LoadingScreen extends BasicScreen {
	private LoadingScreen screen;
	
	@Override
	public void render(float delta) {
		
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
		screen = new LoadingScreen();
		screen.show();
	}
}
