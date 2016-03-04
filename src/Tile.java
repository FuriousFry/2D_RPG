public class Tile {
	private int id;
	private String name;
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
			return true;
		case 1:
			return false;
		case 2:
			return true;
		}
		return false;
	}

	public String[] getDestination() {
		return (new String[]{transition, transitionNote});
	}

}
