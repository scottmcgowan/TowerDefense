package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class GameGUI extends JFrame{
	
	private JTextField chatBar = new JTextField("And I say hey!");
	public JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private String alias;
	private String command;
		
	public GameGUI(){
		setTitle("Game");
		setSize(350, 500);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chatBar.setSize(310, 20);
		chatBar.setLocation(13, 10);
		chatBar.setEditable(true);
		add(chatBar);

		textArea.setLocation(13, 40);
		textArea.setSize(310, 410);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		scrollPane.setSize(310, 410);
		scrollPane.setLocation(13, 40);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		chatBar.addActionListener(new ChatAction());
		repaint();
	}

	private class ChatAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		}
	}
}