package main.states;

import main.Game;
import main.Game.Screens;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.components.MenuButton;

public class TitleMenu extends BaseState{

	public int selectedButton = 1;
	private Image background;
	private MenuButton newGameButton;
	private MenuButton optionsButton;
	private MenuButton exitButton;
	private MenuButton creditsButton;
	protected int totalDelta = 0;
	
	
	public TitleMenu(int id){
		super();
		this.id = id;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
		super.render(gc, sbg, gr);
		
			background.draw(0, 0, Game.inst().scale);
			newGameButton.draw(newGameButton.getMiddle(Game.pixelartResolution.width, true) * Game.inst().scale, 100 * Game.inst().scale);
			optionsButton.draw(optionsButton.getMiddle(Game.pixelartResolution.width, true) * Game.inst().scale, 120 * Game.inst().scale);
			creditsButton.draw(creditsButton.getMiddle(Game.pixelartResolution.width, true) * Game.inst().scale, 140 * Game.inst().scale);
			exitButton.draw(exitButton.getMiddle(Game.pixelartResolution.width, true) * Game.inst().scale, 160 * Game.inst().scale);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Game.inst().keyManager.update(this, delta);
	}

	public void pressedEnter(){
		switch(selectedButton){
			case 4:{
				Game.inst().getContainer().exit();
				break;
			}
			case 3:{
				Game.inst().eventHandler.loadState(Game.inst().getState(Screens.CREDITS.getID()));
				break;
			}
			case 2:{
				Game.inst().eventHandler.loadState(Game.inst().getState(Screens.OPTIONS.getID()));
				break;
			}
			default:{
				Game.inst().eventHandler.loadState(Game.inst().getState(Screens.GAME.getID()));
				break;
			}
		}
	}
	
	public void switchSelectedMenuOption(int option){
		if(option > 4 || option < 1) return;
		switch(option){
			case 1:{
				newGameButton.setState(1);
				optionsButton.setState(2);
				creditsButton.setState(2);
				exitButton.setState(2);
				break;	
			}
			case 2:{
				newGameButton.setState(2);
				optionsButton.setState(1);
				creditsButton.setState(2);
				exitButton.setState(2);
				break;	
			}
			case 3:{
				newGameButton.setState(2);
				optionsButton.setState(2);
				creditsButton.setState(1);
				exitButton.setState(2);
				break;	
			}
			case 4:{
				newGameButton.setState(2);
				optionsButton.setState(2);
				creditsButton.setState(2);
				exitButton.setState(1);
				break;	
			}
			default:{
				newGameButton.setState(2);
				optionsButton.setState(2);
				creditsButton.setState(2);
				exitButton.setState(2);
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
		Game.inst().keyManager.keyPressed(arg0, this);
	}


	@Override
	public void keyReleased(int arg0, char arg1) {
		Game.inst().keyManager.keyReleased(arg0);
	}


	@Override
	public void loadState(GameState state) {
		if(state instanceof TitleMenu){
			try {
				newGameButton = new MenuButton("src/assets/Textures/NewGameButton.png", 2, 1); 
				optionsButton = new MenuButton("src/assets/Textures/OptionsButton.png", 2, 2); 
				creditsButton = new MenuButton("src/assets/Textures/CreditsButton.png", 2, 2); 
				exitButton = new MenuButton("src/assets/Textures/ExitButton.png", 2, 2); 
		
				background = new Image("src/assets/Textures/TitleScreen.png");
				background.setFilter(Image.FILTER_NEAREST);		
				Game.inst().eventHandler.loadedState(state);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			selectedButton = 1;
		}
	}


	@Override
	public void unloadState(GameState state) {
		if(state instanceof TitleMenu){		
			unloadRequest = true;
			newGameButton = null;
			optionsButton = null;
			creditsButton = null;
			exitButton = null;
			background = null;
			Game.inst().eventHandler.unloadedState(state);
		}
	}
}
