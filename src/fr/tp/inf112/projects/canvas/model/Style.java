package fr.tp.inf112.projects.canvas.model;

/**
 * Represents a style, consisting of a background color and stroke properties.
 *
 * @author Dominique Blouin
 *
 */
public interface Style {

    /**
     * Returns an object representing the color to be used to display the background of this figure
     * @return A {@code Color} object. If {@code null} is returned, the default user interface background color
     * will be used.
     */
    Color getBackgroundColor();

    /**
     * Returns an object specifying the properties of the contour of this figure.
     * @return a {@code Stroke} object specifying the properties of the contour of this figure. If {@code null} 
     * is returned, the default values from the user interface preferences will be used.
     */
    Stroke getStroke();
}
