/*
 * Adapted from http://www.agent-based-models.com/blog/
 */

package agents;

//Imports classes from MASON
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;

public class AgentsSimulation extends SimState {
		
	/*
	* To turn this into a simulation environment I have to take advantage of
	* a class already defined in MASON and extend (add my own code to) SimState.
	*/
	
	// Specifies the data variables for the simulation
	public SparseGrid2D agentsSpace;	// 2D space for agents
	public int gridWidth = 100;			// Width of the space
	public int gridHeight = 100;		// Height of the space
	public int n = 100;					// number of agents
	
	public AgentsSimulation(long seed) {
		super(seed);
		// Constructor method for SimState
		// To reuse the previously defined method, must use "super"
	}
	
		public void start(){
		super.start();
			// Reuses the SimState start method
		agentsSpace = new SparseGrid2D(gridWidth, gridHeight);
			// Creates a 2D space
		
	/*
	* Now create the agents and put them in the space.
	*/
		
	for(int i=0; i<n;  i++){
		// 0 to the number of agents -1
		int x = random.nextInt(gridWidth);
			// A random number < gridWidth
		int y = random.nextInt(gridHeight);
			// A random number < gridHeight
		int xdir = random.nextInt(2) - 1;
			// In the range -1 to 1
		int ydir = random.nextInt(2) - 1;
			// In the range -1 to 1
		while(xdir == 0 && ydir == 0){
			xdir = random.nextInt(2) - 1;
			ydir = random.nextInt(2) - 1;
		}
		
		Agent a = new Agent(x,y,xdir,ydir, (random.nextDouble()<.1));
			// This gives agents a 10% chance of being "minorityAgent" (coloured red)
		agentsSpace.setObjectLocation(a, x,y);
			// Put it at location x, y
		schedule.scheduleRepeating(a, 1);
			// Schedule it to repeat in the simulation
		
		/*
		* Used the following to try and add extra sets of agents. Didn't work.
		* Agent b = new Agent(x,y,xdir,ydir);
		* agentsSpace.setObjectLocation(b, x,y);
		* 		// Put it at location x, y
		* schedule.scheduleRepeating(b);
		* 		// Schedule it to repeat in the simulation
		* Agent c = new Agent(x,y,xdir,ydir);
		* agentsSpace.setObjectLocation(c, x,y);
		* 		// Put it at location x, y
		* schedule.scheduleRepeating(c);
		* 		// Schedule it to repeat in the simulation
		*/
		
		}
	}
	
	public static void main(String[] args){
		doLoop(AgentsSimulation.class, args);
		// doLoop is a static method already defined in SimState
		System.exit(0);		// Stop the program when fin.
	}
}	// end AgentsSimulation class
