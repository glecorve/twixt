package twixt;

import java.util.Arrays;

public class SaveMove
{
  
	private int[] p;
	protected boolean isSet; /* Set flag */

	/**
	 *   Constructor
	 */
	public SaveMove()
	{
		isSet = false;
		p = null;
	}

	/**
	 *   Setter of the coordinates (used by the AI algorithm)
	 *
	 *   @param p the corrdinates
	 */
	public void setP(int[] p)
	{
		this.p = p;
		isSet = true;
	}

	/**
	 *   Getter of the coordinates (used by the game loop)
	 *
	 *   @return coordinates
	 *   @throws MoveNotSetException if the move was not set
	 */
	public int[] getP() throws MoveNotSetException
	{
		/* No defined point */
		if ((!isSet) || (p == null) || (p.length  != 2))
			throw new MoveNotSetException();


		/* Return the point */
		isSet = false;
		return p;
	}

	@Override
	public String toString() {
		return "SaveMove [p=" + Arrays.toString(p) + ", isSet=" + isSet + "]";
	}
}
