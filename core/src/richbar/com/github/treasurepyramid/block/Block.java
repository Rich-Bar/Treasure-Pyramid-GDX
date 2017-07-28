package richbar.com.github.treasurepyramid.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import javafx.geometry.BoundingBox;
import richbar.com.github.treasurepyramid.EventSubscriber;
import richbar.com.github.treasurepyramid.Main;

/**
 * Created by Rich Y on 21.07.2017.
 */
public abstract class Block implements EventSubscriber {
	
	public abstract String getId();
	
	Vector3 location;
	String name;
	Mesh mesh;
	BoundingBox bounds;
	Texture texture;
	Material attributes;
	
	public Block(){
		Main.getInstance().getAssetManager().load("data/blocks/"+getId()+".json", String.class);
		Main.getInstance().addAssetListener(this, getId());
	}
	
	public ModelInstance getModel(){
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
			builder.manage(mesh);
			builder.manage(texture);
		return new ModelInstance(builder.end());
	}
	boolean stayUpdated = true;
	@Override
	public void eventUpdate(Event e, Object args) {
		AssetManager manager = (AssetManager)args;
		if(e == Event.ASSET_LOADED && manager.isLoaded("data/blocks/"+getId()+".json")){
			Gdx.app.debug(Main.getLoggerTag(), "loaded" + "data/blocks/"+getId()+".json");
			stayUpdated = false;
			JsonValue json = new JsonReader().parse((String)manager.get("data/blocks/"+getId()+".json"));
			
			name = json.getString("name");
			
			
		}
	}
	
	@Override
	public boolean stayUpdated() {
		return stayUpdated;
	}
}
