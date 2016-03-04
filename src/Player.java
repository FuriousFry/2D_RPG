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
	private boolean[] isKeyPressed = new boolean[4];

	public Player(Game game) {
		this.game = game;

		System.out.println(getMap());
	}

	public void move() {
		if (getMap() != null){
			this.getMap().move(xSpeed, ySpeed);
		} else {
			this.setMap(this.game.getMap());
		}
	}

	public void keyTyped(KeyEvent e) {	
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			isKeyPressed[0]=false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isKeyPressed[1]=false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			isKeyPressed[2]=false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			isKeyPressed[3]=false;
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			isKeyPressed[0]=true;
			moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isKeyPressed[1]=true;
			moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			isKeyPressed[2]=true;
			moveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			isKeyPressed[3]=true;
			moveDown();
		}
	}	
	
	private void moveUp() {
		Timer outertimer = new Timer();
		outertimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isKeyPressed[2] && !isOccupied()){
					if (getMap().getNextTile("Up").isPassable()){
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if ((!isOccupied() || isKeyPressed[2]) && 
										getMap().getNextTile("Up").isPassable()) {
									ySpeed = 1;
									getMap().movePlayerUp();
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
					} else {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if (!isOccupied() || isKeyPressed[2]) {
									setOccupied(true);
									setStatus("running");
								} else {
									setOccupied(false);
									setStatus("standing");
									timer.cancel();
									timer.purge();
								}
							}
						}, 0, 320);
					}
					setFacing("Up");
					outertimer.cancel();
					outertimer.purge();
				} else if (!isKeyPressed[2]) {
					outertimer.cancel();
					outertimer.purge();
				}
			}
		}, 0, 10);
	}
	
	private void moveDown() {
		Timer outertimer = new Timer();
		outertimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isKeyPressed[3] && !isOccupied()){
					if (getMap().getNextTile("Down").isPassable()){
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if ((!isOccupied() || isKeyPressed[3]) && 
										getMap().getNextTile("Down").isPassable()) {
									ySpeed = -1;
									getMap().movePlayerDown();
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
					} else {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if (!isOccupied() || isKeyPressed[3]) {
									setOccupied(true);
									setStatus("running");
								} else {
									setOccupied(false);
									setStatus("standing");
									timer.cancel();
									timer.purge();
								}
							}
						}, 0, 320);
					}
					setFacing("Down");
					outertimer.cancel();
					outertimer.purge();
				} else if (!isKeyPressed[3]) {
					outertimer.cancel();
					outertimer.purge();
				}
			}
		}, 0, 10);
	}

	private void moveRight() {
		Timer outertimer = new Timer();
		outertimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isKeyPressed[1] && !isOccupied()){
					if (getMap().getNextTile("Right").isPassable()){
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if ((!isOccupied() || isKeyPressed[1]) && 
										getMap().getNextTile("Right").isPassable()) {
									xSpeed = -1;
									getMap().movePlayerRight();
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
					} else {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if (!isOccupied() || isKeyPressed[1]) {
									setOccupied(true);
									setStatus("running");
								} else {
									setOccupied(false);
									setStatus("standing");
									timer.cancel();
									timer.purge();
								}
							}
						}, 0, 320);
					}
					setFacing("Right");
					outertimer.cancel();
					outertimer.purge();
				} else if (!isKeyPressed[1]) {
					outertimer.cancel();
					outertimer.purge();
				}
			}
		}, 0, 10);
	}

	private void moveLeft() {
		Timer outertimer = new Timer();
		outertimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isKeyPressed[0] && !isOccupied()){
					if (getMap().getNextTile("Left").isPassable()){
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if ((!isOccupied() || isKeyPressed[0]) && 
										getMap().getNextTile("Left").isPassable()) {
									xSpeed = 1;
									getMap().movePlayerLeft();
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
					} else {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								if (!isOccupied() || isKeyPressed[0]) {
									setOccupied(true);
									setStatus("running");
								} else {
									setOccupied(false);
									setStatus("standing");
									timer.cancel();
									timer.purge();
								}
							}
						}, 0, 320);
					}
					setFacing("Left");
					outertimer.cancel();
					outertimer.purge();
				} else if (!isKeyPressed[0]) {
					outertimer.cancel();
					outertimer.purge();
				}
			}
		}, 0, 10);
	}

	public void paint(Graphics2D g) {
		int offset = 0;
		g.setColor(Color.BLACK);
		if (!isOccupied()){
			if (getFacing().equals("Left")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 32, 63, 63,
						this);
			} else if (getFacing().equals("Right")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 64, 63, 95,
						this);
			} else if (getFacing().equals("Up")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 96, 63, 127,
						this);
			} else if (getFacing().equals("Down")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 0, 63, 31,
						this);
			}
		} else if (getStatus().equals("running") && facing.equals("Left")) {
			offset = Math.min(3,animationProgress / 11);
			g.drawImage(Sprite.getImage("player"),
					304, 224, 336, 256,
					offset*32, 32, offset*32+31, 63,
					this);
			animationProgress++;
			if (animationProgress >= 32) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("running") && facing.equals("Right")) {
			offset = Math.min(3,animationProgress / 11);
			g.drawImage(Sprite.getImage("player"),
					304, 224, 336, 256,
					offset*32, 64, offset*32+31, 95,
					this);
			animationProgress++;
			if (animationProgress >= 32) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("running") && facing.equals("Up")) {
			offset = Math.min(3,animationProgress / 11);
			g.drawImage(Sprite.getImage("player"),
					304, 224, 336, 256,
					offset*32, 96, offset*32+31, 127,
					this);
			animationProgress++;
			if (animationProgress >= 32) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("running") && facing.equals("Down")) {
			offset = Math.min(3,animationProgress / 11);
			g.drawImage(Sprite.getImage("player"),
					304, 224, 336, 256,
					offset*32, 0, offset*32+31, 31,
					this);
			animationProgress++;
			if (animationProgress >= 32) {
				animationProgress = 0;
			}
		} else if (getStatus().equals("menu")) {
			if (getFacing().equals("Left")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 32, 63, 63,
						this);
			} else if (getFacing().equals("Right")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 64, 63, 95,
						this);
			} else if (getFacing().equals("Up")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
						32, 96, 63, 127,
						this);
			} else if (getFacing().equals("Down")){
				g.drawImage(Sprite.getImage("player"),
						304, 224, 336, 256,
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

	public WorldMap getMap() {
		return map;
	}

	public void setMap(WorldMap map) {
		this.map = map;
	}

}
