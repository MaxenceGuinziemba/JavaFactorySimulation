package fr.tp.inf112.projects.canvas.model;

/**
 * Represent the stroke properties of elements to be displayed in a canvas viewer.
 * @author Dominique Blouin
 *
 */
public interface Stroke {

    /**
     * Returns an object representing the color to be used to display this stroke.
     * @return A {@code Color} object. If {@code null} is returned, the default user interface default line color
     * will be used.
     */
    Color getColor();

    /**
     * Returns the thickness of this stroke.
     * @return A positive {@code float} value for the thickness of the stroke. 
     */
    float getThickness();

    /**
     * Returns the dash pattern to be used to display this stroke.
     * @return An array of positive {@code float} values representing the length of the dash pattern elements.
     */
    float[] getDashPattern();
}
