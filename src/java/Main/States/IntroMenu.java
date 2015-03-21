package Main.States;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Main.Game;
import Main.Game.Screens;
import Main.MenuButton;
import Main.Sound;

public class IntroMenu extends BaseState {
	
	private int ID;
	private float fadeoutSpeed = 2000;
	private Sound music;
	private Image logo;
	private MenuButton skipProlog;
	private float timer = 0;
	private float greyscale = 0;
	private boolean skipable = false;
	private Color fadeFilter;
	private float musicPos = 0;
	private boolean isPaused;
	
	public IntroMenu(int ID,Game game){
		super(game);
		this.ID = ID;
	}

	public void switchScreen(){
		music.stop();
		mainGame.enterState(Screens.MAIN.getID());
	}

	@Override
	public boolean isAcceptingInput() {
		return skipable;
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
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentState() instanceof IntroMenu) unpause();
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(arg1.getCurrentState() instanceof IntroMenu) pause();
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		fadeFilter = Color.black;
		logo = new Image("src/assets/Textures/RichyEntertainment_LowRes.png");
		skipProlog = new MenuButton("src/assets/Textures/PrologSkip.png", 1);
		
		logo.setFilter(Image.FILTER_NEAREST);
		
		music = new Sound("src/assets/Sound/SeminararbeitProlog-Intro.ogg");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(timer > 10000)skipProlog.draw(skipProlog.getMiddle(Game.pixelartResolution.width, true) * Game.scale, 200 * Game.scale);
		logo.draw(0, 0, Game.scale * 2, fadeFilter);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		timer += delta;
		if (greyscale < 1 && timer < 13000 && timer > 2000){
			greyscale +=  delta * 1 / (fadeoutSpeed);
		}else if(timer >13000){
			greyscale  -=  delta * 1 / (fadeoutSpeed);
			if(!skipable) skipable = true;
		}
		fadeFilter = new Color(greyscale,greyscale,greyscale,greyscale);
		
		if(!arg0.hasFocus() && !isPaused){
			isPaused = true;
			pause();
		}else if(arg0.hasFocus() && isPaused){
			unpause();
			isPaused = false;
		}
	}
	
	@Override
	public void pause(){
		musicPos = music.getSound().getPosition();
		music.stop();
	}
	
	@Override
	public void unpause(){
		music.playAt(musicPos);
	}

}
