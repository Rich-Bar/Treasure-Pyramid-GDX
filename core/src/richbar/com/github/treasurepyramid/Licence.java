package richbar.com.github.treasurepyramid;

import com.badlogic.gdx.assets.AssetManager;
import richbar.com.github.treasurepyramid.assets.Text;

/**
 * Created by Rich Y on 04.08.2017.
 */
public class Licence implements EventSubscriber {
	public Licence(){
		Main.getInstance().addAssetListener(this, "licence", Text.class);
	}
	
	@Override
	public boolean stayUpdated() {
		return false;
	}
	
	@Override
	public void eventUpdate(Event e, Object args) {
		AssetManager manager = (AssetManager)args;
		if(e == Event.ASSET_LOADED && manager.isLoaded("licence")){
			String licence = manager.get("licence").toString();
			Main.logger.log(licence);
		}
	}
}
