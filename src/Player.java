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
	
	private boolean occupied = false;
	private String facing = "Down";
	private String status = "Standing";

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
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {}
		if (e.getKeyCode() == KeyEvent.VK_UP) {}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveDown();
		}
	}	
	
	private void moveUp() {
		if (!isOccupied()){
			if (map.getNextTile("Up").isPassable()){
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (!isOccupied()) {
							ySpeed = 1;
							map.movePlayerUp();
							setOccupied(true);
							setStatus("running");
						} else {
							ySpeed = 0;
							setOccupied(false);
							setStatus("standing");
							timer.cancel();
							timer.purge();
						}
					}
				}, 0, 320);
			}
			setFacing("Up");
		}
	}
	
	private void moveDown() {
		if (!isOccupied()){
			if (map.getNextTile("Down").isPassable()){
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (!isOccupied()) {
							ySpeed = -1;
							map.movePlayerDown();
							setOccupied(true);
							setStatus("running");
						} else {
							ySpeed = 0;
							setOccupied(false);
							setStatus("standing");
							timer.cancel();
							timer.purge();
						}
					}
				}, 0, 320);
			}
			setFacing("Down");
		}
	}

	private void moveRight() {
		if (!isOccupied()){
			if (map.getNextTile("Right").isPassable()){
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (!isOccupied()) {
							xSpeed = -1;
							map.movePlayerRight();
							setOccupied(true);
							setStatus("running");
						} else {
							xSpeed = 0;
							setOccupied(false);
							setStatus("standing");
							timer.cancel();
							timer.purge();
						}
					}
				}, 0, 320);
			}
			setFacing("Right");
		}
	}

	private void moveLeft() {
		if (!isOccupied()){
			if (map.getNextTile("Left").isPassable()){
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (!isOccupied()) {
							xSpeed = 1;
							map.movePlayerLeft();
							setOccupied(true);
							setStatus("running");
						} else {
							xSpeed = 0;
							setOccupied(false);
							setStatus("standing");
							timer.cancel();
							timer.purge();
						}
					}
				}, 0, 320);
			}
			setFacing("Left");
		}
	}

	public void paint(Graphics2D g) {
		int offset = 0;
		g.setColor(Color.BLACK);
		if (!isOccupied()){
			if (getFacing().equals("Left")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 32, 63, 63,
						this);
			} else if (getFacing().equals("Right")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 64, 63, 95,
						this);
			} else if (getFacing().equals("Up")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 96, 63, 127,
						this);
			} else if (getFacing().equals("Down")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 0, 63, 31,
						this);
			}
		} else if (getStatus().equals("running") && facing.equals("Left")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 32, offset*32+31, 63,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("running") && facing.equals("Right")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 64, offset*32+31, 95,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("running") && facing.equals("Up")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 96, offset*32+31, 127,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("running") && facing.equals("Down")) {
			offset = animationProgress / 11;
			g.drawImage(Sprite.getImage("player"),
					320, 240, 352, 272,
					offset*32, 0, offset*32+31, 31,
					this);
			animationProgress++;
			if (animationProgress > 31) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("menu")) {
			if (getFacing().equals("Left")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 32, 63, 63,
						this);
			} else if (getFacing().equals("Right")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 64, 63, 95,
						this);
			} else if (getFacing().equals("Up")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 96, 63, 127,
						this);
			} else if (getFacing().equals("Down")){
				g.drawImage(Sprite.getImage("player"),
						320, 240, 352, 272,
						32, 0, 63, 31,
						this);
			}			
		}
	}

	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
