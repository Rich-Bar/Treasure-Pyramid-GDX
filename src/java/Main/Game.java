package Main;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import Main.States.*;

public class Game extends StateBasedGame{
	
	public static enum Screens{
		INTRO(0),
		MAIN(1),
		OPTIONS(2),
		CREDITS(3),
		GAME(4);
		
		private int ID;   
	    Screens(int ID) {
	        this.ID = ID;
	    }

	    public int getID() { return ID; }
	}

	
	private static final String TITLE = "Treasure Pyramide";
	private static final String VERSION = "Day2";
	public static final int scale = 4;
	
	public static Dimension pixelartResolution = new Dimension(360, 240);
	private static Dimension nativeResolution;
	private static int offsetX = 0;
	private static int offsetY = 0;
	
	
	public double factor;
	public TrueTypeFont fontText;
	public TrueTypeFont fontMenu;
	private Dimension internalResolution;
	
	public Game() {
		super(TITLE + " [" + VERSION + "]");
		
		this.internalResolution = new Dimension(pixelartResolution.width * scale, pixelartResolution.height * scale);
		
		this.addState(new IntroMenu(Screens.INTRO.getID()));
		this.addState(new TitleMenu(Screens.MAIN.getID(), this));
		this.addState(new OptionsMenu(Screens.OPTIONS.getID()));
		this.addState(new CreditsMenu(Screens.CREDITS.getID()));
		this.addState(new GameScreen(Screens.GAME.getID()));
		
	}
	
	public static void main(String[] args)
	{
		
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(pixelartResolution.width * scale, pixelartResolution.height * scale, false);
			DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
			appgc.setDisplayMode(dm.getWidth(), dm.getHeight(), true);
			
			nativeResolution = new Dimension(dm.getWidth(), dm.getHeight());
			
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void resetResolution(GameContainer gc){
		resetResolution(gc, internalResolution);
	}
	
	public void resetResolution(GameContainer gc, Dimension newResolution){
		internalResolution = newResolution;
		////Handle GameContainer
		gc.setShowFPS(true);
		////Handle Canvas and Bounds
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		//add black columns if necessary
		factor = 0;
		if((double)internalResolution.width / internalResolution.height < (double)nativeResolution.width / nativeResolution.height){
			factor = (double)nativeResolution.height / internalResolution.height;
			offsetX = (int) ((nativeResolution.width - internalResolution.width * factor) /2);
		}else if((double)internalResolution.width / internalResolution.height > (double)nativeResolution.width / nativeResolution.height){
			factor = (double)nativeResolution.width / internalResolution.width;
			offsetY = (int) ((nativeResolution.height - internalResolution.height * factor) /2);
		}
		System.out.println("offsetX: " + offsetX + " - offsetY: " + offsetY);
		
		//apply columns
		GL11.glOrtho(-offsetX ,internalResolution.width + offsetX, internalResolution.height + offsetY, - offsetY, 100, -100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		Font awt = new Font("Consolas", Font.PLAIN, 8);
		fontMenu = new TrueTypeFont(awt, false);
		awt = new Font("Consolas", Font.PLAIN, 8);
		fontText = new TrueTypeFont(awt, false);
		
		this.getState(Screens.INTRO.getID()).init(gc, this);
		this.getState(Screens.MAIN.getID()).init(gc, this);
		this.getState(Screens.OPTIONS.getID()).init(gc, this);
		this.getState(Screens.CREDITS.getID()).init(gc, this);
		this.getState(Screens.GAME.getID()).init(gc, this);
		this.enterState(Screens.MAIN.getID());
		
	}
}
