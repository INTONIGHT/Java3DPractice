package main.graphics;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void floor() {
		for(int y = 0; y<height;y++) {
			double ceiling = (y - height/2.0) / height;
			double z = 8 /ceiling;
			
			for(int x =0; x<width ;x++) {
				double xDepth = (x - width / 2.0) / height;
				xDepth *= z;
				//using a bitwise operator
				//can also use << or >> for some interesting effects
				int intValXDepth = (int) xDepth & 15;
				int intValZ = (int) z & 15;
				pixels[x + y * width] = (intValXDepth * 16) | (intValZ * 16) << 8;
			}
		}
	}

}
