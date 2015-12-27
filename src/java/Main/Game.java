package main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.URL;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.DefaultLogSystem;

import main.components.SheetFont;
import main.language.Localisation;
import main.managers.*;
import main.multiscreen.DisplayManager;
import main.states.*;
import main.types.TreasureOut;
import main.world.entitys.Player;

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
	public static String GAMEDIR;
	
	//Render settings
	public static Dimension pixelartResolution = new Dimension(360, 240);
	public static final int scale = 4;
	
	//Managers and Handlers
	public DisplayManager displayManager;
	public EventHandler eventHandler;
	public KeyManager keyManager;
	public ConfigManager config;
	public SheetFont font;
	public Localisation lang;
	
	//Game 'Universals'
	public Player player = new Player();
	
	//Instance (Singleton)
	private static Game instance;
	private static AppGameContainer appgc;
	
	/** 
	 * Class Constructor
	 */ 
	public Game() {
		super(TITLE + " [" + VERSION + "]");
		
		instance = this;
		
		displayManager = new DisplayManager(appgc);
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
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		GAMEDIR = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
		
		TreasureOut out = new TreasureOut(System.out ,new PrintStream(new FileOutputStream(OSManagement.getAppdataPath() + "last.log")));
		System.setOut(out);
		DefaultLogSystem.out = out;
		
		setLibraryPath(GAMEDIR + "/src/natives/");
		try
		{
			appgc = new AppGameContainer(new Game());
		}
		catch (SlickException ex){
			ex.printStackTrace(out);
		}finally{
			out.println("Terminated - Thx for playing!");
		}
		
	}
	
	/**
	 * Required for starting the game without '-Djava.library.path' argument!
	 * Using the private field "sys_paths" in {@linkplain ClassLoader}
	 * @see http://fahdshariff.blogspot.be/2011/08/changing-java-library-path-at-runtime.html
	 * @param path
	 * @throws Exception
	 */
	public static void setLibraryPath(String path) throws Exception {
		System.out.println("Loading Natives and changing 'java.library.path'");
	    System.setProperty("java.library.path", path);

	    
	    //set sys_paths to null so that java.library.path will be reevalueted next time it is needed
	    final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
	    sysPathsField.setAccessible(true);
	    sysPathsField.set(null, null);
	}
	
	/**
	 * Initializes Managers and States
	 * @param gameContainer as {@link GameContainer}
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		eventHandler.init();
		
		this.getState(Screens.INTRO.getID()).init(gc, this);
		this.getState(Screens.CREDITS.getID()).init(gc, this);
		this.getState(Screens.GAME.getID()).init(gc, this);
		this.getState(Screens.MAIN.getID()).init(gc, this);
		this.getState(Screens.OPTIONS.getID()).init(gc, this);
		
		config.read();
		lang = new Localisation();
		
		eventHandler.loadState(this.getState(Screens.GAME.getID()));
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
