package fr.tp.inf112.projects.robotsim.model;

import java.util.Set;
import java.util.HashSet;
import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.canvas.model.Style;
import fr.tp.inf112.projects.canvas.model.Shape;
import fr.tp.inf112.projects.canvas.model.impl.BasicRectangleShape;

/**
 * Represents a charging station in the robot simulation.
 * A charging station can charge robots and manage their charging behavior.
 * 
 * @author team-24
 */
public class ChargingStation extends Component {

    private static final int DEFAULT_CHARGING_RATE = 10;

    private Set<Robot> chargingRobots;
    private final double chargingRate;

    /**
     * Constructs a ChargingStation with a default charging rate of 10.
     * Delegates to the main constructor.
     *
     * @param name Name of the charging station.
     * @param x X-coordinate of the top-left corner.
     * @param y Y-coordinate of the top-left corner.
     * @param width Width of the charging station.
     * @param height Height of the charging station.
     * @param factory Factory to which the charging station belongs.
     */
    public ChargingStation(String name, int x, int y, int width, int height, Factory factory) {
        this(name, x, y, width, height, DEFAULT_CHARGING_RATE, factory);
    }

    /**
     * Constructs a ChargingStation with a specified charging rate.
     * 
     * @param name Name of the charging station.
     * @param x X-coordinate of the top-left corner.
     * @param y Y-coordinate of the top-left corner.
     * @param width Width of the charging station.
     * @param height Height of the charging station.
     * @param chargingRate Rate at which the station charges robots.
     * @param factory Factory to which the charging station belongs.
     */
    public ChargingStation(String name, int x, int y, int width, int height, double chargingRate, Factory factory) {
        super(name, x, y,
              width,
              height,
              (Style)BasicStyle.CHARGING,
              (Shape)new BasicRectangleShape(width, height),
              factory,
              false
             );
        this.chargingRobots = new HashSet<>();
        this.chargingRate = chargingRate;
        setChargingCapability(true);
    }

    /**
     * Gets the charging rate of the station.
     * 
     * @return Charging rate of the station.
     */
    public double getChargingRate() {
        return this.chargingRate;
    }

    // public void setChargingRate(double newChargingRate) {
    //     this.chargingRate = Math.max(0, Math.min(100, newChargingRate));
    //     getFactory().notifyObservers();
    // }

    /**
     * Connects a robot to the charging station.
     * 
     * @param robot The robot to connect.
     * @return True if the robot was successfully connected, false otherwise.
     */
    public boolean connectRobot(Robot robot) {
        boolean toReturn = this.chargingRobots.add(robot);
        if (toReturn) {
            robot.hold();
            getFactory().notifyObservers();
        }
        return toReturn;
    }

    /**
     * Disconnects a robot from the charging station.
     * 
     * @param robot The robot to disconnect.
     * @return True if the robot was successfully disconnected, false otherwise.
     */
    public boolean disconnectRobot(Robot robot) {
        boolean toReturn = this.chargingRobots.remove(robot);
        if (toReturn) {
            robot.setNeedCharging(false);
            robot.release();
        }
        return toReturn;
    }

    /**
     * Defines the behavior of the charging station, including charging robots
     * and connecting robots that need charging.
     */
    public void behave() {
        for (Robot robot : chargingRobots) {
            double newBatteryLevel = robot.getBattery() + this.chargingRate;
            robot.setBattery(newBatteryLevel);
            if (newBatteryLevel >= 100) {
                disconnectRobot(robot);
            }
        }

        for (Robot robot : getFactory().getRobots()) {
            if (contains(robot) && robot.getNeedCharging()) {
                connectRobot(robot);
            }
        }
    }

    /**
     * Returns a string representation of the charging station.
     * 
     * @return String representation of the charging station.
     */
    @Override
    public String toString() {
        return getName() +"(" + chargingRate + ")" +  chargingRobots.toString();
    }
}
