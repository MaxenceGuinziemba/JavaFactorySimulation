package fr.tp.inf112.projects.canvas.model;

public interface Point extends Comparable<Point> {
	
	/**
	 * Returns the coordinate of this point along the x axis with respect to the top left corner of the containing canvas.
	 * @return A positive {@code int} value in pixels.
	 */
	int getxCoordinate();

	/**
	 * Returns the coordinate of this point along the y axis with respect to the top left corner of the containing canvas.
	 * @return A positive {@code int} value in pixels.
	 */
	int getyCoordinate();

}
