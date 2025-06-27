package fr.tp.inf112.projects.robotsim.model;

import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.canvas.model.impl.BasicRectangleShape;

/**
 * Represents a conveyor in the robot simulation.
 * A conveyor is used to transport items within the factory.
 * 
 * @author team-24
 * 
 */
public class Conveyor extends Component {

    private static final int DEFAULT_CONVEY_RATE = 4;

    private final int conveyRate;
    private boolean activeState;

    /**
     * Constructs a Conveyor with a specified convey rate.
     *
     * @param name the name of the conveyor
     * @param x the x-coordinate of the conveyor
     * @param y the y-coordinate of the conveyor
     * @param width the width of the conveyor
     * @param height the height of the conveyor
     * @param conveyRate the rate at which the conveyor transports items
     * @param factory the factory to which the conveyor belongs
     */
    public Conveyor(String name, int x, int y, int width, int height, int conveyRate, Factory factory) {
        super(name, x, y, width, height, BasicStyle.CONVEYOR, new BasicRectangleShape(width, height), factory, true);
        this.conveyRate = conveyRate;
        this.activeState = false;
    }

    /**
     * Constructs a Conveyor with a default convey rate.
     * Delegates to the main constructor.
     *
     * @param name the name of the conveyor
     * @param x the x-coordinate of the conveyor
     * @param y the y-coordinate of the conveyor
     * @param width the width of the conveyor
     * @param height the height of the conveyor
     * @param factory the factory to which the conveyor belongs
     */
    public Conveyor(String name, int x, int y, int width, int height, Factory factory) {
        this(name, x, y, width, height, DEFAULT_CONVEY_RATE, factory);
    }

    /**
     * Gets the convey rate of the conveyor.
     *
     * @return the convey rate of the conveyor
     */
    public int getConveyRate() {
        return this.conveyRate;
    }

    /**
     * Checks if the conveyor is active.
     *
     * @return true if the conveyor is active, false otherwise
     */
    public boolean isActive() {
        return this.activeState;
    }

    /**
     * Activates the conveyor.
     */
    public void activate() {
        if (activeState) {
            if (getStyle() == BasicStyle.CONVEYOR) {
                setStyle(BasicStyle.CONVEYOR_ALT);
            } else {
                setStyle(BasicStyle.CONVEYOR);
            }
        } else {
            this.activeState = true;
        }
        getFactory().notifyObservers();
    }

    /**
     * Deactivates the conveyor.
     */
    public void deactivate() {
        if (activeState)
            this.activeState = false;
        getFactory().notifyObservers();
    }

    /**
     * Defines the behavior of the conveyor: stops if a robot is at its visit position, otherwise alternates style to simulate movement.
     */
    @Override
    public void behave() {
        boolean robotAtVisit = false;
        for (Robot r : getFactory().getRobots()) {
            Position visitPos = this.getVisit();
            if (r.getxCoordinate() == visitPos.getxCoordinate() && r.getyCoordinate() == visitPos.getyCoordinate()) {
                robotAtVisit = true;
                break;
            }
        }
        if (robotAtVisit) {
            deactivate();
        } else {
            activate();
        }
    }
    
}
