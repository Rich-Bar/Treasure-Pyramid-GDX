package main.components;

import java.io.Serializable;

import main.language.LANGUAGES;
import main.multiscreen.Device.SerialDevice;

@SuppressWarnings("serial")
public class Settings implements Serializable {
	
	public LANGUAGES language;
	public float masterVol = 1f;
	public float musicVol = 1f;
	public float soundVol = 1f;
	public boolean vSync = false;
	public boolean debug = false;
	public SerialDevice[] sDevice;
}
