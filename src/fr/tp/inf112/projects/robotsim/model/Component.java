package fr.tp.inf112.projects.robotsim.model;

import java.io.Serializable;
import fr.tp.inf112.projects.canvas.model.Figure;
import fr.tp.inf112.projects.canvas.model.Style;
import fr.tp.inf112.projects.canvas.model.Shape;

// A component will have an anchor point called position (convention : top left)
// and a width and height (respectively x and y related)
//          position
//              ̌
//   height ^  -----------
//          |  |         |
//          |  |Component|
//          |  |         |
//          |  -----------
//             -----------> width

/**
 * This file contains the definition of the abstract Component class, which represents
 * a component in the robot simulation. The class provides methods to manage the
 * component's position, dimensions, style, and interactions with other components.
 * 
 * @author team-24
 */
public abstract class Component implements Figure, Serializable, Visitable {
    private final static long serialVersionUID = 1L;

    private final String name;
    /** Position of the component (top-left corner). */
    private Position position;
    private Position visitPosition;
    private int width;
    private int height;
    private Style style;
    private Shape shape;
    private Factory factory;
    private boolean chargingCapability = false; // only charging when children class specifies it.
    private final boolean obstacle;

    /**
     * Constructor to initialize a component, add itself to the non-null factory provided.
     * Sets {@code visitPosition} to the center of the component by default.
     *
     * @param name Name of the component.
     * @param x X-coordinate of the top-left corner.
     * @param y Y-coordinate of the top-left corner.
     * @param width Width of the component.
     * @param height Height of the component.
     * @param style Style of the component.
     * @param shape Shape of the component.
     * @param factory Factory to which the component belongs.
     * @param obstacle Specify if the component should be considered an obstacle.
     */
    public Component(String name, int x, int y, int width, int height, Style style, Shape shape, Factory factory, boolean obstacle) {
        this.name = name;
        this.position = new Position(x, y);
        this.width = width;
        this.height = height;
        this.style = style;
        this.shape = shape;
        this.factory = factory;
        if (factory != null) {
            factory.addComponent(this);
        }
        this.obstacle = obstacle;
        this.visitPosition = getCenter();
    }

    /**
     * Gets the name of the component.
     *
     * @return Name of the component.
     */
    @Override
    public String getName() { return name; }

    /**
     * Gets the X-coordinate of the component's position.
     *
     * @return X-coordinate of the position.
     */
    @Override
    public final int getxCoordinate() { return this.position.getxCoordinate(); }

    /**
     * Gets the Y-coordinate of the component's position.
     *
     * @return Y-coordinate of the position.
     */
    @Override
    public final int getyCoordinate() { return this.position.getyCoordinate(); }

    /**
     * Gets the style of the component.
     *
     * @return Style of the component.
     */
    @Override
    public final Style getStyle() { return this.style; }

    /**
     * Gets the shape of the component.
     *
     * @return Shape of the component.
     */
    @Override
    public Shape getShape() { return this.shape; }

    /**
     * Gets the height of the component.
     *
     * @return Height of the component.
     */
    public int getHeight() { return height; }

    /**
     * Gets the width of the component.
     *
     * @return Width of the component.
     */
    public int getWidth() { return width; }

    /**
     * Gets the anchor position of the component.
     *
     * @return Position of the component.
     */
    public Position getAnchor() { return position; }

    /**
     * Gets the factory to which the component belongs.
     *
     * @return Factory of the component.
     */
    public Factory getFactory() { return factory; }

    /**
     * Tells if this component is an obstacle;
     *
     * @return a {@code boolean} indicating if it is an obstacle or not.
     */
    public boolean isObstacle() { return obstacle; }

    /**
     * Tells if this component is an obstacle for the given {@code position};
     *
     * @return a {@code boolean} indicating if it is an obstacle or not for the given position.
     */
    public boolean isObstacle(Position position) { 
        return obstacle && overlays(position);
    }

    /**
     * Tells if this component is an obstacle for the given coordinates;
     *
     * @return a {@code boolean} indicating if it is an obstacle or not for the given coordinates.
     */
    public boolean isObstacle(int x, int y) { 
        return obstacle && overlays(x, y);
    }

    /**
     * Tells if this component is an obstacle for the given square;
     *
     * @return a {@code boolean} indicating if it is an obstacle or not for the given square.
     */
    public boolean isObstacle(int x, int y, int size) { 
        return obstacle && overlays(x, y, size);
    }

    /**
     * Tells if this component is an obstacle for the given rectangle;
     *
     * @return a {@code boolean} indicating if it is an obstacle or not for the given square.
     */
    public boolean isObstacle(int x, int y, int width, int height) { 
        return obstacle && overlays(x, y, width, height);
    }

    /**
     * Gets the corners of the component.
     * Order of corners: top-left, top-right, bottom-left, bottom-right.
     *
     * @return Array of corner positions.
     */
    public final Position[] getCorners() {
        Position[] corners = new Position[4];
        corners[0] = this.position;
        corners[1] = new Position(
                        position.getxCoordinate() + width,
                        position.getyCoordinate()
                        );
        corners[2] = new Position(
                        position.getxCoordinate(),
                        position.getyCoordinate() + height
                        );
        corners[3] = new Position(
                        position.getxCoordinate() + width,
                        position.getyCoordinate() + height
                        );
        return corners;
    }

    /**
     * Sets the position of the component.
     *
     * @param position New position of the component.
     */
    public void setCoordinate(Position position) {
        this.position = position;
        getFactory().notifyObservers();
    }

    /**
     * Sets the position of the component.
     *
     * @param x New X-coordinate.
     * @param y New Y-coordinate.
     */
    public void setCoordinate(int x, int y) {
        setCoordinate(new Position(x, y));
    }

    /**
     * Sets the position of the component according to its center.
     *
     * @param x New X-coordinate of the center.
     * @param y New Y-coordinate of the center.
     */
    public void setCoordinateCenter(int x, int y) {
        setCoordinate(x - width / 2, y - width / 2);
    }

    /**
     * Sets the height of the component.
     *
     * @param height New height of the component.
     */
    public void setHeight(int height) {
        this.height = height;
        getFactory().notifyObservers();
    }

    /**
     * Sets the width of the component.
     *
     * @param width New width of the component.
     */
    public void setWidth(int width) {
        this.width = width;
        getFactory().notifyObservers();
    }

    /**
     * Sets the dimensions of the component.
     *
     * @param width New width of the component.
     * @param height New height of the component.
     */
    public void setDimension(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        getFactory().notifyObservers();
    }

    /**
     * Sets the style of the component.
     *
     * @param style New style of the component.
     */
    public void setStyle(Style style) {
        this.style = style;
        getFactory().notifyObservers();
    }

    /**
     * Sets the shape of the component.
     *
     * @param shape New shape of the component.
     */
    public void setShape(Shape shape) {
        this.shape = shape;
        getFactory().notifyObservers();
    }

    /**
     * Checks if this component overlaps with a square centered at (x, y).
     *
     * @param x the x-coordinate of the square's center
     * @param y the y-coordinate of the square's center
     * @param size the size of the square (width and height)
     * @return {@code true} if the square overlaps this component, {@code false} otherwise
     */
    public boolean overlays(int x, int y, int size) {
        return overlays(x, y, size, size);
    }

    /**
     * Checks if this component overlaps with a rectangle centered at (x, y).
     *
     * @param x the x-coordinate of the rectangle's center
     * @param y the y-coordinate of the rectangle's center
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return {@code true} if the rectangle overlaps this component, {@code false} otherwise
     */
    public boolean overlays(int x, int y, int width, int height) {
        // Bounds of the rectangle to check
        int rectLeft = x - width / 2;
        int rectRight = x + width / 2;
        int rectTop = y - height / 2;
        int rectBottom = y + height / 2;

        // Bounds of this component
        int thisLeft = position.getxCoordinate();
        int thisRight = position.getxCoordinate() + this.width;
        int thisTop = position.getyCoordinate();
        int thisBottom = position.getyCoordinate() + this.height;

        return thisLeft < rectRight && thisRight > rectLeft &&
               thisTop < rectBottom && thisBottom > rectTop;
    }

    /**
     * Checks if this component overlaps with another component.
     *
     * @param other The other component.
     * @return True if the components intersect, false otherwise.
     */
    public boolean overlays(Component other) {
        int thisLeft = position.getxCoordinate();
        int thisRight = position.getxCoordinate() + width;
        int thisBottom = position.getyCoordinate() + height;
        int thisTop = position.getyCoordinate();

        int otherLeft = other.position.getxCoordinate();
        int otherRight = other.position.getxCoordinate() + other.getWidth();
        int otherBottom = other.position.getyCoordinate() + other.getHeight();
        int otherTop = other.position.getyCoordinate();

        return thisLeft < otherRight && thisRight > otherLeft &&
               thisTop < otherBottom && thisBottom > otherTop;
    }

    /**
     * Checks if a point is inside the component.
     *
     * @param x X-coordinate of the point.
     * @param y Y-coordinate of the point.
     * @return True if the point is inside, false otherwise.
     */
    public boolean overlays(int x, int y) {
        int left = this.getxCoordinate();
        int top = this.getyCoordinate();
        int right = left + this.getWidth();
        int bottom = top + this.getHeight();

        boolean inXAxis = x >= left && x <= right;
        boolean inYAxis = y >= top && y <= bottom;

        return inXAxis && inYAxis;
    }

    /**
     * Checks if a position is inside the component.
     *
     * @param position Position to check.
     * @return True if the position is inside, false otherwise.
     */
    public boolean overlays(Position position) {
        return this.overlays(position.getxCoordinate(), position.getyCoordinate());
    }

    /**
     * Checks if another component is completely inside this component.
     *
     * @param other The other component.
     * @return True if the other component is inside, false otherwise.
     */
    public boolean contains(Component other) {
        boolean flag = true;
        int i = 0;
        Position[] otherCorners = other.getCorners();
        while (flag && i < 4) {
            flag = flag && this.overlays(otherCorners[i]);
            i++;
        }
        return flag;
    }

    /**
     * Gets the center position of the component
     *
     * @return The {@link Position} of the center of the component
     */
    public Position getCenter() {
        return new Position(position.getxCoordinate() + width / 2,
                            position.getyCoordinate() + height / 2
                            );
    }

    /**
     * Gets the x-coordinate of the center of the component.
     *
     * @return The x-coordinate of the center
     */
    public int getxCenter() {
        return getCenter().getxCoordinate();
    }

    /**
     * Gets the y-coordinate of the center of the component.
     *
     * @return The y-coordinate of the center
     */
    public int getyCenter() {
        return getCenter().getyCoordinate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getVisit() {
        return visitPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getxVisit() {
        return visitPosition.getxCoordinate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getyVisit() {
        return visitPosition.getyCoordinate();
    }

    /**
     * Sets the visit position to the specified {@code position}.
     *
     * @param position the position to assign as the visit target
     */
    public void setVisitPosition(Position position) {
        visitPosition = position;
    }

    /**
     * Sets the visit position to the specified {@code x}, {@code y}.
     *
     * @param x the x-coordinate of the position to assign as the visit target
     * @param y the y-coordinate of the position to assign as the visit target
     */
    public void setVisitPosition(int x, int y) {
        visitPosition = new Position(x, y);
    }

    /**
     * Sets {@code visitPosition} based on a directional label around a rectangular component.
     * The position is offset from the component by a margin of {@code size / 2 + 1}.
     * <p>
     * Valid labels (case-insensitive):
     * <ul>
     *   <li>"top", "bottom", "left", "right", "center"</li>
     *   <li>"d-top-left", "d-top-right", "d-bottom-left", "d-bottom-right"</li>
     * </ul>
     *
     * @param label the direction relative to the component
     * @param size the robot or spacing size used to calculate margin
     * @throws IllegalArgumentException if the label is invalid
     */
    public void setVisitPosition(String label, int size) {
        int margin = (size / 2) + 1;
        int x = position.getxCoordinate();
        int y = position.getyCoordinate();
        int centerX = x + (width + 1) / 2;
        int centerY = y + (height + 1) / 2;

        switch (label.toLowerCase()) {
            case "top":
                visitPosition = new Position(centerX, y - margin);
                break;
            case "bottom":
                visitPosition = new Position(centerX, y + height + margin);
                break;
            case "left":
                visitPosition = new Position(x - margin, centerY);
                break;
            case "right":
                visitPosition = new Position(x + width + margin, centerY);
                break;
            case "center":
                visitPosition = new Position(centerX, centerY);
                break;
            case "d-top-left":
                visitPosition = new Position(x - margin, y - margin);
                break;
            case "d-top-right":
                visitPosition = new Position(x + width + margin, y - margin);
                break;
            case "d-bottom-left":
                visitPosition = new Position(x - margin, y + height + margin);
                break;
            case "d-bottom-right":
                visitPosition = new Position(x + width + margin, y + height + margin);
                break;
            default:
                throw new IllegalArgumentException("Unknown label: " + label);
        }
    }

    /**
     * Sets the visit position based on a directional label using the default robot size.
     *
     * @param label the direction label (e.g., "top", "center", "d-bottom-right")
     * @see #setVisitPosition(String, int) for more details.
     */
    public void setVisitPosition(String label) {
        this.setVisitPosition(label, Robot.DEFAULT_SIZE);
    }

    /**
     * Defines the behavior of the component.
     * Can be overridden by subclasses.
     */
    public void behave() {}

    /**
     * Calculates the distance to another component (center-to-center).
     *
     * @param other The other component.
     * @return Distance to the other component.
     */
    public double distTo(Component other) {
        double distX = Math.pow(other.getxCenter() - this.getxCenter(), 2);
        double distY = Math.pow(other.getyCenter() - this.getyCenter(), 2);
        return Math.sqrt(distX + distY);
    }

    /**
     * Tells if the component is capable of charging.
     *
     * @return A boolean indicating if the component is capable of charging.
     */
    public boolean isCharging() {
        return chargingCapability;
    }

    /**
     * Sets if the component is capable of charging.
     *
     * @param value A boolean indicating if the component is capable of charging.
     */
    public void setChargingCapability (boolean value) {
        this.chargingCapability = value;
    }

    /**
     * Returns a string representation of the component.
     *
     * @return String representation of the component.
     */
    public String toString() {
        return name;
    }
}
