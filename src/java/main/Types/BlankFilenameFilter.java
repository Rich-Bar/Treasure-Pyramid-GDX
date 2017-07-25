package main.types;

import java.io.File;
import java.io.FilenameFilter;

public class BlankFilenameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return true;
	}

}
