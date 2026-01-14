package main.graphics;
import main.Game;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void floor(Game game) {
		double rotation = game.time / 100.0;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);
		//allows us to manipulate the floor and ceiling seperately
		double floorPosition = 8;
		double ceilingPosition = 8;
		double forward = game.time / 5.0;
		double rightMovement = game.time / 5.0;
		
		for(int y = 0; y<height;y++) {
			double ceiling = (y - height/2.0) / height;
			
			double z = floorPosition /ceiling;
			if(ceiling < 0) {
				z = ceilingPosition / -ceiling;
			}
			
			//this lets you have a change to whats being rendered but it will look wonky if
			//you increment it a lot this is machine dependent so a powerful computer will need smaller increments potentially
		
			
			
			for(int x =0; x<width; x++) {
				double xDepth = (x - width / 2.0) / height;
				xDepth *= z;
				//using a bitwise operator
				//can also use << or >> for some interesting effects
				//subtracting time can get moroe diagonal movemnt
				int intValXDepth = (int) (xDepth * cosine + z * sine + rightMovement);
				int intValZ = (int) (z * cosine - xDepth * sine + forward);
				pixels[x + y * width] = ((intValXDepth & 15)* 16 ) | ((intValZ & 15)* 16) << 8;
			}
		}
	}

}
