package logic;

import java.util.LinkedList;

public class Moves {
    
    private LinkedList<int[]> moveList;
    
    public Moves() {
        super();
        moveList = new LinkedList<int[]>();
    }

	@SuppressWarnings("unchecked")
	public Moves clone()
	{
		Moves m_clone = new Moves();
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
    public int getCurrentColor() {
        return getTurn()+1;
    }
    
    // 1 = yellow; 2 = red
    public int getColor(int moveNumber) {
        if (moveNumber % 2 == 0) {
            return 1;
        }
        else {
            return 2;
        }
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
 
}
