package main.types;

import java.awt.Dimension;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Popup {

	private Image background;
	private Image whiteBorderLR;
	private Image whiteBorderTB;
	
	protected String title = "";
	protected String text = "";
	
	private SheetFont font;
	
	public Popup(String title, String text) {
		
		font = Game.getInstance().font;
		
		this.title = title;
		this.text = text;
		try {
			background = new Image("src/assets/Textures/Controlls/popup_bg.png");
			whiteBorderLR = new Image("src/assets/Textures/Controlls/popup_LR.png");
			whiteBorderTB = new Image("src/assets/Textures/Controlls/popup_TB.png");
			background.setFilter(Image.FILTER_NEAREST);
			whiteBorderLR.setFilter(Image.FILTER_NEAREST);
			whiteBorderTB.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {}
		
	}
	
	public void render(Dimension size, float x, float y){
		render(size, x, y, Color.white);
	}
	
	public void render(Dimension size, float x, float y, Color col){
		x *= Game.scale;
		y *= Game.scale;
		size.width *= Game.scale;
		size.height *=Game.scale;
		
		background.draw(x, y, x + size.width, y + size.height, 0, 0, 100, 100, new Color(1, 1, 1, 0.8f));
		whiteBorderLR.draw(x + size.width - 5 * Game.scale, y + 4 * Game.scale, 1 * Game.scale, size.height - 8 * Game.scale);
		whiteBorderLR.draw(x + 4 * Game.scale, y + 4 * Game.scale, 1 * Game.scale, size.height - 8 * Game.scale);
		whiteBorderTB.draw(x + 4 * Game.scale, y + size.height - 4 * Game.scale, size.width - 8 * Game.scale, 1 * Game.scale);
	
		font.drawString(x/ Game.scale + 12, y/ Game.scale + 4, title, 75);
		font.drawString(x/ Game.scale + 12, y/ Game.scale + 32, text, 25);
	}
	
}
