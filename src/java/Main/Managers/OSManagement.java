package main.managers;

public class OSManagement {
	
	private static String os = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {

		return os.indexOf("win") >= 0;

	}

	public static boolean isMac() {

		return os.indexOf("mac") >= 0;

	}

	public static boolean isUnix() {

		return os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0 ;
		
	}

	public static boolean isSolaris() {

		return os.indexOf("sunos") >= 0;

	}
	
	public static boolean is64Bit(){
		boolean is64bit = false;
		if (isWindows()) {
		    is64bit = System.getenv("ProgramFiles(x86)") != null;
		} else {
		    is64bit = System.getProperty("os.arch").indexOf("64") != -1;
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
		fileLocation = fileLocation.replaceAll("\\\\", "/");
		return fileLocation;
	}
	
}
