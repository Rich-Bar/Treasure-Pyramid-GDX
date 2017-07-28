package richbar.com.github.treasurepyramid;

/**
 * Created by Rich Y on 21.07.2017.
 */
public interface EventSubscriber {
	enum Event{
		ASSET_LOADED, BLOCK_UPDATED, SCREEN_LOAD, SCREEN_LOADED;
	}
	
	boolean stayUpdated();
	
	/**
	 * Tip: use public variable to force update.
	 * -> see ForcableEventSubscriber
	 * @param e
	 * @param args
	 */
	void eventUpdate(Event e, Object args);
}
