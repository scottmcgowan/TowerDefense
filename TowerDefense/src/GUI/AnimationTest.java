package GUI;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimationTest extends JFrame {
	
	public static void main(String[] args) {
		new AnimationTest().setVisible(true);
	}
	
	public AnimationTest() {
		setTitle("Animation Test");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new Movement();
		add(panel);
	}
	
	private class Movement extends JPanel {
		private BufferedImage character;
		private static final int CHAR_WIDTH = 30, CHAR_HEIGHT = 47;
		private Cursor cursor;
		private Timer timer; 		
		
		public Movement() {
			try {
				character = ImageIO.read(new File("images/SpriteSheet.png"));
			} catch (IOException e) {
				System.out.println("Could not find SpriteSheet.png");
			}
			
			try {
				Toolkit tool = Toolkit.getDefaultToolkit();
				Image arrow = ImageIO.read(new File("images/cursor.png"));
				cursor = tool.createCustomCursor(arrow, new Point(20, 20), "Cursor");
				setCursor(cursor);
			} catch (IOException e) {
				System.out.println("Could not find cursor.png");
			}
			
			addMouseListener(new ClickListener());
			timer = new Timer(30, new TimerListener());
			timer.start();
		}
		
		int y = 10;
		
		private class ClickListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicking does nothing.");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Keep the cursor inside the window.");
				timer.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Please move the cursor back into the window.");
				timer.stop();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
		}
		
		private class TimerListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (y == 100) 
					timer.stop();
				else {
					y++;
					repaint();
				}	
			}
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			if (y % 30 >= 10 && y % 30 <= 14)
				g2.drawImage(character.getSubimage(0, 0, CHAR_WIDTH, CHAR_HEIGHT), 10, y, this);
			else if ((y % 30 >= 5 && y % 30 <= 9) || (y % 30 >= 15 && y % 30 <= 19))
				g2.drawImage(character.getSubimage(CHAR_WIDTH, 0, CHAR_WIDTH, CHAR_HEIGHT), 10, y, this);
			else if ((y % 30 >= 0 && y % 30 <= 4) || (y % 30 >= 20 && y % 30 <= 24))
				g2.drawImage(character.getSubimage(2 * CHAR_WIDTH, 0, CHAR_WIDTH, CHAR_HEIGHT), 10, y, this);
			else if (y % 30 >= 25 && y % 30 <= 29)
				g2.drawImage(character.getSubimage(3 * CHAR_WIDTH, 0, CHAR_WIDTH, CHAR_HEIGHT), 10, y, this);
		}
	}
	
}
