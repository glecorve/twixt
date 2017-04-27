package ai;

import logic.Connections;
import java.util.*;
import logic.*;

/**
 *  Baseline AI class
 *
 *  this class implements a random algorithm to choose the cell
 */
public class RandomAI extends AbstractAI {

	
	/**
	 *  Default constructor
	 *
	 */
	public RandomAI()
	{
			super("Random AI");
	}

		
	@Override
    public int[] chooseMove(LogicManager logicManager) 
	{
		return chooseMove(logicManager.getMoves().getCurrentColor(), logicManager.getBoard().clone(),
				logicManager.cloneConnections(logicManager.getMoves().getCurrentColor()),
				logicManager.cloneConnections(logicManager.getMoves().getColor()));
    }

	/**
	 *  Get a random valid point
	 *
	 *  @param playerIndex playerIndex index (1 or 2)
	 *  @param board board (board[i][j] = 0 => free, board[i][j] = 1||2 => occupied)
	 *  @param myConnections current player connections
	 *  @param opConnections opposite player connections
	 *  @return random valid chosen point
	 */
	public int[] chooseMove(int mycolor, int[][] board, Connections myConnections, Connections opConnections) 
	{
        int[] point = new int[2];
        // point[0] represents the row you want to place your peg (values from 0 to getBoardSize()-1)
        // point[1] represents the column you want to place your peg (values from 0 to getBoardSize()-1)
        Random rand = new Random();
        
		do
		{
			point[0] = rand.nextInt(board.length);
			point[1] = rand.nextInt(board.length);
		} while (!LogicManager.isPossibleMove(board, point, mycolor));
		

//		try
//		{
//			Thread.sleep((int)(Math.random() * (500)));
//		}
//		catch (InterruptedException ex)
//		{
//		}

		return point;
    }
	
}
