package main.graphics;

import java.util.Random;

public class Screen extends Render{
	
	public Render test;

	public Screen(int width, int height) {
		super(width, height);
		
		Random random = new Random();
		// TODO Auto-generated constructor stub
		test = new Render(256,256);
		for(int i =0;i< 256*256;i++) {
			test.pixels[i] = random.nextInt();
		}
	}
	
	public void render() {
		//removes the animation instead of persisting the animation
		for(int i =0; i<width*height;i++) {
			pixels[i] = 0;
		}
		//this loop lets you draw it more times and gives it a nice flow
		//if you increase this loop it will start to fry your machine
		for(int i =0; i<100;i++) {
			//if you adjust the part after the % you can speed it up or slow it down bigger numbers will slow it down
			
			int anim = (int) (Math.sin((System.currentTimeMillis() + i) % 2000.0/2000* Math.PI *2) * 200);
			int anim2 = (int) (Math.cos((System.currentTimeMillis() + i) % 2000.0/2000* Math.PI *2) * 200);
			draw(test, (width - 256)/2 + anim, (height - 256)/2 + anim2);
		}
		
	} 

}
