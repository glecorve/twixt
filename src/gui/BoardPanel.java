package gui;

import java.awt.*;
import java.awt.event.*;
import twixt.SaveMove;
import javax.swing.JPanel;

import logic.LogicManager;

/**
 *  The JPanel where the board is painted
 *
 *  This class implements the MouseListener interface in order to access to private variables
 */
public class BoardPanel extends JPanel 
	implements MouseListener
{
    private static final long serialVersionUID = 1L;
	private boolean human_turn; /*< Human turn boolean */
    private GameFrame gameFrame; /*< The frame which contains the panel */
    int[] p; /*< Last Human set point coordinates */

	/**
	 *  Constructor
	 *
	 *  @param gf Parent frame 
	 */
    public BoardPanel(GameFrame gf) {
		p = null;
        gameFrame = gf;
		human_turn = false;
        setBackground(Constants.bgColor);
        setPreferredSize(Constants.boardSize);
        addMouseListener(this);
    }

	/**
	 *  Useless
	 *  
	 *  @param e
	 */
	public void mousePressed(MouseEvent e) {}

	/**
	 *  Useless
	 *  
	 *  @param e
	 */
	public void mouseReleased(MouseEvent e) {}

	/**
	 *  Useless
	 *  
	 *  @param e
	 */
	public void mouseEntered(MouseEvent e) {}

	/**
	 *  Useless
	 *  
	 *  @param e
	 */
	public void mouseExited(MouseEvent e) {}

	/**
	 *  Click event. Only used when it's the human turn
	 *
	 *  @param e the mouse event to access to coordinates
	 */
	public synchronized void mouseClicked(MouseEvent e) 
	{
		if (human_turn)
		{
			if (e.getPoint().x > Constants.offSet &&
				e.getPoint().y > Constants.offSet &&
				e.getPoint().x < Constants.boardSize.width - Constants.offSet &&
				e.getPoint().y < Constants.boardSize.height -  Constants.offSet) 
			{
				p = new int[2];
				p[0] = (e.getPoint().y - Constants.offSet) / Constants.cell;
				p[1] = (e.getPoint().x - Constants.offSet) / Constants.cell;
				this.notify();
			}
		}
	}

	/**
	 * Painting board function
	 *
	 *  @param g the graphic
	 */
    public void paint(Graphics g)
	{
		super.paint(g);
        g.setColor(Constants.boarderColor);
        g.fillRect(0, 0, Constants.boardSize.width, Constants.offSet);
        g.fillRect(0, 0, Constants.offSet, Constants.boardSize.height);
        g.fillRect(0, Constants.boardSize.height - Constants.offSet, Constants.boardSize.width, Constants.offSet);
        g.fillRect(Constants.boardSize.width - Constants.offSet, 0, Constants.offSet, Constants.boardSize.height);
        g.setColor(Constants.textColor);
        g.drawRect(0, 0, Constants.boardSize.width, Constants.boardSize.height);
        g.drawRect(1, 1, Constants.boardSize.width - 2, Constants.boardSize.height - 2);
        g.drawRect(3, 3, Constants.boardSize.width - 6, Constants.boardSize.height - 6);
        
        for (int i = 0; i < LogicManager.getBoardSize(); ++i) {
            char[] c = new char[] {(char) ('A' + i)};
            g.drawChars(c, 0, 1, Constants.offSet + 8 + i * Constants.cell, 30);
            g.drawChars(c, 0, 1, Constants.offSet + 8 + i * Constants.cell, 30 + Constants.boardSize.height - Constants.offSet);
        }
        for (int i = 0; i < Math.min(LogicManager.getBoardSize(), 9); ++i) {
            char[] c = new char[] {(char) ('1' + i)};
            g.drawChars(c, 0, 1, LogicManager.getBoardSize()-1, Constants.offSet + 17 + i * Constants.cell);
            g.drawChars(c, 0, 1, 21 + Constants.boardSize.width - Constants.offSet, Constants.offSet + 17 + i * Constants.cell);
        }
        for (int i = 9; i < LogicManager.getBoardSize(); ++i) {
            char[] c = new char[] {(char) ('1' + i / 19) ,(char) ('0' + (i + 1) % 10)};
            g.drawChars(c, 0, 2, 21, Constants.offSet + 17 + i * Constants.cell);
            g.drawChars(c, 0, 2, 17 + Constants.boardSize.width - Constants.offSet, Constants.offSet + 17 + i * Constants.cell);
        }
        g.setColor(Constants.p1boarder);
        g.fillPolygon(new int[] {Constants.offSet,
                Constants.boardSize.height - Constants.offSet,
                Constants.boardSize.height - Constants.offSet - Constants.cell,
                Constants.offSet + Constants.cell},
                new int [] {Constants.offSet,
                Constants.offSet,
                Constants.offSet + Constants.cell,
                Constants.offSet + Constants.cell}, 4);
        g.fillPolygon(new int[] {Constants.offSet,
                Constants.boardSize.height - Constants.offSet,
                Constants.boardSize.height - Constants.offSet - Constants.cell,
                Constants.offSet + Constants.cell},
                new int [] {Constants.boardSize.width - Constants.offSet,
                Constants.boardSize.width - Constants.offSet,
                Constants.boardSize.width - Constants.offSet - Constants.cell,
                Constants.boardSize.width - Constants.offSet - Constants.cell}, 4);       
        g.setColor(Constants.p2boarder);
        g.fillPolygon(new int [] {Constants.offSet,
                Constants.offSet,
                Constants.offSet + Constants.cell,                
                Constants.offSet + Constants.cell},
                new int[] {Constants.offSet,
                Constants.boardSize.height - Constants.offSet,
                Constants.boardSize.height - Constants.offSet - Constants.cell,
                Constants.offSet + Constants.cell}, 4);
        g.fillPolygon(new int [] {Constants.boardSize.width - Constants.offSet,
                Constants.boardSize.width - Constants.offSet,
                Constants.boardSize.width - Constants.offSet - Constants.cell,
                Constants.boardSize.width - Constants.offSet - Constants.cell},
                new int[] {Constants.offSet, Constants.boardSize.height - Constants.offSet,
                Constants.boardSize.height - Constants.offSet - Constants.cell,
                Constants.offSet + Constants.cell}, 4);
        g.setColor(Constants.textColor);
        g.drawRect(Constants.offSet, Constants.offSet, Constants.boardSize.width - 2 * Constants.offSet, Constants.boardSize.height - 2 * Constants.offSet);
        g.setColor(Constants.guideColor);
        g.drawPolygon(new int[] {Constants.offSet + Constants.cell * 3 / 2,
                Constants.boardSize.width - Constants.offSet - Constants.cell * 3 / 2,
                Constants.offSet + Constants.cell * 3 / 2,
                Constants.boardSize.width / 2,
                Constants.boardSize.width - Constants.offSet - Constants.cell * 3 / 2,
                Constants.offSet + Constants.cell * 3 / 2,
                Constants.boardSize.width - Constants.offSet - Constants.cell * 3 / 2,
                Constants.boardSize.width / 2},
                new int[] {Constants.offSet + Constants.cell * 3 / 2,
                Constants.boardSize.height / 2,
                Constants.boardSize.height - Constants.offSet - Constants.cell * 3 / 2,
                Constants.offSet + Constants.cell * 3 / 2,
                Constants.boardSize.height - Constants.offSet - Constants.cell * 3 / 2,
                Constants.boardSize.height / 2,
                Constants.offSet + Constants.cell * 3 / 2,
                Constants.boardSize.height - Constants.offSet - Constants.cell * 3 / 2}, 8);
				
        int board[][] = gameFrame.logicManager.getBoard();
        for(int i = 0; i < LogicManager.getBoardSize(); ++i) {
            for (int j = 0; j < LogicManager.getBoardSize(); ++j) {
//				System.out.print(board[i][j] + "\t");
                if (i % LogicManager.getBoardSize()-1 != 0 || j % LogicManager.getBoardSize()-1 != 0) {
                    if (board[i][j] == 0) {
                        g.setColor(Constants.holeColor);
                        g.fillOval(Constants.offSet + (Constants.cell - Constants.hole) / 2 + j * Constants.cell, Constants.offSet + (Constants.cell - Constants.hole) / 2 + i * Constants.cell, Constants.hole, Constants.hole);
                    } else {
                        if (board[i][j] == 1)
                            g.setColor(Constants.p1Color);
                        else
                            g.setColor(Constants.p2Color);
                        g.fillOval(Constants.offSet + (Constants.cell - Constants.peg) / 2 + j * Constants.cell, Constants.offSet + (Constants.cell - Constants.peg) / 2 + i * Constants.cell, Constants.peg, Constants.peg);
                    }
                }
            }
//			System.out.println("");
        }
		
        for (int j = 1; j <= 2; ++j) {
            if (j == 1)
                g.setColor(Constants.p1Color);
            else
                g.setColor(Constants.p2Color);
            for(int i = 0; i < gameFrame.logicManager.getConnections(j).getSize(); ++i) {
                int[] c = gameFrame.logicManager.getConnections(j).getConnection(i);
                g.fillPolygon(new int[] {Constants.offSet + Constants.cell / 2 + c[1] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[1] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[1] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[3] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[3] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[3] * Constants.cell - 1},
                        new int[] {Constants.offSet + Constants.cell / 2 + c[0] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[0] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[0] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[2] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[2] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[2] * Constants.cell - 1}, 6);
                g.fillPolygon(new int[] {Constants.offSet + Constants.cell / 2 + c[1] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[1] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[1] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[3] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[3] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[3] * Constants.cell + 1},
                        new int[] {Constants.offSet + Constants.cell / 2 + c[0] * Constants.cell - 1,
                        Constants.offSet + Constants.cell / 2 + c[0] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[0] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[2] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[2] * Constants.cell + 1,
                        Constants.offSet + Constants.cell / 2 + c[2] * Constants.cell - 1}, 6); 
            }
        }
    } // paint


	/**
	 *  This function correspond of the selection of a cell by a human
	 *
	 *  @param save the save move class to store the new move
	 */
	public synchronized void getMove(SaveMove save)
	{
		human_turn = true;

		try
		{
			this.wait();
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
			System.exit(-1);
		}
		save.setP(p);

		
		human_turn = false;
	}


	/**
	 *  This function is used to adding the point to the current panel and adapt other panels
	 *
	 *  @param p coordinates of the added point
	 */
    public synchronized void addPoint(int[] p) 
	{
			paintImmediately(this.getVisibleRect());
            gameFrame.menuPanel.calculateMovesList();
			
    }

} // class
