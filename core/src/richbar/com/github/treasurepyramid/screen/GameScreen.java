package richbar.com.github.treasurepyramid.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import richbar.com.github.treasurepyramid.Licence;
import richbar.com.github.treasurepyramid.Main;
import richbar.com.github.treasurepyramid.World;
import richbar.com.github.treasurepyramid.block.GenericBlock;

/**
 * Created by Rich Y on 21.07.2017.
 */
public class GameScreen extends BasicScreen {
	private GameScreen screen;
	private World world;
	private PerspectiveCamera cam;
	private ModelBatch modelBatch;
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(0.666f,0.666f,0.666f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(cam == null) create();
		modelBatch.begin(cam);
		for(ModelInstance model : loadedModels) modelBatch.render(model);
		modelBatch.end();
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
	public void eventUpdate(Event e, Object args) {
		
	}
	
	@Override
	public void create() {
		
		screen = new GameScreen();
		screen.show();
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(6f, 6f, 6f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		new Licence();
		loadingObjects.add(new GenericBlock());
		/*pillar = new Microblock() {
			@Override
			public String getId() {
				return "PillarMicroBlock";
			}
		}.getModel();*/
		
		Main.logger.line();
		world = new World();
	}
	
	World getWorld(){
		return world;
	}
	
}
