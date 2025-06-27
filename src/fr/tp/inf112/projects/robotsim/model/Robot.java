package fr.tp.inf112.projects.robotsim.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import fr.tp.inf112.projects.canvas.model.Style;
import fr.tp.inf112.projects.canvas.model.Stroke;
import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.canvas.model.impl.BasicStroke;
import fr.tp.inf112.projects.canvas.model.impl.RGBColor;
import fr.tp.inf112.projects.canvas.model.impl.BasicOvalShape;
import fr.tp.inf112.projects.canvas.model.Style;
import fr.tp.inf112.projects.robotsim.model.impl.AStarGraphFactoryPathFinder;


/**
 * Represents a robot in the robot simulation.
 * A robot can move, visit components, and manage its battery level.
 * 
 * The robot's behavior includes moving to specified {@link Visitable} components,
 * charging at {@link ChargingStation}s, and interacting with the {@link Factory}.
 * 
 * @author team-24
 */
public class Robot extends Component {

    private final static long serialVersionUID = 1L;

    public static final int DEFAULT_SIZE = 40;
    private static final int DEFAULT_CAPACITY = 1000;
    private static final int DEFAULT_SPEED = 10;
    private static final Stroke DEFAULT_STROKE = new BasicStroke(RGBColor.PINK,
                                                                 2,
                                                                 new float[]{1}
                                                                );
    private static final Style BATTERY_LVL0 = new BasicStyle(RGBColor.DARK_RED,
                                                             DEFAULT_STROKE
                                                            );
    private static final Style BATTERY_LVL1 = new BasicStyle(RGBColor.RED,
                                                             DEFAULT_STROKE
                                                            );
    private static final Style BATTERY_LVL2 = new BasicStyle(RGBColor.ORANGE,
                                                             DEFAULT_STROKE
                                                            );
    private static final Style BATTERY_LVL3 = new BasicStyle(RGBColor.YELLOW,
                                                             DEFAULT_STROKE
                                                            );
    private static final Style BATTERY_LVL4 = new BasicStyle(RGBColor.GREEN,
                                                             DEFAULT_STROKE
                                                            );
    private static final Style BATTERY_LVL5 = new BasicStyle(RGBColor.DARK_GREEN,
                                                             DEFAULT_STROKE
                                                            );
    private int speed;
    private List<Visitable> toVisit;
    private int currentVisit;
    private double battery; // in percentage
    private boolean needCharging;
    private boolean held; // will specify if robot is held by a machine (for charging, producing, ...)
    private final int capacity;
    private final boolean hasPathFinder;
    private transient FactoryPathFinder pathFinder = null;
    private transient Path currentPath;

    /**
     * Constructs a Robot with specified attributes.
     *
     * @param name Name of the robot.
     * @param x X-coordinate of the robot's position.
     * @param y Y-coordinate of the robot's position.
     * @param width The width of the robot.
     * @param height The height of the robot.
     * @param speed Speed of the robot.
     * @param capacity The battery capacity of the robot.
     * @param style The {@link Style} of the robot.
     * @param factory The {@link Factory} to which the robot belongs.
     * @param pathFinder The {@link FactoryPathFinder} that will helps the robot's movements.
     */
    public Robot(String name, int x, int y, int width, int height, int speed, int capacity, Style style, Factory factory, boolean hasPathFinder) {
        super(name, x, y, width, height, style,
              new BasicOvalShape(width, height),
              factory,
              false
             );
        this.speed = speed;
        this.toVisit = new ArrayList<>();
        this.currentVisit = 0;
        this.battery = 100;
        this.needCharging = false;
        this.held = false;
        this.capacity = capacity;
        this.hasPathFinder = hasPathFinder;
        if (getFactory() != null) {
            getFactory().addRobot(this);
        }
        this.currentPath = null;
    }

    /**
     * Constructs a Robot with default style.
     * Delegates to the main constructor.
     *
     * @param name Name of the robot.
     * @param x X-coordinate of the robot's position.
     * @param y Y-coordinate of the robot's position.
     * @param width The width of the robot.
     * @param height The height of the robot.
     * @param speed Speed of the robot.
     * @param capacity The battery capacity of the robot.
     * @param factory The {@link Factory} to which the robot belongs.
     * @param hasPathFinder Whether the robot uses a path finder.
     */
    public Robot(String name, int x, int y, int width, int height, int speed, int capacity, Factory factory, boolean hasPathFinder) {
        this(name, x, y, width, height, speed, capacity, BATTERY_LVL5, factory, hasPathFinder);
    }

    /**
     * Constructs a Robot with default size and specified speed and capacity.
     * Delegates to the main constructor.
     *
     * @param name Name of the robot.
     * @param x X-coordinate of the robot's position.
     * @param y Y-coordinate of the robot's position.
     * @param speed Speed of the robot.
     * @param capacity The battery capacity of the robot.
     * @param factory The {@link Factory} to which the robot belongs.
     * @param hasPathFinder Whether the robot uses a path finder.
     */
    public Robot(String name, int x, int y, int speed, int capacity, Factory factory, boolean hasPathFinder) {
        this(name, x, y, DEFAULT_SIZE, DEFAULT_SIZE, speed, capacity, BATTERY_LVL5, factory, hasPathFinder);
    }

    /**
     * Constructs a Robot with default size and capacity, and specified speed.
     * Delegates to the main constructor.
     *
     * @param name Name of the robot.
     * @param x X-coordinate of the robot's position.
     * @param y Y-coordinate of the robot's position.
     * @param speed Speed of the robot.
     * @param factory The {@link Factory} to which the robot belongs.
     * @param hasPathFinder Whether the robot uses a path finder.
     */
    public Robot(String name, int x, int y, int speed, Factory factory, boolean hasPathFinder) {
        this(name, x, y, speed, DEFAULT_CAPACITY, factory, hasPathFinder);
    }

    /**
     * Constructs a Robot with default size, speed, and capacity.
     * Delegates to the main constructor.
     *
     * @param name Name of the robot.
     * @param x X-coordinate of the robot's position.
     * @param y Y-coordinate of the robot's position.
     * @param factory The {@link Factory} to which the robot belongs.
     * @param hasPathFinder Whether the robot uses a path finder.
     */
    public Robot(String name, int x, int y, Factory factory, boolean hasPathFinder) {
        this(name, x, y, DEFAULT_SPEED, factory, hasPathFinder);
    }

    /**
     * Initializes the pathFinder based on the hasPathFinder flag.
     */
    private void initializePathFinder() {
        if (hasPathFinder && pathFinder == null) {

            // we search if there's already a corresponding graph
            for (Robot other : getFactory().getRobots()) {
                if (other != this && other.getWidth() == getWidth() && other.getHeight() == getHeight()) {
                    FactoryPathFinder otherPathFinder = other.getPathFinder();
                    if (otherPathFinder != null && otherPathFinder instanceof AStarGraphFactoryPathFinder) {
                        Graph<Position, DefaultWeightedEdge> otherGraph = ((AStarGraphFactoryPathFinder) otherPathFinder).getGraph();
                        if (otherGraph != null) {
                            pathFinder = new AStarGraphFactoryPathFinder(this, otherGraph);
                            break;
                        }
                    }
                }
            }
            // if not we have to calculate the graph
            if (pathFinder == null) pathFinder = new AStarGraphFactoryPathFinder(this);
        }
    }

    /**
     * Gets the speed of the robot.
     * 
     * @return The speed of the robot.
     */
    public int getSpeed() { return this.speed; }

    /**
     * Gets the battery level of the robot.
     * 
     * @return The battery level of the robot in percentage.
     */
    public double getBattery() {
        return this.battery;
    }

    /**
     * Gets the path finder of the robot.
     * 
     * @return The path finder of the robot, null if {@code hasPathFinder} is false.
     */
    public FactoryPathFinder getPathFinder() {
        return pathFinder;
    }

    /**
     * Gets the current path of the robot.
     * 
     * @return The current path of the robot, null if {@code hasPathFinder} is false.
     */
    public Path getCurrentPath() {
        if (!hasPathFinder) return null;
        return currentPath;
    }

    /**
     * Gets the list of {@code Visitable} to visit by the robot.
     * 
     * @return The list of {@code Visitable} to visit by the robot.
     */
    public List<Visitable> getToVisit() {
        return toVisit;
    }

    /**
     * Checks if the robot is held by a machine.
     * 
     * @return True if the robot is held, false otherwise.
     */
    public boolean isHeld() {
        return this.held;
    }

    /**
     * Checks if the robot needs charging.
     * 
     * @return True if the robot needs charging, false otherwise.
     */
    public boolean getNeedCharging() {
        return this.needCharging;
    }

    /**
     * Sets the battery level of the robot and updates its style based on the level.
     * 
     * @param battery The new battery level in percentage.
     */
    public void setBattery(double battery) {
        this.battery = Math.max(0, Math.min(100, battery));
        if (this.battery == 0) {
            setStyle(BATTERY_LVL0);
        } else if (this.battery == 100) {
            setStyle(BATTERY_LVL5);
        } else {
            int lvl = (int) (this.battery / 25);
            switch (lvl) {
                case 0:
                    setStyle(BATTERY_LVL1);
                    break;
                case 1:
                    setStyle(BATTERY_LVL2);
                    break;
                case 2:
                    setStyle(BATTERY_LVL3);
                    break;
                case 3:
                    setStyle(BATTERY_LVL4);
                    break;
                default:
                    setStyle(BATTERY_LVL0); // fallback, should not occcur
            }
        }
        getFactory().notifyObservers();
    }

    /**
     * Sets the speed of the robot.
     * 
     * @param speed The new speed of the robot.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
        getFactory().notifyObservers();
    }

    /**
     * Sets whether the robot needs charging.
     * 
     * @param value True if the robot needs charging, false otherwise.
     */
    public void setNeedCharging(boolean value) {
        if (needCharging != value) {
            this.needCharging = value;
            getFactory().notifyObservers();
        }
    }

    /**
     * Holds the robot, indicating it is being used by a machine.
     */
    public void hold() {
        if (!this.held) {
            this.held = true;
            getFactory().notifyObservers();
        }
    }

    /**
     * Releases the robot, indicating it is no longer being used by a machine.
     */
    public void release() {
        if (this.held) {
            this.held = false;
            this.currentVisit += 1;
            getFactory().notifyObservers();
        }
    }

    /**
     * Adds a collection of {@link Visitable} components to the robot's visit list.
     *
     * @param visitables the collection of {@link Visitable} components to add
     */
    public void addToVisit(Collection<? extends Visitable> visitables) {
        this.toVisit.addAll(visitables);
        getFactory().notifyObservers();
    }

    /**
     * Adds a {@link Visitable} component to the robot's visit list.
     * 
     * @param visitable The {@link Visitable} component to add.
     */
    public void addToVisit(Visitable visitable) {
        this.toVisit.add(visitable);
        getFactory().notifyObservers();
    }

    /**
     * Adds a {@link Visitable} component to the robot's visit list without waiting.
     * 
     * @param visitable The {@link Visitable} component to add.
     */
    public void addToVisitNoWait(Visitable visitable) {
        if (toVisit.size() > 0 && getCurrentVisit() == toVisit.get(0)) { // make sure the robot will go back to the first place "specified by user"
            toVisit.add(0, toVisit.get(0));
            currentVisit += 1;
        }
        toVisit.add(currentVisit, visitable);
        getFactory().notifyObservers();
    }

    /**
     * Returns the current {@link Visitable} the robot is visiting.
     *
     * @return The current {@link Visitable} component, or null if none.
     */
    private Visitable getCurrentVisit() {
        if (currentVisit < toVisit.size()) {
            return toVisit.get(currentVisit);
        } else if (currentVisit == toVisit.size() && !toVisit.isEmpty()) {
            return toVisit.get(0);
        } else {
            return null;
        }
    }

    /**
     * Behaves according to the path finder if enabled, otherwise follows the visit list.
     */
    @Override
    public void behave() {

        if (this.battery == 0) {
            setNeedCharging(true);
            this.goToChargingStation();
        }

        if (hasPathFinder) {
            if (pathFinder == null) initializePathFinder();
            behavePathFinder();
        } else {
            Visitable v = getCurrentVisit();
            if (v != null && !held) {
                int visitX = v.getxVisit(), visitY = v.getyVisit();

                if (getxCenter() == visitX && getyCenter() == visitY) {
                    this.currentVisit += (currentVisit != toVisit.size()) ? 1 : 0;
                } else {
                    this.move(v);
                }
            } else if ( v == null && !held && !needCharging) {
                this.currentVisit += 1;
            }
        }
    }

    /**
     * Moves (without obstacle-awareness) the robot towards a specified {@link Visitable} component.
     * 
     * @param visitable The {@link Visitable} component to move towards.
     * @return the distance covered.
     */
    public double move(Visitable visitable) {
        int visitX = visitable.getxVisit(), visitY = visitable.getyVisit();
        int x = getxCenter(), y = getyCenter();
        int vecX = visitX - x, vecY = visitY - y;
        int normSquared = vecX * vecX + vecY * vecY;
        int speedSquared = this.speed * this.speed;
        if (normSquared <= speedSquared) {
            x = visitable.getxVisit();
            y = visitable.getyVisit();
        } else {
            x += (int)(speed * vecX / Math.sqrt(normSquared));
            y += (int)(speed * vecY / Math.sqrt(normSquared));
        }
        double distMoved = Math.sqrt(Math.pow(x - getxCenter(), 2) + Math.pow(y - getyCenter(), 2));
        setCoordinateCenter(x, y);
        setBattery(this.battery - ((double)speed / 13) * (distMoved * 100 / (double)capacity)); // the 1/13 factor is arbitrary.
        return distMoved;
    }

    /**
     * Behaves according to the path finder logic, moving along the computed path.
     */
    public void behavePathFinder() {
        if (pathFinder == null) return; // should not occur;

        if (held) return;

        if (currentPath == null || currentPath.getTarget() != getCurrentVisit()) {
            Visitable current = getCurrentVisit();
            if (current == null) {
                currentVisit += (currentVisit != toVisit.size()) ? 1 : 0;
                return;
            }
            if (current.isCharging() && !needCharging) {
                setNeedCharging(true); // if user specifically ask a robot to go visit a charging station, it must charge there.
            }
            Path path = pathFinder.findPath(getCurrentVisit());
            if (path.isNull()) return; // Robot is blocked, no path found.
            currentPath = path;
        }

        double totalDistMoved = 0;
        while (totalDistMoved + 1.41 < speed) {
            if (!currentPath.hasNext()) {
                currentVisit += (currentVisit != toVisit.size()) ? 1 : 0;
                currentPath = null; // will calculate path in next update
                break;
            } else {
                Position next = currentPath.getNextPosition();
                totalDistMoved += move(next);
                currentPath.advance();
            }
        }
    }

    /**
     * Sends the robot to the nearest {@link ChargingStation} if it needs charging.
     */
    public void goToChargingStation() {
        Visitable curr = getCurrentVisit();
        if (!curr.isCharging()) {
            // NOTE : if getChargingStation() returns null, null will be added
            // ==> the robot will remain stationary (until a charging station is added).
            this.addToVisitNoWait((Visitable)getFactory().getChargingStation(this));
        }
    }

    /**
     * Returns the name of the robot with its battery level.
     *
     * @return The name of the robot with battery percentage.
     */
    @Override
    public String getName() {
        return super.getName() + String.format("(%.1f%%)", battery);
    }

    /**
     * Returns a string representation of the robot.
     *
     * @return String representation of the robot.
     */
    public String toString() {
        Visitable v = getCurrentVisit();
        String visitName = "null";
        if (v != null) {
            visitName = v.getName();
        }

        return getName() + "-s:" + speed + ",b:" + battery
            + (needCharging ? "y,visit:" : "n,visit:")
            + visitName + (held ? "y,-" : "n,-");
    }
}
