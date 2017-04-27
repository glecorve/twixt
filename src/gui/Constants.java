package gui;

import java.awt.Color;
import java.awt.Dimension;

import logic.LogicManager;

public class Constants {
    
    // don't change
    public static final String gameTitle = "MaraTwixt AI";
    public static final int offSet = 50;
    public static final Dimension boardSize = new Dimension(25*LogicManager.getBoardSize() + 2 * offSet,
                                                            25*LogicManager.getBoardSize() + 2 * offSet);
    public static final int cell = (boardSize.width - 2 * offSet) / LogicManager.getBoardSize();

    // here you can change the width of a hole and a peg
    public static final int hole = 6;
    public static final int peg = 14;
    
    // here you can change colors
    public static final Color bgColor = new Color(255, 255, 255);
    public static final Color guideColor = new Color(0, 200, 0);    
    public static final Color p1Color = new Color(220, 230, 5);
    public static final Color p2Color = new Color(230, 0, 0);
    public static final Color p1boarder = new Color(255, 255, 180);
    public static final Color p2boarder = new Color(255, 180, 180);    
    public static final Color holeColor = new Color(100, 100, 100);
    public static final Color textColor = new Color(0, 0, 0);
    public static final Color boarderColor = new Color(233, 230, 233);
    
    public static final Color menuColor = new Color(190, 190, 200);
    public static final Color menuBorderColor = new Color(50, 50, 50);
    public static final Color btnColor = new Color(220, 220, 230);
    public static final Color progressColor = new Color(150, 150, 255);
    
    // here are the names of your AI if it plays player1 and if it plays player2
    public static final String aiName1 = "MaracAI";
    public static final String aiName2 = "MaraBot";
}
