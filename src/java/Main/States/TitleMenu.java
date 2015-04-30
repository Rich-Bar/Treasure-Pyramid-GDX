package Main.States;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Main.Game;
import Main.Game.Screens;
import Main.Types.MenuButton;

public class TitleMenu extends BaseState{

	public int selectedButton = 1;
	private int ID;
	private Image background;
	private MenuButton newGameButton;
	private MenuButton OptionsButton;
	private MenuButton ExitButton;
	private MenuButton CreditsButton;
	protected int totalDelta = 0;
	
	
	public TitleMenu(int ID, Game sbg){
		super(sbg);
		this.ID = ID;
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
		mainGame.keyManager.update(this, delta);
	}

	public void pressedEnter(){
		switch(selectedButton){
			case 4:{
				mainGame.getContainer().exit();
				break;
			}
			case 3:{
				mainGame.eventHandler.loadState(mainGame.getState(Screens.CREDITS.getID()));
				break;
			}
			case 2:{
				mainGame.eventHandler.loadState(mainGame.getState(Screens.OPTIONS.getID()));
				break;
			}
			default:{
				break;
			}
		}
	}
	
	public void switchSelectedMenuOption(int option){
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
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		mainGame.keyManager.keyPressed(arg0, this);
	}


	@Override
	public void keyReleased(int arg0, char arg1) {
		mainGame.keyManager.keyReleased(arg0);
	}
	
	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentState() instanceof CreditsMenu){
			selectedButton = 1;
		}
	}


	@Override
	public void loadState(GameState State) {
		if(State instanceof TitleMenu){
			try {
				newGameButton = new MenuButton("src/assets/Textures/NewGameButton.png", 2, 1); 
				OptionsButton = new MenuButton("src/assets/Textures/OptionsButton.png", 2, 2); 
				CreditsButton = new MenuButton("src/assets/Textures/CreditsButton.png", 2, 2); 
				ExitButton = new MenuButton("src/assets/Textures/ExitButton.png", 2, 2); 
		
				background = new Image("src/assets/Textures/TitleScreen.png");
				background.setFilter(Image.FILTER_NEAREST);		
				mainGame.eventHandler.loadedState(State);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void unloadState(GameState State) {
		if(State instanceof TitleMenu){		
			unloadRequest = true;
			newGameButton = null;
			OptionsButton = null;
			CreditsButton = null;
			ExitButton = null;
			background = null;
			loadedState(State);
		}
	}
}
