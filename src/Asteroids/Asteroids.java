package Asteroids;

import java.awt.EventQueue;

import javax.swing.JFrame;


public class Asteroids extends JFrame {

	Board b = new Board();
	
	public Asteroids(){
		initUI();
	}
	private void initUI() {
		add(b);
		setTitle("Asteroids");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(b.width, b.height);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
            public void run() {                
                Asteroids game = new Asteroids();
                game.setVisible(true);                
            }
		});
	}
}
