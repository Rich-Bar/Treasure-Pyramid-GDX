package main.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.state.GameState;

import launch.multiscreen.Device;
import main.Game;
import main.events.Event;
import main.events.StateEvents;
import main.states.LoadingScreen;

@SuppressWarnings("rawtypes")
public class EventHandler implements StateEvents{

	boolean firstLoad = true;
	Device device;
	
	public EventHandler(Device device) {
		this.device = device;
		Game.inst().addState(new LoadingScreen());
	}
	
	public static enum Screens{
		StateEvents(Integer.parseInt("1", 2));
		
		private int id;   
	    Screens(int id) {
	        this.id = id;
	    }

	    public int getID() { return id; }
	}
	
    private List<Event> listenerList = new ArrayList<Event>();
	
	public synchronized void addListener(Event listener) {
        listenerList.add( listener );
    }
    
    public synchronized void removeListener(Event listener) {
        listenerList.remove( listener );
    }

	@Override
	public synchronized void loadedState(GameState state) {
		Game.inst().enterState(state.getID());
		Game.inst().dial();
		Iterator listeners = listenerList.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).loadedState(state);
        }
	}

	@Override
	public synchronized void loadState(GameState state) {
		((LoadingScreen) Game.inst().getState(main.Game.Screens.LOADING.getID())).setTarget(state.getID(), Game.inst().getCurrentStateID());
		Game.inst().enterState(Game.Screens.LOADING.getID());
	}
	
	public void notifyLoad(GameState state){
		Iterator listeners = listenerList.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).loadState(state);
        }
	}

	@Override
	public void unloadedState(GameState state) {
		Iterator listeners = listenerList.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).unloadedState(state);
        }
	}

	@Override
	public synchronized void unloadState(GameState state) {
		Iterator listeners = listenerList.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).unloadState(state);
        }
	}

	public void init() {
		((LoadingScreen) Game.inst().getState(Game.Screens.LOADING.getID())).init();
	}
}
