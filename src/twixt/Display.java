package twixt;

/**
 * The interface if displaying informations
 *
 */
public interface Display
{
	/**
	 *  Method called before AI execution
	 *
	 */
	public void prepareAICall();

	
	/**
	 *  Method called after AI execution
	 *
	 */
	public void finishAICall();

	/**
	 *  Method to display the adding tower
	 *
	 *  @param p the tower's coordinates
	 */
	public void addPoint(int[] p);

	/**
	 *  Method to display a win message
	 *
	 *  @param player the id of the winning player
	 */
	public void wonMessage(int player);

	/**
	 *  Method to display a draw message
	 *
	 */
	public void drawMessage();
}
