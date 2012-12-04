package GUI;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MiscOptions extends JFrame{
	/*
	public static void main(String[] args) {
		setServerMessage();
		setHelpMessage();
		setWinMessage();
		setLoseMessage();
	}*/
	
	public void setServerMessage(Component c) {
		JOptionPane server = new JOptionPane();
		server.showMessageDialog(c, "The server is now up!");
	}
	
	public void setHelpMessage(Component c) {
		JOptionPane help = new JOptionPane();
		help.showMessageDialog(c, "How to Play\n\n" + "You will have 50 health.\n" +
				"You will lose 10 life whenever an enemy finishes traversing the path.\n" 
		+ "Defend against the enemies by building towers.\n" +
		"To purchase a tower:\t Click a grass tile and then press a buy tower button.\n\n" +
		"Multiplayer\n" +
		"Play against another player by building towers as well as spawning enemies\n" +
		"A player will lose when they have no more lives.\n" +
		"A tie will result when both players have no money left and when there are no more enemies.\n" +
		"Use space bar to toggle speed of gameplay, and use p to pause the game");
				
	}
	
	public void setWinMessage(Component c) {
		JOptionPane win = new JOptionPane();
		win.showMessageDialog(c, "Congratulations! You won!");
	}

	public void setTieMessage(Component c) {
		JOptionPane tie = new JOptionPane();
		tie.showMessageDialog(c, "It's a tie!");
	}
	
	public static void setLoseMessage(Component c) {
		JOptionPane lose = new JOptionPane();
		lose.showMessageDialog(c, "Too bad, you lost!");
	}
}
