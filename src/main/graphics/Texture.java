package main.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {
	//you may need to right click the project go to libraries and go to the class libraries and add
	//the res folder as part of the class path
	public static Render floor = loadBitmap("/textures/floor.png");
	
	public static Render loadBitmap(String fileName) {
		try {
			
			//so the whole adding as a resource didnt work i need to add the folders to the src folder
			//so keep that in mind for future building can reference the 2d thing I made
			BufferedImage image = ImageIO.read(Texture.class.getResourceAsStream(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width,height);
			image.getRGB(0, 0,width,height,result.pixels,0,width);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
