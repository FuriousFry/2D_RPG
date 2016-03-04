public class Tile {
	private int id;
	private String name;
	@SuppressWarnings("unused")
	private boolean playerPos = false;
	private String transition = "";
	private String transitionNote = "";

	public Tile(String item) {
		String[] parts = item.split(",");
		this.id = Integer.valueOf(parts[0]);
		this.name = parts[1];
		if (parts.length > 2) {
			this.transition = parts[2];
			this.transitionNote = parts[3];
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
			System.out.println(this.name + "is passable");
			return true;
		case 1:
			System.out.println(this.name + "is not passable");
			return false;
		case 2:
			System.out.println(this.name + "is passable");
			return true;
		}
		return false;
	}

	public void setPlayerPos(boolean bool) {
		playerPos = bool;
	}

	public String[] getDestination() {
		return (new String[]{transition, transitionNote});
	}

}
