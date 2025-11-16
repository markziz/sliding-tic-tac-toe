/* 
CS 2210 – Assignment 2 
Name: Mark Ziza 
Student Number: 251442316
Email: mziza@uwo.ca
Created: Oct 7, 2025
This program declares a dictionary exception which
will be used to throw exceptions in other programs
*/
public class DictionaryException extends Exception {

	/**
	 * {@summary constructor which just uses the normal exception constructor}
	 */
	public DictionaryException() {
		super();
	}

	/**
	 * {@summary constructor which just uses the normal exception constructor but
	 * passes a message to output}
	 * 
	 * @param message an error message that will be printed
	 */
	public DictionaryException(String message) {
		super(message);
	}
}// end of class
