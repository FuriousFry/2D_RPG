import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	/*
	 * map has to be generated first, or else the player can't move.
	 */
	private WorldMap map;
	private Player player = new Player(this);
	private Menu menu = new Menu(this, player);
	private MainMenu mainMenu = new MainMenu(this, player);
	static JFrame frame = new JFrame("2D_RPG");
	private boolean isMapLoaded = false;

	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				player.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
				menu.keyPressed(e);
				mainMenu.keyPressed(e);
			}
		});
		setFocusable(true);
	}

	private void move() {
			player.move();
			menu.move();
			mainMenu.move();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (isMapLoaded) {
			this.map.paint(g2d);
			this.player.paint(g2d);
			this.menu.paint(g2d);
		}
		this.mainMenu.paint(g2d);
		
		g2d.setColor(Color.GRAY);
	}

	public static void main(String[] args) throws InterruptedException {
		Game game = new Game();
		frame.add(game);
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				game.move();
				game.repaint();
			}
		}, 0, 10);
	}

	public WorldMap getMap() {
		return this.map;
	}

	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void saveGame() throws IOException {
		SaveGame.saveGame(this.player, this.map);	
	}
	
	/* loads the values stored in the saveGame. 
	0: Player's X Position
	1: Player's Y Position
	2: Map Name
	*/
	public void loadSave() {
		ArrayList<String> saveValues = SaveGame.loadGame();
		this.map = new WorldMap(this, saveValues.get(2));
		this.map.moveMapToUnderPlayer(new int[]{Integer.valueOf(saveValues.get(0)), Integer.valueOf(saveValues.get(1))}, "");
		isMapLoaded = true;
	}

	public void switchMap(String[] destination) {
		this.map = new WorldMap(this, WorldMap.calculateSpawn(destination), destination[0], destination[1]);
		player.setMap(this.map);
	}
}