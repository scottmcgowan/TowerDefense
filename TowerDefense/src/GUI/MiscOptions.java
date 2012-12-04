package GUI;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MiscOptions extends JFrame{
	
	public static void main(String[] args) {
		setServerMessage();
		setHelpMessage();
		setWinMessage();
		setLoseMessage();
	}
	
	public static void setServerMessage() {
		JOptionPane server = new JOptionPane();
		server.showMessageDialog(null, "The server is now up!");
	}
	
	public static void setHelpMessage() {
		JOptionPane help = new JOptionPane();
		help.showMessageDialog(null, "How to Play\n\n" + "You will have 100 geaktg.\n" +
				"You will lose 5 life whenever an enemy finishes traversing the path.\n" 
		+ "Defend against the enemies by building towers.\n" +
		"To purchase a tower:\t Click a grass tile and then press a buy tower button.\n\n" +
		"Multiplayer\n" +
		"Play against another player by building towers as well as spawning enemies\n" +
		"A player will lose when they have no more lives.\n" +
		"A tie will result when both players have no money left and when there are no more enemies.");
				
	}
	
	public static void setWinMessage() {
		JOptionPane win = new JOptionPane();
		win.showMessageDialog(null, "Congratulations! You won!");
	}

	public static void setTieMessage() {
		JOptionPane win = new JOptionPane();
		win.showMessageDialog(null, "It's a tie!");
	}
	
	public static void setLoseMessage() {
		JOptionPane win = new JOptionPane();
		win.showMessageDialog(null, "Too bad, you lost!");
	}
}
