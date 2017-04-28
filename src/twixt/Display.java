package twixt;

/**
 * The interface for displaying information
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
	 * Switch players
	 */
	public void switchPlayers();

	/**
	 *  Method to display a win message
	 *
	 *  @param playerIndex the index of the winning player
	 *  @param player the winning player
	 * @return TODO
	 */
	public int wonMessage(int playerIndex, Player player);

	/**
	 *  Method to display a draw message
	 * @return TODO
	 *
	 */
	public int drawMessage();
}
