package twixt;

import java.util.Arrays;

public class SaveMove
{
  
	private int[] p;

	/**
	 *   Constructor
	 */
	public SaveMove()
	{
		p = null;
	}

	/**
	 *   Setter of the coordinates (used by the AI algorithm)
	 *
	 *   @param p the coordinates
	 */
	public void setP(int[] p)
	{
		this.p = p;
	}
	
	/**
	 *   Flush the save coordinates
	 */
	public void resetP()
	{
		this.p = null;
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
		if (p == null || p.length  != 2)
			throw new MoveNotSetException();

		/* Return the point */
		return p;
	}

	@Override
	public String toString() {
		return "SaveMove [p=" + Arrays.toString(p) + "]";
	}
}
