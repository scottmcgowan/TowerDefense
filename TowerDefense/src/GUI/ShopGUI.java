package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ShopGUI extends JPanel{
	GameCanvas canvas;
	
	public ShopGUI() {
		setLayout(new GridLayout(1, 5, 5, 5));
		setSize(200, 50);
		JButton one = new JButton("1");
		JButton two = new JButton("2");
		JButton three = new JButton("3");
		JButton four = new JButton("4");
		
		one.addActionListener(new shopListener());
		two.addActionListener(new shopListener());
		three.addActionListener(new shopListener());
		four.addActionListener(new shopListener());
		
		add(one);
		add(two);
		add(three);
		add(four);
	}
	
	public void connectToMap(GameCanvas game) {
		this.canvas = game;
	}
	
	private class shopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			JButton button = (JButton) arg0.getSource();
			int i = Integer.parseInt(button.getText());
			canvas.setSelected(i);

		}
	}
}
