package main.graphics;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	double time = 0;
	
	public void floor() {
		for(int y = 0; y<height;y++) {
			double ceiling = (y - height/2.0) / height;
			if(ceiling < 0) {
				ceiling = -ceiling;
			}
			double z = 8 /ceiling;
			//this lets you have a change to whats being rendered but it will look wonky if
			//you increment it a lot this is machine dependent so a powerful computer will need smaller increments potentially
			time += 0.00005;
			
			
			for(int x =0; x<width ;x++) {
				double xDepth = (x - width / 2.0) / height;
				xDepth *= z;
				//using a bitwise operator
				//can also use << or >> for some interesting effects
				//subtracting time can get moroe diagonal movemnt
				int intValXDepth = (int) (xDepth);
				int intValZ = (int) (z + time);
				pixels[x + y * width] = ((intValXDepth & 15)* 16 ) | ((intValZ & 15)* 16) << 8;
			}
		}
	}

}
