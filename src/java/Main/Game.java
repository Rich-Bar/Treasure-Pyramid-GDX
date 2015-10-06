package main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import main.language.Localisation;
import main.managers.*;
import main.multiscreen.DisplayManager;
import main.states.*;
import main.types.Shader;
import main.types.SheetFont;

/**
 * @author Marco Dittrich
 * @version Day11
 */

public class Game extends StateBasedGame{
	
	
	//Screen Enum Table
	public static enum Screens{
		LOADING(0),
		INTRO(1),
		MAIN(2),
		OPTIONS(3),
		CREDITS(4),
		GAME(5);
		
		private int ID;   
	    Screens(int ID) {
	        this.ID = ID;
	    }

	    public int getID() { return ID; }
	}

	//Special constants
	public static final String TITLE = "Treasure Pyramide";
	public static final String VERSION = "Day11";

	//Render settings
	public static Dimension pixelartResolution = new Dimension(360, 240);
	public static final int scale = 4;
	public boolean renderInventory = false;
	public static Dimension mainResolution;
	
	//Managers and Handlers
	public DisplayManager displayManager;
	public EventHandler eventHandler;
	public KeyManager keyManager;
	public ConfigManager config;
	public SheetFont font;
	public Localisation lang;
	
	//Shaders
	public Shader crtshader;
	public Shader baseshader;
	
	//Instance (Singleton)
	private static Game instance;
	
	/** 
	 * Class Constructor
	 */ 
	public Game() {
		super(TITLE + " [" + VERSION + "]");
		
		instance = this;
		
		displayManager = new DisplayManager();
		keyManager = new KeyManager();
		eventHandler = new EventHandler();
		config = new ConfigManager();
		
		this.addState(new IntroMenu(Screens.INTRO.getID()));
		this.addState(new TitleMenu(Screens.MAIN.getID()));
		this.addState(new OptionsMenu(Screens.OPTIONS.getID()));
		this.addState(new CreditsMenu(Screens.CREDITS.getID()));
		this.addState(new GameScreen(Screens.GAME.getID()));
	}
	
	/**
	 * Main method of Treasure Pyramide
	 * @param args as {@linkplain String[]}
	 */
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc = new AppGameContainer(new Game());
			DisplayManager.init(appgc).start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Resets the resolution of the internal display
	 * @param gameContainer as {@link GameContainer}
	 */
	/*public void resetResolution(GameContainer gc){
		resetResolution(gc, internalResolution);
	}*/
	
	/**
	 * Resets the resolution of the internal display to the new resolution
	 * @param gameContainer as {@link GameContainer}
	 * @param newResolution as {@link Dimension}
	 */
	/*public void resetResolution(GameContainer gc, Dimension newResolution){
		internalResolution = newResolution;
		
	}
	*/
	/**
	 * Initializes Managers and States
	 * @param gameContainer as {@link GameContainer}
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		displayManager.setGameResolution(mainResolution);
		displayManager.setMultiscreen(true);
		eventHandler.init();
		
		this.getState(Screens.INTRO.getID()).init(gc, this);
		this.getState(Screens.CREDITS.getID()).init(gc, this);
		this.getState(Screens.GAME.getID()).init(gc, this);
		this.getState(Screens.MAIN.getID()).init(gc, this);
		this.getState(Screens.OPTIONS.getID()).init(gc, this);
		
		config.read();
		lang = new Localisation();
		eventHandler.loadState(this.getState(Screens.INTRO.getID()));
	}
	
	/**
	 * Returns current gamestate
	 * @return currentState as {@link GameState}
	 */
	public GameState getState(){
		return this.getCurrentState();
	}
	
	/**
	 * @return the internalResolution as {@link Dimension}
	 */
	public Dimension getInternalResolution() {
		return new Dimension(pixelartResolution.width * scale, pixelartResolution.height * scale);
	}
	
	/**
	 * Opens standartbrowser and redirects it to Paypal
	 * {@link https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5RQ9AMFVA8CQL}
	 */
	public void donate(){
		try {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		            desktop.browse(new URL("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5RQ9AMFVA8CQL").toURI());
		    }
		
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * gets current instance of game
	 * @return instance as {@link Game}
	 */
	public static Game inst() {
		return instance;
	}
}
