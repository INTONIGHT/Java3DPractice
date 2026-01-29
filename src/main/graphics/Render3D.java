package main.graphics;
import main.Game;

public class Render3D extends Render{
	
	public double[] zBuffer;
	private double renderDistance = 5000;
	

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width*height];
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void floor(Game game) {
		//double rotation = game.time / 100.0;
		double rotation = game.controls.rotation;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);
		//allows us to manipulate the floor and ceiling seperately
		double floorPosition = 8;
		double ceilingPosition = 8;
		double forward = game.controls.z ;
		double right = game.controls.x;
		
		//game.controls.y
		double vertical = game.controls.y;
		
		for(int y = 0; y<height;y++) {
			double ceiling = (y - height/2.0) / height;
			
			double z = (floorPosition + vertical) /ceiling;
			if(ceiling < 0) {
				z = (ceilingPosition - vertical) / -ceiling;
			}
			
			//this lets you have a change to whats being rendered but it will look wonky if
			//you increment it a lot this is machine dependent so a powerful computer will need smaller increments potentially
		
			
			
			for(int x =0; x<width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				//using a bitwise operator
				//can also use << or >> for some interesting effects
				//subtracting time can get moroe diagonal movemnt
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x + y *width] = z;
				//so one option you could do is use the math part for a subsitute if floor is not rendered;
				//((xPix & 15)* 16 ) | ((yPix & 15)* 16) << 8
				pixels[x + y * width] = Texture.floor.pixels[ (xPix & 7) + (yPix & 7) * 8];
				//doing some limiting on what gets rendered
				//this part can change how the fade goes.
//				if(z > 500) {
//					pixels[x +y*width] = 0;
//				}
			}
		}
	}
	
	public void renderDistanceLimiter() {
		for(int i =0; i < width*height;i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));
			//setting minimum and max values;
			if(brightness < 0) {
				brightness = 0;
			}
			if(brightness > 255) {
				brightness = 255;
			}
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;
			
			r = r*brightness / 255;
			g = g*brightness / 255;
			b = b*brightness / 255;
			 
			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}
