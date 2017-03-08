package Asteroids;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Ship extends Sprite implements KeyListener {

	private int x_Velocity;
	private int y_Velocity;
	public ArrayList<Missile> missileList = new ArrayList<>();
	private double rotation;
	long lastShot = System.currentTimeMillis();
	final long threshold = 250;
	boolean reset = false;
	
	public Ship(double x, double y) {
		super(x,y);
		
		initShip();
	}
	
	private void initShip() {
	}

	@Override
	public void move() {
		x += x_Velocity;
		y += y_Velocity;
	}

	@Override
	public void draw(Graphics2D g) {
		g.rotate(getRotation(),x,y -6);
		int[] xList = new int[3];
		xList[0] = (int) x-8;
		xList[1] = (int) x;
		xList[2] = (int) x+8;

		int[] yList = new int[3];
		yList[0] = (int) y;
		yList[1] = (int) y-18;
		yList[2] = (int) y;
		g.drawPolygon(xList, yList, 3);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		
		if (c == KeyEvent.VK_SPACE) {
			long now = System.currentTimeMillis();
			if (now - lastShot > threshold) {
				fire();
				lastShot = now;
			}
		}
		
		if (c == KeyEvent.VK_LEFT) {
			x_Velocity = -1;
		}
		if (c == KeyEvent.VK_RIGHT) {
			x_Velocity = 1;
		}
		if (c == KeyEvent.VK_UP) {
			y_Velocity = -1;
		}
		if (c == KeyEvent.VK_DOWN) {
			y_Velocity = 1;
		}
		if (c == KeyEvent.VK_X) {
			 setRotation((getRotation() + Math.toRadians(20)) % (Math.PI * 2));
		}
		if (c == KeyEvent.VK_Z) {
			 setRotation((getRotation() + Math.toRadians(-20)) % (Math.PI * 2));
		}
	}



	private void fire() {
		missileList.add(new Missile(x,y-6));
	}
	
	public ArrayList<Missile> getMissiles() {
		return missileList;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();

		if (c == KeyEvent.VK_LEFT) {
			x_Velocity = 0;
		}
		if (c == KeyEvent.VK_RIGHT) {
			x_Velocity = 0;
		}
		if (c == KeyEvent.VK_UP) {
			y_Velocity = 0;
		}
		if (c == KeyEvent.VK_DOWN) {
			y_Velocity = 0;
		}
		if (c == KeyEvent.VK_R) {
			reset = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setX(double d) {
		x = d;
	}

	public void setY(double d) {
		y = d;
	}
	
	public boolean returnReset() {
		return reset;
	}

	public void setReset() {
		reset = false;
	}

}
