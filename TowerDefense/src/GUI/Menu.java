//package GUI;
//
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//
//public class Menu extends JPanel{
//
//	public Menu() {
//		this.setLayout(null);
//		JPanel MenuButtons = new JPanel(new GridLayout(4,1,0,5));
//		
//		MenuButtons.setSize(200, 300);
//		MenuButtons.setLocation(550, 125);
//		
//		JButton singlePlayer = new JButton("Single Player");
//		JButton serverPlayer = new JButton("Multiplayer (Server)");
//		JButton clientPlayer = new JButton("Multiplayer (Client)");
//		JButton help = new JButton("Help");
//		
//		singlePlayer.addActionListener(new buttonListener());
//		serverPlayer.addActionListener(new buttonListener());
//		clientPlayer.addActionListener(new buttonListener());
//		help.addActionListener(new buttonListener());
//		
//		MenuButtons.add(singlePlayer);
//		MenuButtons.add(serverPlayer);
//		MenuButtons.add(clientPlayer);
//		MenuButtons.add(help);
//	
//		this.add(MenuButtons);
//	}
//	
//	private class buttonListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
//}
