package objects;

public class Target {
	
	//declare all instances of Target object
	private int x;
	private int y;
	private int ID;
	private boolean captured;
	private boolean broadcasted;
	
	//constructor to initialize instances of Target object
	public Target(int id, int x, int y, boolean captured) {
		this.ID = id;
		this.x = x;
		this.y = y;
		this.captured = captured;
		this.broadcasted = false;
	}
	
	//accessor and mutator methods for all instances of Target object
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getID() {
		return ID;
	}
	
	public boolean getCaptured() {
		return captured;
	}
	
	public boolean getBroadcasted() {
		return this.broadcasted;
	}
	
	public void setCaptured(boolean captured) {
		this.captured = true;
	}
	
	public void setBroadcasted(Boolean broadcasted) {
		this.broadcasted = broadcasted;
	}
}