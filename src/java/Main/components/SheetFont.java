package main.components;

import java.util.ArrayList;
import java.util.List;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class SheetFont{

	private SpriteSheet fontSheetHuge;
	private SpriteSheet fontSheetSmall;
	private int tilesX = 0;
	
	private char[] charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-()[]{}^°\"§$%&/=?!.:,;_<>|#äÄüÜöÖ\\~‘�µ™®©@¶ðΨ¿æß".toCharArray();
	private List<Character> charSet = new ArrayList<Character>();
	
	public SheetFont() {
		for (char c : charList) {
			charSet.add(c);
	    }
		try {
			fontSheetHuge = new SpriteSheet("src/assets/Textures/spriteSheetFont.png", 32, 32);
			fontSheetSmall = new SpriteSheet("src/assets/Textures/spriteSheetFontSmall.png", 32, 32);
			tilesX = fontSheetHuge.getHorizontalCount();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void drawString(float x, float y, String text) {
		drawString(x, y, text, Color.white, 100);
	}
	
	public void drawString(float x, float y, String text, Color col) {
		drawString(x, y, text, col, 100);
	}
	
	public void drawString(float x, float y, String text, float size) {
		drawString(x, y, text, Color.white, size);
	}

	public void drawString(float x, float y, String text, Color col, float size) {
if(fontSheetHuge == null || fontSheetSmall == null) return;
		
		x *= Game.inst().scale;
		y *= Game.inst().scale;
		
		SpriteSheet font = null;
		
		if(size < 50) font = fontSheetSmall;
		else font = fontSheetHuge;
		
		float scale = Game.inst().scale * (size / 100f);
		
		float startX = x;

		char[] charText = text.toUpperCase().toCharArray();
		
		for(char c : charText){
			if(c == '\n'){
				y += (34 * (size / 100f)) * Game.inst().scale;
				startX = x;
			}else{
				if(charSet.contains(c)){
					int index = charSet.indexOf(c);
					int posX = index % tilesX;
					int posY = index / tilesX;
					font.getSprite(posX, posY).draw(startX , y, scale, col);
				}
				startX += 18 * scale;
			}
		}
	}
	
	public void drawStringAlignMiddle(float x, float y, String text, Color col, float size) {
		if(fontSheetHuge == null || fontSheetSmall == null) return;
		
		x *= Game.inst().scale;
		y *= Game.inst().scale;
		
		SpriteSheet font = null;
		
		if(size < 50) font = fontSheetSmall;
		else font = fontSheetHuge;
		
		float scale = Game.inst().scale * (size / 100f);
		
		float startX = x - text.length() * 18 * scale / 2;

		char[] charText = text.toUpperCase().toCharArray();
		
		for(char c : charText){
			if(charSet.contains(c)){
				int index = charSet.indexOf(c);
				int posX = index % tilesX;
				int posY = index / tilesX;
				font.getSprite(posX, posY).draw(startX , y, scale, col);
			}
			startX += 18 * scale;
		}
	}

	public int getHeight(String text) {
		return 32;
	}

	public int getWidth(String text) {
		return text.length() * 18;
	}
	
}