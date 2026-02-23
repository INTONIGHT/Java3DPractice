package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
	Properties properties = new Properties();
	
	public void saveConfiguration(String key, int value) {
		try {
			//this one you cant put a / for whatever reason headache o clock of course
			String path = "src/settings/config.xml";
			File file = new File(path);
			boolean exists = file.exists();
			if(!exists) {
				file.createNewFile();
			}
			//Output streams are used for writing data to a file input streams read data from a file
			OutputStream write = new FileOutputStream(path);
			properties.setProperty(key, Integer.toString(value));
			//the comment can be null just lets you track what you were doing if you want
			properties.storeToXML(write, "Resolution");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfiguration(String path) {
		try {
			InputStream read = new FileInputStream(path);
			//if you store in xml needs to load from xml
			properties.loadFromXML(read);
			String width = properties.getProperty("width");
			String height = properties.getProperty("height");
			//System.out.println("width: " + width + " height: " + height);
			setResolution(Integer.parseInt(width), Integer.parseInt(height));
			read.close();
		}catch(FileNotFoundException e) {
			//basically if the file doesnt exist we want to just give it some default stuff
			//then it should just try to load the persisted stuff;
			saveConfiguration("width",800);
			saveConfiguration("width",600);
			loadConfiguration(path);
			//e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setResolution(int width,int height) {
		if(width == 640 && height == 480) {
			Display.selection = 0;
		}
		if(width == 800 && height == 600) {
			Display.selection = 1;
		}
		if(width == 1024 && height == 768) {
			Display.selection = 2;
		}
	}
}
