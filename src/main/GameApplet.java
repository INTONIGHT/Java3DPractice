package main;

import java.applet.Applet;
import java.awt.BorderLayout;
/**
 * consider using the following:
 * jpackage --input target/ \
  --name JPackageDemoApp \
  --main-jar JPackageDemoApp.jar \
  --main-class com.baeldung.java14.jpackagedemoapp.JPackageDemoApp \
  --type dmg \
  --java-options '--enable-preview'
 */

public class GameApplet extends Applet{
	private static final long serialVersionUID = 1L;
	
	private Display display = new Display();
	
	public void init() {
		setLayout(new BorderLayout());
		add(display);
	}
	public void start() {
		display.start();
	}
	
	public void stop() {
		display.stop();
	}
}
