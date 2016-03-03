import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Player extends JPanel{

	private int xSpeed = 0;
	private int ySpeed = 0;
	private Game game;
	private WorldMap map;
	int animationProgress = 0;

	private boolean[] keyStillPressed = new boolean[4];
	private String moving = "";
	private String facing = "Down";

	public Player(Game game) {
		this.game = game;
		this.map = this.game.getMap();

		System.out.println(map);
	}

	public void move() {
		if (map != null){
			this.map.move(xSpeed, ySpeed);
		}
	}

	public void keyTyped(KeyEvent e) {	
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			keyStillPressed[0] = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			keyStillPressed[1] = false;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			keyStillPressed[2] = false;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			keyStillPressed[3] = false;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyStillPressed[0] = true;
			moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyStillPressed[1] = true;
			moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyStillPressed[2] = true;
			moveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyStillPressed[3] = true;
			moveDown();	
		}
	}

	private void moveDown() {
		if (map.getNextTile("Down").isPassable()){
			if (moving.equals("")){
				moving = "Down";
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (keyStillPressed[3] == false) {
							ySpeed = 0;
							moving = "";
							timer.cancel();
							timer.purge();
						} else {
							if (map.getNextTile("Down").isPassable()){
								ySpeed = -1;
								map.movePlayerDown();
							} else {
								ySpeed = 0;
								moving = "";
								timer.cancel();
								timer.purge();
							}
						}
					}
				}, 0, 320);
			}
		}
		facing = "Down";
	}

	private void moveUp() {
		if (map.getNextTile("Up").isPassable()){
			if (moving.equals("")){
				moving = "Up";
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (keyStillPressed[2] == false) {
							ySpeed = 0;
							moving = "";
							timer.cancel();
							timer.purge();
						} else {
							if (map.getNextTile("Up").isPassable()){
								ySpeed = 1;
								map.movePlayerUp();
							} else {
								ySpeed = 0;
								moving = "";
								timer.cancel();
								timer.purge();
							}
						}
					}
				}, 0, 320);
			}
		}
		facing = "Up";
	}

	private void moveRight() {
		if (map.getNextTile("Right").isPassable()){
			if (moving.equals("")){
				moving = "Right";
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (keyStillPressed[1] == false) {
							xSpeed = 0;
							moving = "";
							timer.cancel();
							timer.purge();
						} else {
							if (map.getNextTile("Right").isPassable()){
								xSpeed = -1;
								map.movePlayerRight();
							} else {
								xSpeed = 0;
								moving = "";
								timer.cancel();
								timer.purge();
							}
						}
					}
				}, 0, 320);
			}
		}
		facing = "Right";
	}

	private void moveLeft() {
		if (map.getNextTile("Left").isPassable()){
			if (moving.equals("")){
				moving = "Left";
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (keyStillPressed[0] == false) {
							xSpeed = 0;
							moving = "";
							timer.cancel();
							timer.purge();
						} else {
							if (map.getNextTile("Left").isPassable()){
								xSpeed = 1;
								map.movePlayerLeft();
							} else {
								xSpeed = 0;
								moving = "";
								timer.cancel();
								timer.purge();
							}
						}
					}
				}, 0, 320);
			}
		}
		facing = "Left";
	}

	public void paint(Graphics2D g) {
		int offset = 0;
		g.setColor(Color.BLACK);
		if (moving.equals("")){
			if (facing.equals("Left")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 32, 63, 63,
						this);
			} else if (facing.equals("Right")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 64, 63, 95,
						this);
			} else if (facing.equals("Up")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 96, 63, 127,
						this);
			} else if (facing.equals("Down")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 0, 63, 31,
						this);
			}
		} else if (moving.equals("Left")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 32, offset*32+31, 63,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (moving.equals("Right")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 64, offset*32+31, 95,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (moving.equals("Up")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 96, offset*32+31, 127,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (moving.equals("Down")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 0, offset*32+31, 31,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		}
	}

}
