package fr.tp.inf112.projects.robotsim.model;

import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.canvas.model.impl.BasicRectangleShape;
import fr.tp.inf112.projects.robotsim.model.exception.InvalidComponentPlacementException;

/**
 * Represents a door in the robot simulation.
 * A door can be opened or closed and is associated with a parent {@link Room}.
 * 
 * @author team-24
 */
public class Door extends Component {
    private static final long serialVersionUID = 1L;
    private boolean openState = false; //the open/close state is solely visual
    private boolean isVertical; // true -> vertical / false -> horizontal
    private final Room parentRoom;

    /**
     * Constructs a Door with specified position, size, and parent {@link Room}.
     * 
     * @param x X-coordinate of the door's position.
     * @param y Y-coordinate of the door's position.
     * @param size Size of the door.
     * @param parentRoom The {@link Room} to which the door belongs.
     * @throws InvalidComponentPlacementException If the door's position is not on the room's border.
     */
    public Door(int x, int y, int size, Room parentRoom) throws InvalidComponentPlacementException {
        // Door are not "linked" to the factory "per se"
        super("door", x, y, 0, 0, BasicStyle.DOOR_CLOSE, null, parentRoom.getFactory(), false);
        this.parentRoom = parentRoom;

        if (!parentRoom.isOnBorder(x, y)) {
            throw new InvalidComponentPlacementException("Coordinates of door not on room border.");
        }

        int roomX = parentRoom.getxCoordinate();
        int roomY = parentRoom.getyCoordinate();
        int roomW = parentRoom.getWidth();
        int roomH = parentRoom.getHeight();

        int width = 5, height = 5; // 5 is the thickness of the door
        // Determine orientation and clamp size
        if (x == roomX || x == roomX + roomW) {
            height = Math.min(size, roomH - (y - roomY));
            x -= width / 2;
            isVertical = true;
        } else if (y == roomY || y == roomY + roomH) {
            width = Math.min(size, roomW - (x - roomX));
            y -= height / 2;
            isVertical = false;
        }
        // Theorically, this second check is useless, since an InvalidDoorPlacementException
        // should've been raised earlier if these conditions were not met. But let's be cautious.

        this.setCoordinate(x, y);
        this.setWidth(width);
        this.setHeight(height);
        this.setShape(new BasicRectangleShape(width, height));
        this.parentRoom.addDoor(this);
    }

    /**
     * Gets the parent {@link Room} of the door.
     * 
     * @return The parent {@link Room} of the door.
     */
    public final Room getParentRoom() { return this.parentRoom; }

    /**
     * Checks if the door is open.
     * 
     * @return True if the door is open, false otherwise.
     */
    public final boolean isOpen() { return this.openState; }

    /**
     * Opens the door and updates its style.
     */
    public final void open() {
        if (!openState) {
            this.openState = true;
            this.setStyle(BasicStyle.DOOR_OPEN);
            getFactory().notifyObservers();
        }
    }

    /**
     * Closes the door and updates its style.
     */
    public final void close() {
        if (openState) {
            this.openState = false;
            this.setStyle(BasicStyle.DOOR_CLOSE);
            getFactory().notifyObservers();
        }
    }

    /**
     * Checks if a point at (x, y) fits in the door (i.e., is within the door's open area).
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the point fits in the open door
     */
    public boolean fitsInDoor(int x, int y) {

        int left = getxCoordinate();
        int top = getyCoordinate();
        int right = left + getWidth();
        int bottom = top + getHeight();

        return x >= left && x < right && y >= top && y < bottom;
    }

    /**
     * Checks if a square centered at (x, y) with side length `size` fits entirely in the door.
     *
     * @param x the x-coordinate of the square's center
     * @param y the y-coordinate of the square's center
     * @param size the size of the square's side
     * @return true if the square fits entirely within the open door
     */
    public boolean fitsInDoor(int x, int y, int size) {
        return fitsInDoor(x, y, size, size);
    }

    /**
     * Checks if a rectangle centered at (x, y) with given width and height fits in the door.
     *
     * @param x the x-coordinate of the rectangle's center
     * @param y the y-coordinate of the rectangle's center
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return true if the rectangle fits entirely within the open door
     */
    public boolean fitsInDoor(int x, int y, int width, int height) {

        int left = getxCoordinate();
        int top = getyCoordinate();
        int right = left + getWidth();
        int bottom = top + getHeight();

        int rectLeft = x - width / 2;
        int rectRight = x + (width + 1) / 2; // +1 for odd sizes
        int rectTop = y - height / 2;
        int rectBottom = y + (height + 1) / 2;

        if (isVertical) {
            return rectTop >= top && rectBottom <= bottom;
        } else {
            return rectLeft >= left && rectRight <= right;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(Position position) {
        int x = position.getxCoordinate();
        int y = position.getyCoordinate();
        // Delegate to parentRoom's isObstacle method
        return parentRoom.isObstacle(position);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y) {
        // Delegate to parentRoom's isObstacle method
        return parentRoom.isObstacle(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y, int size) {
        // Delegate to parentRoom's isObstacle method
        return parentRoom.isObstacle(x, y, size);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y, int width, int height) {
        // Delegate to parentRoom's isObstacle method
        return parentRoom.isObstacle(x, y, width, height);
    }

    /**
     * Returns a string representation of the door.
     *
     * @return String representation of the door.
     */
    @Override
    public String toString() {
        return getName() + "-" + (openState ? "open" : "close") + "-";
    }

    /**
     * Defines the behavior of the door: opens if a robot overlays it, otherwise closes.
     */
    @Override
    public void behave() {
        for (Robot r : getFactory().getRobots()) {
            if (r.overlays(this)) {
                open();
                return;
            }
        }
        close();
        return;
    }
}
