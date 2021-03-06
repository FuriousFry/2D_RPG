import java.awt.Color;
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
	int currentOption = 0;
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
			g.drawImage(Sprite.getImage("game_menu"), 0, 0,
					this);
			g.setFont(new Font("Courier", Font.BOLD, 25));
			for (int i = 0; i < options.size(); i++){
				g.setColor(Color.BLACK);
				g.drawString(options.get(i), 481, 40*(i+1)+11);
				g.setColor(Color.GRAY);
				g.drawString(options.get(i), 480, 40*(i+1)+10);
			}
			g.drawImage(Sprite.getImage("menu_arrow_right"), 455, 35+(currentOption)*40, this);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (!this.player.isOccupied()){
				currentOption = 0;
				blockPlayer();
			} else if (this.active) {
				releasePlayer();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			if (this.active){
				moveSelectionDown();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			if (this.active){
				moveSelectionUp();
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
		String selectedOption = options.get(currentOption);
		if (selectedOption.equals("QUIT")){
			game.close();
		} else if (selectedOption.equals("RESUME")) {
			releasePlayer();
		} else if (selectedOption.equals("SAVE")){
			try {
				game.saveGame();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			releasePlayer();
		}
	}

	private void moveSelectionDown() {
		currentOption++;
		if (currentOption>=options.size()){
			currentOption=0;
		}
	}

	private void moveSelectionUp() {
		currentOption--;
		if (currentOption<0){
			currentOption=options.size()-1;
		}
	}

}
