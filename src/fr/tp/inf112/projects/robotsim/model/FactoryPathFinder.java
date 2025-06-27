package fr.tp.inf112.projects.robotsim.model;

import java.util.List;

/**
 * Interface for finding paths within the factory.
 * Provides a method to compute and retrieve a list of positions representing a path.
 * This interface is used to decouple path calculation from robot behavior logic.
 *
 * @author team-24
 */
public interface FactoryPathFinder {

    /**
     * Computes the shortest path between to a target visit.
     *
     * @param target The target visit to reach.
     * @return A {@link Path} object representing the path to target.
     */
    Path findPath(Visitable target);
}
