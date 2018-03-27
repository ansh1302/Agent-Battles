package objects;

import java.util.ArrayList;

public class Agent {
	
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
    public Agent(int id, int x, int y, int lastX, int lastY, String direction) {
    	this.score = 0;
    	this.lastX = lastX;
    	this.lastY = lastY;
    	this.x = x;
    	this.y = y;
    	this.direction = direction;
    	changeDirection(this.direction);
    	this.ID = id;
    	this.targetX = new ArrayList<Integer>();
    	this.targetY = new ArrayList<Integer>();
    }
    
    //accessor and mutator methods for all instances of Agent object
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
    
    public double getDistance(int x1, int x2, int y1, int y2) {
    	return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }
    
    public boolean checkAdded(int x, int y) {
    	for (int i = 0; i < this.targetX.size(); i++) {
    		if ((this.targetX.get(i) == x) && (this.targetY.get(i) == y)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void receive(int x, int y, int targetNum) {
    	if (targetNum == this.ID) {
    		if (!this.checkAdded(x, y)) {
	    		if (this.targetX.size() < 2) {
	    			this.targetX.add(x); this.targetY.add(y);
	    		} else {
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
    		}
    	}
    }
    
    public void removeMem(int x, int y) {
    	for (int i = 0; i < this.targetX.size(); i++) {
    		if ((this.targetX.get(i) == x) && (this.targetY.get(i) == y)) {
    			if (this.ID == 2) {
    				System.out.println("\nRemoving: ("+x+", "+y+")");
    			}
    			this.targetX.remove(i); this.targetY.remove(i);
    		}
    	}
    }
}