package fr.tp.inf112.projects.canvas.model;

/**
 * Represents a rectangle shape of given width and height.
 * 
 * @author Dominique Blouin
 *
 */
public interface RectangleShape extends Shape {
	
	/**
	 * Returns the width of this rectangle shape.
	 * 
	 * @return A positive {@code int} value for the with in pixels of this shape.
	 */
	int getWidth();

	/**
	 * Returns the height of this rectangle shape.
	 * 
	 * @return A positive {@code int} value for the height in pixels of this shape.
	 */
	int getHeight();
}
