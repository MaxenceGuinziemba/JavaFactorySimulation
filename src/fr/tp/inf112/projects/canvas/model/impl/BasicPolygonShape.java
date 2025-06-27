package fr.tp.inf112.projects.canvas.model.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.io.Serializable;
import fr.tp.inf112.projects.canvas.model.PolygonShape;
import fr.tp.inf112.projects.canvas.model.Point;

/**
 * A basic implementation of the {@link PolygonShape} interface.
 * Represents a polygon defined by a set of points.
 * 
 * <p>
 * This class is serializable to allow persistence.
 * </p>
 * 
 * @author team-24
 */
public class BasicPolygonShape implements PolygonShape, Serializable {
    private LinkedHashSet<Point> points;

    /**
     * Generates the vertices of a regular polygon.
     * 
     * @param cx            The x-coordinate of the polygon's center.
     * @param cy            The y-coordinate of the polygon's center.
     * @param numberOfSides The number of sides of the polygon.
     * @param diameter      The diameter of the polygon.
     * @return A {@link LinkedHashSet} of {@link Point} objects representing the vertices.
     */
    public static LinkedHashSet<Point> generatePolygonVertices(int cx, int cy, int numberOfSides, int diameter) {
        LinkedHashSet<BasicPoint> basicPoints = new LinkedHashSet<>();

        double radius = (double) diameter / 2;

        for (int i = 0; i < numberOfSides; i++) {
            double angle = 2 * Math.PI * i / numberOfSides;

            int x = (int) Math.round(cx + radius * (1 + Math.cos(angle)));
            int y = (int) Math.round(cy + radius * (1 + Math.sin(angle)));

            basicPoints.add(new BasicPoint(x, y));
        }

        LinkedHashSet<Point> points = new LinkedHashSet<>(basicPoints);
        return points;
    }

    /**
     * Constructs a {@code BasicPolygonShape} with a regular polygon's vertices.
     * 
     * @param cx            The x-coordinate of the polygon's center.
     * @param cy            The y-coordinate of the polygon's center.
     * @param numberOfSides The number of sides of the polygon.
     * @param diameter      The diameter of the polygon.
     */
    public BasicPolygonShape(int cx, int cy, int numberOfSides, int diameter) {
        this.points = generatePolygonVertices(cx, cy, numberOfSides, diameter);
    }

    /**
     * Constructs a {@code BasicPolygonShape} with a regular polygon's vertices centered at (0, 0).
     * 
     * @param numberOfSides The number of sides of the polygon.
     * @param diameter      The diameter of the polygon.
     */
    public BasicPolygonShape(int numberOfSides, int diameter) {
        this.points = generatePolygonVertices(0, 0, numberOfSides, diameter);
    }

    /**
     * Constructs a {@code BasicPolygonShape} with the specified array of points.
     * 
     * @param argPoints An array of {@link Point} objects defining the polygon's vertices.
     */
    public BasicPolygonShape(Point[] argPoints) {
        for (int i = 0; i < argPoints.length; i++) {
            this.points.add(argPoints[i]);
        }
    }

    /**
     * Constructs a {@code BasicPolygonShape} with the specified set of points.
     * 
     * @param points A {@link LinkedHashSet} of {@link Point} objects defining the polygon's vertices.
     */
    public BasicPolygonShape(LinkedHashSet<Point> points) {
        this.points = points;
    }

    /**
     * Returns the set of points defining the polygon.
     * 
     * @return A {@link Set} of {@link Point} objects representing the polygon's vertices.
     */
    @Override
    public Set<Point> getPoints() {
        return this.points;
    }
}
