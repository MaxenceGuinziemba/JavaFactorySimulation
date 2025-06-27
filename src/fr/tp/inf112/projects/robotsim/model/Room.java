package fr.tp.inf112.projects.robotsim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.canvas.model.impl.BasicRectangleShape;

/**
 * Represents a room in the robot simulation.
 * A room can contain {@link Door}s and is part of a {@link Factory}.
 * 
 * <p>
 * The room's behavior includes checking if a position is on its border
 * and managing its associated {@link Door}s. Borders of a room are considered
 * obstacles.
 * </p>
 * 
 * @author team-24
 */
public class Room extends Component {
    private List<Door> doors = new ArrayList<>();

    /**
     * Constructs a Room with specified attributes.
     * 
     * @param name Name of the room.
     * @param x X-coordinate of the room's position.
     * @param y Y-coordinate of the room's position.
     * @param width Width of the room.
     * @param height Height of the room.
     * @param factory The {@link Factory} to which the room belongs.
     */
    public Room(String name, int x, int y, int width, int height, Factory factory) {
        super(name, x, y, width, height, BasicStyle.ROOM, new BasicRectangleShape(width, height), factory, false);
    }

    /**
     * Checks if a given position is on the border of the room.
     * 
     * @param x X-coordinate of the position.
     * @param y Y-coordinate of the position.
     * @return True if the position is on the border, false otherwise.
     */
    public boolean isOnBorder(int x, int y) {
        int left = getxCoordinate();
        int right = left + getWidth();
        int top = getyCoordinate();
        int bottom = top + getHeight();

        boolean onLeft = x == left && y >= top && y <= bottom;
        boolean onRight = x == right && y >= top && y <= bottom;
        boolean onTop = y == top && x >= left && x <= right;
        boolean onBottom = y == bottom && x >= left && x <= right;

        return onLeft || onRight || onTop || onBottom;
    }

    /**
     * Checks if a rectangle with center at (x, y) and specified width and height is on the border of the room.
     * This checks if any part of the rectangle, including its interior, is touching the border.
     * 
     * @param x X-coordinate of the CENTER of the rectangle.
     * @param y Y-coordinate of the CENTER of the rectangle.
     * @param width Width of the rectangle.
     * @param height Height of the rectangle.
     * @return True if any part of the rectangle or its interior is on the border, false otherwise.
     */
    public boolean isOnBorder(int x, int y, int width, int height) {
        int halfW = width / 2;
        int halfH = height / 2;
        int left = x - halfW;
        int right = x + halfW;
        int top = y - halfH;
        int bottom = y + halfH;

        int roomLeft = getxCoordinate();
        int roomRight = getxCoordinate() + getWidth();
        int roomTop = getyCoordinate();
        int roomBottom = getyCoordinate() + getHeight();

        boolean inxLimit = left <= roomRight && right >= roomLeft;
        boolean inyLimit = bottom >= roomTop && top <= roomBottom;

        // Check if any part of the rectangle, including its edges, intersects with the border.
        boolean onLeftEdge = left <= roomLeft && right >= roomLeft && inyLimit;
        boolean onRightEdge = right >= roomRight && left <= roomRight && inyLimit;
        boolean onTopEdge = top <= roomTop && bottom >= roomTop && inxLimit;
        boolean onBottomEdge = bottom >= roomBottom && top <= roomBottom && inxLimit;

        return onLeftEdge || onRightEdge || onTopEdge || onBottomEdge;
    }

    /**
     * Checks if a square with center at (x, y) and size is on the border of the room.
     * This checks if any part of the square, including its interior, is touching the border.
     * 
     * @param x X-coordinate of the CENTER of the square.
     * @param y Y-coordinate of the CENTER of the square.
     * @param size Length of the square's sides.
     * @return True if any part of the square or its interior is on the border, false otherwise.
     */
    public boolean isOnBorder(int x, int y, int size) {
        return isOnBorder(x, y, size, size);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(Position position) {
        int x = position.getxCoordinate();
        int y = position.getyCoordinate();
        return isOnBorder(x, y) && doors.stream().noneMatch(door -> door.fitsInDoor(x, y));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y) {
        return isOnBorder(x, y) && doors.stream().noneMatch(door -> door.fitsInDoor(x, y));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y, int size) {
        return isOnBorder(x, y, size) && doors.stream().noneMatch(door -> door.fitsInDoor(x, y, size));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y, int width, int height) {
        return isOnBorder(x, y, width, height) && doors.stream().noneMatch(door -> door.fitsInDoor(x, y, width, height));
    }

    /**
     * Adds a {@link Door} to the room if it belongs to this room.
     * 
     * @param door The {@link Door} to add.
     * @return True if the door was successfully added, false otherwise.
     */
    public boolean addDoor(Door door) {
        if (door.getParentRoom() != this) return false;
        doors.add(door);
        return true;
    }

    /**
     * Returns the list of doors in the room.
     *
     * @return List of {@link Door} objects in the room.
     */
    public List<Door> getDoors() {
        return doors;
    }

    /**
     * Returns the name of the room with its doors.
     *
     * @return String representation of the room and its doors.
     */
    @Override
    public String toString() {
        return getName() + doors.toString();
    }
}
