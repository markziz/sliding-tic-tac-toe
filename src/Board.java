/* 
CS 2210 – Assignment 2 
Name: Mark Ziza 
Student Number: 251442316
Email: mziza@uwo.ca
Created: Oct 7, 2025
This program represent the game board that is being played on and
provides many methods that are needed to help play the game in other classes
*/
public class Board implements BoardADT {

	private int boardSize;
	private int emptyPositions;
	private int maxLevels;
	private char[][] theBoard;

	/**
	 * {@summary initializes board's attributes and makes a full empty board}
	 * 
	 * @param boardSize      size of the board (nxn)
	 * @param emptyPositions number of empty position on board before sliding begins
	 * @param maxLevels      decides the quality of game that will be played by the
	 *                       computer
	 */
	public Board(int boardSize, int emptyPositions, int maxLevels) {
		this.boardSize = boardSize;
		this.emptyPositions = emptyPositions;
		this.maxLevels = maxLevels;

		// going through 2D array of board and making all slots empty
		theBoard = new char[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				theBoard[i][j] = 'E';
			}
		}
	}

	/**
	 * {@summary making a new hash dictionary with array size 8209}
	 * 
	 * @return the hash dictionary
	 */
	public HashDictionary makeDictionary() {
		int size = 8209;
		return new HashDictionary(size);
	}

	/**
	 * {@summary checks if the current layout on the board is already stored in the
	 * given hash dictionary}
	 * 
	 * @param dict given hash dictionary storing board layouts
	 * @return the given layout object associated to the layout of the current board
	 *         if it is already in the hash dict or -1 if it is not
	 */
	public int repeatedLayout(HashDictionary dict) {
		String s = "";

		// make a key of the current board layout (down column then left to right)
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				s = s + theBoard[j][i];
			}
		}
		// see if this layout is already stores or return -1 if not
		return dict.get(s);
	}

	/**
	 * {@summary try storing the given layout of board in hash dict}
	 * 
	 * @param dict  hash dictionary storing layouts
	 * @param score the current score of the board
	 */
	public void storeLayout(HashDictionary dict, int score) {
		String s = "";

		// get the string key representation of the current board
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				s = s + theBoard[j][i];
			}
		}

		// make a new layout object of the found key and the given score
		// try to put it in the given hash dict
		Layout layout = new Layout(s, score);
		try {
			dict.put(layout);
		} catch (DictionaryException e) {
			System.out.println("This is already stored");// exception if layout already exists in hash dict
		}
	}

	/**
	 * {@summary stores symbol in Board[row][col]}
	 * 
	 * @param row    row number of board
	 * @param col    column number of board
	 * @param symbol symbol we are storing
	 */
	public void saveTile(int row, int col, char symbol) {
		theBoard[row][col] = symbol;
	}

	/**
	 * {@summary returns true if Board[row][col] is empty otherwise it returns
	 * false}
	 * 
	 * @param row row number of board
	 * @param col column number of board
	 * @return true if Board[row][col] is empty otherwise it returns false
	 */
	public boolean positionIsEmpty(int row, int col) {
		return theBoard[row][col] == 'E';
	}

	/**
	 * {@summary returns true if Board[row][col] is occupied by the computer
	 * otherwise it returns false }
	 * 
	 * @param row row number of board
	 * @param col column number of board
	 * @return true if Board[row][col] is occupied by the computer otherwise it
	 *         returns false
	 */
	public boolean isComputerTile(int row, int col) {
		return theBoard[row][col] == 'R';
	}

	/**
	 * {@summary returns true if Board[row][col] is occupied by the player otherwise
	 * it returns false }
	 * 
	 * @param row row number of board
	 * @param col column number of board
	 * @return true if Board[row][col] is occupied by the player otherwise it
	 *         returns false
	 */
	public boolean isHumanTile(int row, int col) {
		return theBoard[row][col] == 'B';
	}

	/**
	 * {@summary find if player or computer has won (depending on symbol given)}
	 * 
	 * @param symbol the symbol corresponding to the player/computer
	 * @return true if this board is won false if not
	 */
	public boolean winner(char symbol) {
		int symbolCount = 0;

		// looking at each row and seeing if it is full of our symbol
		// then resetting the count on every new row (looking for row win)
		for (int i = 0; i < boardSize; i++) {
			symbolCount = 0;
			for (int j = 0; j < boardSize; j++) {
				if (theBoard[i][j] == symbol)
					symbolCount++;
				if (symbolCount == boardSize)
					return true;
			}
		}

		// looking at each column and seeing if it is full of our symbol
		// then resetting the count on every new column (looking for column win)
		for (int i = 0; i < boardSize; i++) {
			symbolCount = 0;
			for (int j = 0; j < boardSize; j++) {
				if (theBoard[j][i] == symbol)
					symbolCount++;
				if (symbolCount == boardSize)
					return true;
			}
		}

		// looking at left/right diagonal and seeing if it is full of our symbol
		// (looking for left/right diagonal win)
		symbolCount = 0;
		for (int i = 0; i < boardSize; i++) {
			if (theBoard[i][i] == symbol)
				symbolCount++;
			if (symbolCount == boardSize)
				return true;
		}

		// looking at right/left diagonal and seeing if it is full of our symbol
		// (looking for right/left diagonal win)
		symbolCount = 0;
		for (int i = 0; i < boardSize; i++) {
			if (theBoard[i][boardSize - 1 - i] == symbol)
				symbolCount++;
		}
		if (symbolCount == boardSize)
			return true;

		// return false if no win
		return false;
	}

	/**
	 * {@summary looking if it is a draw on the current players turn}
	 * 
	 * @param symbol         the symbol corresponding to the player
	 *                       (computer/player)
	 * @param emptyPositions number of positions of the board that must remain empty
	 * @return true if it is a draw false if not
	 */
	public boolean isDraw(char symbol, int emptyPositions) {
		// if computer of player won it is not a draw
		if (winner('R') || winner('B'))
			return false;

		int emptyPosCount = 0;

		// count empty positions on board
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (theBoard[i][j] == 'E')
					emptyPosCount++;
			}
		}
		// if board is full it is a draw
		if (emptyPosCount == 0)
			return true;

		// if board is not full and more tiles can be placed it is not a draw
		if (emptyPosCount != emptyPositions) {
			return false;
		}

		// (looking if any sliding can be done)
		// look at each empty space and see if any of given symbol is
		// next to it
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (theBoard[i][j] == 'E') {
					if ((i > 0 && j > 0 && theBoard[i - 1][j - 1] == symbol)// looking up left
							|| (i > 0 && theBoard[i - 1][j] == symbol)// looking up
							|| (i > 0 && j < boardSize - 1 && theBoard[i - 1][j + 1] == symbol)// looking up right
							|| (j > 0 && theBoard[i][j - 1] == symbol)// looking left
							|| (j < boardSize - 1 && theBoard[i][j + 1] == symbol)// looking right
							|| (j > 0 && i < boardSize - 1 && theBoard[i + 1][j - 1] == symbol)// looking down left
							|| (i < boardSize - 1 && theBoard[i + 1][j] == symbol)// looking down
							|| (i < boardSize - 1 && j < boardSize - 1 && theBoard[i + 1][j + 1] == symbol)) {// looking down right
						return false;// not a draw if symbol is next to empty space
					} // end of inner if
				} // end of outer if
			} // end of inner for
		} // end of outer for

		return true;// is draw after all other conditions are checked and none hit
	}

	/**
	 * {@summary returned a value depending on what state the game is at}
	 * 
	 * @param symbol         given symbol that is of a player/computer on board
	 * @param emptyPositions number of positions of the board that must remain empty
	 * @return 3 if computer won, 0 if player won, 2 if draw, 1 if game undecided
	 */
	public int evaluate(char symbol, int emptyPositions) {
		if (winner('R'))
			return 3;// check if computer won
		if (winner('B'))
			return 0;// check if player won
		if (isDraw(symbol, emptyPositions))
			return 2;// check if draw
		return 1;// if none of others, game undecided
	}

}// end of class
