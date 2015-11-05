package main.world.items;

import java.util.ArrayList;
import java.util.List;

public final class Inventory{
	
	private List<Item> items = new ArrayList<>();
	private int selection = 0;
	
	public List<Item> getItems(){
		return items;
	}
	
	public boolean hasItem(Item reqItem){
		return items.contains(reqItem);
	}
	
	public void removeItem(Item item){
		items.remove(item);
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public Item getCurrentItem(){
		if(items.size()-1 < selection) return null;
		return items.get(selection);
	}
	
}