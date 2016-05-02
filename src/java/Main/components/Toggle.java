package main.components;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

	public class Toggle{
		
		private Image frame;
		private Image dot;
		public boolean value = false;
		
		public Toggle() {
			try {
				frame = new Image("Textures/Controlls/toggle_frame.png");
				dot = new Image("Textures/Controlls/toggle_dot.png");
				frame.setFilter(Image.FILTER_NEAREST);
				dot.setFilter(Image.FILTER_NEAREST);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		public Toggle(boolean value){
			this.value = value;
			try {
				frame = new Image("Textures/Controlls/toggle_frame.png");
				dot = new Image("Textures/Controlls/toggle_dot.png");
				frame.setFilter(Image.FILTER_NEAREST);
				dot.setFilter(Image.FILTER_NEAREST);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		public void draw(float x, float y, float scale){
			draw(x, y, scale, Color.white);
		}
		
		public void draw(float x, float y, float scale, Color col){
			frame.draw(x, y, Game.inst().scale * (scale/100), col);
			if(value) dot.draw(x, y, Game.inst().scale * (scale/100), col);
		}
		
	}