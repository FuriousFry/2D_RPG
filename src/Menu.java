import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JPanel{
	Game game;
	Player player;
	boolean active = false;
	ArrayList<String> options = new ArrayList<String>(); 
	String previousStatus = "";

	public Menu(Game game, Player player) {
		this.game = game;
		this.player = player;
		try {
			BufferedReader in = new BufferedReader(new FileReader("dialogue/menu.txt"));
			String line;
			while((line = in.readLine()) != null)
			{
			    options.add(line);
			}
			in.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void move() {
		
	}

	public void paint(Graphics2D g) {
		if (this.active){
			g.drawImage(Sprite.getImage("dialogue"), 120, 300,
					this);
			g.setFont(new Font("Courier", Font.PLAIN, 36));
			for (int i = 0; i < options.size(); i++){
				g.drawString(options.get(i), 160, 300+40*i);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (!this.player.isOccupied()){
				blockPlayer();
			} else if (this.active) {
				releasePlayer();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			if (this.active){
				moveSelectionUp();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			if (this.active){
				moveSelectionDown();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			if (this.active){
				selectOption();
			}
		}
	}

	private void releasePlayer() {
		System.out.println("closed menu");
		this.player.setStatus(this.previousStatus);
		this.active = false;
		this.player.setOccupied(false);	
	}

	private void blockPlayer() {
		System.out.println("opened menu");
		this.previousStatus = this.player.getStatus();
		this.active = true;
		this.player.setOccupied(true);
		this.player.setStatus("menu");		
	}

	private void selectOption() {
		// TODO Auto-generated method stub
		
	}

	private void moveSelectionDown() {
		// TODO Auto-generated method stub
		
	}

	private void moveSelectionUp() {
		// TODO Auto-generated method stub
		
	}

}
