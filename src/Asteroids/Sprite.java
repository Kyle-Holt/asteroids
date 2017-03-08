package Asteroids;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


public abstract class Sprite {
	
	protected double x;
	protected double y;
	protected int height;
	protected int width;
	protected boolean visible;
	
	public Sprite(double x2, double y2) {
		this.x = x2;
		this.y = y2;
		this.height = 30;
		this.width = 10;
		visible = true;
	}
	
	public Sprite() {
		visible = true;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public abstract void move();
	
	public abstract void draw(Graphics2D g);
	
	Rectangle2D getRect() {
		return new Rectangle.Double(x, y, width, height);
	}
}
