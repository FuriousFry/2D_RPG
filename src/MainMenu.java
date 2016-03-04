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
public class MainMenu extends JPanel{
	Game game;
	boolean active = true;
	ArrayList<String> options = new ArrayList<String>(); 
	int currentOption = 0;
	private Player player;

	public MainMenu(Game game, Player player) {
		this.player = player;
		this.game = game;
		this.player.setOccupied(true);
		try {
			BufferedReader in = new BufferedReader(new FileReader("dialogue/main_menu.txt"));
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

	private void selectOption() {
		String selectedOption = options.get(currentOption);
		if (selectedOption.equals("QUIT")){
			game.close();
		} else if (selectedOption.equals("NEW")) {
			try {
				SaveGame.saveGame("default");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.loadSave();
			this.active = false;
			player.setOccupied(false);
		} else if (selectedOption.equals("LOAD")){
			game.loadSave();
			this.active = false;
			player.setOccupied(false);
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
