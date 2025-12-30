package main;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Having Fun";
	
	private Thread thread;
	private boolean running = false;
	
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
	
	public void run() {
		
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
		
		
	}

}
