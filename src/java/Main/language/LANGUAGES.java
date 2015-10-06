package main.language;

import java.io.File;

	public enum LANGUAGES{
		GERMAN_DE(new File("src/assets/Lang/de.lang")),
		ENGLISH_EN(new File("src/assets/Lang/en.lang")),
		PIRATE_PI(new File("src/assets/Lang/pi.lang"));
		
		private File file;
		private boolean notified = false;
		private LANGUAGES(File loc) {
			file = loc;
		}
		
		public File getFile(){
			if(!file.exists() && !notified){
				System.out.println("Couldn't find Language File: '" + file.getAbsolutePath() + "'");
				notified = true;
			}
			return file;
		}
	}