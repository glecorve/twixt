package logic;

import java.util.LinkedList;

public class Connections {

    private LinkedList<int[]> connectionList;
    
    public Connections() {
        super();
        connectionList = new LinkedList<int[]>();    
    }

	@SuppressWarnings("unchecked")
	public Connections clone()
	{
		Connections c = new Connections();
		c.connectionList = (LinkedList<int[]>) connectionList.clone();
		return c;
	}
	
    // add a connection (startpoint, endpoint)
    public void add(int row1, int col1, int row2, int col2) {
        connectionList.addLast(new int[] {row1, col1, row2, col2});
    }
    
    // add a connection (startpoint, endpoint)
    public void add(int[] point1, int[] point2) {
        add(point1[0], point1[1], point2[0], point2[1]);
    }
    
    // remove all connections starting or ending at point (row, col)
    public void remove(int row, int col) {
       remove(new int[] {row, col});
    }
    
    // remove all connections starting or ending at point
    public void remove(int[] point) {
        int[] con;
        int i = 0;
        while (i < connectionList.size()) {
            con = connectionList.get(i);
            if ((con[0] == point[0] && con[1] == point[1]) || (con[2] == point[0] && con[3] == point[1])) {
                connectionList.remove(i);
            } else {
                ++i;
            }
        }
    }
    
    public int getSize() {
        return connectionList.size();
    }

    public boolean hasConnection(int row1, int col1, int row2, int col2) {
        for (int i = 0; i < getSize(); ++i) {
            int[] t = connectionList.get(i);
            if ((t[0] == row1 && t[1] == col1 && t[2] == row2 && t[3] == col2)
                    || (t[0] == row2 && t[1] == col2 && t[2] == row1 && t[3] == col1)) {
                return true;
            }
                
        }
        return false;
    }
    
    public int[] getConnection(int i) {
        return connectionList.get(i);
    }
    
}
