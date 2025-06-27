package fr.tp.inf112.projects.canvas.model;

import java.util.Collection;

/**
 * Represents the canvas of canvas viewer user interface into which 2D figures will be displayed.
 * 
 * @author Dominique Blouin
 *
 */
public interface Canvas {

    /**
     * Returns a unique identifier for this canvas model.
     * @return A unique {@code String} identifier.
     */
    String getId();

    /**
     * Sets the identifier of this canvas model.
     * @param id A unique {@code String} identifier.
     */
    void setId(String id);

    /**
     * Returns the name of this canvas.
     * @return A non {@code null} non empty {@code String} name.
     */
    String getName();

    /**
     * Returns the width of this canvas.
     * 
     * @return A positive {@code int} value for the width in pixels of this canvas.
     */
    int getWidth();

    /**
     * Returns the height of this canvas.
     * 
     * @return A positive {@code int} value for the height in pixels of this canvas.
     */
    int getHeight();

    /**
     * Returns the 2D geometric figures of this canvas.
     * @return A {@code Collection} of {@code Figure} objects.
     */
    Collection<Figure> getFigures();

    /**
     * Returns a {@code Style} object characterizing how this figure should be displayed.
     * @return A {@code Style} object. If {@code null} is returned, the default user interface style
     * properties will be used.
     */
    Style getStyle();
}
