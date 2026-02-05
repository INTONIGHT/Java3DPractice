package main.input;

public class Controller {
	public double x,y, z, rotation, xa, za, rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	//public static boolean ableToRun = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runAnim = false;
	
	public void tick(boolean forward,boolean back, boolean left, boolean right,boolean jump, boolean crouch,boolean run) {
		double rotationSpeed = 0.025;
		double jumpHeight = 0.5;
		double crouchHeight = 0.3;
		//you could add a prone with around 0.8
		
		double walkSpeed = 0.5;
		double xMove = 0;
		double zMove = 0;
		
		
		if(forward) {
			zMove++;
			walk = true;
		}
		
		if(back) {
			zMove--;
			walk = true;
		}
		
		if(left) {
			xMove--;
			walk = true;
		}
		
		if(right) {
			xMove++;
			walk = true;
		}
		
		if(turnLeft) {
			rotationa -= rotationSpeed;
			walk = true;
		}
		
		if(turnRight) {
			rotationa += rotationSpeed;
			walk = true;
		}
		if(jump) {
			y += jumpHeight;
			run = false;
		}
		if(crouch) {
			y -= crouchHeight;
			run = false;
			crouchWalk = true;
			walkSpeed = 0.2;
		}
		if(run) {
			walkSpeed = 1;
			walk = true;
			runAnim = true;
		}
		
		if(!crouch) {
			crouchWalk = false;
		}
		
		if(!run) {
			runAnim = false;
		}
		
		if(!forward && !back && !left && !right && !turnLeft && !turnRight && !run) {
			walk = false;
		}
		
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
		
		x += xa;
		z += za;
		y *= 0.9;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotation *= 0.5;
	}
}
