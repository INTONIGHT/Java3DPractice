package main.graphics;

import java.util.Random;

import main.Game;
import main.input.Controller;

public class Render3D extends Render {

	public double[] zBuffer;
	private double renderDistance = 5000;
	private double forward,right,cosine,sine,up;
	
	

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		// TODO Auto-generated constructor stub
	}

	public void floor(Game game) {
		// double rotation = game.time / 100.0;
		double rotation = 0;//game.controls.rotation;
		 cosine = Math.cos(rotation);
		 sine = Math.sin(rotation);
		// allows us to manipulate the floor and ceiling seperately
		double floorPosition = 8;
		double ceilingPosition = 8;
		 forward = game.controls.z;
		
		 right = game.controls.x;
		 up = game.controls.y;
		double walking = Math.sin(game.time / 6.0) * 0.5;
		if (Controller.crouchWalk) {
			walking = Math.sin(game.time / 6.0) * 0.25;
		}
		// allows you to control the animation of bobbing when running
		if (Controller.runAnim) {
			walking = Math.sin(game.time / 6.0) * 0.8;
		}

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;

			double z = (floorPosition + up) / ceiling;
			if (Controller.walk) {
				z = (floorPosition + up + walking) / ceiling;
			}
			if (ceiling < 0) {
				z = (ceilingPosition - up) / -ceiling;
				if (Controller.walk) {
					z = (ceilingPosition - up - walking) / -ceiling;
				}
			}

			// this lets you have a change to whats being rendered but it will look wonky if
			// you increment it a lot this is machine dependent so a powerful computer will
			// need smaller increments potentially

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				// using a bitwise operator
				// can also use << or >> for some interesting effects
				// subtracting time can get moroe diagonal movemnt
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;

				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;
				// so one option you could do is use the math part for a subsitute if floor is
				// not rendered;
				// ((xPix & 15)* 16 ) | ((yPix & 15)* 16) << 8
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 8];
				// doing some limiting on what gets rendered
				// this part can change how the fade goes.
//				if(z > 500) {
//					pixels[x +y*width] = 0;
//				}
			}
		}
		
		
	}
	
	public void renderWalls(double xLeft, double xRight, double zDistance, double yHeight) {
		//calculateing the y position of the wall
		double xcLeft = ((xLeft) - right) * 2;
		double zcLeft = ((zDistance) - forward) * 2;
		
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		//top left corner
		//i call up up
		double yCornerTL = ((-yHeight) - up) * 2;
		double yCornerBL = ((+0.5 - yHeight) - up) * 2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;
		
		double xcRight = ((xRight) - right) * 2;
		double zcRight = ((zDistance) - forward) * 2;
		
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight)-up) *2;
		double yCornerBR = ((+0.5 - yHeight) - up) * 2;
		
		double rotRightSideZ = zcRight * cosine + xcRight * sine;
		
		//now we have our corner pins.
		//left edge of the wall
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);
		
		if(xPixelLeft >= xPixelRight) {
			return;
		}
		
		int xPixelLeftInt = (int) xPixelLeft;
		int xPixelRightInt = (int) xPixelRight;
		
		if(xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}
		if(xPixelRightInt > width) {
			xPixelRightInt = width;
		}
		
		//corner pins
		double yPixelLeftTop = (int) (yCornerTL / rotLeftSideZ * height + height / 2);
		double yPixelLeftBottom = (int) (yCornerBL / rotLeftSideZ * height + height / 2);
		double yPixelRightTop = (int) (yCornerTR / rotRightSideZ * height + height / 2);
		double yPixelRightBottom = (int) (yCornerBR / rotRightSideZ * height + height / 2);
		
		for(int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			
			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;
			
			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);
			
			if(yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if(yPixelTopInt > height) {
				yPixelTopInt = height;
			}
			
			for(int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				//can be any color
				
				pixels[x + y*width] = 0x1B91E0;
				zBuffer[x + y *width] = 0;
			}
		}
	}
	

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));
			// setting minimum and max values;
			if (brightness < 0) {
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}
