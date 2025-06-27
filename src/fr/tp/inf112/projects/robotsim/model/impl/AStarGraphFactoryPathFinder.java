package fr.tp.inf112.projects.robotsim.model.impl;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

import fr.tp.inf112.projects.robotsim.model.Factory;
import fr.tp.inf112.projects.robotsim.model.FactoryPathFinder;
import fr.tp.inf112.projects.robotsim.model.Path;
import fr.tp.inf112.projects.robotsim.model.Robot;
import fr.tp.inf112.projects.robotsim.model.Visitable;
import fr.tp.inf112.projects.robotsim.model.Position;

/**
 * Implements a path finder for the factory using a graph-based approach and A* algorithm.
 * Builds a graph of the factory layout and computes shortest paths for robots.
 *
 * @author team-24
 */
public class AStarGraphFactoryPathFinder implements FactoryPathFinder {
    private Robot robot;
    private Graph<Position, DefaultWeightedEdge> graph = null;
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(AStarGraphFactoryPathFinder.class.getName());

    /**
     * Constructs a AStarGraphFactoryPathFinder for a given robot.
     * Compute the graph of the factory adpated to the robot's dimensions.
     *
     * @param robot The robot for which to compute paths.
     */
    public AStarGraphFactoryPathFinder(Robot robot) {
        this.robot = robot;
        this.graph = buildFactoryGraph(robot.getFactory(), robot.getWidth(), robot.getHeight());
    }

    /**
     * Constructs a AStarGraphFactoryPathFinder for a given robot.
     *
     * @param robot The robot for which to compute paths.
     * @param graph The graph of the factory adapted to robot.
     */
    public AStarGraphFactoryPathFinder(Robot robot, Graph<Position, DefaultWeightedEdge> graph) {
        this.robot = robot;
        this.graph = graph;
    }

    /**
     * Builds a graph representation of the factory for pathfinding.
     *
     * @param factory The factory to build the graph from.
     * @param robotWidth The width of the robot.
     * @param robotHeight The height of the robot.
     * @return The constructed graph.
     */
    public static Graph<Position, DefaultWeightedEdge> buildFactoryGraph(Factory factory, int robotWidth, int robotHeight) {
        LOGGER.info("Building factory graph...");
        Graph<Position, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        int startX = factory.getxCoordinate();
        int startY = factory.getyCoordinate();
        int endX = startX + factory.getWidth();
        int endY = startY + factory.getHeight();

        // Directions: {dx, dy, weight}
        int[][] directions = {
            {0, -1, 100},   // Up
            {0, 1, 100},    // Down
            {-1, 0, 100},   // Left
            {1, 0, 100},    // Right
            {-1, -1, 141},  // Top-left
            {-1, 1, 141},   // Bottom-left
            {1, -1, 141},   // Top-right
            {1, 1, 141}     // Bottom-right
        };

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {

                Position current = new Position(x, y);
                graph.addVertex(current);

                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];

                    if (nx >= startX && nx < endX && ny >= startY && ny < endY &&
                        !factory.isObstacle(nx, ny, robotWidth, robotHeight)) {

                        Position neighbor = new Position(nx, ny);
                        graph.addVertex(neighbor);

                        if (!graph.containsEdge(current, neighbor)) {
                            DefaultWeightedEdge edge = graph.addEdge(current, neighbor);
                            graph.setEdgeWeight(edge, dir[2]);
                        }
                    }
                }
            }
        }
        LOGGER.info("Factory graph built with " + graph.vertexSet().size() + " vertices and " + graph.edgeSet().size() + " edges.");
        return graph;
    }

    /**
     * Returns the graph used for pathfinding.
     *
     * @return The graph of positions and edges.
     */
    public Graph<Position, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    /**
     * Returns the robot associated for the pathfinding.
     *
     * @return The robot associated to this path finder.
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Finds the shortest path from the robot's current position to the target visitable.
     *
     * @param target The target visitable to reach.
     * @return A {@link Path} object representing the shortest path, or null if no path exists.
     */
    @Override
    public Path findPath(Visitable target) {
        if (target == null) return null;

        Position start = new Position(robot.getxCenter(), robot.getyCenter());
        Position end = new Position(target.getxVisit(), target.getyVisit());

        // Define a simple Euclidean heuristic
        AStarAdmissibleHeuristic<Position> heuristic = (v1, v2) -> {
            int dx = v1.getxCoordinate() - v2.getxCoordinate();
            int dy = v1.getyCoordinate() - v2.getyCoordinate();
            return Math.sqrt(dx * dx + dy * dy);
        };

        // Use A* algorithm with the heuristic
        AStarShortestPath<Position, DefaultWeightedEdge> astar = new AStarShortestPath<>(graph, heuristic);

        GraphPath<Position, DefaultWeightedEdge> aStarPath = astar.getPath(start, end);

        List<Position> shortestPath = aStarPath != null ? aStarPath.getVertexList() : null;

        return new Path(shortestPath, target);
    }

}
