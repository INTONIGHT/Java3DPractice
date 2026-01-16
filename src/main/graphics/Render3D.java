package main.graphics;
import main.Game;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
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
		
		for(int y = 0; y<height;y++) {
			double ceiling = (y - height/2.0) / height;
			
			double z = floorPosition /ceiling;
			if(ceiling < 0) {
				z = ceilingPosition / -ceiling;
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
				pixels[x + y * width] = ((xPix & 15)* 16 ) | ((yPix & 15)* 16) << 8;
			}
		}
	}

}
