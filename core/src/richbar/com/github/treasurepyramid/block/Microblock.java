package richbar.com.github.treasurepyramid.block;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import richbar.com.github.treasurepyramid.Main;
import richbar.com.github.treasurepyramid.assets.Text;

/**
 * Created by Rich Y on 11.08.2017.
 */
public abstract class Microblock extends Block {
	
	private Model model;
	private String modelPath;
	
	static Model createModel(String modelResource) {
		Material material = new Material(ColorAttribute.createDiffuse(Color.RED));
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		for (int z = 0; z < 20; z++) {
			for (int y = 0; y < 20; y++) {
				for (int x = 0; x < 20; x++) {
					if (modelResource.charAt(x) != '_') {
						modelBuilder.node();
						MeshPartBuilder mesh_part_builder = modelBuilder.part("box", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position
								| VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, material);
						BoxShapeBuilder.build(mesh_part_builder, 1, 1, 1);
						mesh_part_builder.setVertexTransform(new Matrix4().scl(5,5,5));
					}
				}
				modelResource = modelResource.substring(20, modelResource.length());
			}
		}
		return modelBuilder.end();
	}
	
	
	@Override
	public void eventUpdate(Event e, Object args) {
		if (e != Event.ASSET_LOADED) return;
		AssetManager manager = Main.getInstance().getAssetManager();
		if (manager.isLoaded(modelPath)) {
			model = createModel(manager.get(modelPath).toString().replaceAll("\r\n|\r|\n", ""));
			Main.logger.log("loaded Microblock: " + modelPath);
			stayUpdated = false;
		}
		else if (manager.isLoaded("blocks/" + getId() + ".json")) {
			super.eventUpdate(e, args);
			stayUpdated = true;
			JsonValue json = new JsonReader().parse((manager.get("blocks/" + getId() + ".json")).toString());
			if (json.has("model")) {
				modelPath = "blocks/" + json.getString("model");
				Main.getInstance().addAssetListener(this, modelPath, Text.class);
			} else
				stayUpdated = false;
		}
	}
	
	@Override
	public Model getModel() {
		if(model==null) return super.getModel();
		return model;
	}
}
