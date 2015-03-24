package Main.Types;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Main.Game;

public class MenuButton extends Image{
	
	private int states = 1;
	private int state = 1;
	
	public MenuButton(String location, int states) throws SlickException {
		super(location, false, FILTER_NEAREST);
		this.states = states;		
		setState(1);
	}
	
	public MenuButton(String location, int states , int state) throws SlickException {
		super(location, false, FILTER_NEAREST);
		this.states = states;		
		setState(state);
	}
	
	public int getMiddle(int fullLength, boolean isWidth){
		int finWidth = 0;
		finWidth = width / states;
		if(isWidth)return fullLength/2 - finWidth/2;
		else return fullLength/2 - finWidth/2;
	}

	public void setState(int state) {
		if(state > 0 && state <= states){
			this.state = state;
		}	
	}

	@Override
	public void draw(float x, float y) {
		float finWidth = 0;
		finWidth = width / states;
		super.draw(x, y, x + finWidth * Game.scale, y + height * Game.scale, (state -1) * finWidth, 0, finWidth * state, height);
	}
}
