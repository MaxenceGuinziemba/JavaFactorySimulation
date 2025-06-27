package fr.tp.inf112.projects.robotsim.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a path consisting of a sequence of positions, with a target to reach.
 * The path allows navigation between positions with the ability to move forward and backward.
 *
 * @author team-24
 */
public class Path {
    private final List<Position> path;
    private final Visitable target;
    private int currentIndex;

    /**
     * Constructs a new Path.
     * 
     * @param path A list of positions representing the path.
     * @param target The visitable target the path is leading to.
     */
    public Path(List<Position> path, Visitable target) {
        this.path = path;
        this.target = target;
        this.currentIndex = 0;
    }

    /**
     * Gets the list of positions in the path.
     * 
     * @return A list of positions representing the path.
     */
    public List<Position> getPath() {
        return path;
    }

    /**
     * Gets the target that the path is leading to.
     * 
     * @return The visitable target.
     */
    public Visitable getTarget() {
        return target;
    }

    /**
     * Gets the current index in the path.
     * 
     * @return The current index of the path.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Sets the current index in the path.
     * 
     * @param currentIndex The new current index of the path.
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * Gets the current position in the path based on the current index.
     * 
     * @return The current position.
     */
    public Position getCurrentPosition() {
        return path.get(currentIndex);
    }

    /**
     * Checks if there is a next position in the path.
     * 
     * @return true if there is a next position, false otherwise.
     */
    public boolean hasNext() {
        if (isNull()) return false;
        return currentIndex < path.size() - 1;
    }

    /**
     * Checks if the Path is null (meaning attribute {@code path} is null)
     * 
     * @return true if path is null, false otherwise.
     */
    public boolean isNull() {
        return path == null;
    }

    /**
     * Gets the next position in the path based on the current index.
     * 
     * @return The next position, or null if it does not exists.
     */
    public Position getNextPosition() {
        if (!hasNext()) return null;
        return path.get(currentIndex + 1);
    }

    /**
     * Checks if there is a previous position in the path.
     * 
     * @return true if there is a previous position, false otherwise.
     */
    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    /**
     * Moves to the next position in the path, if there is one.
     */
    public void advance() {
        if (hasNext()) {
            currentIndex++;
        }
    }

    /**
     * Moves to the previous position in the path, if there is one.
     */
    public void stepBack() {
        if (hasPrevious()) {
            currentIndex--;
        }
    }

    /**
     * Returns a string representation of the path.
     *
     * @return String representation of the path.
     */
    @Override
    public String toString() {
        return "Path{" +
               "path=" + (isNull() ? "null" : path) +
               ", currentIndex=" + currentIndex +
               ", currentPosition=" + (isNull() || path.isEmpty() ? "N/A" : getCurrentPosition()) +
               ", target=" + (target != null ? target.getName() : "null") +
               '}';
    }
}
