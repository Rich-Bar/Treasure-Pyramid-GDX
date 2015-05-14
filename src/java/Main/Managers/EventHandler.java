package main.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.state.GameState;

import main.Game;
import main.elements.levels.Level;
import main.events.Event;
import main.events.LevelEvents;
import main.events.StateEvents;
import main.states.LoadingScreen;

@SuppressWarnings("rawtypes")
public class EventHandler implements StateEvents, LevelEvents{

	private Game mainGame;
	boolean firstLoad = true;
	
	public EventHandler(Game game) {
		mainGame = game;
		
		mainGame.addState(new LoadingScreen(game));
	}
	
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
		mainGame.enterState(S.getID());
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).loadedState(S);
        }
	}

	@Override
	public synchronized void loadState(GameState S) {
		((LoadingScreen) mainGame.getState(main.Game.Screens.LOADING.getID())).setTarget(S.getID(), mainGame.getCurrentStateID());
		mainGame.enterState(Game.Screens.LOADING.getID());
	}
	
	public void notifyLoad(GameState S){
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).loadState(S);
        }
	}

	@Override
	public void unloadedState(GameState S) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).unloadedState(S);
        }
	}

	@Override
	public synchronized void unloadState(GameState S) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof StateEvents)((StateEvents) thisListener).unloadState(S);
        }
	}

	@Override
	public void loadedLevel(Level L) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof LevelEvents)((LevelEvents) thisListener).loadedLevel(L);
        }
	}

	@Override
	public void loadLevel(Level L) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof LevelEvents)((LevelEvents) thisListener).loadLevel(L);
        }
	}

	@Override
	public void unloadLevel(Level L) {
		Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
        	Event thisListener = (Event)listeners.next();
        	if(thisListener instanceof LevelEvents)((LevelEvents) thisListener).unloadLevel(L);
        }
	}

	public void init() {
		((LoadingScreen) mainGame.getState(Game.Screens.LOADING.getID())).init();
	}
}
