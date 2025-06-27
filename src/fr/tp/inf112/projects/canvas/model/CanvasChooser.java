package fr.tp.inf112.projects.canvas.model;

import java.io.IOException;

/**
 * Represents an object used to chose a canvas model from the data store. Some implementations may involve user interaction
 * via UI components.
 * 
 * @author Dominique Blouin
 *
 */
public interface CanvasChooser {
	
	/**
	 * Returns an existing canvas identifier, potentially querying the user to choose among existing canvas in the data store..
	 * @return {@code null} if no canvas was chosen, otherwise a unique {@code String} value.
	 * @throws IOException
	 */
	String choseCanvas()
	throws IOException;

	/**
	 * Creates a new unique canvas identifier.
	 * @return A unique non {@code null} {@code String} value.
	 * @throws IOException When a problem occurs when accessing data store.
	 */
	String newCanvasId()
	throws IOException;
}
