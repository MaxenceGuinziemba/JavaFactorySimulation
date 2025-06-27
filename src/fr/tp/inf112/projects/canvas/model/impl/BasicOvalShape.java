package fr.tp.inf112.projects.canvas.model.impl;

import java.io.Serializable;
import fr.tp.inf112.projects.canvas.model.OvalShape;

/**
 * A basic implementation of the {@link OvalShape} interface.
 * Represents an oval shape with specified height and width.
 * 
 * <p>
 * This class is serializable to allow persistence.
 * </p>
 * 
 * @author team-24
 */
public class BasicOvalShape implements OvalShape, Serializable {
    private final static long serialVersionUID = 1L;
    private int height;
    private int width;

    /**
     * Constructs a {@code BasicOvalShape} with the specified height and width.
     * 
     * @param height The height of the oval.
     * @param width  The width of the oval.
     */
    public BasicOvalShape(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Returns the height of the oval.
     * 
     * @return The height of the oval.
     */
    public int getHeight() { return this.height; }

    /**
     * Returns the width of the oval.
     * 
     * @return The width of the oval.
     */
    public int getWidth() { return this.width; }
}
