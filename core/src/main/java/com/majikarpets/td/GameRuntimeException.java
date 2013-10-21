package com.majikarpets.td;

/**
 * An exception for errors in game logic that are either unrecoverable or should not occur by contract.
 * @author Aaron Cake
 */
public class GameRuntimeException extends RuntimeException {

	/**
	 * Creates a new GameRuntimeException with the given message and reason
	 * @param message the message
	 * @param reason the reason
	 */
	public GameRuntimeException(String message, Throwable reason) {
		super(message, reason);
	}
	
	/**
	 * Creates a new GameRuntimeException with the given message
	 * @param message the message
	 */
	public GameRuntimeException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new GameRuntimeException from the given reason
	 * @param reason the reason
	 */
	public GameRuntimeException(Throwable reason) {
		super(reason);
	}
	
}
