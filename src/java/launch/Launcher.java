package launch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.newdawn.slick.util.DefaultLogSystem;

import launch.multiscreen.DisplayManager;
import main.managers.OSManagement;
import main.types.TreasureOut;

public class Launcher {
	
	public DisplayManager dispMan;
	public static String gameDir = "";
	
	public Launcher(boolean multiscreen) throws UnsupportedEncodingException {
		if(multiscreen){
			dispMan = new DisplayManager();
		}else{
		}
		while(!dispMan.init){}
		dispMan.waitForIt();
	}
	
	
	
	/**
	 * Main method of Treasure Pyramide
	 * @param args as {@linkplain String[]}
	 * @throws Exception 
	 */
	public static void main(String... args) throws Exception
	{
		
		gameDir = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
		
		TreasureOut out = new TreasureOut(System.out ,new PrintStream(new FileOutputStream(OSManagement.getAppdataPath() + "last.log")));
		System.setOut(out);
		DefaultLogSystem.out = out;
		setLibraryPath(gameDir + "/src/natives/");
		@SuppressWarnings("unused")
		Launcher launch = new Launcher(true); /// Use true to start in multiscreen mode - currently not working
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

	public static void setDeviceLog(String deviceName) throws IOException{
		TreasureOut out;
		try {
			out = new TreasureOut(System.out ,new PrintStream(new FileOutputStream(OSManagement.getAppdataPath() + deviceName + "-last.log")));

		System.setOut(out);
		DefaultLogSystem.out = out;	
		} catch (FileNotFoundException e) {
			new File(OSManagement.getAppdataPath() + deviceName + "-last.log").createNewFile();
			setDeviceLog(deviceName);
		}
	}

}