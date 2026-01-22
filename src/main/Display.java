package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.graphics.Render;
import main.graphics.Screen;
import main.input.Controller;
import main.input.InputHandler;

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
	private Game game;
	private InputHandler input;
	private int updatedX = 0;
	private int updatedY = 0;
	private int fps;
	
	private int oldX = 0;
	
	public Display() {
		Dimension size = new Dimension(WIDTH,HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(WIDTH,HEIGHT);
		game = new Game();
		img = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		
		input = new InputHandler();
		//For some reason this will only focus if you click the actual panel
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
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
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		while(running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			
			while(unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount ++;
				if(tickCount % 60 == 0) {
					//if you want to display fps
					//System.out.println(frames + " fps");
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
			
			
			updatedX = InputHandler.mouseX;
			updatedY = InputHandler.mouseY;
			
			if(updatedX > oldX) {
				Controller.turnRight = true;
			}
			if(updatedX < oldX) {
				Controller.turnLeft = true;
			}
			if(updatedX == oldX) {
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}
			oldX = updatedX;
		}
	}
	
	private void render() {
		// TODO Auto-generated method stub
		BufferStrategy buffStrat = this.getBufferStrategy();
		if(buffStrat == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(game);
		
		for(int i =0 ; i<WIDTH*HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = buffStrat.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Verdana",0,50));
		g.setColor(Color.cyan);
		
		g.drawString(fps + "FPS",50,50);
		g.dispose();
		buffStrat.show();
	}

	private void tick() {
		// TODO Auto-generated method stub
		game.tick(input.key);
	}

	//Handles the display and main loop
	public static void main(String[] args) {
		BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "Blank");
		// TODO Auto-generated method stub
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.getContentPane().setCursor(blank);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle(TITLE);
		//frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		game.start();
		
		
	}

}
