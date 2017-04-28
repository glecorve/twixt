package gui;

import ai.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class NewGameDialog extends JDialog{

    private static final long serialVersionUID = 1L;
    
    protected JTextField player1Tfd;
    protected JTextField player2Tfd;
    protected JComboBox<String> ai1Chb;
    protected JComboBox<String> ai2Chb;
    protected boolean exitOK;
	
	Factory f_ai;

    public NewGameDialog(String playerName1, String playerName2) {
        super();
		

		f_ai = new Factory();
		
        setTitle(Constants.gameTitle);
        setModal(true);
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new BorderLayout());
        p.setLayout(new GridLayout(2,1,0,0));
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Constants.p1boarder);
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "Player 1"));
        JPanel pnl2 = new JPanel(new GridLayout(2,1,0,0));
        pnl2.setBackground(Constants.p1boarder);
        pnl2.add(new JLabel(" Name: "));
        pnl2.add(new JLabel(" AI: "));
        pnl.add(pnl2, BorderLayout.WEST);
        player1Tfd = new JTextField(10);
        player1Tfd.setText(playerName1);
        pnl2 = new JPanel(new GridLayout(2,1,0,0));
        pnl2.setBackground(Constants.p1boarder);
        pnl2.add(player1Tfd);


		Vector<String> ia = new Vector <String>();
		ia.add("");
		ia.addAll(f_ai.list_AI());
        ai1Chb = new JComboBox<String>(ia);
        ai1Chb.setBackground(Constants.p1boarder);
        ai1Chb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) ai1Chb.getSelectedItem();
				if (name.equals("")) {
					name = "A";
				}
				else if (name.equals(player2Tfd.getText())) {
        			player2Tfd.setText(name + " 2");
        			name += " 1";
        		}
				player1Tfd.setText(name);				
			}
		});
        pnl2.add(ai1Chb);
        pnl.add(pnl2, BorderLayout.EAST);  
        p.add(pnl);
        pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Constants.p2boarder);
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "Player 2"));
        pnl2 = new JPanel(new GridLayout(2,1,0,0));
        pnl2.setBackground(Constants.p2boarder);
        pnl2.add(new JLabel(" Name: "));
        pnl2.add(new JLabel(" AI: "));
        pnl.add(pnl2, BorderLayout.WEST);
        player2Tfd = new JTextField(10);
        player2Tfd.setText(playerName2);
        pnl2 = new JPanel(new GridLayout(2,1,0,0));
        pnl2.setBackground(Constants.p2boarder);
        pnl2.add(player2Tfd);

		
        ai2Chb = new JComboBox<String>(ia);
        ai2Chb.setBackground(Constants.p2boarder);
        ai2Chb.setSelectedIndex(0);
        ai2Chb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) ai2Chb.getSelectedItem();
				if (name.equals("")) {
					name = "B";
				}
				else if (name.equals(player1Tfd.getText())) {
        			player1Tfd.setText(name + " 1");
        			name += " 2";
        		}
				player2Tfd.setText(name);				
			}
		});
        pnl2.add(ai2Chb);
        pnl.add(pnl2, BorderLayout.EAST);  
        p.add(pnl);     
        add(p, BorderLayout.CENTER);
        p = new JPanel(new GridLayout(1,2,0,0));
        JButton btn = new JButton("OK");
        btn.setBackground(Constants.btnColor);
        btn.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
                if (((!ai1Chb.getSelectedItem().equals("")) || !player1Tfd.getText().trim().equals("")) &&
					((!ai2Chb.getSelectedItem().equals("")) || !player2Tfd.getText().trim().equals("")))
				{
                    exitOK = true;
                    setVisible(false);
                }
				else
				{
                    JOptionPane.showMessageDialog(NewGameDialog.this, "Please enter a name.", Constants.gameTitle, JOptionPane.ERROR_MESSAGE);
                }
            }            
        });
        p.add(btn);
        btn = new JButton("Abort");
        btn.setBackground(Constants.btnColor);
        btn.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
                exitOK = false;
                setVisible(false);
            }            
        });
        p.add(btn);
        add(p, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2, (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2) ;
    }
    
}
