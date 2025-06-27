package fr.tp.inf112.projects.canvas.model.impl;

import java.io.Serializable;
import fr.tp.inf112.projects.canvas.model.Style;
import fr.tp.inf112.projects.canvas.model.Stroke;
import fr.tp.inf112.projects.canvas.model.Color;

/**
 * A basic implementation of the {@link Style} interface.
 * Represents a style with a background color and a stroke.
 * 
 * <p>
 * This class is serializable to allow persistence.
 * </p>
 * 
 * <p>
 * Includes predefined styles for various use cases.
 * </p>
 * 
 * @author team-24
 */
public class BasicStyle implements Style, Serializable {
    public static final BasicStyle FACTORY = new BasicStyle(RGBColor.WHITE,
                                                            new BasicStroke(RGBColor.BLACK, 10, new float[]{1})
                                                            );
    public static final BasicStyle ROBOT = new BasicStyle(RGBColor.GREEN,
                                                          new BasicStroke(RGBColor.BLACK, 1, new float[]{1})
                                                          );
    public static final BasicStyle ROOM = new BasicStyle(RGBColor.GRAY,
                                                         new BasicStroke(RGBColor.BLACK, 5, new float[]{1, 1})
                                                         );
    public static final BasicStyle DOOR_CLOSE = new BasicStyle(RGBColor.DARK_GRAY,
                                                         new BasicStroke(RGBColor.GRAY, 5, new float[]{1})
                                                         );
    public static final BasicStyle DOOR_OPEN = new BasicStyle(RGBColor.WHITE,
                                                         new BasicStroke(RGBColor.GRAY, 5, new float[]{1})
                                                         );
    public static final BasicStyle CHARGING = new BasicStyle(new RGBColor(87, 190, 255), // light blue
                                                          new BasicStroke(RGBColor.BLUE, 2, new float[]{4, 4})
                                                          );
    public static final BasicStyle PRODUCTION_ACTIVE = new BasicStyle(RGBColor.YELLOW,
                                                       new BasicStroke(RGBColor.BLACK, 5, new float[]{2, 5, 10})
                                                       );
    public static final BasicStyle PRODUCTION_ACTIVE_ALT = new BasicStyle(RGBColor.YELLOW,
                                                       new BasicStroke(RGBColor.BLACK, 5, new float[]{10, 2, 5})
                                                       );
    public static final BasicStyle PRODUCTION_INACTIVE = new BasicStyle(new RGBColor(59, 59, 0), // dark yellow
                                                         new BasicStroke(RGBColor.BLACK, 0.25f, new float[]{0.2f, 0.2f})
                                                         );
    public static final BasicStyle CONVEYOR = new BasicStyle(RGBColor.DARK_GRAY,
                                              new BasicStroke(RGBColor.BLACK, 5, new float[]{5, 10})
                                              );
    public static final BasicStyle CONVEYOR_ALT = new BasicStyle(RGBColor.DARK_GRAY,
                                              new BasicStroke(RGBColor.BLACK, 5, new float[]{10, 5})
                                              );
    private final Color backgroundColor;
    private final Stroke stroke;

    /**
     * Constructs a {@code BasicStyle} with the specified background color and stroke.
     * 
     * @param backgroundColor The background color of the style.
     * @param stroke          The stroke of the style.
     */
    public BasicStyle(Color backgroundColor, Stroke stroke) {
        this.backgroundColor = backgroundColor;
        this.stroke = stroke;
    }

    /**
     * Returns the background color of the style.
     * 
     * @return The {@link Color} of the background.
     */
    @Override
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Returns the stroke of the style.
     * 
     * @return The {@link Stroke} of the style.
     */
    @Override
    public Stroke getStroke() {
        return this.stroke;
    }
}
