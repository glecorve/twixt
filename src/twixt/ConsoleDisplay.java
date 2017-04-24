package twixt;
/* @(#)ConsoleDisplay.java
 */
/**
 *
 *
 * @author <a href="mailto:lemaguer@">Sébastien Le Maguer</a>
 */

public class ConsoleDisplay implements Display
{
  
	/**
	 *  Method called before AI execution
	 *
	 */
	public void prepareAICall()
	{
	}

	
	/**
	 *  Method called after AI execution
	 *
	 */
	public void finishAICall()
	{
	}

	/**
	 *  Method to display the adding tower
	 *
	 *  @param p the tower's coordinates
	 */
	public void addPoint(int[] p)
	{
		System.out.println("Ajout du point (" + p[0] + ", " + p[1] + ")");
	}

	/**
	 *  Method to display a win message
	 *
	 *  @param player the id of the winning player
	 */
	public void wonMessage(int player)
	{
		
		System.out.println("==> vainqueur joueur "+ player);
	}

	/**
	 *  Method to display a draw message
	 *
	 */
	public void drawMessage()
	{
		System.out.println("==> égalité\n");
	}
}
