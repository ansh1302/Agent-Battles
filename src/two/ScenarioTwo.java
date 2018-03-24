package two;

import objects.Agent;
import objects.Target;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.*;

public class ScenarioTwo extends JComponent {

	/*ON THE REPORT, SPECIFY THAT BFS TAKES ON A PATH OF MOVING HORIZONTALLY UNTIL THE X COORDINATES OF AGENT AND TARGET MATCH AND THEN MOVING VERTICALLY TO THE TARGET SINCE THERE ARE NO OBSTACLES
	 * AFTER NOTICING THAT BFS ALWAYS RETURNED THIS PATH, WE SIMPLIFIED THE CODE TO DO THE SAME INSTEAD OF CODING BFS IN THE CODE*/
	
	//initialize agent's physical characteristics
    int trainW = 50;
    int trainH = 50;
    int trainSpeed = 50;
    
    //declare array lists to store all agents and respective targets
    ArrayList<Agent> agents = new ArrayList<Agent>();
    ArrayList<Target> targets = new ArrayList<Target>();
    
    //create each agent object
	Agent agent1 = new Agent(1, 100, 100, 100, 100, "RIGHT");
	Agent agent2 = new Agent(2, 200, 200, 200, 200, "LEFT");
	Agent agent3 = new Agent(3, 300, 300, 300, 300, "UP");
	Agent agent4 = new Agent(4, 400, 400, 400, 400, "DOWN");
	Agent agent5 = new Agent(5, 500, 500, 500, 500, "RIGHT");
	
	//create each target object
    Target target1 = new Target(1, 400, 650, false);
    Target target2 = new Target(2, 100, 650, false);
    Target target3 = new Target(3, 400, 700, false);
    Target target4 = new Target(4, 50, 150, false);
    Target target5 = new Target(5, 650, 250, false);

    //constructor for the field's canvas
    public ScenarioTwo() {
    	
    	//store all agents in array list
    	agents.add(agent1);
    	agents.add(agent2);
    	agents.add(agent3);
    	agents.add(agent4);
    	agents.add(agent5);
    	
    	//store all targets in array list
    	targets.add(target1);
    	targets.add(target2);
    	targets.add(target3);
    	targets.add(target4);
    	targets.add(target5);
    	
    	//GUI runnable method
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    repaint();
                    try {Thread.sleep(100);} catch (Exception ex) {}
                }
            }
        });

        animationThread.start();
    }

    //method to draw objects on field canvas
    public void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D) g; //object interface graphics library
        Random rand = new Random();
        
        //store screen width and height
        int w = getWidth();
        int h = getHeight();
        
        //decision loop for each agent
        for (int i = 0; i < agents.size(); i++) {
        	int  n = rand.nextInt(10) + 1;
        	
        	//if target's location is known to be within the boundaries of the field, send agent to its target
        	if (agents.get(i).getTargetX() >= 0 && agents.get(i).getTargetY() >= 0) {
            	if(agents.get(i).getTargetX() > agents.get(i).getX()) {
            		agents.get(i).changeDirection("RIGHT");
            	}
            	if(agents.get(i).getTargetX() < agents.get(i).getX()) {
            		agents.get(i).changeDirection("LEFT");
            	}
            	if(agents.get(i).getTargetY() < agents.get(i).getY()) {
            		agents.get(i).changeDirection("UP");
            	}
            	if(agents.get(i).getTargetY() > agents.get(i).getY()) {
            		agents.get(i).changeDirection("DOWN");
            	}
            	
            //else if agent is not colliding with any boundary, give it random movement
            } else if (!checkCollision1(agents.get(i), w, h)) {
            	if (n == 1)  {
                	agents.get(i).changeDirection("LEFT");
                } 
                else if (n == 2)  {
                	agents.get(i).changeDirection("RIGHT");
                }
                else if (n == 3)  {
                	agents.get(i).changeDirection("UP");
                }
                else if (n == 4) {
                	agents.get(i).changeDirection("DOWN");
                }
            }
        	
        	//run the game if not all targets have been captured
        	if (!allTargetsTaken()) {
        		//move agent either horizontally or vertically
            	if (agents.get(i).getDirection().equals("LEFT") || agents.get(i).getDirection().equals("RIGHT")) {
                	agents.get(i).setX(agents.get(i).getLastX() + (trainSpeed*agents.get(i).getDirectionX()));
                } else if (agents.get(i).getDirection().equals("UP") || agents.get(i).getDirection().equals("DOWN")) {
                	agents.get(i).setY(agents.get(i).getLastY() + (trainSpeed*agents.get(i).getDirectionY()));
                }
        	}
        	
        	//determine which targets to keep present on playing field
        	for (int j = 0; j < targets.size(); j++) {
        		
        		//broadcast target if agent lands on it and the IDs don't match
        		if(targets.get(j).getX() == agents.get(i).getX() && targets.get(j).getY() == agents.get(i).getY() && !targets.get(j).getCaptured()) {
        			if(targets.get(j).getID() != agents.get(i).getID()) {
        				broadcast(agents.get(i), targets.get(j));
        			}
        		}
        		
        		//if matched target belongs to agent and is not captured, then capture it
        		if (checkTarget(agents.get(i), targets.get(j))) {
        			
        			//set target's location and known location outside the playing field 
                	targets.get(j).setX(-100); targets.get(j).setY(-100);
                	agents.get(i).setTargetX(-100);
                	agents.get(i).setTargetY(-100);
                	
                	//set target's captured status to true and increment respective agent's score
                	targets.get(j).setCaptured(true);
                	agents.get(i).incrementScore();
                    System.out.println("Target acquired!");
                }
        		
        	}
        	
        	//store agent's last known location for next movement
        	agents.get(i).setLastX(agents.get(i).getX());
            agents.get(i).setLastY(agents.get(i).getY());
        }
        
      //re-draw all field objects
        gg.setColor(Color.GRAY);
        gg.fillOval(agent1.getX(), agent1.getY(), trainW, trainH);
        gg.fillOval(target1.getX(), target1.getY(), trainW, trainH);
        gg.setColor(Color.RED);
        gg.fillOval(agent2.getX(), agent2.getY(), trainW, trainH);
        gg.fillOval(target2.getX(), target2.getY(), trainW, trainH);
        gg.setColor(Color.BLUE);
        gg.fillOval(agent3.getX(), agent3.getY(), trainW, trainH);
        gg.fillOval(target3.getX(), target3.getY(), trainW, trainH);
        gg.setColor(Color.GREEN);
        gg.fillOval(agent4.getX(), agent4.getY(), trainW, trainH);
        gg.fillOval(target4.getX(), target4.getY(), trainW, trainH);
        gg.setColor(Color.YELLOW);
        gg.fillOval(agent5.getX(), agent5.getY(), trainW, trainH);
        gg.fillOval(target5.getX(), target5.getY(), trainW, trainH);
    }
    
    //method to check if all targets are captured
    public boolean allTargetsTaken() {
    	for (int i = 0; i < targets.size(); i++) {
    		if (!targets.get(i).getCaptured()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    //method to check if current target should be captured or not
    public boolean checkTarget(Agent agent, Target target) {
    	if ((agent.getX() == target.getX()) && (agent.getY() == target.getY()) && (agent.getID() == target.getID()) && (!target.getCaptured())) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //method to check for agents' collisions with boundaries and update direction accordingly
    public boolean checkCollision1(Agent agent, int w, int h) {
    	if ((agent.getX()+100) >= w) {
    		agent.changeDirection("LEFT");
    		return true;
    	} else if (agent.getX() <= 0) {
    		agent.changeDirection("RIGHT");
    		return true;
    	} else if (agent.getY() <= 0) {
    		agent.changeDirection("DOWN");
    		return true;
    	} else if ((agent.getY()+100) >= h) {
    		agent.changeDirection("UP");
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //method to broadcast target location to all other agents
    public void broadcast(Agent agent, Target target) {
    	for (int i = 0; i < agents.size(); i++) {
    		if (agents.get(i).getID() == agent.getID()) {
    			continue;
    		} else {
    			agents.get(i).receive(target.getX(), target.getY(), target.getID());
    		}
    	}
    }
}