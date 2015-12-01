package main.multiscreen;

import main.Game;

@SuppressWarnings("rawtypes")
public enum Window {
	GameScreen(Game.class),
	InventoryScreen(InventoryScreen.class),
	BlackScreen(BlackScreen.class),
	None(null);
	
	private Class comp;
	private Window(Class c) {
		comp = c;
	}
	
	public boolean isInstance(Object o){
		return comp.isInstance(o);
	}
}
