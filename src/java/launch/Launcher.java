package launch;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Arrays;

import org.newdawn.slick.util.DefaultLogSystem;

import launch.multiscreen.DisplayManager;
import launch.multiscreen.Window;
import main.managers.OSManagement;
import main.types.BlankFilenameFilter;
import main.types.TreasureOut;

public class Launcher {
	
	public DisplayManager dispMan;
	public static String gameDir = "";
	private static TreasureOut out;
	
	public Launcher(boolean multiscreen) throws InterruptedException, IOException {
		if(multiscreen){
			dispMan = new DisplayManager();
			while(dispMan.running){
				dispMan.waitForIt();
			}
		}else{
			String gDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getIDstring().replace("\\\\", "/");
			String[] pbArgs = {"java", "-cp", '"' + Launcher.getNativePath() + Launcher.getPath() + '"', "launch.multiscreen.Device", "-gdevice:" + gDevice, "-type:" + Window.GameScreen}; 
			ProcessBuilder pb = new ProcessBuilder(pbArgs);
			pb.start().waitFor();
		}
	}
	
	
	
	/**
	 * Main method of Treasure Pyramide
	 * @param args as {@linkplain String[]}
	 * @throws Exception 
	 */
	public static void main(String... args) throws Exception
	{
		
		gameDir = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
		
		out = new TreasureOut(System.out);
		out.addStream(new PrintStream(new FileOutputStream(OSManagement.getAppdataPath() + "launcher.log")));
		System.setOut(out);
		DefaultLogSystem.out = out;
		setUpAppdata();
		
		@SuppressWarnings("unused")
		Launcher launch = new Launcher(false); /// Use true to start in multiscreen mode - currently not working
		out.println("Terminated Launcher - Start might have been successful?!");
		
	}
	
	private static void setUpAppdata() throws IOException{
		File appData = new File(OSManagement.getAppdataPath());
		if(!appData.exists()){
			appData.createNewFile();
		}
		
		String nativeList = "[jinput-dx8.dll, jinput-dx8_64.dll, jinput-raw.dll, jinput-raw_64.dll, libjinput-linux.so, libjinput-linux64.so, libjinput-osx.dylib, liblwjgl.dylib, liblwjgl.so, liblwjgl64.so, libopenal.so, libopenal64.so, lwjgl.dll, lwjgl64.dll, openal.dylib, OpenAL32.dll, OpenAL64.dll]";
		String libraryList = "[jinput.jar, jogg-0.0.7.jar, jorbis-0.0.15.jar, lwjgl.jar, lwjgl_util.jar, slick.jar]";
		if(!Arrays.toString(new File(appData + "/Natives/").list(new BlankFilenameFilter())).equals(nativeList)
		|| !Arrays.toString(new File(appData + "/Librarys/").list(new BlankFilenameFilter())).equals(libraryList)){
			copyDirectory(new File(gameDir + "/src/natives/"), new File(appData + "/Natives/"));
			copyDirectory(new File(gameDir + "/src/lib/"), new File(appData + "/Librarys/"));
		}
	}
	
	public static String getNativePath(){
		String basePath = OSManagement.getAppdataPath() + "Librarys/";
		String[] files = new File(basePath).list(new BlankFilenameFilter());
		String path = "";
		for(String filePath : files){
			path += basePath + filePath + ";";
		}
		return path;
	}
	
	public static String getPath() throws UnsupportedEncodingException{
		String abstPath = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return URLDecoder.decode(abstPath, "UTF-8").replaceFirst("/", "");
	}
	
	/**
	 * Required for starting the game without '-Djava.library.path' argument!
	 * Using the private field "sys_paths" in {@linkplain ClassLoader}
	 * @see http://fahdshariff.blogspot.be/2011/08/changing-java-library-path-at-runtime.html
	 * @param path
	 * @throws Exception
	 */
	public static void setLibraryPath() throws Exception {
		System.out.println("Loading Natives and changing 'java.library.path'");
		System.setProperty("java.library.path", OSManagement.getAppdataPath() + "/Natives/");

		
		//set sys_paths to null so that java.library.path will be reevalueted next time it is needed
		final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
		sysPathsField.setAccessible(true);
		sysPathsField.set(null, null);
	}	

	public static void setDeviceLog(String deviceName) throws IOException{
		setDeviceLog(System.out, deviceName);
	}
	
	public static void setDeviceLog(OutputStream oStream, String deviceName) throws IOException{
		TreasureOut out;
		try {
			out = new TreasureOut(oStream);
			out.addStream(new PrintStream(new FileOutputStream(OSManagement.getAppdataPath() + deviceName + "-last.log")));
	
			System.setOut(out);
			DefaultLogSystem.out = out;	
		} catch (FileNotFoundException e) {
			new File(OSManagement.getAppdataPath() + deviceName + "-last.log").createNewFile();
			setDeviceLog(oStream, deviceName);
		}
	}
	
	public static void copyDirectory(File sourceLocation , File targetLocation)
			throws IOException {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}
				String[] children = sourceLocation.list();
			for (int i=0; i<children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]),
						new File(targetLocation, children[i]));
			}
		} else {
				InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);
				// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}