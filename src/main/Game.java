package main;

import java.awt.event.KeyEvent;

import main.input.Controller;
import main.level.Level;

public class Game {
	public int time;
	public Controller controls;
	public Level level;
	
	public Game() {
		controls = new Controller();
		level = new Level(20,20);
	}
	
	public void tick(boolean[] key) {
		//time+= .0025;
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean run = key[KeyEvent.VK_SHIFT];
		
		
		controls.tick(forward, back, left, right,jump,crouch,run);
	}
}
