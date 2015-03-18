package Main.States;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Main.Game;
import Main.MenuButton;

public class TitleMenu implements GameState{

	private int ID;
	private int buttonSwitchSpeed = 500;
	private long[] timeIntervalls = new long[1];
	private List<Integer> keysPressed = new ArrayList<Integer>();
	private Image background;
	private MenuButton newGameButton;
	private MenuButton OptionsButton;
	private MenuButton ExitButton;
	private MenuButton CreditsButton;
	private int selectedButton = 1;
	private Game mainGame;
	protected int totalDelta = 0;
	
	
	public TitleMenu(int ID, Game sbg){
		this.ID = ID;
		mainGame = sbg;
	}
	

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
		background.draw(0, 0, Game.scale);
		newGameButton.draw(newGameButton.getMiddle(Game.pixelartResolution.width, true) * Game.scale, 100 * Game.scale);
		OptionsButton.draw(OptionsButton.getMiddle(Game.pixelartResolution.width, true) * Game.scale, 120 * Game.scale);
		CreditsButton.draw(CreditsButton.getMiddle(Game.pixelartResolution.width, true) * Game.scale, 140 * Game.scale);
		ExitButton.draw(ExitButton.getMiddle(Game.pixelartResolution.width, true) * Game.scale, 160 * Game.scale);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if(!keysPressed.isEmpty()){
			for (int keyCode : keysPressed) {
				if(keyCode == 17 || keyCode == 200 || keyCode == 31 || keyCode == 208){
					timeIntervalls[0] += delta;
					if(timeIntervalls[0] > buttonSwitchSpeed){
						timeIntervalls[0] = 0;
						keyAction(keyCode);
					}
				}
			}
		}
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		mainGame.resetResolution(gc);
		
		newGameButton = new MenuButton("src/assets/Textures/NewGameButton.png", 2, 1); 
		OptionsButton = new MenuButton("src/assets/Textures/OptionsButton.png", 2, 2); 
		CreditsButton = new MenuButton("src/assets/Textures/CreditsButton.png", 2, 2); 
		ExitButton = new MenuButton("src/assets/Textures/ExitButton.png", 2, 2); 
		
		background = new Image("src/assets/Textures/TitleScreen.png");
		
	}

	private void keyAction(int keyCode){
		if(keyCode == 17 || keyCode == 200){
			selectedButton--;
		}else if(keyCode == 31 || keyCode == 208){
			selectedButton++;
		}else if(keyCode == 28 || keyCode == 57){
			pressedEnter();
		}
		if(selectedButton > 4)selectedButton = 1;
		if(selectedButton < 1)selectedButton = 4;
		switchSelectedMenuOption(selectedButton);
	}
	
	private void pressedEnter(){
		switch(selectedButton){
			case 4:{
				System.exit(0);
				break;
			}
			default:{
				break;
			}
		}
	}
	
	private void switchSelectedMenuOption(int option){
		if(option > 4 || option < 1) return;
		switch(option){
			case 1:{
				newGameButton.setState(1);
				OptionsButton.setState(2);
				CreditsButton.setState(2);
				ExitButton.setState(2);
				break;	
			}
			case 2:{
				newGameButton.setState(2);
				OptionsButton.setState(1);
				CreditsButton.setState(2);
				ExitButton.setState(2);
				break;	
			}
			case 3:{
				newGameButton.setState(2);
				OptionsButton.setState(2);
				CreditsButton.setState(1);
				ExitButton.setState(2);
				break;	
			}
			case 4:{
				newGameButton.setState(2);
				OptionsButton.setState(2);
				CreditsButton.setState(2);
				ExitButton.setState(1);
				break;	
			}
			default:{
				newGameButton.setState(2);
				OptionsButton.setState(2);
				CreditsButton.setState(2);
				ExitButton.setState(2);
				break;	
			}
		}
	}
	

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isAcceptingInput() {
		return true;
	}


	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(int arg0, char arg1) {
		keysPressed.add(arg0);
		keyAction(arg0);
		
	}


	@Override
	public void keyReleased(int arg0, char arg1) {
		timeIntervalls[0] = 0;
		keysPressed.remove(keysPressed.indexOf(arg0));
	}


	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
}
