package Main;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Main.Shaders.ShaderProgram;
import Main.States.*;

@SuppressWarnings("unused")
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

	private ShaderProgram shader = new ShaderProgram();
	private Dimension internalResolution;
	
	public KeyManager keyManager = new KeyManager(this);
	public double factor;
	public boolean vsync = false;
	public int maxFps= 120;
	
	
	public Game() {
		super(TITLE + " [" + VERSION + "]");
		
		this.internalResolution = new Dimension(pixelartResolution.width * scale, pixelartResolution.height * scale);
		
		this.addState(new IntroMenu(Screens.INTRO.getID(), this));
		this.addState(new TitleMenu(Screens.MAIN.getID(), this));
		this.addState(new OptionsMenu(Screens.OPTIONS.getID(), this));
		this.addState(new CreditsMenu(Screens.CREDITS.getID(), this));
		this.addState(new GameScreen(Screens.GAME.getID(), this));
		
	}
	
	public static void main(String[] args)
	{
		
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game());
			appgc.setMaximumLogicUpdateInterval(240);
			appgc.setUpdateOnlyWhenVisible(false);
			appgc.setTitle("Treasure Pyramid");
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
		gc.setTargetFrameRate(maxFps -1);	//-1 Fixes the 1 more FPS bug
		gc.setVSync(vsync);
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
		 
		/*_________________
		|-- - SHADER - --|
		|________________|
		shader.init("src/assets/Shaders/base.vsh", "src/assets/Shaders/passthrough.fsh");
		 
		
		int shaderid = shader.getProgramId();
		float[] f = {1f,1f,1f};
		
		GL20.glUseProgram(shaderid);
		
		int samplerloc = GL20.glGetUniformLocation(shaderid, "u_sampler2D");
		int transloc = GL20.glGetUniformLocation(shaderid, "u_projTrans");
		
		System.out.println(samplerloc + " - " + transloc);
		
		GL30.glBindVertexArray(shader.constructVertexArrayObject());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		
		if( glGetError() != GL_NO_ERROR )
		{
			throw new RuntimeException("OpenGL error: "+GLU.gluErrorString(glGetError()));
		}*/
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(Screens.INTRO.getID()).init(gc, this);
		this.getState(Screens.MAIN.getID()).init(gc, this);
		this.getState(Screens.OPTIONS.getID()).init(gc, this);
		this.getState(Screens.CREDITS.getID()).init(gc, this);
		this.getState(Screens.GAME.getID()).init(gc, this);
		this.enterState(Screens.INTRO.getID());
	}
	
	public GameState getState(){
		return this.getCurrentState();
	}
	
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
}
