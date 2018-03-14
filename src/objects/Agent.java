package objects;

public class Agent {
	
	//declare all instances of Agent object
	private int lastX;
	private int lastY;
    private int x;
    private int y;
    private int directionX;
    private int directionY;
    private String direction;
    private int targetX;
    private int targetY;
    private int ID;
    private int score;
    
    //constructor to initialize all instances of Agent object
    public Agent(int id, int x, int y, int lastX, int lastY, String direction) {
    	this.score = 0;
    	this.lastX = lastX;
    	this.lastY = lastY;
    	this.x = x;
    	this.y = y;
    	this.direction = direction;
    	changeDirection(this.direction);
    	this.ID = id;
    	this.targetX = -1;
    	this.targetY = -1;
    }
    
    //accessor and mutator methods for all instances of Agent object
    public int getScore() {
    	return score;
    }
    
    public void incrementScore() {
    	score++;
    }
    
    public int getTargetX() {
    	return this.targetX;
    }
    
    public int getTargetY() {
    	return this.targetY;
    }
        
    public int getID() {
    	return ID;
    }
    
    public int getX() {
    	return x;
    }
    
    public void setX(int newX) {
    	x = newX;
    }
    
    public int getY() {
    	return y;
    }
    
    public void setY(int newY) {
    	y = newY;
    }
    
    public int getLastX() {
    	return lastX;
    }
    
    public int getLastY() {
    	return lastY;
    }
    
    public void setLastX(int recentX) {
    	lastX = recentX;
    }
    
    public void setLastY(int recentY) {
    	lastY = recentY;
    }
    
    public int getDirectionX() {
    	return directionX;
    }
    
    public int getDirectionY() {
    	return directionY;
    }
    
    public void toggleXDirection() {
    	directionX *= -1; 
    }
    
    public void toggleYDirection() {
    	directionY *= -1;
    }
    
    public String getDirection() {
    	return this.direction;
    }
    
    public void changeDirection(String command) {
    	if(command.equals("UP")) {
    		direction = "UP";
    		directionY = -1;
    	}
    	if(command.equals("DOWN")) {
    		direction = "DOWN";
    		directionY = 1;
    	}
    	if(command.equals("LEFT")) {
    		direction = "LEFT";
    		directionX = -1;
    	}
    	if(command.equals("RIGHT")) {
    		direction = "RIGHT";
    		directionX = 1;
    	}
    }
    
    public void setTargetX(int x) {
    	this.targetX = x;
    }
    
    public void setTargetY(int y) {
    	this.targetY = y;
    }
    
    public void receive(int x, int y, int targetNum) {
    	if(targetNum == this.ID) {
	    	this.targetX = x;
	    	this.targetY = y;
    	}
    }
    
    /*public int broadcastX() {
    	return x;
    }
    
    public int broadcastY() {
    	return y;
    }*/
}