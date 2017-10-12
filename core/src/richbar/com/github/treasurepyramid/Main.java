package richbar.com.github.treasurepyramid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import richbar.com.github.treasurepyramid.assets.Text;
import richbar.com.github.treasurepyramid.assets.TextLoader;
import richbar.com.github.treasurepyramid.screen.GameScreen;

import java.awt.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Game {
	private static Main instance;
	public static Logger logger;
	private SpriteBatch batch;
	private BitmapFont font;
	private AssetManager assetManager = new AssetManager();
	private Map<EventSubscriber, String> assetManagerListener = new ConcurrentHashMap<EventSubscriber, String>();
	private LinkedList<Screen> activeScreens = new LinkedList<Screen>();
	
	
	public Main() {
		instance = this;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	
	@Override
	public void create() {
		assetManager.setLoader(
				Text.class,
				new TextLoader(new InternalFileHandleResolver())
		);
		
		//logger = new Logger(Gdx.app.getApplicationLogger());
		logger = new Logger(Gdx.app.getApplicationLogger(), Logger.LogLevel.DEBUG);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		activeScreens.add(new GameScreen());
		activeScreens.get(0).resume();
		activeScreens.get(0).show();
	}
	
	/**
	 * Opens standartbrowser and redirects it to Paypal
	 * {@link "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5RQ9AMFVA8CQL"}
	 */
	public void donate() {
		try {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URL("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5RQ9AMFVA8CQL").toURI());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int loadedCound = 0;
	@Override
	public void render() {
		super.render();
		for (Screen screen : activeScreens) {
			float delta = Gdx.graphics.getDeltaTime();
			screen.render(delta);
		}
		try{
			if(assetManager.update() && loadedCound != assetManager.getLoadedAssets()) {
				for (Map.Entry<EventSubscriber, String> subEntry : assetManagerListener.entrySet())
					if(assetManager.isLoaded(subEntry.getValue())) {
						subEntry.getKey().eventUpdate(EventSubscriber.Event.ASSET_LOADED, assetManager);
						if(!subEntry.getKey().stayUpdated()) removeAssetListener(subEntry.getKey());
						logger.line();
					}
				loadedCound = assetManager.getLoadedAssets();
				logger.log(loadedCound + " Assets are loaded");
				logger.line();
			}
		}catch(GdxRuntimeException exception){
			logger.log(Logger.LogLevel.ERROR, exception.getLocalizedMessage());
		}
		batch.begin();
		font.draw(batch, Gdx.graphics.getFramesPerSecond() + "", 0, 480);
		batch.end();
	}
	
	@Override
	public void dispose(){
		logger.destroy();
		batch.dispose();
	}
	
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public void addAssetListener(EventSubscriber sub, String fileName, Class type) {
		assetManager.load(fileName, type);
		assetManagerListener.put(sub, fileName);
	}
	
	public String removeAssetListener(EventSubscriber sub) {
		return assetManagerListener.remove(sub);
	}
	
}
