package richbar.com.github.treasurepyramid.screen;

import com.badlogic.gdx.Screen;
import richbar.com.github.treasurepyramid.EventSubscriber;

/**
 * Created by Rich Y on 21.07.2017.
 */
public abstract class BasicScreen implements Screen, EventSubscriber{
	
	String screenId;
	int width, height;
	boolean resizeable = true, fixedAspect = true, visible;
	
	
	@Override
	public void show() { visible = true; }
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height= height;
	}
	
	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
	
	@Override
	public void hide() { visible = false; }
	
	@Override
	public void eventUpdate(Event e, Object args) {
		
	}
	public abstract void create();
}
