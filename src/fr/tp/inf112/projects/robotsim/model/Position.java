package fr.tp.inf112.projects.robotsim.model;

import java.io.Serializable;

/**
 * Represents a position in a 2D space with x and y coordinates.
 * Provides methods to get and set coordinates, and to enforce limits on the coordinates.
 * 
 * @author team-24
 * 
 */
public class Position implements Visitable, Serializable {

    private static final long serialVersionUID = 1L;

    private static int[] x_limit = null;
    private static int[] y_limit = null;

    private int xCoordinate;
    private int yCoordinate;

    /**
     * Constructs a Position with specified x and y coordinates.
     * The coordinates are clamped to the defined limits if any.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public Position(int x, int y) {
        this.xCoordinate = clampX(x);
        this.yCoordinate = clampY(y);
    }

    /**
     * Returns the x-coordinate of this position.
     *
     * @return the x-coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Returns the y-coordinate of this position.
     *
     * @return the y-coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /** {@inheritDoc} */
    @Override
    public int getxVisit() {
        return xCoordinate;
    }

    /** {@inheritDoc} */
    @Override
    public int getyVisit() {
        return yCoordinate;
    }

    /** {@inheritDoc} */
    @Override
    public Position getVisit() {
        return this;
    }

    public void setxCoordinate(int x) {
        this.xCoordinate = clampX(x);
    }

    public void setyCoordinate(int y) {
        this.yCoordinate = clampY(y);
    }

    /**
     * Sets both x and y coordinates at once.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setCoordinate(int x, int y) {
        this.xCoordinate = clampX(x);
        this.yCoordinate = clampY(y);
    }

    /**
     * Sets the x-coordinate limit.
     *
     * @param min the minimum x-coordinate value
     * @param max the maximum x-coordinate value
     */
    public static void setxLimit(int min, int max) {
        if (min <= max) {
            x_limit = new int[] { min, max };
        } else {
            x_limit = null;
        }
    }

    /**
     * Sets the y-coordinate limit.
     *
     * @param min the minimum y-coordinate value
     * @param max the maximum y-coordinate value
     */
    public static void setyLimit(int min, int max) {
        if (min <= max) {
            y_limit = new int[] { min, max };
        } else {
            y_limit = null;
        }
    }

    /**
     * Clamps the x-coordinate to the defined limits if any.
     *
     * @param x the x-coordinate to clamp
     * @return the clamped x-coordinate
     */
    private static int clampX(int x) {
        if (x_limit == null || x_limit.length != 2) return x;
        return Math.max(x_limit[0], Math.min(x, x_limit[1]));
    }

    /**
     * Clamps the y-coordinate to the defined limits if any.
     *
     * @param y the y-coordinate to clamp
     * @return the clamped y-coordinate
     */
    private static int clampY(int y) {
        if (y_limit == null || y_limit.length != 2) return y;
        return Math.max(y_limit[0], Math.min(y, y_limit[1]));
    }

    /**
     * Indicate that a {@link Position} is not capable of charging.
     *
     * @return {@code false}.
     */
    @Override
    public boolean isCharging() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "(" + xCoordinate + "," + yCoordinate + ")";
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Position other = (Position) obj;
        return this.xCoordinate == other.xCoordinate && this.yCoordinate == other.yCoordinate;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(xCoordinate);
        result = 31 * result + Integer.hashCode(yCoordinate);
        return result;
    }

    /**
     * Returns the name of this position in the format Position(x,y).
     *
     * @return the name of this position
     */
    @Override
    public String getName() {
        return "Position(" + xCoordinate + "," + yCoordinate + ")";
    }
}
