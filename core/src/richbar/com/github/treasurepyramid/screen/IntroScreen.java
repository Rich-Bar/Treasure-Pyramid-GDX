package richbar.com.github.treasurepyramid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import richbar.com.github.treasurepyramid.Licence;
import richbar.com.github.treasurepyramid.Main;
import richbar.com.github.treasurepyramid.block.GenericBlock;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class IntroScreen extends BasicScreen{
	
	private ModelBatch modelBatch;
	private PerspectiveCamera cam;
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(cam == null) create();
		modelBatch.begin(cam);
		for(ModelInstance model : loadedModels) modelBatch.render(model);
		modelBatch.end();
	}
	
	@Override
	public void dispose() {
		
	}
	
	@Override
	public boolean stayUpdated() {
		return false;
	}
	
	@Override
	public void create() {
		loadingObjects.add(new GenericBlock());
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		new Licence();
		Main.logger.line();
	}
}
