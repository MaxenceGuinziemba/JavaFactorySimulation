package fr.tp.inf112.projects.robotsim.model;

import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.robotsim.model.exception.InvalidComponentPlacementException;
import fr.tp.inf112.projects.canvas.model.impl.BasicPolygonShape;
import fr.tp.inf112.projects.canvas.model.Style;

/**
 * Represents a production machine in the simulation.
 * A production machine is placed inside a room and can be activated or deactivated.
 * It has a production rate and a visual style that changes based on its state.
 * 
 * @author team-24
 * 
 */
public class ProductionMachine extends Component {

    private static final Style ACTIVE_STYLE= BasicStyle.PRODUCTION_ACTIVE;
    private static final Style ACTIVE_STYLE_ALT = BasicStyle.PRODUCTION_ACTIVE_ALT;
    private static final Style INACTIVE_STYLE = BasicStyle.PRODUCTION_INACTIVE;
    private static final int DEFAULT_PRODUCTION_RATE = 4; // in unit/update_animation
    private static final int DEFAULT_SIZE = 50;

    private final Room parentRoom;
    private int productionRate;
    private boolean activeState;

    /**
     * Constructs a ProductionMachine with the specified attributes.
     *
     * @param name the name of the production machine.
     * @param x the x-coordinate of the production machine.
     * @param y the y-coordinate of the production machine.
     * @param size the size of the production machine.
     * @param productionRate the production rate of the machine.
     * @param parentRoom the room in which the machine is placed.
     * @param factory the factory to which the machine belongs.
     * @throws InvalidComponentPlacementException if the machine does not fit or is not inside the specified room.
     */
    public ProductionMachine(String name, int x, int y, int size, int productionRate, Room parentRoom, Factory factory)
    throws InvalidComponentPlacementException {
        super(name, x, y, size, size,
              BasicStyle.PRODUCTION_ACTIVE,
              new BasicPolygonShape(x, y, 8, size),
              factory,
              true
             );

        if (!parentRoom.contains(this)) {
            throw new InvalidComponentPlacementException("Production Machine does not fit, or isn't inside the specified parent room");
        }
        this.parentRoom = parentRoom;
        this.productionRate = productionRate;
        this.activeState = true;
    }

    public ProductionMachine(String name, int x, int y, int size, Room parentRoom, Factory factory)
    throws InvalidComponentPlacementException {
        this(name, x, y, size, DEFAULT_PRODUCTION_RATE, parentRoom, factory);
    }

    public ProductionMachine(String name, int x, int y, Room parentRoom, Factory factory)
    throws InvalidComponentPlacementException {
        this(name, x, y, DEFAULT_SIZE, DEFAULT_PRODUCTION_RATE, parentRoom, factory);
    }

    /**
     * Gets the production rate of the machine.
     *
     * @return the production rate of the machine
     */
    public int getProductionRate() { return this.productionRate; }

    /**
     * Gets the current state of the machine.
     *
     * @return true if the machine is active, false otherwise
     */
    public boolean getState() { return this.activeState; }

    /**
     * Gets the parent room of the machine.
     *
     * @return the room in which the machine is placed
     */
    public Room getParentRoom() { return this.parentRoom; }

    /**
     * Activates the machine, changing its style to active.
     */
    public void activate() {
        if (activeState) {
            if (getStyle() == ACTIVE_STYLE) {
                setStyle(ACTIVE_STYLE_ALT);
            } else {
                setStyle(ACTIVE_STYLE);
            }
        } else {
            this.setStyle(ACTIVE_STYLE);
            this.activeState = true;
        }
        getFactory().notifyObservers();
    }

    /**
     * Deactivates the machine, changing its style to inactive.
     */
    public void deactivate() {
        if (this.activeState) {
            this.setStyle(INACTIVE_STYLE);
            this.activeState = false;
            getFactory().notifyObservers();
        }
    }

    /**
     * Defines the behavior of the production machine: deactivates if a robot is at its visit position, otherwise activates.
     */
    @Override
    public void behave() {
        boolean robotAtVisit = false;
        for (Robot r : getFactory().getRobots()) {
            Position visitPos = this.getVisit();
            if (r.getxCoordinate() == getVisit().getxCoordinate() && r.getyCoordinate() == getVisit().getyCoordinate()) {
                robotAtVisit = true;
                break;
            }
        }
        if (robotAtVisit) {
            System.out.println("A robot is visinting.");
            deactivate();
        } else {
            activate();
        }
    }
}


