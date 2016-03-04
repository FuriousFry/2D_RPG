import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import java.awt.Color;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings({ "unused", "serial" })
public class WorldMap extends JPanel{

	private int x = 0;
	private int y = 0;
	private Game game;
	private ArrayList<ArrayList<Tile>> mapItems = new ArrayList<ArrayList<Tile>>();
	private boolean mapLoaded = false;
	private int[] playerPos = new int[2];
	private String name ="";

	public WorldMap(Game game, String string) {
		this.game = game;
		this.name = string;
		loadmap(string);
	}

	public WorldMap(Game game, int[] calculateSpawn, String string) {
		this.game = game;
		this.name = string;
		loadmap(string);
		this.playerPos = calculateSpawn;
		setPlayerSpawnPoint(this.playerPos);
	}

	public void setPlayerSpawnPoint(int[] playerPositionInput) {
		// Calculates the Player's position from the tile that has the attribute "spawn"		
		// Offsets the map so that the player spawns where the spawnpoint is
		System.out.println("Map thinks Position is: " + playerPositionInput[0] + ", " + playerPositionInput[1]);
		this.x -= playerPositionInput[0]*32;
		this.playerPos[0] = playerPositionInput[0];
		this.x += 304;
		this.y -= playerPositionInput[1]*32;
		this.playerPos[1] = playerPositionInput[1];
		this.y += 230;
	}

	public Tile getNextTile(String direction) {
		//System.out.print("next in direction " + direction + ": ");
		switch (direction){
		case "Down":
			return getTile(getPlayerPos()[0], getPlayerPos()[1]+1);
		case "Up":
			return getTile(getPlayerPos()[0], getPlayerPos()[1]-1);
		case "Left":
			return getTile(getPlayerPos()[0]-1, getPlayerPos()[1]);
		case "Right":
			return getTile(getPlayerPos()[0]+1, getPlayerPos()[1]);
		}
		return null;
	}

	private Tile getCurrentTile() {
		System.out.println(getPlayerPos()[0] + ", " + getPlayerPos()[1]);
		return getTile(getPlayerPos()[0], getPlayerPos()[1]);
	}

	private Tile getTile(int j, int i) {
		if (i < mapItems.size() && i >= 0){
			if (j <mapItems.get(i).size() && j >=0){
				return mapItems.get(i).get(j);
			}
		}
		return null;
	}

	private void loadmap(String mapName) {
		JSONParser parser = new JSONParser();

		try {        	
			Object obj = parser.parse(new FileReader(
					"maps/"+mapName+".txt"));

			JSONObject jsonObject = (JSONObject) obj;

			String name = (String) jsonObject.get("Name");
			String author = (String) jsonObject.get("Author");
			JSONArray jsonMapItemRow = (JSONArray) jsonObject.get("Map Item Rows");

			System.out.println("loading map: " + name);
			System.out.println("written by: " + author);
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = jsonMapItemRow.iterator();
			JSONObject currentRow;            
			while (iterator.hasNext()) {
				currentRow = iterator.next();
				JSONArray jsonMapItems = (JSONArray) currentRow.get("Row Items");
				@SuppressWarnings("unchecked")
				Iterator<String> stringIterator = jsonMapItems.iterator();
				ArrayList <Tile> itemsInRow = new ArrayList<Tile>();
				while (stringIterator.hasNext()){
					String currentItem = stringIterator.next();
					itemsInRow.add(new Tile(currentItem));
				}
				this.mapItems.add(itemsInRow);
			}
			this.mapLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void move(int xSpeed, int ySpeed) {
		x += xSpeed;
		y += ySpeed;				
	}

	public void paint(Graphics2D g) {
		if (this.mapLoaded){
			for (int i = 0; i < this.mapItems.size(); i++) {
				for (int j = 0; j < this.mapItems.get(i).size(); j++){
					switch (mapItems.get(i).get(j).getID()){
					case 0:
						g.drawImage(Sprite.getImage("grass"), (x+j*32), (y+i*32), this);
						break;
					case 1:
						g.drawImage(Sprite.getImage("rocks"), (x+j*32), (y+i*32), this);
						break;
					case 2:
						g.drawImage(Sprite.getImage("door_on_rocks"), (x+j*32), (y+i*32), this);
						break;
					}
				}
			}
		}
	}

	public void movePlayerDown() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Down").setPlayerPos(true);
		getPlayerPos()[1]+=1;
		Sound.playSound("walk");
		checkForEffect();
	}

	public void movePlayerUp() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Up").setPlayerPos(true);
		getPlayerPos()[1]-=1;
		Sound.playSound("walk");
		checkForEffect();
	}

	public void movePlayerRight() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Right").setPlayerPos(true);
		getPlayerPos()[0]+=1;
		Sound.playSound("walk");
		checkForEffect();
	}

	public void movePlayerLeft() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Left").setPlayerPos(true);
		getPlayerPos()[0]-=1;
		Sound.playSound("walk");
		checkForEffect();
	}

	private void checkForEffect() {
		if (getCurrentTile().getID()== 2) {
			game.switchMap(getCurrentTile().getDestination());
		}
		
	}

	public int[] getPlayerPos() {
		return this.playerPos;
	}

	public void setPlayerPosX(int playerPosX) {
		this.playerPos[0] = playerPosX;
	}
	
	public void setPlayerPosY(int playerPosY) {
		this.playerPos[1] = playerPosY;
	}
	
	public String getName() {
		return this.name;
	}

	public static int[] calculateSpawn(String[] destination) {
		switch (destination[1]){
		case "testmap":
			switch (destination[2]){
			case "north":
				return new int[]{7,2};
			}
			break;
		case "testmap2":
			switch (destination[2]){
			case "south":
				return new int[]{4,7};
			}
			break;
		}
		return new int[]{0,0};
	}
}