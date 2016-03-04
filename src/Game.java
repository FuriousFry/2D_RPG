import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	/*
	 * map has to be generated first, or else the player can't move.
	 */
	private WorldMap map = new WorldMap(this, "testmap");
	private Player player = new Player(this);
	private String gameState = "Run";
	private Menu menu = new Menu(this, player);

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
			}
		});
		setFocusable(true);
	}

	private void move() {
		if (gameState.equals("Run")){
			player.move();
		} else if (gameState.equals("Dialogue")){
			menu.move();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		map.paint(g2d);
		player.paint(g2d);
		menu.paint(g2d);

		g2d.setColor(Color.GRAY);
	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("2D_RPG");
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
}