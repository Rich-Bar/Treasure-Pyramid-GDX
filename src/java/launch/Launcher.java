package launch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.newdawn.slick.util.DefaultLogSystem;

import launch.multiscreen.DisplayManager;
import main.managers.OSManagement;
import main.types.TreasureOut;

public class Launcher {
	
	public DisplayManager dispMan;
	
	
	public Launcher() {
		dispMan = new DisplayManager();
		boolean running = true;
		while(running){
			running = false;
			for(Thread thread : dispMan.getThreads()){
				if(thread.isAlive()) running = true;
			}
		}
	}
	
	
	
	/**
	 * Main method of Treasure Pyramide
	 * @param args as {@linkplain String[]}
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		
		
		String GAMEDIR = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
		
		TreasureOut out = new TreasureOut(System.out ,new PrintStream(new FileOutputStream(OSManagement.getAppdataPath() + "last.log")));
		System.setOut(out);
		DefaultLogSystem.out = out;
		setLibraryPath(GAMEDIR + "/src/natives/");
		@SuppressWarnings("unused")
		Launcher launch = new Launcher();
		out.println("Terminated Launcher - Start might have been successful?!");
		
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
	
}
