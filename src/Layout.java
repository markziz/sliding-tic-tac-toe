/* 
CS 2210 – Assignment 2 
Name: Mark Ziza 
Student Number: 251442316
Email: mziza@uwo.ca
Created: Oct 7, 2025
This program creates the layout object which will store
the score of the current game and what the board looks like
to help see who is winning in the match 
*/
public class Layout {

	private String boardLayout;
	private int score;

	/**
	 * {@summary constructor of layout}
	 * 
	 * @param boardLayout String representation of the board
	 * @param score       number that represents what state the game is at (who is
	 *                    winning,draw,neither)
	 */
	public Layout(String boardLayout, int score) {
		this.boardLayout = boardLayout;
		this.score = score;
	}

	/**
	 * {@summary getter method that gets the board layout}
	 * 
	 * @return the board layout
	 */
	public String getBoardLayout() {
		return boardLayout;
	}

	/**
	 * {@summary getter method that gets the boards score}
	 * 
	 * @return score of the board
	 */
	public int getScore() {
		return score;
	}

}// end of class
