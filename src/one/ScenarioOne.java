package one;

import objects.Agent;
import objects.Target;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.*;

public class ScenarioOne extends JComponent {

	/*CHECK IF THE COMMON METHODS FROM EACH SCENARIO CLASS CAN BE PUT INTO ONE CLASS AND HAVE THE SAME INSTANCE USED EVERYWHERE*/
	//initialize agent's physical characteristics
    
	//declare array lists to store all agents and respective targets
    ArrayList<Agent> agents = new ArrayList<Agent>();
    //ArrayList<Target> targets = new ArrayList<Target>();
    ArrayList<ArrayList<Target>> targets = new ArrayList<ArrayList<Target>>();
  //array of direction strings
	String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
	double[] happy_array = new double[5]; // required for CSV
	
	int iteration = 1;

    //constructor for the field's canvas
    public ScenarioOne() {
    	//starting coordinates and direction
    	Random r = new Random();
    	int dir; int coorX, coorY;
    	
    	//loop to dynamically create and initialize agents
    	for (int i = 0; i < 5; i++) {
    		dir = r.nextInt(3) + 0;
    		coorX = getRounded(r.nextInt(600) + 100); coorY = getRounded(r.nextInt(600) + 100);
    		agents.add(new Agent(i, coorX, coorY, directions[dir]));
    	}
    	
    	//loop to dynamically create and initialize all targets
    	int IDcounter = 0;
    	for (int i = 0; i < 5; i++) {
    		targets.add(new ArrayList<Target>());
    		for (int j = 0; j < 5; j++) {
    			coorX = getRounded(r.nextInt(600) + 100); coorY = getRounded(r.nextInt(600) + 100);
    			targets.get(i).add(new Target(IDcounter, coorX, coorY, false));
    		}
    		IDcounter++;
    	}
    	
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
        int w = 800; int h = 800;
        
        //decision loop for each agent
        for (int i = 0; i < agents.size(); i++) {
        	int  n = rand.nextInt(10) + 1;
        	
        	//if target's location is known to be within the boundaries of the field, send agent to its target
        	if ((agents.get(i).getMemLength() > 0)) {
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
            } else if (!checkCollision(agents.get(i), w, h)) {
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
        	if (!checkEndOfGame()) {
        		//move agent either horizontally or vertically
	            if (agents.get(i).getDirection().equals("LEFT") || agents.get(i).getDirection().equals("RIGHT")) {
	               	agents.get(i).setX(agents.get(i).getLastX() + (agents.get(i).getSpeed()*agents.get(i).getDirectionX()));
	            } else if (agents.get(i).getDirection().equals("UP") || agents.get(i).getDirection().equals("DOWN")) {
	               	agents.get(i).setY(agents.get(i).getLastY() + (agents.get(i).getSpeed()*agents.get(i).getDirectionY()));
	            }
	            agents.get(i).addStep();
        	}
        	
        	checkRange(i); //check if any target is in range with any agent
        	//store agent's last known location for next movement
        	agents.get(i).setLastX(agents.get(i).getX()); agents.get(i).setLastY(agents.get(i).getY());
        }
        
        drawObjects(50, 50, gg); //draw all objects on screen
    }
    
    //method to display all visual components on screen
    public void drawObjects(int agentW, int agentH, Graphics gg) {
    	//draw in all the objects
        for (int i = 0; i < 5; i++) {
    		if (i == 0) {
    			gg.setColor(Color.BLACK);
		        gg.fillOval(agents.get(0).getX(), agents.get(0).getY(), agentW, agentH);
		        gg.setColor(Color.GRAY);
		        gg.fillOval(agents.get(0).getX()+12, agents.get(0).getY()+12, agentW-24, agentH-24);
    				for (int j = 0; j < targets.get(i).size(); j++) {
    					gg.fillOval(targets.get(i).get(j).getX(), targets.get(i).get(j).getY(), agentW, agentH);
    				}
    		} else if (i == 1) {
    			gg.setColor(Color.BLACK);
		        gg.fillOval(agents.get(1).getX(), agents.get(1).getY(), agentW, agentH);
		        gg.setColor(Color.RED);
		        gg.fillOval(agents.get(1).getX()+12, agents.get(1).getY()+12, agentW-24, agentH-24);
    				for (int j = 0; j < targets.get(i).size(); j++) {
    					gg.fillOval(targets.get(i).get(j).getX(), targets.get(i).get(j).getY(), agentW, agentH);
    				}
    		} else if (i == 2) {
    			gg.setColor(Color.BLACK);
		        gg.fillOval(agents.get(2).getX(), agents.get(2).getY(), agentW, agentH);
		        gg.setColor(Color.BLUE);
		        gg.fillOval(agents.get(2).getX()+12, agents.get(2).getY()+12, agentW-24, agentH-24);
    				for (int j = 0; j < targets.get(i).size(); j++) {    					
    					gg.fillOval(targets.get(i).get(j).getX(), targets.get(i).get(j).getY(), agentW, agentH);
    				}
    		} else if (i == 3) {
    			gg.setColor(Color.BLACK);
		        gg.fillOval(agents.get(3).getX(), agents.get(3).getY(), agentW, agentH);
		        gg.setColor(Color.GREEN);
		        gg.fillOval(agents.get(3).getX()+12, agents.get(3).getY()+12, agentW-24, agentH-24);
    				for (int j = 0; j < targets.get(i).size(); j++) {
    					gg.fillOval(targets.get(i).get(j).getX(), targets.get(i).get(j).getY(), agentW, agentH);
    				}
    		} else if (i == 4) {
    				gg.setColor(Color.BLACK);
    		        gg.fillOval(agents.get(4).getX(), agents.get(4).getY(), agentW, agentH);
    		        gg.setColor(Color.YELLOW);
    		        gg.fillOval(agents.get(4).getX()+12, agents.get(4).getY()+12, agentW-24, agentH-24);
    				for (int j = 0; j < targets.get(i).size(); j++) {
    					gg.fillOval(targets.get(i).get(j).getX(), targets.get(i).get(j).getY(), agentW, agentH);
    				}
    		}
    	}
    }
    
    //method to check each agent's radar
    public void checkRange(int i) {
    	Random r = new Random();
    	
    	//check if any targets are within range of any agents
    	for (int x = 0; x < targets.size(); x++) {
        	for (int j = 0; j < targets.get(x).size(); j++) {
        		
        		//broadcast target if agent lands in range and IDs don't match, otherwise acquire target
        		if((getDistance(agents.get(i).getX(), targets.get(x).get(j).getX(), agents.get(i).getY(), targets.get(x).get(j).getY()) <= 50) && (!targets.get(x).get(j).getCaptured())) {
        			
        			//check if target belongs to current agent
        			if(targets.get(x).get(j).getID() != agents.get(i).getID()) {
        				if ((r.nextInt(2) + 1) == 1) { //if 1, lie
        					broadcast(agents.get(i), targets.get(x).get(j), false);
        				} else { //tell the truth
        					broadcast(agents.get(i), targets.get(x).get(j), true);
        				}
        			} else {
        				
        				//set target's location and known location outside the playing field 
        				agents.get(i).removeMem(targets.get(x).get(j).getX(), targets.get(x).get(j).getY());
                    	targets.get(x).get(j).setX(-100); targets.get(x).get(j).setY(-100);
                    	
                    	//set target's captured status to true and increment respective agent's score
                    	targets.get(x).get(j).setCaptured(true);
                    	agents.get(i).incrementScore();
        			}
        		}
        	}
    	}
    }
    
    //round to nearest multiple of 50
    public int getRounded(int x) {
    	if (x%50 == 0) {
    		return x; //return same number if it is already a multiple of 50
    	} else {
    		return x + (50 - (x % 50));
    	}
    }
    
    //method to calculate the distance between two objects
    public double getDistance(int x1, int x2, int y1, int y2) {
    	return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }
    
    //method to check if any agent has acquired all of its targets to signal end of game
    public boolean checkEndOfGame() {
    	for (int i = 0; i < agents.size(); i++) {
    		if (agents.get(i).getScore() == 5) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //method to check for agents' collisions with boundaries and update direction accordingly
    public boolean checkCollision(Agent agent, int w, int h) {
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
    public void broadcast(Agent agent, Target target, boolean truth) {
    	for (int i = 0; i < agents.size(); i++) {
    		if (agents.get(i).getID() == agent.getID()) { //do not broadcast to yourself
    			continue;
    		} else {
    			
    			//either send the correct or incorrect target coordinates
    			if (truth = true) {
    				agents.get(i).receive(target.getX(), target.getY(), target.getID());
    			} else {
    				agents.get(i).receive(agents.get(0).getX(), agents.get(0).getY(), target.getID());
    			}
    		}
    	}
    }
	public void generateCSVValues() throws IOException {
		double max_happy = 0;
		double min_happy = 100;
		for (int i = 0; i < agents.size(); i++) {
			double col_d = agents.get(i).getScore(); // Treasures found by agent
			double col_e = agents.get(i).getSteps(); // Steps taken by agent
			double col_f = (col_d / (col_e + 1)); // Agent Happiness

			happy_array[i] = col_f;

			//get max happiness
			if (col_f > max_happy) {
				max_happy = col_f;
			}

			//get min happiness
			if (col_f < min_happy) {
				min_happy = col_f;
			}
		}
		double col_g = max_happy; // Max Happiness
		double col_h = min_happy; // Min Happiness
		double col_i = getMean(); // Average Happiness
		double col_j = getStdDev(); // Standard Deviation of Happiness
		outputToCSV(col_g, col_h, col_i, col_j);
	}

	public void outputToCSV( double col_g, double col_h, double col_i, double col_j) throws IOException {
		double col_a = 1;

		FileWriter fileWriter = null;
		try {
			String filename = "G11_1.csv";
			fileWriter = new FileWriter(filename, true);
			fileWriter.append("Scenario");
			fileWriter.append(",");
			fileWriter.append("Iteration");
			fileWriter.append(",");
			fileWriter.append("Agent");
			fileWriter.append(",");
			fileWriter.append("# targets collected");
			fileWriter.append(",");
			fileWriter.append("# of steps");
			fileWriter.append(",");
			fileWriter.append("Happiness");
			fileWriter.append(",");
			fileWriter.append("Maximum Happiness");
			fileWriter.append(",");
			fileWriter.append("Minimum Happiness");
			fileWriter.append(",");
			fileWriter.append("Average Happiness");
			fileWriter.append(",");
			fileWriter.append("Standard Deviation on Happiness");
			fileWriter.append(",");
			fileWriter.append("Agent Competitiveness");
			fileWriter.append(",");
			fileWriter.append("\n");

			//Scenario
			for (int i = 0; i < agents.size(); i++)
			{
				fileWriter.append(Double.toString(col_a));
				fileWriter.append(",");

				double col_b = iteration;
				fileWriter.append((Double.toString(col_b)));
				fileWriter.append(",");

				double col_c = (agents.get(i).getID() + 1);
				fileWriter.append(Double.toString(col_c));
				fileWriter.append(",");

				double col_d = (agents.get(i).getScore());    // col_d = Number of collected targets by the agent
				fileWriter.append(Double.toString(col_d));
				fileWriter.append(",");

				double col_e = (agents.get(i).getSteps());    // col_e = Number of steps taken by the agent at the end of the iteration
				fileWriter.append(Double.toString(col_e));
				fileWriter.append(",");
				double col_f = (col_d / (col_e + 1));         // col_f = agent happiness

				fileWriter.append(Double.toString(col_f));
				fileWriter.append(",");

				fileWriter.append(Double.toString(col_g));   // col_g = max happiness for each iteration
				fileWriter.append(",");

				fileWriter.append(Double.toString(col_h));  // col_h = min happiness for each iteration
				fileWriter.append(",");

				fileWriter.append(Double.toString(col_i));  // col_i = avg happiness for each iteration
				fileWriter.append(",");

				fileWriter.append(Double.toString(col_j));  //col_j = std dev of happiness in each iteration
				fileWriter.append(",");

				double col_k = ((col_f - col_h) / (col_g - col_h)); // col_k = agent competitiveness
				fileWriter.append(Double.toString(col_k));
				fileWriter.append(",");
				fileWriter.append("\n");
			}
		}
		catch(Exception e){
			System.out.println("Error in CSVFileWriter!");
			e.printStackTrace();
		} finally{
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter!");
				e.printStackTrace();
			}
		}
		String fileName2 = "G11_2.csv";

		FileWriter fileWriter1 = new FileWriter(fileName2, true);
		fileWriter1.append("Scenario");
		fileWriter1.append(",");
		fileWriter1.append("Average Happiness");
		fileWriter1.append(",");
		fileWriter1.append("Agent Competitiveness");
		fileWriter1.append(",");
		fileWriter1.append("\n");

		for (int i = 0; i < agents.size(); i++) {

			fileWriter1.append(Double.toString(col_a));
			fileWriter1.append(",");

			fileWriter1.append(Double.toString(col_i));
			fileWriter1.append(",");

			double col_d = (agents.get(i).getScore());
			double col_e = (agents.get(i).getSteps());
			double col_f = (col_d / (col_e + 1));
			double col_k = ((col_f - col_h)/(col_g - col_h));
			fileWriter1.append(Double.toString(col_k));
			fileWriter1.append(",");
			fileWriter1.append("\n");
		}
		fileWriter1.flush();
		fileWriter1.close();
	}

	// Stat Functions for CSV Values
	public double getMean() {
		double sum = 0.0;
		for(double a : happy_array)
			sum += a;
		return sum/agents.size();
	}

	//determine variance
	public double getVariance() {
		double mean = getMean();
		double temp = 0;
		for(double a :happy_array)
			temp += (a-mean)*(a-mean);
		return temp/(agents.size()-1);
	}

	//determine standard deviation of happiness
	public double getStdDev() {
		return Math.sqrt(getVariance());
	}
}
