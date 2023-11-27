package battleship.finalproject;

public abstract class Ship {
	protected String name;
	protected int size;
	protected int guns;
	
	//Ship Constructor
	public Ship(String name, int size, int guns) {
		this.name = name;
		this.size = size;
		this.guns = guns;
	}
	//getters and Setters
	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public int getGuns() {
		return guns;
	}

	public void setGuns(int guns) {
		this.guns = guns;
	}


	
}
