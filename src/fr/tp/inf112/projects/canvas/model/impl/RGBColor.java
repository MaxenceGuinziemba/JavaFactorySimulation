package fr.tp.inf112.projects.canvas.model.impl;

import java.io.Serializable;

import fr.tp.inf112.projects.canvas.model.Color;

/**
 * Basic implementation of the {@code Color} interface storing the red, green and blue components as integer attributes.
 * @author Dominique Blouin
 *
 */
public class RGBColor implements Color, Serializable {
	
	private static final long serialVersionUID = 5961066409978511540L;

	/**
     * The color white in the default sRGB space.
     */
    public static final Color WHITE = new RGBColor(255, 255, 255);

    /**
     * The color light gray in the default sRGB space.
     */
    public static final Color LIGHT_GRAY = new RGBColor(192, 192, 192);

    /**
     * The color gray in the default sRGB space.
     */
    public static final Color GRAY = new RGBColor(128, 128, 128);

    /**
     * The color dark gray in the default sRGB space.
     */
    public static final Color DARK_GRAY = new RGBColor(64, 64, 64);

    /**
     * The color black in the default sRGB space.
     */
    public static final Color BLACK = new RGBColor(0, 0, 0);

    /**
     * The color red in the default sRGB space.
     */
    public static final Color RED = new RGBColor(255, 0, 0);

    /**
     * The color dark red in the default sRGB space.
     */
    public static final Color DARK_RED = new RGBColor(139, 0, 0);

    /**
     * The color pink in the default sRGB space.
     */
    public static final Color PINK = new RGBColor(255, 175, 175);

    /**
     * The color orange in the default sRGB space.
     */
    public static final Color ORANGE = new RGBColor(255, 200, 0);

    /**
     * The color yellow in the default sRGB space.
     */
    public static final Color YELLOW = new RGBColor(255, 255, 0);

    /**
     * The color green in the default sRGB space.
     */
    public static final Color GREEN = new RGBColor(0, 255, 0);

    /**
     * The color dark green in the default sRGB space.
     */
    public static final Color DARK_GREEN = new RGBColor(0, 139, 0);

    /**
     * The color magenta in the default sRGB space.
     */
    public static final Color MAGENTA = new RGBColor(255, 0, 255);

    /**
     * The color cyan in the default sRGB space.
     */
    public static final Color CYAN = new RGBColor(0, 255, 255);

    /**
     * The color blue in the default sRGB space.
     */
    public static final Color BLUE = new RGBColor(0, 0, 255);

    /**
     * An {@code int} attribute for the red component of this color
     */
    private final int redComponent;

    /**
     * An {@code int} attribute for the green component of this color
     */
	private final int greenComponent;
	
    /**
     * An {@code int} attribute for the blue component of this color
     */
	private final int blueComponent;
	
	public RGBColor() {
		this(0, 0, 0);
	}

	/**
	 * Constructs a color with the specified RGB components.
	 * @param redComponent a positive {@code int} value for the red color component. 
	 * @param greenComponent a positive {@code int} value for the green color component.
	 * @param blueComponent a positive {@code int} value for the blue color component.
	 */
	public RGBColor(final int redComponent, 
					final int greenComponent, 
					final int blueComponent) {
		super();
		
		this.redComponent = redComponent;
		this.greenComponent = greenComponent;
		this.blueComponent = blueComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRedComponent() {
		return redComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getGreenComponent() {
		return greenComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBlueComponent() {
		return blueComponent;
	}
}
