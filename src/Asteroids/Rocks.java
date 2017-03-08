package Asteroids;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Rocks extends Sprite {

	double resultX;
	double resultY;
	double resultSize;
	int size;
	Random r = new Random();
	
	public Rocks(double x, double y) {
		super(x, y);
		
		initRock();
	}
	
	public Rocks() {
	}

	private void initRock() {
		double Low = -1;
		double High = 1;
		resultX = Low + (High - Low)*r.nextDouble(); 
		resultY = Low + (High - Low)*r.nextDouble(); 
		double lowSize = 1;
		double highSize = 3;
		resultSize = r.nextInt((int) (highSize - lowSize)) + lowSize; 
	}

	@Override
	public void move() {
		//random speed up to 1, random direction
		x += resultX;
		y += resultY;
	}
	
	public double getXSpeed() {
		return resultX;
	}
	
	public double getYSpeed() {
		return resultY;
	}
	
	public void setXSpeed(double x) {
		resultX = x;
	}
	
	public void setYSpeed(double y) {
		resultY = y;
	}


	@Override
	public void draw(Graphics2D g) {
		g.draw(new Ellipse2D.Double(x,y,resultSize*width,resultSize*width));
	}

	public double getSize() {
		return resultSize;
	}
	
	public void setSize(double d) {
		resultSize = 1;
	}

	public void reduceSize() {
		resultSize -= 1;
	}
}
