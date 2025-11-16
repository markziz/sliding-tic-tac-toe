
/* 
CS 2210 – Assignment 2 
Name: Mark Ziza 
Student Number: 251442316
Email: mziza@uwo.ca
Created: Oct 7, 2025
This program stores layout objects as a dictionary with board layout as a key
in a separate chained array using a polynomial hash function
*/
import java.util.LinkedList;

public class HashDictionary implements DictionaryADT {

	private LinkedList<Layout>[] hashTable;
	private int size;

	/**
	 * {@summary initializes the separate chained array}
	 * 
	 * @param size length of array
	 */
	public HashDictionary(int size) {
		this.size = size;
		hashTable = new LinkedList[this.size];// initializes array with given size

		// adds an empty linked list of layout objects in each slot of the array
		for (int i = 0; i < this.size; i++) {
			hashTable[i] = new LinkedList<Layout>();
		}
	}

	/**
	 * {@summary method that puts layout object in hash dictionary or throws
	 * exception for duplicates}
	 * 
	 * @param data a layout object we are trying to put in the hash dictionary
	 * @return 1 if there was a collision or 0 if not
	 */
	public int put(Layout data) throws DictionaryException {
		boolean isCollision;
		int position = polynomialHashFunction(data.getBoardLayout(), size);// using hash function to find where to put data
		LinkedList<Layout> linkList = hashTable[position];// grabbing linked list at the found position

		isCollision = !linkList.isEmpty();// checking for collision

		// checking for any duplicate keys in linked list and throw exception if there
		// is
		for (int i = 0; i < linkList.size(); i++) {
			if (linkList.get(i).getBoardLayout().equals(data.getBoardLayout())) {
				throw new DictionaryException("This key is already logged");
			}
		}

		// if no duplicates add data to linked list and return if there was a collision
		linkList.add(data);
		if (isCollision)
			return 1;
		else
			return 0;
	}

	/**
	 * {@summary removes entry matching given key from hash dictionary}
	 * 
	 * @param boardLayout String representation of board (key for dictionary)
	 */
	public void remove(String boardLayout) throws DictionaryException {
		boolean didRemove = false;
		int position = polynomialHashFunction(boardLayout, size);// using hash function to find where the key should be
		LinkedList<Layout> linkList = hashTable[position];// grabbing linked list at the found position

		// looking through list and removing the matching data to the key given if it
		// exists
		for (int i = 0; i < linkList.size(); i++) {
			if (linkList.get(i).getBoardLayout().equals(boardLayout)) {
				linkList.remove(i);
				didRemove = true;
				break;
			}
		}
		// if nothing was removed throw exception
		if (!didRemove)
			throw new DictionaryException("No matching key to remove");
	}

	/**
	 * {@summary gets the score of a specific layout associated to a given board
	 * layout key}
	 * 
	 * @param boardLayout a board layout associated to a layout
	 * @return score of the layout associated to given key (board layout) or -1 if
	 *         not found
	 */
	public int get(String boardLayout) {
		int position = polynomialHashFunction(boardLayout, size);// using hash function to find where the key should be
		LinkedList<Layout> linkList = hashTable[position];// grabbing linked list at the found position

		// looking through linked list and returning score of layout object with same
		// key as one that was given
		for (int i = 0; i < linkList.size(); i++) {
			if (linkList.get(i).getBoardLayout().equals(boardLayout)) {
				return linkList.get(i).getScore();
			}
		}
		return -1;// return -1 if not found
	}

	/**
	 * {@summary helper method that finds where we should put our dictionary}
	 * 
	 * @param key      the board layout of our data
	 * @param hashSize size of the array we will be storing data in
	 * @return position that key and its associated data should be stored in
	 */
	private int polynomialHashFunction(String key, int hashSize) {
		int primeValue = 37;// prime number that will be used in our polynomial function (pre-determined)
		int value = (int) key.charAt(0);// stores the converted int from our string key (will end up being our position
										// value)

		// goes through the whole key and converts to a int (using a formula) that is compressed onto our
		// array size so that the position is valid
		for (int i = 1; i < key.length(); i++) {
			value = (value * primeValue + (int) key.charAt(i)) % hashSize;
		}
		return value;
	}
}// end of class
