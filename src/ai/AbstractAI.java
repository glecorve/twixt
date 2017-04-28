package ai;

import twixt.*;
import logic.*;

/**
 *  Abstract class to set the structure of an AI player
 */

public abstract class AbstractAI implements Player {
	protected String name;
	private SaveMove chosenMove;

	
	/**
	 *  Default constructor
	 *
	 */
	public AbstractAI()
	{
		this.name = this.getClass().getName();
		chosenMove = null;
	}
	
	/**
	 *  Default constructor
	 *
	 */
	public AbstractAI(String _name)
	{
		this.name = _name;
		chosenMove = null;
	}

		
	/**
	 *  Get move method as called by the game's main controller
	 *
	 *  @param logicManager the logicManager to access to the game status
	 *  @param saveMove the object used to save the chosen move
	 */
    public final void getMove(LogicManager logicManager, SaveMove saveMove) {
    	chosenMove = saveMove;
    	chosenMove.setP(chooseMove(logicManager));
    }
    
    
    /**
	 *  Choose a move (To be implemented)
	 *
	 *  @param logicManager the logicManager to access to the game status
	 */
    public abstract int[] chooseMove(LogicManager logicManager);

	
	/**
	 *  Get the name of the current player (classname)
	 *
	 *  @return the name of the current player
	 */
	public final String getName()
	{
		return name;
	}
	
	public final void setName(String newName) {
		name = newName;
	}
	
	
	/**
	 * Return the currently saved move
	 * 
	 * @return A point
	 */
	protected final SaveMove getChosenMove() {
		return chosenMove;
	}

	
	/**
	 * Save a given point to be played
	 * @param point a point
	 */
	protected final void saveMove(int[] point) {
		this.chosenMove.setP(point);
	}
	
	
}
