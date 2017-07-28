package richbar.com.github.treasurepyramid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import richbar.com.github.treasurepyramid.screen.IntroScreen;

import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main extends ApplicationAdapter {
	private static Main instance;
	private SpriteBatch batch;
	private BitmapFont font;
	private AssetManager assetManager = new AssetManager();
	private Map<EventSubscriber, String> assetManagerListener = new HashMap<EventSubscriber, String>();
	private LinkedList<Screen> activeScreens = new LinkedList();
	
	
	public Main(){
		instance = this;
	}
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.graphics.setVSync(false);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		Gdx.app.debug(getLoggerTag(),"||-------------------------------------------------------------------------------||");
		Gdx.app.debug(getLoggerTag(),"|        ______)                              _____                               |");
		Gdx.app.debug(getLoggerTag(),"        (, /                                 (, /   )                   ,   /)     ");
		Gdx.app.debug(getLoggerTag(),"          /   __   _  _   _       __   _      _/__ /      __  _  ___      _(/      ");
		Gdx.app.debug(getLoggerTag(),"       ) /   / (__(/_(_(_/_)_(_(_/ (__(/_     /      (_/_/ (_(_(_// (__(_(_(_      ");
		Gdx.app.debug(getLoggerTag(),"      (_/                                  ) /      .-/                            ");
		Gdx.app.debug(getLoggerTag(),"|                                         (_/      (_/                            |");
		Gdx.app.debug(getLoggerTag(),"||-------------------------------------------------------------------------------||");
		Gdx.app.debug(getLoggerTag(),"||      Developer & Creator Marco Dittrich - https://www.patreon.com/RichY       ||");
		Gdx.app.debug(getLoggerTag(),"||                                                                               ||");
		Gdx.app.debug(getLoggerTag(),"||  Version: 0.0.1A                                                              ||");
		Gdx.app.debug(getLoggerTag(),"||||---------------------------------------------------------------------------||||");
		Gdx.app.debug(getLoggerTag(),"|                                                                                 |");
		Gdx.app.debug(getLoggerTag(),"|  Other Developers:                                                              |");
		Gdx.app.debug(getLoggerTag(),"|                                                                                 |");
		Gdx.app.debug(getLoggerTag(),"||||---------------------------------------------------------------------------||||");
		
		activeScreens.add(new IntroScreen());
		activeScreens.get(0).show();
	}
	
	
	/**
	 * Opens standartbrowser and redirects it to Paypal
	 * {@link "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5RQ9AMFVA8CQL"}
	 */
	public void donate(){
		try {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URL("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5RQ9AMFVA8CQL").toURI());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Main getInstance(){
		return instance;
	}
	public static String getLoggerTag(){
		return LocalDateTime.now() +" / Treasure Pyramid >";
	}
	int loadedCount;
	@Override
	public void render () {
		super.render();
		for(Screen screen : activeScreens){
			float delta = Gdx.graphics.getDeltaTime();
			screen.render(delta);
		}
		assetManager.update();
		if(assetManager.getQueuedAssets() > 0 && assetManager.getQueuedAssets() != loadedCount) {
			if (assetManager.getQueuedAssets() < loadedCount)
				for (Map.Entry<EventSubscriber, String> subpair : assetManagerListener.entrySet()) {
					if (subpair.getValue() == "" || subpair.getValue().equals(assetManager.getAssetNames().get(assetManager.getLoadedAssets()))) {
						subpair.getKey().eventUpdate(EventSubscriber.Event.ASSET_LOADED, assetManager);
						if (!subpair.getKey().stayUpdated()) removeAssetManager(subpair.getKey());
						Gdx.app.debug(getLoggerTag(), "Loaded Asset [" + subpair.getValue() + "] and Notified.");
					}
				}
			loadedCount = assetManager.getQueuedAssets();
		}
		batch.begin();
		font.draw(batch, Gdx.graphics.getFramesPerSecond() +"", 0, 480);
		batch.end();
	}
	
	@Override
	public void dispose () {
		
		Gdx.app.debug(getLoggerTag(),"|                                                                                 |");
		Gdx.app.debug(getLoggerTag(),"|                                       EOG                                       |");
		Gdx.app.debug(getLoggerTag(),"|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		batch.dispose();
	}
	
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public void addAssetListener(EventSubscriber sub, String fileName){
		assetManagerListener.put(sub, fileName);
	}
	
	public String removeAssetManager(EventSubscriber sub){
		return assetManagerListener.remove(sub);
	}
}
