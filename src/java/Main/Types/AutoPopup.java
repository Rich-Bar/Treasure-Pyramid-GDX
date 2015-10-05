package main.types;

import java.awt.Dimension;

import main.Game;

public class AutoPopup extends Popup{
	
	public AutoPopup(String title, String text) {
		super(title, text);
	}
	
	public void renderW(int width) {
		Dimension size = new Dimension();
		int x, y = 0;
		
		x = (int) ((Game.pixelartResolution.width - width) * Game.scale * 0.5); 
		
		y = (int) (Game.pixelartResolution.height - (36 + 4.25 * (1 + (text.length() - text.replace("\n", "").length()))) * Game.scale);
		
		super.render(size, x, y);
	}
	
	
	public void renderH(int height) {
		
		Dimension size = new Dimension();
		int x, y = 0;
		
		x = (int) ((Game.pixelartResolution.width - height) * Game.scale * 0.5); 
		
		y = (int) (Game.pixelartResolution.height - (36 + 4.25 * (1 + (text.length() - text.replace("\n", "").length()))) * Game.scale);
		
		
		super.render(size, x, y);
	}

}
