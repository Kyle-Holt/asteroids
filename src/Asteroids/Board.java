package Asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, KeyListener {

	private double yStart = 200;
	private double xStart = 300;
	Ship ship = new Ship(xStart, yStart);
	Timer tm = new Timer(5, this);
	Rocks rockList[];
	int NUM_OF_ROCKS = 10;
	int width = 1000;
	int height = 800;
	public ArrayList<Missile> missileList = new ArrayList<>();
	Random r = new Random();
	private Rocks[] newRockList = new Rocks[NUM_OF_ROCKS];
	int score = 0;
	private int lives = 3;
	boolean gameOver = false;
	
	public Board() {
		initBoard();
	}
	
	public void initBoard() {
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setBackground(Color.BLACK);
		
		rockList = new Rocks[NUM_OF_ROCKS];
		for(int k = 0; k < NUM_OF_ROCKS; k++) {
			int x = randomInt(width, 0);
			int y = randomInt(height, 0);
			int directionChoice = randomInt(2, 0);
			int otherChoice = 1 - directionChoice;
			rockList[k] = new Rocks(x*directionChoice,y*otherChoice);
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw2DShapes(g);
		g.drawString("Score", 10,10);
		g.drawString(Integer.toString(score), 10,20);
		
		g.drawString("Lives", 60,10);
		g.drawString(Integer.toString(lives), 60,20);
		
		if(lives <= 0) {
			tm.stop();
			gameOver = true;
			g.drawString("Push R to continue", width/2-50, height/2);
			if(ship.returnReset() == true) {
				ship.setReset();
				reset();
			}
		}
		
		repaint();
	}
	
	private void reset() {
		missileList.clear();
		rockList = new Rocks[NUM_OF_ROCKS];
		for(int k = 0; k < NUM_OF_ROCKS; k++) {
			int x = randomInt(width, 0);
			int y = randomInt(height, 0);
			int directionChoice = randomInt(2, 0);
			int otherChoice = 1 - directionChoice;
			rockList[k] = new Rocks(x*directionChoice,y*otherChoice);
		}
		ship.setX(xStart );
		ship.setY(yStart);
		score = 0;
		lives = 3;
		gameOver = false;
		tm.start();
	}

	private void draw2DShapes(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		AffineTransform oldXForm = gg.getTransform();
		  /* Enable anti-aliasing and pure stroke */
	    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	    
	    if (lives > 0) {
	    	ship.draw(gg);
	    }
	    
	    gg.setTransform(oldXForm);

	    for (int i = 0; i < missileList.size(); i++) {
	    	if(missileList.get(i).isRotated() == false){
	    		missileList.get(i).setRotation(ship.getRotation());
	    	}	 
	    	if(missileList.get(i).getVisible() == true) {
	    		missileList.get(i).draw(gg);
		    	missileList.get(i).move();
	    	}	
	    }
	    
	    for (int i = 0; i < NUM_OF_ROCKS; i++){
	    	if(rockList[i].getVisible() == true) {
	    		rockList[i].draw(gg);
	    	}
	    	
	    	if(newRockList[i] != null && newRockList[i].getVisible() == true) {
	    		newRockList[i].draw(gg);
	    	}
	    	
	    	if(rockList[i].getVisible() == false) {
	    		int x = randomInt(width, 0);
				int y = randomInt(height, 0);
				int directionChoice = randomInt(2, 0);
				int otherChoice = 1 - directionChoice;
	    		rockList[i] = new Rocks(x*directionChoice,y*otherChoice);
	    	}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ship.move();
		missileList = ship.getMissiles();
		checkCollision();
		checkOutofBounds();
		for (int i = 0; i < NUM_OF_ROCKS; i++){
			rockList[i].move();
			if(newRockList.length > 0) {
				if(newRockList[i] != null) {
					newRockList[i].move();
					if(newRockList[i].getX() < 0 || newRockList[i].getX() > width || newRockList[i].getY() < 0 || newRockList[i].getY() > height) {
						newRockList[i].setVisible(false);
					}
				}
			}
			if(rockList[i].getX() < 0 || rockList[i].getX() > width || rockList[i].getY() < 0 || rockList[i].getY() > height) {
				rockList[i].setVisible(false);
			}
		}
	}

	private void checkCollision() {
		for (int i = 0; i < rockList.length; i++) {
			if(ship.getRect().intersects(rockList[i].getRect())){
				rockList[i].setVisible(false);
				ship.setVisible(false);
				lives  -= 1;
				ship.setX(xStart);
				ship.setY(yStart);
				ship.setRotation(0);
			}
		}
		
		for (int j = 0; j < missileList.size(); j++) {
			for (int i = 0; i < rockList.length; i++) {
				if(rockList[i].getRect().intersects(missileList.get(j).getRect())) {
					score += 10;
					if(rockList[i].getSize() > 1) {
						rockList[i].reduceSize();
						
						Rocks newRock = new Rocks(rockList[i].getX(),rockList[i].getY());
						newRock.setSize(1);
						newRock.setXSpeed(-rockList[i].getXSpeed());
						newRock.setYSpeed(-rockList[i].getYSpeed());
						newRockList[i] = newRock;
					}
					else {
						rockList[i].setVisible(false);

					}
					missileList.get(j).setVisible(false);
					missileList.remove(j);
					break;
				}
				if(newRockList.length > 0){
					if(newRockList[i] != null) {
						if(newRockList[i].getRect().intersects(missileList.get(j).getRect())) {
							score += 10;
							newRockList[i].setVisible(false);
							missileList.get(j).setVisible(false);
							missileList.remove(j);
							break;
						}
					}
				}
			}
		}
	}

	public int randomInt(double High, double Low) {
		double Result = r.nextInt((int) (High-Low)) + Low;
		return (int) Result;
	}
	
	private void checkOutofBounds() {
		 for (int i = 0; i < missileList.size(); i++) {
			 if(missileList.get(i).getX() > width || missileList.get(i).getX() < 0 || missileList.get(i).getY() > height || missileList.get(i).getY() < 0) {
				 missileList.remove(i);
			 }
		 }
		 if (ship.getX() < -10) {
			 ship.setX(width + 10);
		 }
		 else if (ship.getX() > width+20) {
			 ship.setX(0);
		 }
		 else if (ship.getY() < -10) {
			 ship.setY(height + 10);
		 }
		 else if (ship.getY() > height + 20) {
			 ship.setY(0);
		 }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ship.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ship.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
}
