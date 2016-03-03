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

	public WorldMap(Game game) {
		this.game = game;
		loadmap();
		setPlayerSpawnPoint();
	}
	
	private void setPlayerSpawnPoint() {
		// Calculates the Player's position from the tile that has the attribute "spawn"
		for (int y = 0; y<mapItems.size(); y++){
			ArrayList<Tile> tileRow = mapItems.get(y);
			for (int x = 0; x<tileRow.size(); x++){
				if (tileRow.get(x).isSpawnPoint()) {
					playerPos[0]=x;
					playerPos[1]=y;
					System.out.println(playerPos[0] + ", " + playerPos[1]);
				}
			}
		}
		// Offsets the map so that the player spawns where the spawnpoint is
		this.x -= playerPos[0]*32;
		this.x += 320;
		this.y -= playerPos[1]*32;
		this.y += 240;
	}

	public Tile getNextTile(String direction) {
		//System.out.print("next in direction " + direction + ": ");
		switch (direction){
		case "Down":
			return getTile(playerPos[0], playerPos[1]+1);
		case "Up":
			return getTile(playerPos[0], playerPos[1]-1);
		case "Left":
			return getTile(playerPos[0]-1, playerPos[1]);
		case "Right":
			return getTile(playerPos[0]+1, playerPos[1]);
		}
		return null;
	}
	
	private Tile getCurrentTile() {
		System.out.print("current position: ");
		System.out.println(playerPos[0] + ", " + playerPos[1]);
		return getTile(playerPos[0], playerPos[1]);
	}

	private Tile getTile(int j, int i) {
		if (i < mapItems.size() && i >= 0){
			if (j <mapItems.get(i).size() && j >=0){
				return mapItems.get(i).get(j);
			}
		}
		return null;
	}

	private void loadmap() {
		JSONParser parser = new JSONParser();
		 
        try {        	
            Object obj = parser.parse(new FileReader(
                    "maps/testmap.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            String name = (String) jsonObject.get("Name");
            String author = (String) jsonObject.get("Author");
            JSONArray jsonMapItemRow = (JSONArray) jsonObject.get("Map Item Rows");
 
            System.out.println("loading map: " + name);
            System.out.println("written by: " + author);
            System.out.println("Map Items:");
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
                    System.out.print(currentItem);
    			}
    			this.mapItems.add(itemsInRow);
    			System.out.print("\n");
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
						g.setColor(Color.GREEN);
						g.drawImage(Sprite.getImage("grass"), (x+j*32), (y+i*32), this);
						break;
					case 1:
						g.setColor(Color.GRAY);
						g.drawImage(Sprite.getImage("rocks"), (x+j*32), (y+i*32), this);
						break;
					}
				}
			}
		}
	}

	public void movePlayerDown() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Down").setPlayerPos(true);
		playerPos[1]+=1;
	}

	public void movePlayerUp() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Up").setPlayerPos(true);
		playerPos[1]-=1;
	}
	
	public void movePlayerRight() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Right").setPlayerPos(true);
		playerPos[0]+=1;
	}
	
	public void movePlayerLeft() {
		getCurrentTile().setPlayerPos(false);
		getNextTile("Left").setPlayerPos(true);
		playerPos[0]-=1;
	}

}
