package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.graphics.Render;
import main.graphics.Screen;
import java.awt.image.DataBufferInt;
import java.awt.image.RenderedImage;
import java.awt.image.DataBuffer;

public class Display extends Canvas implements Runnable{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Having Fun";
	
	private Thread thread;
	private boolean running = false;
	//private Render render;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	
	public Display() {
		screen = new Screen(WIDTH,HEIGHT);
		img = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public void start() {
		if(running) {
			return;
		}else {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void stop() {
		if(!running) {
			return;
		}else {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	//main loop
	public void run() {
		while(running) {
			tick();
			render();
		}
	}
	
	private void render() {
		// TODO Auto-generated method stub
		BufferStrategy buffStrat = this.getBufferStrategy();
		if(buffStrat == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render();
		
		for(int i =0 ; i<WIDTH*HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = buffStrat.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		buffStrat.show();
	}

	private void tick() {
		// TODO Auto-generated method stub
		
	}

	//Handles the display and main loop
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		game.start();
		
		
	}

}
