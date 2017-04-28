package twixt;

import logic.LogicManager;


public interface Player
{
  
		
	/**
	 *  Get move method (To override)
	 */
    public void getMove(LogicManager logicManager, SaveMove save);

	public String getName();
	
	public void setName(String newName);
}
