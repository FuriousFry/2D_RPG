public class Tile {
	private int id;
	private String name;
	@SuppressWarnings("unused")
	private boolean playerPos = false;
	private boolean spawnPoint = false;

	public Tile(String item) {
		String[] parts = item.split(",");
		this.id = Integer.valueOf(parts[0]);
		this.name = parts[1];
		if (parts.length > 2) {
			if(parts[2].equals("spawn")){
				this.setSpawnPoint(true);
			}
		}
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public boolean isPassable() {
		switch (this.id) {
		case 0:
			System.out.println("is passable");
			return true;
		case 1:
			System.out.println("is not passable");
			return false;
		}
		return false;
	}

	public void setPlayerPos(boolean bool) {
		playerPos = bool;
	}

	public boolean isSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(boolean spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

}
