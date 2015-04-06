package Main.States;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Main.Game;

public class OptionsMenu extends BaseState{
	
	private int ID;
	private Game mainGame;
	
	private Slider mainVolume;
	private Slider musicVolume;
	private Slider soundVolume;
	private Toggle vSync;
	private Toggle debug;
	@SuppressWarnings("unused")
	private Toggle keyAxis;
	
	public OptionsMenu(int ID, Game game){
		super(game);
		this.ID = ID;
		mainGame = game;
	}

	@Override
	public boolean isAcceptingInput() {
		return false;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		mainVolume = new Slider((int)( mainGame.config.settings.getMasterVol()*100));
		musicVolume = new Slider((int)( mainGame.config.settings.getMusicVol()*100));
		soundVolume = new Slider((int)( mainGame.config.settings.getSoundVol()*100));
		vSync = new Toggle(mainGame.config.settings.isvSync());
		debug = new Toggle(mainGame.config.settings.isDebug());
		keyAxis = new Toggle(mainGame.config.settings.getKeyAxis());
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		mainVolume.draw(300 * Game.scale, 50  * Game.scale, 50);
		musicVolume.draw(300 * Game.scale, 65  * Game.scale, 50);
		soundVolume.draw(300 * Game.scale, 80  * Game.scale, 50);
		vSync.draw(300 * Game.scale, 100  * Game.scale, 50);
		debug.draw(300 * Game.scale, 140  * Game.scale, 50);

		mainGame.font.drawStringAlignMiddle(160, 10, "Options",Color.white, 75);
		mainGame.font.drawStringAlignMiddle(160, 10, "____________",Color.white, 75);
		
		mainGame.font.drawString(20, 50, "Main Volume:", 35);
		mainGame.font.drawString(30, 65, "Music Volume:", 35);
		mainGame.font.drawString(30, 80, "Sound Volume:", 35);
		mainGame.font.drawString(20, 100, "VSync:", 35);
		mainGame.font.drawString(20, 120, "Key Axis:", 35);
		mainGame.font.drawString(20, 140, "Debug:", 35);

		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unpause() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	private class Slider{
		
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
			frame.draw(x, y, Game.scale * (scale/100));
			fill.draw(x, y, x + (63 * value / 100) * Game.scale  * (scale/100), y + 21 *  Game.scale * (scale/100), 0, 0, (63 * value / 100), 21);
		}
		
	}
	
	private class Toggle{
		
		private Image frame;
		private Image dot;
		public boolean value = false;
		
		@SuppressWarnings("unused")
		public Toggle() {
			try {
				frame = new Image("src/assets/Textures/Controlls/toggle_frame.png");
				dot = new Image("src/assets/Textures/Controlls/toggle_dot.png");
				frame.setFilter(Image.FILTER_NEAREST);
				dot.setFilter(Image.FILTER_NEAREST);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		public Toggle(boolean value){
			this.value = value;
			try {
				frame = new Image("src/assets/Textures/Controlls/toggle_frame.png");
				dot = new Image("src/assets/Textures/Controlls/toggle_dot.png");
				frame.setFilter(Image.FILTER_NEAREST);
				dot.setFilter(Image.FILTER_NEAREST);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		public void draw(float x, float y, float scale){
			frame.draw(x, y, Game.scale * (scale/100));
			if(value) dot.draw(x, y, Game.scale * (scale/100));
		}
		
	}

}
