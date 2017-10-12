package richbar.com.github.treasurepyramid;

/**
 * Created by Rich Y on 21.07.2017.
 */
public abstract class ForceableEventSubscriber implements EventSubscriber{
	
	@Override
	public boolean stayUpdated() {
		return false;
	}
}
