package main.managers;

public class OSManagement {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}
	
	public static boolean is64Bit(){
		boolean is64bit = false;
		if (System.getProperty("os.name").contains("Windows")) {
		    is64bit = (System.getenv("ProgramFiles(x86)") != null);
		} else {
		    is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
		}
		return is64bit;
	}
	
	public static String getAppdataPath(){
		String fileLocation;
		if (OSManagement.isWindows()){
			fileLocation = System.getenv("AppData") + "/TreasurePyramid/";
		}else{
			fileLocation = System.getProperty("user.home");
			fileLocation += "/Library/Application Support/TreasurePyramid/";
		}
		return fileLocation;
	}
	
}
