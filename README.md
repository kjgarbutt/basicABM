basicABM
========

My first Agent-Based Model. Adapted from www.agent-based-models.com/blog/ Very simple model: bouncing balls that change direction randomly when they hit the grid boundary or each other. Added a minority group that are a different colour and shape also. 


This tutorial is taken from: http://agent-based-models.com/ABM_Lab/PSC120/CreatingABM.html


Creating a Basic Agent-Based Model  

In Java, Classes (i.e., programs) are placed in packages.  To create a program in Java, we need at least one package that contains at least one class.  To create a basic agent-based model (ABM) in Java using MASON, we will need to create a project and then a package in that project, which will contain the Classes we need for an ABM simulation.

Let's call our project "basicABM" and the package "agents".  So, initially, our folder structure will be:
            
              basicABM
                       agents

When ABMs using MASON, we will need to write at least three Classes.  First, we need to write a Class for our agents.  It may be that we want several types of agents in which case we may want to write more than one class or use inheritance to build other agents from out basic agent.  But, for now, we only need one class and we will call it:

                                Agent.java

Second, we need an environment for simulating our agents.  In this Class we can specify how to start a simulation, how many agents there are, and other aspect of the agents' environment we would like to simulate.  This fill will be called:

                                AgentsSimulation.java

Third, we should define a graphical users interface.  This will allow us to easily control and display our simulation.  We will call this file:

                                GraphicalUI.java

Thus, our first basic agent-based model will have the following structure:

              basicABM
                       agents
                                Agent.java
                                AgentsSimulation.java
                                GraphicalUI.java

Let's create them in eclipse.

In Eclipse, let's first  make a new Java project.

1. Menu --> File --> New --> Project...

2. Choose "java Project" -->  click on "Next" button

3. Next to "Project name" enter "basicABM" -- click "Finish".

4. Menu --> File --> New -->  Package --> next to "name" enter, for
    example, "agents --> click "Finish".

5. Menu -->  New --> Class

6. Next to "name" enter, for example, "Agent.java".

7. Click "Finish"

8.  Menu -->  New --> Class

9. Next to "name" enter, for example, "AgentsSimulation.java".

10. Click "Finish"

11.  Menu -->  New --> Class

12. Next to "name" enter, for example, "GraphicalUI.java".

13. Click "Finish"


Adding MASON14

MASON14 (i.e., the 14th version) contains a number of classes and packages for making ABMs.  For example, we can create say 200 agents, but at each time step of a simulation, something has to control how they are to behave and the order in which the are to behave.  To do this, we need to integrate MASON into our simulation.  We will do this by importing the classes we need.

To do this, we first need to tell our project to use the MASON14 project.  To do this, select our project "basicABM" and  choose "properties" from the "File" menu.  Select "java Build Path" and use the "add" button to add the MASON14 project.

To add MASON classes to the simulation requires a lot of background knowledge about how MASON and Java works.  Perhaps the best way to learn how to do it is one step at a time.

To create an agent that MASON can use in a simulation, MASON will have to understand the interface we are using.  The best way to do that is to use an interface MASON uses for agents.  This interfaces is called "Steppable".

To do this, we need to import two classes from MASON14:

import sim.engine.Steppable;
import sim.engine.SimState;

Then we need to declare that our agent class implements the Steppable class and add the step method to our Agent class as illustrated below.

package agents;

import sim.engine.Steppable;
import sim.engine.SimState;
 
public class Agent implements Steppable{

 
       public void step(SimState state){
             
       }
}  // end of Agent class
Now, we have to specify how our agents will behave at each time step in a simulation.  Let's assume that they move in a 2-dimensional space and that they continue to move in the same direction until they hit a wall.  When they hit a wall, they bounce off and move in the opposite direction.   If they hit another agent(s), the agents change direction randomly.

To do this, it would be a good idea for each agent to store 5 pieces of data in variables that keep track of location, direction of movement, and whether to randomize movement.  Let's add these variables to our Agent class.

package agents;

import sim.engine.Steppable;
import sim.engine.SimState;
 
public class Agent implements Steppable{

        public int x;     // x coordinate of agent
        public int y;     // y coordinate of agent
        public int xdir;  // the number of spaces moved
                          //along the x-axis
                          // on each time step
        public int ydir;  // the number of spaces moved along
                          //the y-axis
                          // on each time step
        public boolean randomize = false;
        // if an agent collides with another
        //randomize the directions.

 
       public void step(SimState state){
             
       }
}  // end of Agent class
Now let's start writing the methods an agent will need to move around in its 2D world.  First consider what happens when it hits a wall (i.e., the boundaries of its 2D world).  What we want it to do is to bounce back and start moving in the opposite direction.  To do this let's write two methods hitx and hity, which handle the cases when an agent hits a wall along the x and y axises.


        public int hitx (int boundLow, int boundHigh, int x){
            if (x < boundLow) {
                x++;  // back up
                xdir = -xdir; // reverse direction
            }
            else if (x >= boundHigh) {
                x--; // back up
                xdir = -xdir; // reverse direction
            }              
            return x;          
        }
       
        public int hity (int boundLow, int boundHigh, int y){
             if (y < boundLow) {
                 y++;  // back up
                 ydir = -ydir; // reverse direction
             }
            else if (y >= boundHigh) {
               y--; // back up
               ydir = -ydir; // reverse direction
               }              
            return y;          
        }        

Both methods test for the low and high bounds along the x and y axises.  If a wall is hit along one or both dimensions, an agent backs up, and reverses direction along the axis.  We can add these methods to our Agent class.

package agents;

import sim.engine.Steppable;
import sim.engine.SimState;
 
public class Agent implements Steppable{

        public int x;     // x coordinate of agent
        public int y;     // y coordinate of agent
        public int xdir;  // the number of spaces moved
                          // along the x-axis
                          // on each time step
        public int ydir;  // the number of spaces moved
                          // along the y-axis
                          // on each time step
        public boolean randomize = false; // if an agent
                          // collides with another
                          //randomize the directions.

 
       public void step(SimState state){
             
       }

        public int hitx (int boundLow, int boundHigh, int x){
            if (x < boundLow) {
                x++;  // back up
                xdir = -xdir; // reverse direction
            }
            else if (x >= boundHigh) {
                x--; // back up
                xdir = -xdir; // reverse direction
            }              
            return x;          
        }
       
        public int hity (int boundLow, int boundHigh, int y){
             if (y < boundLow) {
                 y++;  // back up
                 ydir = -ydir; // reverse direction
             }
            else if (y >= boundHigh) {
               y--; // back up
               ydir = -ydir; // reverse direction
               }              
            return y;          
        }
}  // end of Agent class
Constructors

A new idea that we have not talked about, but that we have used is the idea creating a new object.  For example, when we created arrays, we used the special word new, which created an array of a specified length.  When we run agent-based models, we are going to create a number of agents, say, 100.  When we create each agent, we might want to give each one a unique location and each one a specific direction of movement.  To do so, we must write a specific constructor method, which is different from other methods in that its name is the name of the class.  For our agents, one constructor method could be this:


       public Agent(int x, int y, int xdir, int ydir){
           this.x = x;
           this.y = y;
           this.xdir = xdir;
           this.ydir = ydir;
        }

The special word this refers to the agent so that "x" variable in the agent is not confused with the "x" variable in the method's arguments.  Let's add this to our Agent class:

package agents;

import sim.engine.Steppable;
import sim.engine.SimState;
 
public class Agent implements Steppable{

        public int x;     // x coordinate of agent
        public int y;     // y coordinate of agent
        public int xdir;  // the number of spaces moved
                          // along the x-axis
                          // on each time step
        public int ydir;  // the number of spaces moved
                            // along the y-axis
                            // on each time step
        public boolean randomize = false; // if an agent
                            // collides with another
                             //randomize the directions.

        /*
         * Lets make an agent constructor method.
         *  "this" refers to the agent itself
         */      
 
         public Agent(int x, int y, int xdir, int ydir){
           this.x = x;
           this.y = y;
           this.xdir = xdir;
           this.ydir = ydir;
        } 

 
       public void step(SimState state){
             
       }

       public int hitx (int boundLow, int boundHigh, int x){
            if (x < boundLow) {
                x++;  // back up
                xdir = -xdir; // reverse direction
            }
            else if (x >= boundHigh) {
                x--; // back up
                xdir = -xdir; // reverse direction
            }              
            return x;          
        }
       
        public int hity (int boundLow, int boundHigh, int y){
             if (y < boundLow) {
                 y++;  // back up
                 ydir = -ydir; // reverse direction
             }
            else if (y >= boundHigh) {
               y--; // back up
               ydir = -ydir; // reverse direction
               }              
            return y;          
        }
}  // end of Agent class    
Step Method

To finish the program for our agents, we have to write the details of the predefined step method.  In our simulation, on each time step, each agent (in random order) will have its step method called.  When it is called the agent will do its thing, which is what ever we tell it to do.  So, let's write a simple step method.

        public void step(SimState state){
             AgentsSimulation as = (AgentsSimulation)state;
            // Convert state
            // into the AgentsSimulation that we will
            // soon write
             
             /*
              * Check for collisions!
              */   
               
              Bag bag = as.agentsSpace.getObjectsAtLocation(x, y);
               // get all the agents at x, y

        /*
        * A Bag is some thing that holds objects such as our agents.
        * We have to add an import statement to use Bags.
        */
             if (bag != null && bag.numObjs > 1) 
        // if there is more than 1, make them randomize
              for(int i=0;i<bag.numObjs;i++) {
                // For every agent in the bag
                   Agent a = (Agent)(bag.objs[i]);
                    // Convert it to an agent
                   a.randomize = true;
                    // Tell it to randomize its direction
               }

      
       /*
        * If there was a collision, randomize
        */
      
         if (randomize) {
                // if randomize is true, a collision occurred
               xdir = state.random.nextInt(3) - 1; //is -1, 0, or 1
               ydir = state.random.nextInt(3) - 1; //is -1, 0, or 1
               randomize = false;
                // randomize only once after a collision
         }
               
                int newx = x + xdir;// Change the x position by xdir
                int newy = y + ydir;// Change the y position by ydir
               
                newx = hitx(0,as.gridWidth,newx);
                    // Does it hit a wall
                    // along the x axis and make the
                                                                                                         // appropriate corrections
                newy = hity(0,as.gridWidth,newy);
                // Does it hit a wal in
                // along the y axis and make the
                // appropriate corrections
               
                /*
                 * Now move the agent
                 */
                as.agentsSpace.setObjectLocation(this, newx, newy);
                // set its new location  
                x = newx; // Set x to newx
                y = newy; // Set y to newy
        } // end step method

package agents;

import sim.engine.Steppable;
import sim.engine.SimState;
import sim.util.Bag;

public class Agent implements Steppable{

        public int x;     // x coordinate of agent
        public int y;     // y coordinate of agent
        public int xdir;  // the number of spaces moved
                          // along the x-axis
                          // on each time step
        public int ydir;  // the number of spaces moved
                            // along the y-axis
                            // on each time step
        public boolean randomize = false; // if an agent
                            // collides with another
                             //randomize the directions.

        /*
         * Lets make an agent constructor method.
         *  "this" refers to the agent itself
         */      
 
         public Agent(int x, int y, int xdir, int ydir){
           this.x = x;
           this.y = y;
           this.xdir = xdir;
           this.ydir = ydir;
        }
 
        public void step(SimState state){
             AgentsSimulation as = (AgentsSimulation)state;
            // Convert state
            // into the AgentsSimulation that we will
            // soon write
             
             /*
              * Check for collisions!
              */   
               
              Bag bag = as.agentsSpace.getObjectsAtLocation(x, y);
               // get all the agents at x, y

        /*
        * A Bag is some thing that holds objects such as our agents.
        * We have to add an import statement to use Bags.
        */
             if (bag != null && bag.numObjs > 1) 
        // if there is more than 1, make them randomize
              for(int i=0;i<bag.numObjs;i++) {
                // For every agent in the bag
                   Agent a = (Agent)(bag.objs[i]);
                    // Convert it to an agent
                   a.randomize = true;
                    // Tell it to randomize its direction
               }

      
       /*
        * If there was a collision, randomize
        */
      
         if (randomize) {
                // if randomize is true, a collision occurred
               xdir = state.random.nextInt(3) - 1; //is -1, 0, or 1
               ydir = state.random.nextInt(3) - 1; //is -1, 0, or 1
               randomize = false;
                // randomize only once after a collision
         }
               
                int newx = x + xdir;// Change the x position by xdir
                int newy = y + ydir;// Change the y position by ydir
               
                newx = hitx(0,as.gridWidth,newx);
                    // Does it hit a wall
                    // along the x axis and make the
                    // appropriate corrections
                newy = hity(0,as.gridWidth,newy);
                // Does it hit a wal in
                // along the y axis and make the
                // appropriate corrections
               
                /*
                 * Now move the agent
                 */
                as.agentsSpace.setObjectLocation(this, newx, newy);
                // set its new location  
                x = newx; // Set x to newx
                y = newy; // Set y to newy
        } // end step method

       
        public int hitx (int boundLow, int boundHigh, int x){
            if (x < boundLow) {
                x++;  // back up
                xdir = -xdir; // reverse direction
            }
            else if (x >= boundHigh) {
                x--; // back up
                xdir = -xdir; // reverse direction
            }              
            return x;          
        }
       
        public int hity (int boundLow, int boundHigh, int y){
             if (y < boundLow) {
                 y++;  // back up
                 ydir = -ydir; // reverse direction
             }
            else if (y >= boundHigh) {
               y--; // back up
               ydir = -ydir; // reverse direction
               }              
            return y;          
        }
}   // end of Agent class   
One last thing we will need to have done is to add the following import statement:

import sim.util.Bag;


Simulation Environment

The second class we must define is simulation environment for our agents.  After creating this new class we have:

package agents;

public class AgentsSimulation {

       public static void main(String[] args) {       
               
        }
}

To turn this into a simulation environment and to take advantage of the classes already defined in MASON14, we are going to extend a class already defined in MASON14.  By extending a class, we use what is already written and we add additional code to it.  It is a nice way to reuse good code without having to rewrite it for every ABM we want to build.  The class we want to extend is called SimState and we will import it from MASON14 using the following import statement:

import sim.engine.SimState;

In this case, we also have to write a new constructor method.  All we want to do at this point is to use the previously defined one and pass it a random number "seed".  To reuse a previously defined method, we use the special word super.

        public AgentsSimulation(long seed) {
                super(seed); //use the constructor for SimState
        }

This gives us:

package agents;

import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
 
public class AgentsSimulation extends SimState {
    //extend the class SimState

       
        public AgentsSimulation(long seed) {
             super(seed); //use the constructor for SimState
        }


        public static void main(String[] args) {
            doLoop(AgentsSimulation.class, args);
            //doLoop is a static method
             //already defined in SimState
            System.exit(0);//Stop the program when finished.
        }

Next, we need to specify the data variables we need for this simulation.  We need to specify the 2D space, its width, height, and the number of agents to be placed in this space.

        public SparseGrid2D agentsSpace; // 2D space for agents
        public int gridWidth = 100; // width of the space
        public int gridHeight = 100; // Height of the space
        public int n = 100; //number of agents

Clearly, in more complicated simulations, we will need more data, but this is enough for now.  If we add these, we get:

package agents;

import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
 
public class AgentsSimulation extends SimState {
    //extend the class SimState

        public SparseGrid2D agentsSpace; // 2D space for agents
        public int gridWidth = 100; // width of the space
        public int gridHeight = 100; // Height of the space
        public int n = 100; //number of agents
       
        public AgentsSimulation(long seed) {
             super(seed); //use the constructor for SimState
        }


        public static void main(String[] args) {
            doLoop(AgentsSimulation.class, args);
            //doLoop is a static method
             //already defined in SimState
            System.exit(0);//Stop the program when finished.
        }
}  // end AgentsSimulation class
We can finish up writing this class by giving it a start method.  What we will do is reuse the start method already defined for SimState and add our own code after it.

        public void start(){
           super.start(); // reuse the SimState start method
                         // Now add our own code

           agentsSpace = new SparseGrid2D(gridWidth, gridHeight);
                                // create a 2-D space
               
                /*
                 * Now create the agents and put them in the space.
                 */

            for(int i=0;i<n;i++){ // 0 to the number of agents - 1
               int x = random.nextInt(gridWidth);
                // a random number < gridWidth
               int y = random.nextInt(gridHeight);
                    //a random number < gridHeight
               int xdir = random.nextInt(2) - 1;
                    //in the range -1 to 1
               int ydir = random.nextInt(2) - 1;
                    //in the range -1 to 1
               Agent a = new Agent(x,y,xdir,ydir);
               agentsSpace.setObjectLocation(a, x,y);
                    //put it at location x, y
               schedule.scheduleRepeating(a);
                    // schedule it to repeat inthe simulation
                }
        }

That is it for a very simple simulation of agents.

package agents;

import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
 
public class AgentsSimulation extends SimState {
    //extend the class SimState

        public SparseGrid2D agentsSpace; // 2D space for agents
        public int gridWidth = 100; // width of the space
        public int gridHeight = 100; // Height of the space
        public int n = 100; //number of agents
       
        public AgentsSimulation(long seed) {
             super(seed); //use the constructor for SimState
        }
       
        public void start(){
           super.start(); // reuse the SimState start method
                         // Now add our own code

           agentsSpace = new SparseGrid2D(gridWidth, gridHeight);
                                // create a 2-D space
               
                /*
                 * Now create the agents and put them in the space.
                 */

            for(int i=0;i<n;i++){ // 0 to the number of agents - 1
               int x = random.nextInt(gridWidth);
                // a random number < gridWidth
               int y = random.nextInt(gridHeight);
                    //a random number < gridHeight
               int xdir = random.nextInt(2) - 1;
                    //in the range -1 to 1
               int ydir = random.nextInt(2) - 1;
                    //in the range -1 to 1
               Agent a = new Agent(x,y,xdir,ydir);
               agentsSpace.setObjectLocation(a, x,y);
                    //put it at location x, y
               schedule.scheduleRepeating(a);
                    // schedule it to repeat inthe simulation
                }
        }

        public static void main(String[] args) {
            doLoop(AgentsSimulation.class, args);
            //doLoop is a static method
             //already defined in SimState
            System.exit(0);//Stop the program when finished.
        }
}   // end AgentsSimulation class

Graphical User Interface

This is one of the most important components of an ABM and it is also the most dependent on MASON14.  It is time consuming to explain, so for now, I will provide a template that we will modify as needed for this and future simulations.

The class GraphicalUI allows us to run AgentsSimulation so that we can see the agents move around and to control the simulation in different ways.  As with AgentsSimulation, we need to extend an already defined class in MASON14 called GUIState.

package agents;

import java.awt.Color;
import javax.swing.JFrame;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.*;
import sim.portrayal.grid.*;
import sim.util.Bag;
 
public class GraphicalUI extends GUIState{
       
        public Display2D display; // variable for the graphic display
        public JFrame displayFrame; // for the display frame
        SparseGridPortrayal2D agentsPortrayal = new
            SparseGridPortrayal2D();
          // Graphic portrayal of the agents in 2D space           

         protected GraphicalUI() { // Constructor for this class
         super(new AgentsSimulation(System.currentTimeMillis()));
                         // Create a simulation in it
        }

 
        public GraphicalUI(SimState state) {
            super(state);
            // Pass the already created simulation to it
            }
 
        public static String getName() {
            return "Agents Simulation";
                   // return a name for what this simulation is about
             }
 
        public void quit() {
             super.quit(); // Use the already defined quit method
               
             if (displayFrame!=null) displayFrame.dispose();
                displayFrame = null; 
                // when quiting get rid of the display
                display = null;      
        }
 
        public void start() {
              super.start(); // use the predefined start method
               setupPortrayals(); // add setupPortrayals method below
        }

           
        public void load(SimState state) {
            super.load(state);
            // load the simulation into the interface
            setupPortrayals(); // call setuuPortrayals
        }


          /*
           * This is the one method we will change the most for each
.          *  simulation.  Specifically, we determine what agents
           * look like. 
           */
              
        public void setupPortrayals() {
            agentsPortrayal.setField(((AgentsSimulation)state).agentsSpace);
     // Here we give the agentsProtrayal, the agentsSpace so that it
             // can graphically represent it.
Bag agents = ((AgentsSimulation)state).agentsSpace.getAllObjects();              //get all the agents
       for(int i=0; i<agents.numObjs;i++){// for all of the agents
               Agent a = (Agent)agents.get(i);
                    //get each agent from 0 to n

        /*
         * Now we are going to use RGB colors to set the color
.        * of our agents.  The alpha level determines how 
         * transparent the agent is. 
         */

               float red = (float)1.0;                             
               float green = (float)0.0;
               float blue = (float)0.0;
               float alpha = (float)1.0;
               agentsPortrayal.setPortrayalForObject (a, newsim.portrayal.simple.OvalPortrayal2D (new Color(red,green,blue,alpha)));      
            }
    // This complicated statement makes an oval portrayal of
             // a given color
            display.reset();
            // call the predefined reset method for the display
            display.repaint(); // call the repaint method
         }
 
         public void init(Controller c){
         super.init(c); 
            // use the predefined method to initialize the
               // controller, c
             display = new Display2D(400,400,this,1);
            // make the display
         // that is 400 x 400 pixels.  You can set it to other values
             displayFrame = display.createFrame();
                // create the frame
             c.registerFrame(displayFrame);  
                // let the controller control the
              //frame
             displayFrame.setVisible(true);  // make it visible
             display.setBackdrop(Color.black);
                // make the background black
             display.attach(agentsPortrayal,"Agents Simulation");
                // attach the display
                }
           
        public Object getSimulationInspectedObject() {
              return state; // This returns the simulation
         }

       
         public static void main(String[] args) {
               GraphicalUI ex = new GraphicalUI();
            // make the user interface
                // and call it ex
                Console c = new Console(ex);
            // make the console and give it
                 // the user interface to display
                c.setVisible(true); // make the console visible
                System.out.println("Start Simulation");
            // Print that the
                  // simulation started
                }
} // end GraphicalUI class



