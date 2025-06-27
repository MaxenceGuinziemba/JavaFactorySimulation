package fr.tp.inf112.projects.canvas.model;

/**
 * Represents an oval shape with given width and height.
 * 
 * @author Dominique Blouin
 *
 */
public interface OvalShape extends Shape {

	/**
	 * Returns the width of this oval shape.
	 * 
	 * @return A positive {@code int}  value for the with in pixels of this figure.
	 */
	int getWidth();

	/**
	 * Returns the height of this oval shape.
	 * 
	 * @return A positive {@code int}  value for the height in pixels of this figure.
	 */
	int getHeight();
}
