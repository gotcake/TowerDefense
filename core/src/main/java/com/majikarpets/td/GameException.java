package com.majikarpets.td;

/**
 * An exception for errors in game logic
 * @author Aaron Cake
 */
public class GameException extends Exception {
	
	/**
	 * Creates a new GameException with the given message and reason
	 * @param message the message
	 * @param reason the reason
	 */
	public GameException(String message, Throwable reason) {
		super(message, reason);
	}
	
	/**
	 * Creates a new GameException with the given message
	 * @param message the message
	 */
	public GameException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new GameException from the given reason
	 * @param reason the reason
	 */
	public GameException(Throwable reason) {
		super(reason);
	}
	
}
