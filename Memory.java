package Lab2;

import java.util.Random;

/**
 * Memory class
 * 		A text-based memory game for the console
 * @author Jessica Glover
 *
 */
public class Memory 
{
	private final char[] POSS_CHARS = {'!', '@', '#', '$', '%', '&', '*', 'e', 's',
								 'i', 'J', 'M', 'G', 'c', '+', '-', '=', 'a'};		/** Characters that can appear on the game board */
	
	private char[] characters; /** Characters that do appear on the game board. */
	
	private char[][] display; /** The display board */
	
	private char[][] board;	  /** The hidden board */
	
	private int boardDim;
	private int boardSize; /** boardDim squared */
	
	
	
	/**
	 * Default constructor
	 * 		calls the overloaded constructor and gives it the default board dimension of 4x4
	 */
	public Memory()
	{
		this(4);  
		
	}
	
	
	
	/**
	 * Overloaded constructor
	 * 		Initializes all class fields
	 * @param boardDimension the desired board size
	 */
	public Memory(int boardDimension)
	{
		boardDim = boardDimension;
		boardSize = boardDim * boardDim;
		board = new char[boardDim][boardDim];
		characters = new char[boardSize];
		setCharacters(); 
		setDisplay();
		fillBoard();
	}
	
	
	
	/**
	 * setCharacters utility
	 * 		Initializes and fills the characters array with the appropriate number of elements from
	 * 		the POSS_CHARS array.
	 */
	private void setCharacters()
	{	
		int k = 0; //increments index of characters array

		for (int j = 0; j < boardSize/2; j ++)
		{
			characters[k] = POSS_CHARS[j];
			characters[k + 1] = POSS_CHARS[j];
			k += 2;
		}
		
		shuffle();
	}
	
	
	
	/**
	 * shuffle utility
	 * 		Uses a Fisher-Yates shuffle to randomize the elements of the characters array.
	 */
	private void shuffle()
	{
		int n = characters.length - 1;
		for (int i = n; i > 0; i--)
		{
			char temp = 'a';
			Random rnd = new Random();
			int j = rnd.nextInt(i + 1);
			
			temp = characters[i];
			characters[i] = characters[j];
			characters[j] = temp;	
		}
	}
	
	
	
	/**
	 * toString method
	 * 		Overrides Object.toString() 
	 * 	
	 * @return String representation of the display board.
	 */
	public String toString() 
	
	{
		String result = "\n     Current Board\n\n";
		result += "     "; //five spaces
		
		for (int i = 0; i < boardDim; i++)
		{
			result += ((i + 1) + "   "); //3 spaces
		}
		
		for (int i = 0; i < boardDim; i++)
		{
			result += "\n\n";
			if (i < 9)
			{
				result += (" " + (i + 1) + " ");
			}
			for (int j = 0; j < boardDim; j++)
			{
					result += ("  " + display[i][j] + " ");
				}
			}
		
		
		return result;
	}

	
	
	/**
	 * fillBoard utility
	 * 		Populates the game board with characters from the characters array.
	 */
	private void fillBoard()
	{
		int counter = 0;
		
		for (int j = 0; j < boardDim; j++)
		{
			for (int k = 0; k < boardDim; k++)
			{
				board[j][k] = characters[counter];
				counter++;
			}
		}
		
	}
	
	
	
	/**
	 * setDisplay utility
	 * 		Initializes the display board to be filled with '?' characters.
	 */
	private void setDisplay()
	{
		display = new char[boardDim][boardDim];
		
		for (int i = 0; i < boardDim; i++)
		{
			for (int j = 0; j < boardDim; j++)
			{
				display[i][j] = '?';
			}
		}
	}
	
	
	
	/**
	 * reveal method
	 * 		Verifies validity of parameters. Then, if valid, modifies the display board to show a previously hidden character.
	 * @param int row number
	 * @param int column number
	 * @return boolean true if valid entries for row and col were used, false otherwise.
	 */
	public boolean reveal(int row, int col)
	{
		if (row > boardDim || row <= 0 || col > boardDim || col <= 0) 
		{
			return false;
		} 
		else
		{
			row -= 1;
			col -= 1;

			display[row][col] = board[row][col];
			return true;
		}
	}
	
	
	
	/**
	 * hide method
	 * 		If the input is not part of a displayed match, sets display element at input coordinates back to '?'
	 * @param row
	 * @param col
	 * @return boolean true if input should be hidden, false otherwise
	 */
	public boolean hide(int row, int col)
	{
		row -=1;
		col -= 1;

		boolean foundMatch = false;
		for (int i = 0; i < boardDim; i++)
		{
			for (int j = 0; j < boardDim; j++)
			{
				if (!(i == row && j == col) && (display[i][j] == display[row][col])) //checks whether input is part of a displayed match
				{
					foundMatch = true;
				}
			}
		}
		
		if (!foundMatch)
		{
			display[row][col] = '?';
			return true;
		}
		else
		{
			return false;
		}
		
	}
		

	
	/**
	 * match method
	 * 		Determines if two distinct board elements match.
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return boolean true if a match, false if not a match or elements are not distinct
	 */
	public boolean match(int row1, int col1, int row2, int col2)
	{
		if (row1 == row2 && col1 == col2)
		{
			return false; //returns false if user enters the same thing for their first and second selection.
		}
		
		if (board[row1 - 1][col1 - 1] == board[row2 - 1][col2 - 1])
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	/**
	 * isGameComplete method
	 * 		Searches for '?' in the display array to determine whether all matches have been revealed.
	 * @return boolean true if all matches have been revealed, false otherwise
	 */
	public boolean isGameComplete()
	{
		for (int i = 0; i < boardDim; i++)
		{
			for (int j = 0; j < boardDim; j++)
			{
				if (display[i][j] == '?')
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	
}

/*
 * Problems encountered:
 * 
 * 1.At first my design was such that if a user selected a "card" that was already revealed as part of a match, 
 * it would be hidden at the end of that turn. I found it difficult to figure out where in my logic to check for that.
 * I ended up putting it in the service class's hide method, but I had tried before to instead use a returned boolean in the 
 * main method.
 * 
 * 2. I had a similar problem with input validation for the card selection. I didn't want the program to end with an exception
 * when a user typed invalid input, but I wasn't sure where or how to write code to allow the user to keep entering in a new selection 
 * until they'd made one that made sense.
 * I ended up writing a method called isValid() in the Client class that did the input validation, and depending on the type of user error,
 * printed an appropriate message to help the user type correct input. Then I put the card selection code in a do while loop that was 
 * controlled by the boolean result of isValid().
 * 
 * 3. I had various other problems, but they were all tied to my difficulty with visualizing the logical flow of my program while debugging. I often
 * found myself wishing I had a giant dry-erase board to write a flowchart and UML diagram on. Or that I knew how to use software to draw those
 * things. 
 * 
 * Possible improvements:
 * 1. I would like to make it so that the console only displays the current board and most recent instructions.
 * 
 * 2. Could add a sort of scoring element to the game: Count the number of repeat card reveals. If no one card is ever revealed three times,
 * then the user played a perfect game. (need to think about other conditions/definition of perfect- there is a chance that the user could reveal 
 * a card for the first time as their first selection having seen its match once and not choose the match as their second selection. 
 * Then if the user matches them correctly later, each of those cards will only have been revealed twice, 
 * but it's questionable whether the player played a "perfect" game.)
 * 
 * 3. Once I know more about GUI, I could make an easier-to-read interface with clickable cards.
 * 
 * 4. Could set the display array to be equal to the board array and then print it to the console if the user decides to quit mid-game.
 * 
 * 
 */
