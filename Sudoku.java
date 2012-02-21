/*
 * Noah Alonso-Torres
 * Sudoku Puzzle Game
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class Sudoku extends JComponent implements ActionListener  
{
	private JTextField field  = new JTextField(10);
	private JButton checkButton = new JButton("Check");
	private JButton solveButton = new JButton("Solve");
	private JButton clearButton = new JButton("Clear");
	private JMenuItem easy = new JMenuItem("Easy");
	private JMenuItem medium  = new JMenuItem("Medium");
	private JMenuItem hard = new JMenuItem("Hard");
	private JMenuItem exit = new JMenuItem("Exit");
	
	private JTextField[][] board = new JTextField[9][9];
	private static Dimension SIZE = new Dimension(350,350);
	
	public Sudoku()
	{
		JFrame frame = new JFrame("Sudoku");
		frame.setLayout(new BorderLayout());  
		frame.setPreferredSize(SIZE);
		
		JMenuBar menuBar = new JMenuBar();
	    frame.setJMenuBar(menuBar);
	    
	    JMenu leftMenu = new JMenu("Samples");
        leftMenu.add(easy);
        leftMenu.add(medium);
        leftMenu.add(hard);
        leftMenu.addSeparator(); 
        leftMenu.add(exit);
        
        JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(9,9));
        
        for(int row = 0; row < board.length; row++)
			for(int col = 0; col < board[row].length; col++)
        	{
				board[row][col] = new JTextField();
				board[row][col].setHorizontalAlignment(SwingConstants.CENTER);
				buttonPanel.add(board[row][col]);
        	}
        //to get the thicker lines
        Color borderColor = Color.BLACK;
        int wide=3, narrow=1;
        int top, bottom, left, right;
        for (int row=0; row<9; ++row)
            for (int col=0; col<9; col++) 
            {
                top=bottom=left=right=narrow;
                if (col==2 || col==5) right  = wide;
                if (row==2 || row==5) bottom = wide;
                board[row][col].setBorder(new MatteBorder(top, left, bottom, right, borderColor));
            }
        
        menuBar.add(leftMenu);
        
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new GridLayout(1,3));
		buttonPanel2.add(checkButton);
		buttonPanel2.add(solveButton);
		buttonPanel2.add(clearButton);
		
		frame.add(field, BorderLayout.SOUTH);
		frame.getContentPane().add(buttonPanel2,BorderLayout.NORTH);
		frame.getContentPane().add(buttonPanel,BorderLayout.CENTER);	
		
		easy.addActionListener(this); 
	    medium.addActionListener(this);
	    hard.addActionListener(this);
	    exit.addActionListener(this);
		checkButton.addActionListener(this);
		solveButton.addActionListener(this);
		clearButton.addActionListener(this);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

	
	private void clear() {
		String clear = "";
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9 ; col++) {
				board[row][col].setText(clear);
			}
		}	
	}

	private boolean solve() {
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				
				if (board[row][col].getText().equals("")){
					
					for (int i = 1; i < 10; i++) {
						board[row][col].setText(Integer.toString(i));
						
						if (checkCell(row, col) && solve()) return true;
					}
				board[row][col].setText("");
				return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkCell(int row, int col)
    {
          if(checkRow(row,col)==true && checkCol(row,col)==true && checkCluster(row,col)==true)
                return true;
          else
                return false;
    }          
   
    public boolean checkRow(int row, int col)
    {
          if(board[row][col].getText().equals(""))
                return true;
          for(int col2=0; col2<9; ++col2)
                if(col!=col2)
                      if(board[row][col].getText().equals(board[row][col2].getText()))
                            return false;
                return true;
    }
   
    public boolean checkCol(int row, int col)
    {
          if(board[row][col].getText().equals(""))
                return true;
          for(int row2=0; row2<9; ++row2)
                if(row!=row2)
                      if(board[row][col].getText().equals(board[row2][col].getText()))
                            return false;
                return true;
    }
   
    public boolean checkCluster(int row,int col)
    {
          if(board[row][col].getText().equals(""))
                return true;
         
          int clusterUpperLeftRow = row-row%3;
          int clusterUpperLeftCol = col-col%3;
                     
          for(int row2=0; row2<3; ++row2)
                for(int col2=0; col2<3; ++col2)
                      if(row!=clusterUpperLeftRow+row2 && col!=clusterUpperLeftCol+col2)
                            if(board[row][col].getText().equals(board[clusterUpperLeftRow+row2][clusterUpperLeftCol+col2].getText()))
                                  return false;
                return true;                 
    }
    private void check()
    {
          field.setText("Board is valid");
          for(int row=0;row<9;row++)
          {
                for(int col=0;col<9;col++)
                {
                      if(checkCell(row, col)==false)
                            field.setText("Board contains conflicts");
                }
          }
    }

	private void handleEasy() {
		int indexValue = 0;
		String [] stringArray = new String[81];
		int[] boardValues =
		{ 0, 2, 3, 4, 5, 6, 7, 8, 9,
		  4, 5, 6, 7, 8, 9, 1, 2, 3,
		  7, 8, 9, 1, 2, 3, 0, 5, 0,
		  2, 1, 4, 3, 6, 5, 8, 9, 7,
		  3, 6, 5, 8, 9, 7, 2, 1, 4,
		  8, 9, 7, 2, 1, 4, 0, 6, 5,
		  5, 3, 1, 6, 4, 2, 9, 7, 8,
		  6, 4, 2, 9, 7, 8, 5, 3, 1,
		  9, 7, 8, 5, 3, 1, 6, 4, 0,
		 };
		for (int i = 0; i < 81; i++) {
			stringArray[i] = Integer.toString(boardValues[i]);
			if (stringArray[i].equals("0")) stringArray[i] = "";
		}
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9 ; col++) {
				board[row][col].setText(stringArray[indexValue]);
				indexValue++;
			}
		}	
	}

	private void handleMedium() {
		int indexValue = 0;
		String [] stringArray = new String[81];
		int[] boardValues =
		{ 0, 0, 3, 2, 0, 0, 5, 7, 0,
		  0, 0, 7, 0, 0, 0, 0, 0, 0,
		  0, 0, 0, 6, 0, 0, 3, 0, 0,
		  0, 2, 0, 0, 3, 9, 0, 1, 0,
		  9, 0, 0, 0, 1, 0, 0, 0, 4,
		  0, 5, 0, 7, 2, 0, 0, 8, 0,
		  0, 0, 9, 0, 0, 3, 0, 0, 0,
		  0, 0, 0, 0, 0, 0, 4, 0, 0,
		  0, 4, 5, 0, 0, 8, 6, 0, 0,
		 };
		for (int i = 0; i < 81; i++) {
			stringArray[i] = Integer.toString(boardValues[i]);
			if (stringArray[i].equals("0")) stringArray[i] = "";
		}
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9 ; col++) {
				board[row][col].setText(stringArray[indexValue]);
				indexValue++;
			}
		}
	}

	private void handleHard() {
		int indexValue = 0;
		String [] stringArray = new String[81];
		int[] boardValues =
		{ 5, 0, 0, 7, 0, 0, 6, 0, 0,
		  0, 0, 3, 8, 0, 0, 0, 0, 0,
		  0, 0, 0, 0, 0, 0, 2, 0, 0,
		  6, 2, 0, 4, 0, 0, 0, 0, 0,
		  0, 0, 0, 0, 0, 0, 0, 9, 1,
		  7, 0, 0, 0, 0, 0, 0, 0, 0,
		  0, 0, 0, 0, 3, 5, 0, 8, 0,
		  4, 0, 0, 0, 0, 0, 1, 0, 0, 
		  0, 0, 0, 0, 9, 0, 0, 0, 0,
		 };
		for (int i = 0; i < 81; i++) {
			stringArray[i] = Integer.toString(boardValues[i]);
			if (stringArray[i].equals("0")) stringArray[i] = "";
		}
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9 ; col++) {
				board[row][col].setText(stringArray[indexValue]);
				indexValue++;
			}
		}
	}

	private void handleExit() 
	{
		System.out.println("Exiting.");
	    System.exit(0);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==checkButton) check();
		if(event.getSource()==solveButton) solve();
		if(event.getSource()==clearButton) clear();
		if (event.getSource() == easy) handleEasy();
		if (event.getSource() == medium) handleMedium();
		if (event.getSource() == hard) handleHard();
		if (event.getSource() == exit) handleExit();
	}
	
	public static void main(String[] args)
	{
		new Sudoku();
	}
}

	
