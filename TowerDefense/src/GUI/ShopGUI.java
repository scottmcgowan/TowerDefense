package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.MultiPlayerShop;

public class ShopGUI extends JPanel{
	GameCanvas canvas;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	// GUI for the shop buttons
	public ShopGUI() {
		setLayout(new GridLayout(3, 9, 5, 5));
		setSize(600, 100);
		
		AllButtonListener listenerToAllButtons = new AllButtonListener();

		for (MultiPlayerShop.Item i : MultiPlayerShop.Item.values()) {
			JButton b = new JButton(i.name() + "  " + "(" + i.value + ")");
			buttons.add(b);
			b.addActionListener(listenerToAllButtons);
			add(b);
		}
		repaint();
	}

	public void connectToMap(GameCanvas game) {
		this.canvas = game;
	}
	
	private class AllButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent theEvent) {
			// Determine which of the five buttons was clicked
			JButton clickButton = (JButton) theEvent.getSource();
			String text = clickButton.getText().substring(0, clickButton.getText().length()-7);
			// game.sendMessage(text);
			canvas.setSelected(text);
			
			if(canvas.getClicked() != null)
				canvas.purchase();
		}
	}
}