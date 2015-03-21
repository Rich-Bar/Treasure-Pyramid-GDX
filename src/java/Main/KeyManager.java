package Main;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.state.GameState;

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
			if(keyCode == 17 || keyCode == 200){
				screen.selectedButton--;
			}else if(keyCode == 31 || keyCode == 208){
				screen.selectedButton++;
			}else if(keyCode == 28 || keyCode == 57){
				screen.pressedEnter();
			}
			if(screen.selectedButton > 4)screen.selectedButton = 1;
			if(screen.selectedButton < 1)screen.selectedButton = 4;
			screen.switchSelectedMenuOption(screen.selectedButton);
		}else if(gs instanceof OptionsMenu){
			//OptionsMenu screen = (OptionsMenu) gs;
		}else if(gs instanceof IntroMenu){
			IntroMenu screen = (IntroMenu) gs;
			if(keyCode == 28 || keyCode == 57){
				screen.switchScreen();
			}
		}else if(gs instanceof CreditsMenu){
			CreditsMenu screen = (CreditsMenu) gs;
			if(keyCode == 35) mainGame.donate();
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
		timeIntervalls[keyCode] = 0;
		if(keysPressed.contains(keyCode))keysPressed.remove(keysPressed.indexOf(keyCode));
	}	
}
