package main.components;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

	public class Slider{
		
		private Image frame;
		private Image fill;
		private int value = 100;
		
		public Slider(int value) {
			this.value = value;
			try {
				frame = new Image("src/assets/Textures/Controlls/slider_frame.png");
				fill = new Image("src/assets/Textures/Controlls/slider_fill.png");
				frame.setFilter(Image.FILTER_NEAREST);
				fill.setFilter(Image.FILTER_NEAREST);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			if(value > 100) value = 100;
			else if(value < 0 ) value = 0; 
			this.value = value;
		}
		
		public void draw(float x, float y, float scale){
			draw(x, y, scale, Color.white);
		}
		
		public void draw(float x, float y, float scale, Color col){
			frame.draw(x, y, Game.inst().scale * (scale/100), col);
			fill.draw(x, y, x + (63 * value / 100) * Game.inst().scale  * (scale/100), y + 21 *  Game.inst().scale * (scale/100), 0, 0, 63 * value / 100, 21, col);
		}
		
	}