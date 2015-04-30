package Main.Managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.state.GameState;

import Main.Events.Event;
import Main.Events.StateEvents;

@SuppressWarnings("rawtypes")
public class EventHandler implements StateEvents{

	public static enum Screens{
		StateEvents(Integer.parseInt("1", 2));
		
		private int ID;   
	    Screens(int ID) {
	        this.ID = ID;
	    }

	    public int getID() { return ID; }
	}
	
    private List<Event> _listeners = new ArrayList<Event>();
	
	public synchronized void addListener(Event l ) {
        _listeners.add( l );
    }
    
    public synchronized void removeListener(Event l ) {
        _listeners.remove( l );
    }

	@Override
	public synchronized void loadedState(GameState S) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ((StateEvents) listeners.next()).loadedState(S);
        }
	}

	@Override
	public synchronized void loadState(GameState S) {

		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	((StateEvents) listeners.next()).loadState(S);
        }
	}

	@Override
	public synchronized void unloadState(GameState S) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	((StateEvents) listeners.next()).unloadState(S);
        }
	}
}
