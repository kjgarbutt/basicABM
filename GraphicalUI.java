/*
 * Adapted from http://www.agent-based-models.com/blog/
 */

package agents;

// Imports Java classes
import java.awt.*;
import java.awt.geom.*;
import javax.swing.JFrame;
// Imports classes from MASON
import sim.display.*;
import sim.engine.*;
import sim.portrayal.*;
import sim.portrayal.grid.*;
import sim.portrayal.simple.OrientedPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;
import sim.util.Bag;

public class GraphicalUI extends GUIState{

	/*
	* Need to extend GUIState class which is already defined in MASON
	* so that AgentSimulation.java can be visualised by MASON
	*/
	
	public Display2D display;		// Variable for the graphic display
	public JFrame displayFrame;		// For the display frame
	SparseGridPortrayal2D agentsPortrayal = new
		SparseGridPortrayal2D();
		// Graphic portrayal of the agents in 2D space
	
	protected GraphicalUI()	{		// Constructor for this class
	super(new AgentsSimulation(System.currentTimeMillis()));
	// Use this to check on agents: super(new AgentsSimulation(12345678));
	}
	
	public GraphicalUI(SimState state)	{
		super(state);
		// Pass the already created simulation to it
		}
	
	public static String getName()	{
		return "Agents Simulation";
			// return a name for what this simulation is about
	}

	public void quit()	{
		super.quit();			// Use the already defined quit method
		
		if(displayFrame!=null) displayFrame.dispose();
			displayFrame = null;
			// When quitting get rid of the display
			display = null;
	}
	
	public void start()	{
		super.start();			// Use the predefined start method
		setupPortrayals();		// Add the setupPortrayals method below
	}

	public void load(SimState state)	{
		super.load(state);;		// Load the simulation into the interface
		setupPortrayals();		// Call the setupPortrayals
	}
	
	/*
	 * This is the method used to determine what agents look like. 
	 * It was tricky and I've left in failed attempts to remember 
	 * what NOT to do!
	 */
	
	public void setupPortrayals()	{
		agentsPortrayal.setField(((AgentsSimulation)state).agentsSpace);
		// Here I give the agentsPortrayal, the agentsSpace so that it
		// can graphically represent it.
		
	Bag agents = ((AgentsSimulation)state).agentsSpace.getAllObjects();
		// Get all the agents
		for(int i=0; i<agents.numObjs; i++)	{
		// For all of the agents
				Agent a = (Agent)agents.get(i);
				// Get each agent from 0 to n
	
				if(a.minorityAgent)
					agentsPortrayal.setPortrayalForObject(a, new RectanglePortrayal2D(Color.red));
				// If agent is a minority agent, then make it a red square
				else
					agentsPortrayal.setPortrayalForObject(a, new OvalPortrayal2D(Color.green));
				// Else, make it a majority agent and a green oval
				
				// How to make the size of the agents different?
				
				/*
				 * Bag agents = ((AgentsSimulation)state).agentsSpace.getAllObjects();
				 * 			// Get all the agents
				 * for(int i=0; i<agents.numObjs; i++)	{
				 * 			// For all of the agents
				 * Agent a = (Agent)agents.get(i);
				 * 			// Get each agent from 0 to n
				 * Agent b = (Agent)agents.get(i);
				 * Agent c = (Agent)agents.get(i);
				 * 			// Added two separate kinds of agents: b and c		
				 * float red = (float)1.0;
				 * float green = (float)0.0;
				 * float blue = (float)0.0;
				 * float alpha = (float)1.0;
				 * 
				 * agentsPortrayal.setPortrayalForObject (a,
				 * new sim.portrayal.simple.OvalPortrayal2D (new Color(red, green,blue,alpha)));
				 * 			// Then added the following to portray agents b and c...
				 * agentsPortrayal.setPortrayalForObject (b,
				 * new sim.portrayal.simple.OvalPortrayal2D (new Color(green, alpha)));
				 * agentsPortrayal.setPortrayalForObject (c,
				 * new sim.portrayal.simple.OvalPortrayal2D (new Color(green,blue,alpha)));
				 * 			// Didn't work. The float colours end up all the same.
				 * I tried the following:
				 * agentsPortrayal.setPortrayalForObject (a, new sim.portrayal.simple.HexagonalPortrayal2D (Color.black));
				 * agentsPortrayal.setPortrayalForObject (b, new sim.portrayal.simple.OvalPortrayal2D (Color.red));
				 * agentsPortrayal.setPortrayalForObject (c, new sim.portrayal.simple.RectanglePortrayal2D (Color.green));
				 * 			// Didn't work. Only got green rectangles. The last statement always runs, not the others. WHY?
				 * 			// Possibly due to the @display.reset() and @display.repaint() running at the end. Not sure
				 */
				
				/* I tried the following also:
				 * agentsPortrayal.setPortrayalForObject (a, new sim.portrayal.simple.HexagonalPortrayal2D (new Color(red,green,blue,alpha)),
				 * 		b, new sim.portrayal.simple.RectanglePortrayal2D (new Color(green,blue,alpha)),
				 * 		c, new sim.portrayal.simple.OvalPortrayal2D (new Color(red,blue,alpha)));
				 * 			// Also didn't work. WHY?
				 * 			// Can use the following to set all agents green:
				 * 			// agentsPortrayal.setPortrayalForAll(new OvalPortrayal2D(Color.green));
				 */ 
			
			display.reset();
			// Call the predefined reset method for the display
			display.setBackdrop(Color.white);
			// Sets the display backdrop white
			display.repaint();
			// Call the repaint method
		}	
	}
			
		public void init(Controller c)	{
		super.init(c);
			// Use the predefined method to initialize the controller, c
			display = new Display2D(600,600,this,1);
			// Make the display 600x600 pixels. 
			displayFrame = display.createFrame();
			// Create the frame
			c.registerFrame(displayFrame);
			// Let the controller control the frame
			displayFrame.setVisible(true);
			// Make it visible
			display.setBackdrop(Color.white);
			// Make the background white
			display.attach(agentsPortrayal,"Agents Simulation");
			// Attach the display
			}
		
		public Object getSimulationInspectedObject()	{
			return state;				// This returns the simulation
		}
		
		public static void main(String[] args)	{
			GraphicalUI ex = new GraphicalUI();
			// Make the user interface and call it ex
			Console c = new Console(ex);
			// Make the console and give it the user interface display
			c.setVisible(true);			// make the console visible
			System.out.println("Start Simulation");
			// Print that the simulation has started
		}
}	// End of GraphicalUI class
