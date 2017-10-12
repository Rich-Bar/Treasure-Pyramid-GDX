package richbar.com.github.treasurepyramid.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import richbar.com.github.treasurepyramid.EventSubscriber;
import richbar.com.github.treasurepyramid.assets.Loadable;
import richbar.com.github.treasurepyramid.block.Block;

import java.util.LinkedList;

/**
 * Created by Rich Y on 21.07.2017.
 */
public abstract class BasicScreen implements Screen, EventSubscriber{
	
	String screenId;
	int width, height;
	boolean resizeable = true, fixedAspect = true, visible;
	protected LinkedList<ModelInstance> loadedModels = new LinkedList<ModelInstance>();
	protected LinkedList<Loadable> loadingObjects = new LinkedList<Loadable>();
	
	
	@Override
	public void show() { visible = true; }
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height= height;
	}
	
	/**
	 * CALL SUPER or loadData!!
	 * otherwise loading infrastructure is useless...
	 * @param delta
	 */
	@Override
	public void render(float delta) {
		loadData();
	}
	
	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
	
	@Override
	public void hide() { visible = false; }
	
	@Override
	public void eventUpdate(Event e, Object args) {}
	
	public abstract void create();
	
	void loadData() {
		for (Loadable obj : loadingObjects){
			if(obj.isLoaded()){
				if(obj instanceof Block) loadedModels.add(new ModelInstance(((Block) obj).getModel()));
				loadingObjects.remove(obj);
			}
		}
	}
}
