package ai;

import java.util.*;
import logic.*;

/**
 *  Baseline AI class
 *
 *  this class implements a random algorithm to choose the cell
 */
public class RandomConnectionAI extends AbstractAI {

	
	/**
	 *  Default constructor
	 *
	 */
	public RandomConnectionAI()
	{
			super("Random Connection AI");
	}

		
	@Override
    public int[] chooseMove(LogicManager logicManager) 
	{
		int myColor = logicManager.getCurrentColor();
		int[][] board = logicManager.getBoard();
		
		// Get all my current pegs
		List<int[]> myPegs = new LinkedList<int[]>();
		for (int i = 0; i < logicManager.getBoardSize(); i++) {
			for (int j = 0; j < logicManager.getBoardSize(); j++) {
				if (board[i][j] == myColor) {
					int[] p = {i, j};
					myPegs.add(p);
				}
			}
		}
		
		// Build all possible connections (vertical and horizontal connections are distinguished)
		List<int[]> forwardConnectingSuccessors = new LinkedList<int[]>();
		List<int[]> sideConnectingSuccessors = new LinkedList<int[]>();
		int[][] knightMoves = {{-2,-1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
		for (int[] p: myPegs) {
			for (int[] delta: knightMoves) {
				int[] possibleSuccessor = {p[0] + delta[0], p[1] + delta[1]};
				if (logicManager.isPossibleConnection(p, possibleSuccessor, myColor)
						&& logicManager.isPossibleMove(possibleSuccessor, myColor)) {
					if (Math.abs(delta[0]) > Math.abs(delta[1])) {
						if (myColor == 1) {
							forwardConnectingSuccessors.add(possibleSuccessor);
						}
						else {
							sideConnectingSuccessors.add(possibleSuccessor);
						}
					}
					else {
						if (myColor == 1) {
							sideConnectingSuccessors.add(possibleSuccessor);
						}
						else {
							forwardConnectingSuccessors.add(possibleSuccessor);
						}
					}
				}
			}
		}
		
		// Try forward connections first
		if (!forwardConnectingSuccessors.isEmpty()) {
			int selectedIndex = (int) Math.floor(Math.random()*forwardConnectingSuccessors.size());
			return forwardConnectingSuccessors.get(selectedIndex);
		}
		// Try side connections
		else if (!sideConnectingSuccessors.isEmpty()) {
			int selectedIndex = (int) Math.floor(Math.random()*sideConnectingSuccessors.size());
			return sideConnectingSuccessors.get(selectedIndex);
		}
		// Play any possible move otherwise
		else {
			List<int[]> allMoves = logicManager.getAllPossibleMoves();
			int selectedIndex = (int) Math.floor(Math.random()*allMoves.size());
			return allMoves.get(selectedIndex);
		}
		
    }

}
