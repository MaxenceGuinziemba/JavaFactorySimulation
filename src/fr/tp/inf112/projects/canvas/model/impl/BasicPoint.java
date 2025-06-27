package fr.tp.inf112.projects.canvas.model.impl;

import java.io.Serializable;

import fr.tp.inf112.projects.canvas.model.Point;

/**
 * Basic implementation of the {@code Point} interface.
 * @author Dominique Blouin
 *
 */
public class BasicPoint implements Point, Serializable {

    private static final long serialVersionUID = 5630077292027232863L;

    private final int xCoordinate;

    private final int yCoordinate;

    public BasicPoint() {
        this(0, 0);
    }

    public BasicPoint(final int xCoordinate, 
                       final int yCoordinate) {
        super();

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public int compareTo(Point point) {
        return 0;
    }

    @Override
    public String toString() {
        return "(x:" + xCoordinate + " ; y:" + yCoordinate +")";
    }
}
