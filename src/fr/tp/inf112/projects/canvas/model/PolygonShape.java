package fr.tp.inf112.projects.canvas.model;

import java.util.Set;

/**
 * Represents a polygon shape having a given set of points.
 * @author Dominique Blouin
 *
 */
public interface PolygonShape extends Shape {

	/**
	 * Returns the points composing this polygon. The points will be linked by edges in the order that the
	 * {@code Set} will provide them. Therefore, the implementation of {@code Set} should be carefully chosen so that
	 * the shape is drawn consistently, i.e., always in the same order.
	 * @return A <code>Set</code> whose elements consist of the points of the polygon.
	 */
	Set<Point> getPoints();
}
