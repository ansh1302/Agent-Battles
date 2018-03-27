package objects;

import java.util.ArrayList;

public class Agent {
	
	//all instances of the Agent object
	private int lastX;
	private int lastY;
    private int x;
    private int y;
    private int directionX;
    private int directionY;
    private String direction;
    private ArrayList<Integer> targetX;
    private ArrayList<Integer> targetY;
    private int ID;
    private int score;
    
    //constructor to initialize all instances of Agent object
    public Agent(int id, int x, int y, String direction) {
    	this.score = 0;
    	this.lastX = x;
    	this.lastY = y;
    	this.x = x;
    	this.y = y;
    	this.direction = direction;
    	this.ID = id;
    	this.targetX = new ArrayList<Integer>();
    	this.targetY = new ArrayList<Integer>();
    	changeDirection(this.direction);
    }
    
    //getter and setter methods for all instances of Agent object
    public int getScore() {
    	return score;
    }
    
    public void incrementScore() {
    	score++;
    }
    
    public int getMemLength() {
    	return this.targetX.size();
    }
    
    public int getTargetX() {
    	return this.targetX.get(0);
    }
    
    public int getTargetY() {
    	return this.targetY.get(0);
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
    
    public String getDirection() {
    	return this.direction;
    }
    
    //method to assign the change in agent's direction
    public void changeDirection(String command) {
    	if (command.equals("UP")) {
    		direction = "UP";
    		directionY = -1;
    	} else if (command.equals("DOWN")) {
    		direction = "DOWN";
    		directionY = 1;
    	} else if (command.equals("LEFT")) {
    		direction = "LEFT";
    		directionX = -1;
    	} else if (command.equals("RIGHT")) {
    		direction = "RIGHT";
    		directionX = 1;
    	}
    }
    
    //method to flip the agent's current direction
    public void flipDirection() {
    	if (this.direction.equals("UP")) {
    		this.changeDirection("DOWN");
    	} else if (this.direction.equals("DOWN")) {
    		this.changeDirection("UP");
    	} else if (this.direction.equals("LEFT")) {
    		this.changeDirection("RIGHT");
    	} else if (this.direction.equals("RIGHT")) {
    		this.changeDirection("LEFT");
    	}
    }
    
    //method to calculate the distance between two points
    public double getDistance(int x1, int x2, int y1, int y2) {
    	return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }
    
    //method to check if a given target is already in agent's memory
    public boolean checkAdded(int x, int y) {
    	for (int i = 0; i < this.targetX.size(); i++) {
    		if ((this.targetX.get(i) == x) && (this.targetY.get(i) == y)) {
    			return true; //return true if target found in memory
    		}
    	}
    	return false; //return false if no match found
    }
    
    //method to store an agent's target in its memory
    public void receive(int x, int y, int targetNum) {
    	if (targetNum == this.ID) { //check to make sure the correct agent is receiving it
    		if (!this.checkAdded(x, y)) { //only add if it is not already in memory
    			
    			//check where in the scheduling queue to add the new target location
    			if (this.targetX.size() > 0) {
    				
    				//check if agent is already closest to new target location so it can go there first before following its memory queue
    				if (getDistance(this.getX(), this.getTargetX(), this.getY(), this.getTargetY()) > getDistance(this.getX(), x, this.getY(), y)) {
    					this.targetX.add(0, x); this.targetY.add(0, y);
    				} else {
    					
    					//find the known target in the queue to which new target is closest to and add it following that already-known target
		    			double shortest = Double.MAX_VALUE; double distance = 0;
		    			int shortestIndex = this.targetX.size()-1;
		    			
		    			for (int i = 0; i < this.targetX.size(); i++) {
		    				distance = getDistance(x, this.targetX.get(i), y, this.targetY.get(i));
		    				
		    				if (distance <= shortest) {
		    					shortest = distance;
		    					shortestIndex = i+1;
		    				}
		    			}
		    			this.targetX.add(shortestIndex, x); this.targetY.add(shortestIndex, y);
		    		}
    			} else { this.targetX.add(x); this.targetY.add(y); } //add without thinking if memory queue is empty
    		}
    	}
    }
    
    //method to remove a target from an agent's memory queue once it has been acquired
    public void removeMem(int x, int y) {
    	for (int i = 0; i < this.targetX.size(); i++) {
    		if ((this.targetX.get(i) == x) && (this.targetY.get(i) == y)) {
    			this.targetX.remove(i); this.targetY.remove(i);
    		}
    	}
    }
}