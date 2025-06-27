package fr.tp.inf112.projects.robotsim.test;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import fr.tp.inf112.projects.robotsim.model.*;
import fr.tp.inf112.projects.robotsim.model.impl.DijkstraGraphFactoryPathFinder;
import fr.tp.inf112.projects.robotsim.model.exception.InvalidComponentPlacementException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility class for visualizing and exporting graphs of the factory layout.
 * Provides methods to export a graph as a PNG image and a main method for testing.
 *
 * @author team-24
 */
public class GraphVisualizer {

    /**
     * Exports a filtered region of the factory's graph to a PNG image file.
     *
     * @param factory The factory whose graph is to be exported.
     * @param robot The robot used for graph construction.
     * @param filename The name of the output PNG file.
     */
    public static void exportGraphToPNG(fr.tp.inf112.projects.robotsim.model.Factory factory, Robot robot, String filename) {
        System.out.println("Building graph...");
        Graph<Position, DefaultWeightedEdge> fullGraph =
            DijkstraGraphFactoryPathFinder.buildFactoryGraph(factory, robot.getWidth(), robot.getHeight());

        System.out.println("Filtering graph region...");
        Graph<Position, DefaultWeightedEdge> filteredGraph =
            new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        // Add only vertices in region
        for (Position p : fullGraph.vertexSet()) {
            if ( 1080 <= p.getxCoordinate() && p.getxCoordinate() <= 1090 && 565 <= p.getyCoordinate() && p.getyCoordinate() <= 575) {
                if (factory.isObstacle(p)) System.out.println("obs");
                filteredGraph.addVertex(p);
            }
        }

        // Add edges between filtered vertices if they exist in the original graph
        for (Position source : filteredGraph.vertexSet()) {
            for (DefaultWeightedEdge edge : fullGraph.edgesOf(source)) {
                Position target = fullGraph.getEdgeTarget(edge);
                if (source.equals(target)) {
                    target = fullGraph.getEdgeSource(edge);  // handle undirected edges
                }
                if (filteredGraph.containsVertex(target)) {
                    if (!filteredGraph.containsEdge(source, target)) {
                        DefaultWeightedEdge newEdge = filteredGraph.addEdge(source, target);
                        if (newEdge != null) {
                            filteredGraph.setEdgeWeight(newEdge, fullGraph.getEdgeWeight(edge));
                        }
                    }
                }
            }
        }

        if (filteredGraph.vertexSet().isEmpty()) {
            System.err.println("Filtered graph has no vertices. Aborting.");
            return;
        }

         try {
            System.out.println("Creating adapter...");
            JGraphXAdapter<Position, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(filteredGraph);

            graphAdapter.setLabelsClipped(true); // optional, trims labels if needed
            graphAdapter.getEdgeToCellMap().forEach((edge, cell) -> cell.setValue(""));

            System.out.println("Applying layout...");
            mxCircleLayout layout = new mxCircleLayout(graphAdapter);
            layout.execute(graphAdapter.getDefaultParent());

            System.out.println("Rendering image...");
            BufferedImage image = mxCellRenderer.createBufferedImage(
                graphAdapter, null, 2, java.awt.Color.WHITE, true, null
            );

            if (image == null) {
                System.err.println("Image creation failed. Check if graph has valid layout/bounds.");
                return;
            }

            File outputfile = new File("img/graph.png");
            ImageIO.write(image, "PNG", outputfile);
            System.out.println("Graph exported to ./img/graph.png");

        } catch (Exception e) {
            System.err.println("Error while visualizing or saving graph:");
            e.printStackTrace();
        }
    }

    /**
     * Main method for testing the graph visualization.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Set up test factory scenario
        fr.tp.inf112.projects.robotsim.model.Factory factory = new Factory(null, "Factory", 1200, 700);

        List<Room> rooms = new ArrayList<>();
        // x, y, width, height
        int[][] roomData = {
            {10, 10, 550, 320},
            {640, 10, 550, 320},
            {10, 390, 550, 320},
            {640, 390, 550, 320}
        };
        for (int i = 0; i < roomData.length; i++) {
            rooms.add(new Room("Room-" + i, roomData[i][0], roomData[i][1], roomData[i][2], roomData[i][3], factory));
        }

        List<Door> doors = new ArrayList<>();
        // x, y, size, parentRoom
        int[][] doorData = {
            {40, 330, 120, 0},
            {640, 80, 120, 1},
            {680, 330, 120, 1},
            {390, 390, 120, 2},
            {855, 390, 120, 3},
        };

        for (int i = 0; i < doorData.length; i++) {
            try {
                doors.add(new Door(doorData[i][0], doorData[i][1], doorData[i][2], rooms.get(doorData[i][3])));
            } catch (InvalidComponentPlacementException ex) {
                ex.printStackTrace();
            }
        }

        List<ProductionMachine> prods = new ArrayList<>();
        //x, y, size, parentRoom, visitPosition
        int[][] prodData = {
            {30, 20, 80, 0},
            {1090, 590, 80, 3},
        };
        String[] prodVisit = {
            "bottom",
            "top"
        };

        for (int i = 0; i < prodData.length; i++) {
            try {
                prods.add(new ProductionMachine("Production Machine-" + i, prodData[i][0], prodData[i][1], prodData[i][2], rooms.get(prodData[i][3]), factory));
                prods.get(i).setVisitPosition(prodVisit[i], Robot.DEFAULT_SIZE);
            } catch (InvalidComponentPlacementException ex) {
                ex.printStackTrace();
            }
        }

        List<ChargingStation> chargs = new ArrayList<>();
        //x, y, width, height
        int[][] chargData = {
            {660, 30, 100, 100},
            {30, 410, 100, 260},
            {660, 570, 100, 100},
        };

        for (int i = 0; i < chargData.length; i++) {
            chargs.add(new ChargingStation("Charging Station-" + i, chargData[i][0], chargData[i][1], chargData[i][2], chargData[i][3], factory));
        }

        List<Conveyor> conveyors = new ArrayList<>();
        //x, y, width, height
        int[][] conveyorData = {
            {1060, 20, 120, 300},
        };
        String[] conveyorVisit = {
            "left"
        };

        for (int i = 0; i < conveyorData.length; i++) {
            conveyors.add(new Conveyor("Conveyor-" + i, conveyorData[i][0], conveyorData[i][1], conveyorData[i][2], conveyorData[i][3], factory));
            conveyors.get(i).setVisitPosition(conveyorVisit[i]);
        }

        Robot robot1 = new Robot("Robot-1", 935, 145, factory, true);

        exportGraphToPNG(factory, robot1, "graph.png");
    }
}
