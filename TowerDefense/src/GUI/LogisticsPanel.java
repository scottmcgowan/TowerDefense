package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogisticsPanel extends JPanel{

	JLabel health = new JLabel("20");
	JLabel money = new JLabel("600");
	public LogisticsPanel() {
		setPreferredSize(new Dimension(75,75));
		setLayout(new GridLayout(2, 2));
		
		JPanel healthImage = new paintImage("heart");
		healthImage.setSize(30,30);
		add(healthImage);
		add(health);
		
		JPanel moneyImage = new paintImage("munny");
		add(moneyImage);
		add(money);
	}
	
	private class paintImage extends JPanel{
		
		String image;
		public paintImage(String image) {
			this.image = image;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D gr = (Graphics2D) g;
			
			if (image.equals("heart")) {
				try {
					gr.drawImage(ImageIO.read(new File("images/Heart.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find grass.png");
				}
			}
			else
				try {
					gr.drawImage(ImageIO.read(new File("images/Coin.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find grass.png");
				}
		}
	}
	
	public void updateHealth(int HP) {
		health.setText(HP + "");
	}
	
	public void updateMoney(int munny) {
		money.setText(munny + "");
	}
}
