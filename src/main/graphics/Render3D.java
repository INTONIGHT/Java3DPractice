package main.graphics;

import java.util.Random;

import main.Game;
import main.input.Controller;
import main.level.Block;
import main.level.Level;

public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zBufferWall;
	private double renderDistance = 5000;
	private double forward,right,cosine,sine,up,walking;
	
	

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		// TODO Auto-generated constructor stub
		zBufferWall = new double[width];
	}

	public void floor(Game game) {
		for(int x =0;x<width;x++) {
			zBufferWall[x] = 0;
		}
		
		// double rotation = Math.sin(game.time / 40.0) * 0.5;
		double rotation = game.controls.rotation;
		 cosine = Math.cos(rotation);
		 sine = Math.sin(rotation);
		// allows us to manipulate the floor and ceiling seperately
		double floorPosition = 8;
		double ceilingPosition = 8;
		 forward = game.controls.z;
		 //keeping it at 0 and letting it go back to 0 if you stop walking
		 walking = 0;
		
		 right = game.controls.x;
		 up = game.controls.y;
		 
		

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;

			double z = (floorPosition + up) / ceiling;
			
			if (Controller.crouchWalk && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.25;
				z = (floorPosition + up + walking) / ceiling;
			}
			// allows you to control the animation of bobbing when running
			if (Controller.runAnim && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.8;
				z = (floorPosition + up + walking) / ceiling;
			}
			
			if (Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;
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
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 16];
				// doing some limiting on what gets rendered
				// this part can change how the fade goes.
//				if(z > 500) {
//					pixels[x +y*width] = 0;
//				}
			}
		}
		
		Level level = game.level;
		//controls size of how many walls are generated
		int size = 20;
		
		for(int xBlock = -size; xBlock <= size; xBlock++) {
			for(int zBlock = -size; zBlock<= size; zBlock ++) {
				Block block = level.createBlock(xBlock, zBlock);
				Block east = level.createBlock(xBlock + 1, zBlock);
				Block south = level.createBlock(xBlock, zBlock + 1);
				
				if(block.solid) {
					if(!east.solid) {
						renderWalls(xBlock + 1, xBlock+1,zBlock,zBlock+1,0);
					}
					if(!south.solid) {
						renderWalls(xBlock + 1, xBlock-1,zBlock+1,zBlock+1,0);
					}
				}else {
					if(east.solid) {
						renderWalls(xBlock+1,xBlock+1,zBlock+1,zBlock,0);
					}
					if(south.solid) {
						renderWalls(xBlock,xBlock+1,zBlock+1,zBlock+1,0);
					}
				}
			}
		}
		
		for(int xBlock = -size; xBlock <= size; xBlock++) {
			for(int zBlock = -size; zBlock<= size; zBlock ++) {
				Block block = level.createBlock(xBlock, zBlock);
				Block east = level.createBlock(xBlock + 1, zBlock);
				Block south = level.createBlock(xBlock, zBlock + 1);
				
				if(block.solid) {
					if(!east.solid) {
						renderWalls(xBlock + 1, xBlock+1,zBlock,zBlock+1,0.5);
					}
					if(!south.solid) {
						renderWalls(xBlock + 1, xBlock-1,zBlock+1,zBlock+1,0.5);
					}
				}else {
					if(east.solid) {
						renderWalls(xBlock+1,xBlock+1,zBlock+1,zBlock,0.5);
					}
					if(south.solid) {
						renderWalls(xBlock,xBlock+1,zBlock+1,zBlock+1,0.5);
					}
				}
			}
		}
	}
	
	public void renderWalls(double xLeft, double xRight, double zDistanceLeft,double zDistanceRight, double yHeight) {
		//calculateing the y position of the wall
		double upCorrect = 0.0625;
		double rightCorrect = 0.0625;
		double forwardCorrect = 0.0625;
		double walkCorrect = -0.0625;
		
		double xcLeft = ((xLeft / 2) - (right * rightCorrect )) * 2;
		double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;
		
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		//top left corner
		//i call up up
		double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;
		
		double xcRight = ((xRight / 2) - (right * rightCorrect)) * 2;
		double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;
		
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight)-(-up * upCorrect + (walking * walkCorrect))) *2;
		double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		
		double rotRightSideZ = zcRight * cosine + xcRight * sine;
		
		
		double tex30 = 0;
		double tex40 = 8;
		double clip = 0.5;
		//fixing clipping
		if(rotLeftSideZ < clip && rotRightSideZ <clip) {
			return;
		}
		
		if(rotLeftSideZ < clip) {
			//this is an algorithim to correct the view
			//cohen suthgerland algorithm. also line clipping is helpful to look up
			double clipAdjust = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clipAdjust;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clipAdjust;
			tex30 = tex30 + (tex40 - tex30) * clipAdjust;
			
		}
		if(rotRightSideZ < clip) {
			
			double clipAdjust = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clipAdjust;
			rotRightSideZ = rotLeftSideZ + (rotRightSideX - rotLeftSideX) * clipAdjust;
			tex40 = tex30 + (tex40 - tex30) * clipAdjust;
			
		}
		//cllipping needs to be done before this adjust as it changes what the rotleftside and right side are equal to
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
		double yPixelLeftTop =  (yCornerTL / rotLeftSideZ * height + height / 2);
		double yPixelLeftBottom =  (yCornerBL / rotLeftSideZ * height + height / 2);
		double yPixelRightTop =  (yCornerTR / rotRightSideZ * height + height / 2);
		double yPixelRightBottom =  (yCornerBR / rotRightSideZ * height + height / 2);
		
		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex30 / rotLeftSideZ;
		double tex4 = tex40 / rotRightSideZ - tex3;
		
		
		
		for(int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);
			if(zBufferWall[x] > zWall) {
				continue;
			}
			
			zBufferWall[x] = zWall;
			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);
			
			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;
			
			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);
			
			if(yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if(yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}
			
			for(int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				//can be any color
				//0x1B91E0
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);
				//that last variable is the size of the image based horizontally
				//to move over the image to the proper image in the sprite sheet need to add it to the xtexture or yTexture after the modification
				pixels[x + y * width] = Texture.floor.pixels[(xTexture & 7) + 8 + (yTexture & 7) * 16];
				
				//way to make the wall have some pixels generated
				//pixels[x + y*width] = xTexture * 100 + yTexture * 100 * 256;
				zBuffer[x + y *width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;
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
