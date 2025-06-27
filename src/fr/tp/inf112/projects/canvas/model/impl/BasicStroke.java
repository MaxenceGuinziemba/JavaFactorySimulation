package fr.tp.inf112.projects.canvas.model.impl;

import java.io.Serializable;
import fr.tp.inf112.projects.canvas.model.Stroke;
import fr.tp.inf112.projects.canvas.model.Color;

/**
 * A basic implementation of the {@link Stroke} interface.
 * Represents a stroke with a specified color, thickness, and dash pattern.
 * 
 * <p>
 * This class is serializable to allow persistence.
 * </p>
 * 
 * @author team-24
 */
public class BasicStroke implements Stroke, Serializable {
    private final Color color;
    private final float thickness;
    private final float[] dashPattern;

    /**
     * Constructs a {@code BasicStroke} with the specified color, thickness, and dash pattern.
     * 
     * @param color       The color of the stroke.
     * @param thickness   The thickness of the stroke.
     * @param dashPattern The dash pattern of the stroke, or {@code null} for a solid line.
     */
    public BasicStroke(Color color, float thickness, float[] dashPattern) {
        this.color = color;
        this.thickness = thickness;
        this.dashPattern = dashPattern;
    }

    /**
     * Returns the color of the stroke.
     * 
     * @return The {@link Color} of the stroke.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Returns the thickness of the stroke.
     * 
     * @return The thickness of the stroke.
     */
    @Override
    public float getThickness() {
        return thickness;
    }

    /**
     * Returns the dash pattern of the stroke.
     * 
     * @return A float array representing the dash pattern, or {@code null} for a solid line.
     */
    @Override
    public float[] getDashPattern() {
        return dashPattern;
    }
}
