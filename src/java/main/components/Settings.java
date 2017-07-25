package main.components;

import java.io.Serializable;

import launch.multiscreen.Device.SerialDevice;
import main.language.LANGUAGES;

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
