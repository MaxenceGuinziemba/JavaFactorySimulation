package fr.tp.inf112.projects.canvas.model;

/**
 * Represents a figure to be displayed in the canvas of the canvas viewer user interface .
 * @author Dominique Blouin
 *
 */
public interface Figure {

    /**
     * Returns the name of this figure. This name will be displayed on the top left corner of the figure in the canvas.
     * @return A non {@code null} non empty {@code String} name.
     */
    String getName();

    /**
     * Returns the coordinate of this figure along the x axis with respect to the top left corner of the canvas containing
     * this figure.
     * @return A positive {@code int} value in pixels.
     */
    int getxCoordinate();

    /**
     * Returns the coordinate of this figure along the y axis with respect to the top left corner of the canvas containing
     * this figure.
     * @return A positive {@code int} value in pixels.
     */
    int getyCoordinate();

    /**
     * Returns a {@code Style} object characterizing how this figure should be displayed.
     * @return A {@code Style} object. If {@code null} is returned, the default user interface style
     * properties will be used.
     */
    Style getStyle();

    /**
     * Returns a {@code Shape} object specifying the shape to display this figure.
     * @return A {@code Shape} object. If {@code null} is returned, the default user interface style
     * properties will be used.
     */
    Shape getShape();
}
