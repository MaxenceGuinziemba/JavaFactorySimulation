package fr.tp.inf112.projects.canvas.model;

/**
 * Specifies the color of an element to be drawn in the user interface.
 * 
 * @author Dominique Blouin
 *
 */
public interface Color {
	
    /**
     * Returns the red color component of this color.
	 * @return A positive {@code int} value for red color component. 
	 */
	int getRedComponent();

    /**
     * Returns the green color component of this color.
	 * @return A positive {@code int} value for green color component. 
	 */
	int getGreenComponent();
	
    /**
     * Returns the blue color component of this color.
	 * @return A positive {@code int} value for blue color component. 
	 */
	int getBlueComponent();
}
