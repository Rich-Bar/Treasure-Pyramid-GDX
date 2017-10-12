package richbar.com.github.treasurepyramid.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import richbar.com.github.treasurepyramid.EventSubscriber;
import richbar.com.github.treasurepyramid.Main;
import richbar.com.github.treasurepyramid.assets.Loadable;
import richbar.com.github.treasurepyramid.assets.Text;

/**
 * Created by Rich Y on 21.07.2017.
 */
public abstract class Block implements EventSubscriber, Loadable {
	
	public abstract String getId();
	
	Vector3 pos;
	String name;
	Model model;
	BoundingBox bounds;
	Texture texture;
	Material attributes;
	
	private boolean loadedData;
	public boolean isLoaded(){return loadedData;}
	
	public Block(){
		Main.getInstance().addAssetListener(this, "blocks/"+getId()+".json", Text.class);
	}
	
	public Block(Block block){
		Main.getInstance().addAssetListener(this, "blocks/"+block.getId()+".json", Text.class);
		pos = block.pos;
		attributes = block.attributes;
		bounds = block.bounds;
		
	}
	
	public Model getModel(){
		if(model != null) return model;
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		Material material = new Material(TextureAttribute.createDiffuse(texture));
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		modelBuilder.node();
		MeshPartBuilder mesh_part_builder = modelBuilder.part("box", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position
				| VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, material);
		BoxShapeBuilder.build(mesh_part_builder, 5, 5, 5);
		return modelBuilder.end();
	}
	
	boolean stayUpdated = true;
	@Override
	public void eventUpdate(Event e, Object args) {
		AssetManager manager = Main.getInstance().getAssetManager();
		if(e == Event.ASSET_LOADED && manager.isLoaded("blocks/"+getId()+".json")){
			Main.logger.log("loaded blocks/"+getId()+".json");
			stayUpdated = false;
			JsonValue json = new JsonReader().parse((manager.get("blocks/"+getId()+".json")).toString());
			
			name = json.getString("name");
			texture = new Texture(Gdx.files.internal(json.getString("texture")));
			loadedData = true;
		}
		//generate Cube Model if Absent
		model = getModel();
	}
	
	@Override
	public boolean stayUpdated() {
		return stayUpdated;
	}
}
