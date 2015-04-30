package Main.Managers;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.state.GameState;
import org.newdawn.slick.Input;

import Main.Game;
import Main.Game.Screens;
import Main.States.*;

public class KeyManager {

	private long[] timeIntervalls = new long[1024];
	private List<Integer> keysPressed = new ArrayList<Integer>();
	private Game mainGame;
	private static int buttonSwitchSpeed = 400;
	
	public KeyManager(Game mainGame) {
		this.mainGame = mainGame;
	}
	
	public void keyAction(int keyCode, GameState gs){
		
		if(gs instanceof TitleMenu){
			TitleMenu screen = (TitleMenu) gs;
			if(keyCode == Input.KEY_DOWN || keyCode == Input.KEY_S){
				screen.selectedButton++;
			}else if(keyCode == Input.KEY_UP || keyCode == Input.KEY_W){
				screen.selectedButton--;
			}
			
			if(screen.selectedButton > 4)screen.selectedButton = 1;
			if(screen.selectedButton < 1)screen.selectedButton = 4;
			screen.switchSelectedMenuOption(screen.selectedButton);
			
			if(keyCode == Input.KEY_ENTER || keyCode == Input.KEY_SPACE){
				screen.pressedEnter();
			}
			
		}else if(gs instanceof OptionsMenu){
			OptionsMenu screen = (OptionsMenu) gs;
			if(keyCode == Input.KEY_UP || keyCode == Input.KEY_W){
				screen.setSelected(screen.getSelected() - 1);
			}else if(keyCode == Input.KEY_DOWN || keyCode == Input.KEY_S){
				screen.setSelected(screen.getSelected() + 1);
			}else if(keyCode == Input.KEY_BACK || keyCode == Input.KEY_ESCAPE || keyCode == Input.KEY_BACKSLASH){
				mainGame.eventHandler.loadState(mainGame.getState(Screens.MAIN.getID()));
				System.out.println("Back to title menu!");
			}else if(keyCode == Input.KEY_ENTER || keyCode == Input.KEY_SPACE){
				screen.pressedEnter();
			}else if(keyCode == Input.KEY_A || keyCode == Input.KEY_LEFT || keyCode == Input.KEY_MINUS){
				buttonSwitchSpeed = 250;
				screen.changeSlider(-10);
			}else if(keyCode == Input.KEY_D || keyCode == Input.KEY_RIGHT || keyCode == Input.KEY_ADD){
				buttonSwitchSpeed = 250;
				screen.changeSlider(+10);
			}
			
			
		}else if(gs instanceof IntroMenu){
			IntroMenu screen = (IntroMenu) gs;
			if(keyCode == Input.KEY_ENTER || keyCode == Input.KEY_SPACE){
				screen.switchScreen();
			}
			
			
		}else if(gs instanceof CreditsMenu){
			CreditsMenu screen = (CreditsMenu) gs;
			if(keyCode == Input.KEY_H) mainGame.donate();
			else screen.switchScreen();
			
			
		}else if(gs instanceof GameScreen){
			//GameScreen screen = (GameScreen) gs;
		} 

	}
	
	public void update(GameState gs, int delta){
		if(!keysPressed.isEmpty()){
			for (int keyCode : keysPressed) {
				timeIntervalls[keyCode] += delta;
				if(timeIntervalls[keyCode] > buttonSwitchSpeed){
					timeIntervalls[keyCode] = 0;
					keyAction(keyCode, gs);
				}
			}
		}
		
	}
	
	public void keyPressed(int keyCode, GameState gs){
		keysPressed.add(keyCode);
		keyAction(keyCode, gs);
	}
	
	public void keyReleased(int keyCode){
		buttonSwitchSpeed = 400;
		timeIntervalls[keyCode] = 0;
		if(keysPressed.contains(keyCode))keysPressed.remove(keysPressed.indexOf(keyCode));
	}	
}
