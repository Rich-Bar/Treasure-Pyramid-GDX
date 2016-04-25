package main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import launch.multiscreen.Device;
import main.components.SheetFont;
import main.language.Localisation;
import main.managers.*;
import main.states.*;
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
		
		private int id;   
	    Screens(int id) {
	        this.id = id;
	    }

	    public int getID() { return id; }
	}

	//Special constants
	public static final String TITLE = "Treasure Pyramide";
	public static final String VERSION = "Day11";
	
	//Render settings
	public static Dimension pixelartResolution = new Dimension(360, 240);
	public float scale = 1;
	private Device device;
	
	//Managers and Handlers
	public EventHandler eventHandler;
	public KeyManager keyManager;
	public ConfigManager config;
	public SheetFont font;
	public Localisation lang;
	
	//Game 'Universals'
	public Player player = new Player();
	
	//Instance (Singleton)
	private static Game instance;
	
	/** 
	 * Class Constructor
	 */ 
	public Game(Device device) {
		super(TITLE + " [" + VERSION + "]");

		instance = this;

		this.device = device;
		
		keyManager = new KeyManager();
		eventHandler = new EventHandler(device);
		config = new ConfigManager();
		
		this.addState(new IntroMenu(Screens.INTRO.getID()));
		this.addState(new TitleMenu(Screens.MAIN.getID()));
		this.addState(new OptionsMenu(Screens.OPTIONS.getID()));
		this.addState(new CreditsMenu(Screens.CREDITS.getID()));
		this.addState(new GameScreen(Screens.GAME.getID()));
		
	}
	
	/**
	 * Initializes Managers and States
	 * @param gameContainer as {@link GameContainer}
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		eventHandler.init();
		font = new SheetFont();	
		
		this.getState(Screens.INTRO.getID()).init(gc, this);
		this.getState(Screens.CREDITS.getID()).init(gc, this);
		this.getState(Screens.GAME.getID()).init(gc, this);
		this.getState(Screens.MAIN.getID()).init(gc, this);
		this.getState(Screens.OPTIONS.getID()).init(gc, this);
		
		config.read();
		lang = new Localisation();
		
		dial();
		
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
	 * Returns current Device the game is running on
	 * @return Device as {@link Device}
	 */
	public Device getDevice(){
		return device;
	}
	
	/**
	 * @return the internalResolution as {@link Dimension}
	 */
	public Dimension getInternalResolution() {
		int width = (int) (pixelartResolution.width * scale);
		int height = (int) (pixelartResolution.height * scale);
		return new Dimension(width, height);
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
	
	public void dial(){
		System.out.println("Dialing Screen["+ device.type.name() +"]: " + device.title);
		
		////Handle Canvas and Bounds
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		Rectangle internalResolution = device.getDevice().getDefaultConfiguration().getBounds();
		
		//add black columns if necessary
		device.factor = 0;
		if(internalResolution.getWidth() * 1.0 / internalResolution.getHeight() >= Game.pixelartResolution.getWidth() * 1.0 / Game.pixelartResolution.getHeight()){
			device.factor = (double)internalResolution.getHeight() / Game.pixelartResolution.getHeight();
			device.offset = (int) ((Game.pixelartResolution.getWidth() * device.factor - internalResolution.getWidth()) /2);
			device.lrColumns = true;
		}else if(internalResolution.getWidth() * 1.0 / internalResolution.getHeight() < Game.pixelartResolution.getWidth() * 1.0 / Game.pixelartResolution.getHeight()){
			device.factor = (double)internalResolution.getWidth() / Game.pixelartResolution.getWidth();
			device.offset = (int) ((Game.pixelartResolution.getHeight() * device.factor - internalResolution.getHeight()) /2);
			device.lrColumns = false;
		}
		scale = (float) device.factor;
		
		//apply columns
		GL11.glOrtho(	device.lrColumns ? device.offset : 0 ,
						internalResolution.getWidth() + (device.lrColumns ? device.offset : 0),
						internalResolution.getHeight() + (device.lrColumns ? 0 : device.offset),
						device.lrColumns ? 0 : device.offset,
							100, -100);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		 
		device.gFrame.setCursor(device.gFrame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
}
