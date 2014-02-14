/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Author: Robert Thayer
Author email: rthayer@alum.ups.edu
Author twitter: @Kitsucoon
*/

package rollforinitiative;

// Player class designed to track individual players and NPCs and their initiative,
// overwatch, and whether they have used Seize the Initiative.
import java.util.Comparator;
import javax.swing.JOptionPane;

public class Player implements Comparable<Player>, java.io.Serializable
{
	private String name; // Holds name of player
	private int baseInit; // Base Initiative score. Used to break ties.
	private int totalInit; // Total Initiative score.
	private boolean npc; // Player character or NPC
	private boolean actionTaken; // Whether player has taken their action this round.
        private int numOfDice = 1; // For NPC characters, the number of +d6
        private int overwatch; // Track overwatch score.
        private boolean seize = false; // Determines if player is using Seize the Initiative.
	
	// Constructor for NPCs
	public Player(String name, int baseInit, int totalInit, int diceNumber, boolean seize)
	{
		this.name = name;
		this.baseInit = baseInit;
		this.totalInit = totalInit;
		this.npc = true;
		this.actionTaken = false;
                this.numOfDice = diceNumber;
                this.overwatch = 0;
                this.seize = seize;
	}
        
        // Constructor for PCs
        public Player(String name, int totalInit, boolean seize)
	{
		this.name = name;
		this.totalInit = totalInit;
		this.npc = false;
		this.actionTaken = false;
                this.overwatch = 0;
                this.seize = seize;
	}
        
        // getter for seize the initiative
        public boolean getSeize()
        {
            return this.seize;
        }
        
        // setter for seize the initiative
        public void setSeize(boolean x)
        {
            this.seize = x;
        }
	
        // setter for overwatch
        public void setOverwatch(int x)
        {
            this.overwatch = x;
        }
        
        // getter for overwatch
        public int getOverwatch()
        {
            return overwatch;
        }
        
        // setter for numOfDice
        public void setNumOfDice(int x)
        {
            this.numOfDice = x;
        }
        
        // method to modify overwatch score
        public void modOverwatch(int x)
        {
            overwatch += x;
        }
        
        // getter for numOfDice
        public int getNumOfDice()
        {
            return this.numOfDice;
        }
        
        // Checks player's overwatch score. If it has reached 40, display message
        // that player has reached convergence.
        public void converge()
        {
            if (this.overwatch >= 40)
            {
                JOptionPane.showMessageDialog(null, name + 
                        " has reached convergence at score " + overwatch + ".",
                        "Convergence", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        // method to modify num of dice
        public void modifyNumOfDice(int x)
        {
            this.numOfDice += x;
        }
        
        // method to modify the base initiative score
        public void modifyBase(int x)
        {
            this.baseInit += x;
        }
        
        // method to modify total initiative score
	public void modifier(int x)
	{
		this.totalInit += x;
	}
	
	// Getter for name
	public String getName()
	{
		return this.name;
	}
	
	// Setter for name
	public void setName(String name)
	{
		this.name = name;
	}
	
	// Getter for base initiative
	public int getBaseInit()
	{
		return this.baseInit;
	}
	
	// Setter for base initiative
	public void setBaseInit(int baseInit)
	{
		this.baseInit = baseInit;
	}
	
	// Getter for total initiative
	public int getTotalInit()
	{
		return this.totalInit;
	}
	
	// Setter for total initiative
	public void setTotalInit(int totalInit)
	{
		this.totalInit = totalInit;
	}
	
	// Getter for NPC field
	public boolean getNPC()
	{
		return npc;
	}
	
	// Setter for NPC
	public void setNPC(boolean npc)
	{
		this.npc = npc;
	}
	
	// Getter for actionTaken
	public boolean getActionTaken()
	{
		return actionTaken;
	}
	
	// Setter for actionTaken
	public void setActionTaken(boolean actionTaken)
	{
		this.actionTaken = actionTaken;
	}
		
	// Override the .toString method
	// Outputs [<Total Init Score>] <Name> B: <Base Score>
	// Example: [21] Gun Bunny B: 10
	@Override
	public String toString()
	{
		return "[" + totalInit + "] " + name;
	}
	
	// Override the compareTo
	@Override
	public int compareTo(Player c)
	{
		return this.totalInit - c.totalInit;
	}
	
	// Method to determine if two players are tied.
	public boolean isTied(Player c)
	{
		return (this.compareTo(c) == 0);
	}
		
	// Comparator classes
	public static class Comparators
	{
		// Sort by total initiative
		public static Comparator<Player> TOTAL = new Comparator<Player>()
		{
                    @Override
                    public int compare(Player o1, Player o2)
                    {
                        // Check to see if both or neither player has used "Seize the Initiative."
                        if (o1.seize && o2.seize || !o1.seize && !o2.seize)
                        {
                            int i = o2.totalInit - o1.totalInit;
                            return i;
                        }
                        // If player 1 has Seized but 2 hasn't, sort player 1 higher
                        else if (o1.seize && !o2.seize)
                        {
                            return -1;
                        }
                        // If player 2 has Seized, but 1 hasn't, sort player 2 higher
                        else 
                            return 1;
                    }
		};
	}
}