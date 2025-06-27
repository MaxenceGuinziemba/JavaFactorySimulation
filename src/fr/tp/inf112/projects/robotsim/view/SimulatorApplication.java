package fr.tp.inf112.projects.robotsim.view;

import java.util.logging.Logger;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import fr.tp.inf112.projects.canvas.view.CanvasViewer;
import fr.tp.inf112.projects.canvas.model.CanvasPersistenceManager;
import fr.tp.inf112.projects.canvas.model.impl.BasicCanvasPersistenceManager;
import fr.tp.inf112.projects.robotsim.model.*;
import fr.tp.inf112.projects.robotsim.model.exception.*;
import fr.tp.inf112.projects.robotsim.controller.*;

/**
 * Entry point for the robot simulation application.
 *
 * The application initializes a {@link Factory}, sets up {@link Room}s, {@link Door}s,
 * {@link ProductionMachine}s, {@link ChargingStation}s, and {@link Robot}s, and starts
 * the simulation using a {@link SimulatorController} and {@link CanvasViewer}.
 * 
 * @author team-24
 */
public class SimulatorApplication {
    private static final Logger LOGGER = Logger.getLogger(SimulatorApplication.class.getName());

    /**
     * Main method to start the robot simulation.
     *
     * @param args Command-line arguments for the application.
     */
    public static void main(String[] args) {
        LOGGER.info("Starting the robot simulator...");
        LOGGER.config("With parameters " + Arrays.toString(args) + ".");

        Factory factory = null;

        int scenario = 0;

        if (args.length > 0) {
            try {
                scenario = Integer.parseInt(args[0]);
                if (scenario < 0 || scenario > 2) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid scenario argument: " + Arrays.toString(args) + ". Starting with default scenario (0).");
                scenario = 0;
            } catch (Exception e) {
                scenario = 0;
            }
        }

        if (scenario == 0 || scenario == 1) {
            factory = new Factory(null, "Factory-" + scenario, 1200, 700);

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

            Robot robot0 = new Robot("Robot-0", 450, 260, factory, true);

            robot0.addToVisit(new Position(450, 260));
            robot0.addToVisit(prods.get(0));
            robot0.addToVisit(conveyors.get(0));
            robot0.addToVisit(prods.get(1));
            robot0.addToVisit(prods.get(1));

            if (scenario == 1) {
                Robot robot1 = new Robot("Robot-1", 930, 40, factory, true);

                robot1.addToVisit(new Position(930, 40));
                robot1.addToVisit(prods.get(1));
                robot1.addToVisit(conveyors.get(0));
            }

        } else if (scenario == 2) {
            factory = new Factory(null, "Factory-2", 800, 600);

            List<Room> rooms = new ArrayList<>();
            // x, y, width, height
            int[][] roomData = {
                {10, 10, 780, 580},
            };
            for (int i = 0; i < roomData.length; i++) {
                rooms.add(new Room("Room-" + i, roomData[i][0], roomData[i][1], roomData[i][2], roomData[i][3], factory));
            }

            List<Door> doors = new ArrayList<>();
            // x, y, size, parentRoom
            int[][] doorData = {
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
                {50, 340, 80, 0},
                {20, 500, 80, 0},
                {305, 380, 80, 0},
                {440, 380, 80, 0},
                {700, 500, 80, 0}
            };
            String[] prodVisit = {
                "top",
                "d-top-right",
                "top",
                "bottom",
                "d-top-left"
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
                {40, 30, 100, 100},
                {345, 480, 100, 100},
                {660, 30, 100, 100}
            };

            for (int i = 0; i < chargData.length; i++) {
                chargs.add(new ChargingStation("Charging Station-" + i, chargData[i][0], chargData[i][1], chargData[i][2], chargData[i][3], factory));
            }

            List<Conveyor> conveyors = new ArrayList<>();
            //x, y, width, height
            int[][] conveyorData = {
                {180, 10, 120, 310},
                {480, 10, 120, 310}
            };
            String[] conveyorVisit = {
                "right",
                "left"
            };

            for (int i = 0; i < conveyorData.length; i++) {
                conveyors.add(new Conveyor("Conveyor-" + i, conveyorData[i][0], conveyorData[i][1], conveyorData[i][2], conveyorData[i][3], factory));
                conveyors.get(i).setVisitPosition(conveyorVisit[i]);
            }

            Robot robot0 = new Robot("Robot-0", 380, 230, factory, true);

            robot0.addToVisit(new Position(380, 230));
            robot0.addToVisit(conveyors.get(0));
            robot0.addToVisit(prods.get(0));
            robot0.addToVisit(prods.get(1));
            robot0.addToVisit(conveyors.get(1));
            robot0.addToVisit(prods.get(4));
            robot0.addToVisit(prods.get(3));
            robot0.addToVisit(conveyors.get(0));
        }

        if (factory != null) {
            CanvasPersistenceManager persistenceManager = new BasicCanvasPersistenceManager();
            SimulatorController controller = new SimulatorController(factory, persistenceManager);
            CanvasViewer viewer = new CanvasViewer(controller);
        }

    }
}
