package fr.tp.inf112.projects.robotsim.model.exception;

/**
 * Exception thrown when an invalid placement of a component is attempted.
 * 
 * <p>
 * This exception is used to indicate that a component cannot be placed
 * in the specified location due to constraints or invalid conditions.
 * </p>
 * 
 * @see Exception
 */
public class InvalidComponentPlacementException extends Exception {
    /**
     * Constructs a new {@code InvalidComponentPlacementException} with the specified detail message.
     * 
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidComponentPlacementException(String message) {
        super(message);
    }
}
