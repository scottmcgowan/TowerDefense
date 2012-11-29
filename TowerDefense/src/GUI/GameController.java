package GUI;

import java.util.List;

import javax.swing.Timer;

import model.Game;
import model.Player;

public class GameController {

	private Player thisPlayer;
	private Player otherPlayer;
	private List orders;
	private Game game;
	private List spawnQueue;
	private Timer timer;
	
	public void update() {
		game.update();
		
	}

}
