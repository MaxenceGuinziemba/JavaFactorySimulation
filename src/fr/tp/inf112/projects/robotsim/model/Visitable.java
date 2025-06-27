package fr.tp.inf112.projects.robotsim.model;

/**
 * Represents a visitable component in the robot simulation.
 * A visitable component has a position and a name, and can be visited by robots.
 *
 * @author team-24
 */
public interface Visitable {

    /**
     * Gets the {@link Position} of the visitable component.
     *
     * @return The {@link Position} to visit.
     */
    Position getVisit();

    /**
     * Gets the X-coordinate of the visitable component.
     *
     * @return The X-coordinate of the center of the component.
     */
    int getxVisit();

    /**
     * Gets the X-coordinate of the visitable component.
     *
     * @return The Y-coordinate of the center of the component.
     */
    int getyVisit();

    /**
     * Tells if the visitable component is capable of charging.
     *
     * @return The boolean indicating charging capability.
     */
    boolean isCharging();

    /**
     * Gets the name of the visitable component.
     * 
     * @return The name of the component.
     */
    String getName();
}
