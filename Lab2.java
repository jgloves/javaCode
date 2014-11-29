/*
 * Jessica Glover
 * CSI 162
 * Lab 2
 * 9/22/2014
 * 
 * note: I chose to document my client class since I used many methods to organize my logic.
 */
package Lab2;

import java.util.Scanner;

/**
 * Lab 2 class
 * 		Client for Memory class. Implements the game of Memory.
 * 
 * @author Jessica Glover
 *
 */
public class Lab2 {
	
	private static Scanner keyboard = new Scanner(System.in);
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		boolean done = false;
		boolean gameOver;
		
		greeting();
		
		while (!done)
		{
			int size = sizePrompt();
			//System.out.println(size);
			Memory game;
			gameOver = false;
			
			if(size == -1)
			{
				game = new Memory();
			}
			else
			{
				game = new Memory(size);
			}
			
			while (!gameOver)
			{
				System.out.println(game.toString());
				int[] cardPair = new int[4];
				int arrayIndex = 0;
				
				for (int i = 0; i < 2; i++)
				{
					
					String selection;
					int row = 0;
					int col = 0;
					
					do{
						selection = selectCard(i);
					} while (!isValid(selection, size));
					
					
					row = Character.getNumericValue(selection.charAt(0));
					col = Character.getNumericValue(selection.charAt(1));
					
					System.out.println("row " + row + ", col " + col);
					
					if (game.reveal(row, col))
					{
						cardPair[arrayIndex] = row;
						arrayIndex++;
						cardPair[arrayIndex] = col;
						arrayIndex++;
					}
					
					System.out.println(game.toString());
					
				}

				
				if (!game.match(cardPair[0], cardPair[1], cardPair[2], cardPair[3]))
				{
					
					System.out.println("\nSorry, no match.\n"
							+ "Enter Q to quit or any letter to continue: ");

					
					if (keyboard.nextLine().equalsIgnoreCase("Q"))
					{
						closingMessage();
						gameOver = true;
						done = true;
					}
					else
					{
						game.hide(cardPair[0], cardPair[1]);
						game.hide(cardPair[2], cardPair[3]);
					}
					
				}
				else
				{
					System.out.println("\nYou have a match!");

					if (game.isGameComplete())
					{
						System.out.println(game.toString());
						winningMessage();
						gameOver = true;

						if (!playAgainPrompt())
						{
							closingMessage();
							done = true;
						}
					}
					else
					{
						System.out.println("\nEnter Q to quit or any letter to continue: ");

						if (keyboard.nextLine().equalsIgnoreCase("Q"))
						{
							closingMessage();
							gameOver = true;
							done = true;
						}

					}

				}
			
			}
			
		} 
		
	}
	
	
	
	/**
	 * greeting utility
	 * 		Prints a welcome message to the console.
	 */
	public static void greeting()
	{
		System.out.println("Welcome to Memory!");
	}
	
	
	
	/**
	 * sizePrompt utility 
	 * 		prompts the user for a board size, accepts and validates input.
	 * @return int user's desired board size, or -1 for default
	 * 
	 */
	public static int sizePrompt() {
		int choice = 4;
		boolean done = false;
		while (!done) {
			System.out.println("\nEnter your desired board size: \n "
					+ "[2] 2x2 \n [4] 4x4 \n [6] 6x6 \n"
					+ "Or press Enter for Quick Play (4x4 board)");

			String input = keyboard.nextLine();

			if (input.equals("")) {
				done = true;
			} else {
				char boardSize = input.charAt(0);

				if (boardSize == '2' || boardSize == '4' || boardSize == '6') {
					choice = Character.getNumericValue(boardSize);
					done = true;
				} else {
					System.out.println("I did not understand your answer.");
				}

			}
			
		}

		return choice;
	}
	
	
	
	/**
	 * selectCard method
	 * 		prompts the user for a card selection, reads and returns the input.
	 * @param int number (0 or 1)
	 * @return String user input
	 */
	public static String selectCard(int number)
	{
		String input;
		String ordinal;
		
		if (number == 0)
		{
			ordinal = "first";
		}
		else 
		{
			ordinal = "second";
		}
		
		System.out.print("\nEnter a row and col number of your " + ordinal + " selection.\n"); 
				
		input = keyboard.nextLine();
		
		return input;
		
	}
	
	
	
	/**
	 * winningMessage utility
	 * 		Prints a message of congratulations to the console.
	 */
	public static void winningMessage()
	{
		System.out.println("\nCongratulations! You found all the matches.");
	}
	
	
	
	/**
	 * playAgainPrompt utility
	 * 		Asks the user whether they would like to play again. Accepts user input.
	 * @return boolean true if user indicated a yes answer, false otherwise.
	 */
	public static boolean playAgainPrompt()
	{
		System.out.print("\nWould you like to play again? (Y/N)");
		if(keyboard.nextLine().equalsIgnoreCase("y"))
		{
			return true;
		}
		else 
		{
			return false;
		}
		
	}
	
	
	
	/**
	 * closingMessage utility
	 * 		Prints a closing message to the console.
	 */
	public static void closingMessage()
	{
		System.out.println("\nThanks for playing!");
	}
	
	
	
	/**
	 * isValid utility
	 * 		Determines whether user input for card selection is valid.
	 * 
	 * @param String user input for card selection
	 * @return boolean true if valid input, false otherwise
	 */
	public static boolean isValid(String input, int size)
	{
		if (input.length() != 2)
		{
			System.out.println("example: Type \"11\" for row 1 column 1");
			return false;
		}
		
		for (int i = 0; i < input.length(); i++)
		{
			if(!Character.isDigit(input.charAt(i)))
			{
				System.out.println("example: Type \"11\" for row 1 column 1");
				return false;
			}
			
			if(Character.getNumericValue(input.charAt(i)) <= 0 || Character.getNumericValue(input.charAt(i)) > size)
			{
				System.out.println("Please choose valid row and col numbers.");
				return false;
			}
		}
		
		return true;
	}
	
	

}
