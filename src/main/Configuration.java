package main;

import java.io.File;
import java.io.FileOutputStream;
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
			//the comment can be null
			properties.storeToXML(write, "Resolution");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
