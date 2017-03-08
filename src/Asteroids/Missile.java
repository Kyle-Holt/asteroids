package Asteroids;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Missile extends Sprite {

	double width = 5;
	double height = 1;
	double x;
	double y;
	private double rotation;
	private boolean rotated;
	
	
	public Missile(double x, double y) {
		super(x,y);
		
		this.x = x;
		this.y = y;
		rotated = false;
	}
	
	public Missile() {
		rotated = false;
	}
	
	@Override
	public void move() {
		x += 0.1*Math.cos(rotation - Math.PI/2);
		y += 0.1*Math.sin(rotation - Math.PI/2);
	}

	@Override
	public void draw(Graphics2D g) {
		g.draw(new Rectangle2D.Double(x, y, width, height));
	}
	
	public double getY() {
		return y;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
		
	}

	public void setY(double y2) {
		this.y = y2;
	}
	
	public void setRotation(double d) {
		rotation = d;
		rotated = true;
	}
	
	public boolean isRotated() {
		return rotated;
	}
	
	Rectangle2D getRect() {
		return new Rectangle.Double(x, y, width, height);
	}
}
