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


//to export as a runnable jar
	//file->export under java runnable jar file.
	//browse display main file extract required libraries into generated jar.
//if resource is out of sync make sure to refresh on the project 
//hit finish donnt worry about warnings. then you can see the file and then run it withopen with java platform
//for the applet use jar file and have src res and then you can export as a jar file.
//you can then just create an html file and run the .class in the jar needs <applet code="main.GameApplet" archive="<jar name>"
//export runnable jar file browse for a folder. hit finish then go to that folder 
//you can use an application like launnch4j or any program to convert the jar file into a runnable .exe file
public class Display extends Canvas implements Runnable{
	
	public static int width = 800;
	public static int height = 600;
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
	public static int selection = 0;
	
	private int oldX = 0;
	
	public static int mouseSpeed;
	
	public Display() {
		Dimension size = new Dimension(WIDTH,HEIGHT);
		
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(getGameWidth(),getGameHeight());
		game = new Game();
		img = new BufferedImage(getGameWidth(),getGameHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		
		input = new InputHandler();
		//For some reason this will only focus if you click the actual panel
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
	}
	
	public synchronized void start() {
		if(running) {
			return;
		}else {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public synchronized void stop() {
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
			//should let you automatically move on screen
			requestFocus();
			
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
				//render();
				frames++;
			}
			//render();
			//frames++;
			renderMenu();
			
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
			mouseSpeed = Math.abs(updatedX - oldX);
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
		
		for(int i =0 ; i<getGameWidth() * getGameHeight(); i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = buffStrat.getDrawGraphics();
		g.drawImage(img, 0, 0, getGameWidth(), getGameHeight(), null);
		//System.out.println("gamewidth: " + getGameWidth() + "gameheight: " + getGameHeight());
		g.setFont(new Font("Verdana",0,50));
		g.setColor(Color.cyan);
		
		g.drawString(fps + "FPS",50,50);
		g.dispose();
		buffStrat.show();
	}
	
	private void renderMenu() {
		// TODO Auto-generated method stub
		BufferStrategy buffStrat = this.getBufferStrategy();
		if(buffStrat == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = buffStrat.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 400);
		g.dispose();
		buffStrat.show();
	}

	private void tick() {
		// TODO Auto-generated method stub
		game.tick(input.key);
	}
	
	public static int getGameWidth() {
		
		
		return width;
		
	}
	public static int getGameHeight() {
		
		
		return height;
	}

	//Handles the display and main loop
	public static void main(String[] args) {
		Display display = new Display();
		new Launcher(0,display);
		
		
	}

}
