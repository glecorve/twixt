package logic;

import java.util.LinkedList;

public class Moves {
    
    private LinkedList<int[]> moveList;
    private boolean swap; // false = player 1 is white, top down; true = player1 is black, left right
    
    public Moves() {
        super();
        moveList = new LinkedList<int[]>();
        swap = false;
    }

	@SuppressWarnings("unchecked")
	public Moves clone()
	{
		Moves m_clone = new Moves();
		m_clone.swap = swap;
		m_clone.moveList = (LinkedList<int[]>) moveList.clone();
		return m_clone;
	}
    // add a move
    public void add(int row, int col) {
        add(new int[] {row, col});
    }
    
    // add a move
    public void add(int[] point) {
        moveList.addLast(point);
        if (point[0] == 0 && point[1] == 0)
            swap = true;
    }
    
    // remove last move
    public void remove() {
        moveList.removeLast();
    }
    
    // starting with 0
    // returns < row; col >
    public int[] getMove(int moveNumber) {
        return moveList.get(moveNumber);
    }
    
    public int[] getMove() {
//		System.out.println("taille = " + moveList.size());
        return moveList.getLast();
    }
    
    // 0 = player who played the first peg
    public int getPlayer(int moveNumber) {
        return moveNumber % 2;
    }
    
    // player who played last peg
    public int getPlayer() {
        return getPlayer(moveList.size() + 1);
    }
    
    // which player's turn it is
    public int getTurn() {
        return getPlayer(moveList.size());
    }
    
    // color of the player whose turn it is
    public int getColorOnTurn() {
        if ((getTurn() == 0 && !swap) || (getTurn() == 1 && swap))
            return 1;
        else
            return 2;
    }
    
    // 0 = swap; 1 = white; 2 = black
    public int getColor(int moveNumber) {
        if (swap && moveNumber == 1)
            return 0;
        else if (moveNumber == 0 || (moveNumber % 2 == 0 && !swap) || (moveNumber % 2 == 1 && swap))
            return 1;
        else
            return 2;
    }
    
    public int getColor() {
        return getColor(moveList.size() - 1);
    }
    
    public int getSize() {
        return moveList.size();
    }
    
    public boolean isFree(int[] point) {
        for (int i = 0; i < moveList.size(); ++i) {
            if (moveList.get(i).equals(point)) return false;
        }
        return true;
    }
    
    public boolean getSwap() {
        return swap;
    }
 
}
