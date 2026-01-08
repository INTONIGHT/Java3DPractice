package main.graphics;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void floor() {
		for(int y = 0; y<height;y++) {
			double yDepth = y - height/2;
			double z = 100.0 /yDepth;
			
			for(int x =0; x<width ;x++) {
				double xDepth = x - width / 2;
				xDepth *= z;
				//using a bitwise operator
				//can also use << or >> for some interesting effects
				int intValXDepth = (int) xDepth & 5;
				pixels[x + y * width] = intValXDepth * 128;
			}
		}
	}

}
