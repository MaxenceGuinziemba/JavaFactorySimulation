package fr.tp.inf112.projects.canvas.model.impl;

import java.io.Serializable;
import fr.tp.inf112.projects.canvas.model.RectangleShape;

/**
 * A basic implementation of the {@link RectangleShape} interface.
 * Represents a rectangle with specified width and height.
 * 
 * <p>
 * This class is serializable to allow persistence.
 * </p>
 * 
 * @author team-24
 */
public class BasicRectangleShape implements RectangleShape, Serializable {
    private int width;
    private int height;

    /**
     * Constructs a {@code BasicRectangleShape} with the specified width and height.
     * 
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public BasicRectangleShape(int width, int height) {
        this.height = height;
        this.width = width;
    }

    /**
     * Returns the width of the rectangle.
     * 
     * @return The width of the rectangle.
     */
    public int getWidth() { return this.width; }

    /**
     * Returns the height of the rectangle.
     * 
     * @return The height of the rectangle.
     */
    public int getHeight() { return this.height; }
}

