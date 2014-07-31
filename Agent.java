/*
 * Adapted from http://www.agent-based-models.com/blog/
 */

package agents;

// Imports classes from MASON
import sim.engine.Steppable;
import sim.engine.SimState;
import sim.util.Bag;

public class Agent implements Steppable{
	// Declares that the Agent implements the Steppable class
	
	/*
	 * Here I specify how the Agents will behave at each time step.
	 * They move in a 2D space and continue to move in the same
	 * direction until they hit a wall or hit another Agent.
	 * Each agent stores 5 pieces of data in variables that keep track of
	 * location, direction of movement, and whether to randomize movement.
	 * These variables (x, y, xdir, ydir, randomize & minorityAgent) are
	 * added to our Agent class below.
	 */
	 
	public int x;		// x coordinate of agent
	public int y;		// y coordinate of agent
	public int xdir;	// the number of spaces moved
						// along the x-axis on each step
	public int ydir;	// the number of spaces moved
						// along the y-axis on each step
	public boolean randomize = false;
						// if an agent collides with another,
						// randomize direction
	public boolean minorityAgent = false;
						// sets some agents as a 'minority'
	
	/*
	 * Here is the agent CONSTRUCTOR METHOD.
	 * In ABMs 100s of agents are created and generally we want to give each
	 * one a unique location and a specific direction of movement.
	 * To do this we write a specific CONSTRUCTOR METHOD which is different
	 * from other methods in that its name is the name of the class.
	 * "this" refers to the agent itself.
	 */
	
	public Agent(int x, int y, int xdir, int ydir, boolean minority){
		this.x = x;
		this.y = y;
		this.xdir = xdir;
		this.ydir = ydir;
		this.minorityAgent = minority;
	}
	
	/*
	* I have to write the details of the predefined step method.
	* On each time step, each agent (in random order) will have its
	* step method called.
	* When it is called the agent will check the location of the agent,
	* check if there is another agent at that location, and if so, there has
	* been a collision and it tell the AGent to change direction.
	* It also checks to see if the agent has hit a boundary (HITY, HITX) and
	* if so, it tells it to change direction.
	* It then sets a new location for the agent (heading in a new direction)
	* and ends the step method (it will then repeat and make it move like a 
	* ball!).
	*/
	
	public void step(SimState state){
		AgentsSimulation as = (AgentsSimulation)state;
		// Convert state into the AgentSimulation
		
		/*
		* A Bag is something that holds objects such as the agents.
		* Need to add import sim.util.Bag; to use Bags.
		*/
		
		Bag bag = as.agentsSpace.getObjectsAtLocation(x,y);
			// get all the agents at x,y	
		
		/*
		 * Check for collisions!
		 */
		
		if (bag != null && bag.numObjs > 1)
			// If there is more than 1, make them randomize
		for(int i=0; i<bag.numObjs; i++){
			// For every agent in the bag
			Agent a = (Agent)(bag.objs[i]);
			// Convert it to an agent
			a.randomize = true;
			// Tell it to randomize its direction
		}
		
		/*
		 * If there was a collision, randomize	
		 */
		
		while(randomize){
			// If randomize is true, a collision occured
			xdir = state.random.nextInt(3) - 1; //is -1, 0, or 1
			ydir = state.random.nextInt(3) - 1; //is -1, 0, or 1
			if(xdir != 0 || ydir != 0)
				randomize = false;
					// randomize only once after a collision
		}
		
			int newx = x + xdir;	// change the x position by xdir
			int newy = y + ydir;	// Change the y position by ydir
			
			newx = hitx(0,as.gridWidth,newx);
				// Does it hit a wall along the x-axis? Then make the
				// appropriate corrections
			newy = hity(0,as.gridHeight,newy);
				// Does it hit a wall along the y-axis? Then make the
				// appropriate corrections
			
			/*
			 * Now move the agent
			 */
			
			as.agentsSpace.setObjectLocation(this, newx, newy);
			// set its new location
			x = newx;			// Set x to newx
			y = newy;			// Set y to newy
		}	// end step method
	
		/*
		* Below is the method that tells the Agent how to move around in
		* its 2D world. The Agent will bounce off boundaries and start
		* moving in the opposite direction.
		* There are two methods -- hitx and hity -- which handle the
		* cases when an agent hits a wall along the x and y axes.
		* Both methods test for the low and high bounds along the x and y
		* axes and if a wall is hit along one or both dimensions, the
		* Agent backs up, and reverses direction along the axis.  
		*/
		
		public int hitx (int boundLow, int boundHigh, int x){
			if (x < boundLow){
				x++; 			// back up
				xdir = -xdir;	// reverse direction
					// xdir = xdir; will make them stick to the x-axis walls
					// until hit by another, quite fun
			}
			else if (x >= boundHigh){
				x--;			// back up
				xdir = -xdir;	// reverse direction
					// xdir = xdir; will make them stick to the x-axis walls
					// until hit by another, quite fun
			}
			return x;
		}
		
		public int hity (int boundLow, int boundHigh, int y){
			if (y < boundLow){
				y++;			// back up
				ydir = -ydir;	// reverse direction
					// ydir = ydir; will make them stick to the y-axis walls
					// until hit by another, quite fun
			}
			else if (y >= boundHigh){
				y--;			// back up
				ydir = -ydir;	// reverse direction
					// ydir = ydir; will make them stick to the y-axis walls
					// until hit by another, quite fun
				}
			return y;
		}
}	// end of Agent class
