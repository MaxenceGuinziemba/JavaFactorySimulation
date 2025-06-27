package fr.tp.inf112.projects.robotsim.model;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;

import fr.tp.inf112.projects.canvas.model.Canvas;
import fr.tp.inf112.projects.canvas.model.impl.BasicStyle;
import fr.tp.inf112.projects.canvas.model.impl.BasicRectangleShape;
import fr.tp.inf112.projects.canvas.model.Figure;
import fr.tp.inf112.projects.canvas.controller.Observable;
import fr.tp.inf112.projects.canvas.controller.Observer;

/**
 * Represents a factory in the robot simulation.
 * A factory contains components, robots, and manages their behavior and simulation state.
 * 
 * @author team-24
 */
public class Factory extends Component implements Canvas, Observable {
    private final static long serialVersionUID = 1L;

    private String id;
    private List<Component> components;
    private List<Robot> robots;
    transient private Set<Observer> observers;
    private boolean simulationRunning;

    /**
     * Constructs a Factory with a specified ID, name, width, and height.
     * 
     * @param id Unique identifier for the factory.
     * @param name Name of the factory.
     * @param width Width of the factory.
     * @param height Height of the factory.
     */
    public Factory(String id, String name, int width, int height) {
        super(name, 0, 0, width, height, BasicStyle.FACTORY, new BasicRectangleShape(width, height), null, false);
        this.id = id;
        components = new ArrayList<>();
        robots = new ArrayList<>();
        observers = new HashSet<>();
        this.simulationRunning = false;
    }

    /**
     * Gets the unique identifier of the factory.
     * 
     * @return The factory's ID.
     */
    @Override
    public String getId() { return this.id; }

    /**
     * Sets the unique identifier of the factory.
     * 
     * @param id The new ID for the factory.
     */
    @Override
    public void setId(String id) { this.id = id; }

    /**
     * Gets all figures (components) in the factory.
     * 
     * @return A collection of {@link Figure} objects in the factory.
     */
    @Override
    public Collection<Figure> getFigures() {
        List<Figure> toReturn = new ArrayList<>(this.components);
        toReturn.add(0, this);
        return (Collection<Figure>)toReturn;
    }

    /**
     * Returns the list of components in the factory.
     *
     * @return List of {@link Component} objects in the factory.
     */
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * Returns the list of robots in the factory.
     *
     * @return List of {@link Robot} objects in the factory.
     */
    public List<Robot> getRobots() {
        return this.robots;
    }

    /**
     * Adds a component to the factory.
     * 
     * @param component The non-null {@link Component} to add.
     */
    public void addComponent(Component component) {
        if (component != null) {
            this.components.add(component);
        }
    }

    /**
     * Adds a robot to the factory.
     * 
     * @param robot The {@link Robot} to add.
     */
    public void addRobot(Robot robot) {
        this.robots.add(robot);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public boolean addObserver(Observer o) {
        if (observers == null) observers = new HashSet<>();
        return observers.add(o);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public boolean removeObserver(Observer o) {
        if (observers == null) return false;
        return observers.remove(o);
    }

    /**
     * Returns the number of observers registered to the factory.
     *
     * @return The number of observers.
     */
    public int countObservers() {
        return (observers != null) ? observers.size() : 0;
    }

    /**
     * Removes all observers from the factory.
     */
    public void removeObservers() {
        this.observers = new HashSet<>();
    }

    /**
     * Notifies all registered observers of a change in the factory.
     */
    protected void notifyObservers() {
        if (observers == null) return;
        for (final Observer observer : observers) {
            observer.modelChanged();
        }
    }

    /**
     * Prints a summary of the factory to the console.
     */
    public void printToConsole(){
        System.out.println("Factory ( id:" + this.id +" )"
                + this.getName()
                + " contains "
                + this.components.size()
                + " components");
    }

    /**
     * Starts the simulation for the factory.
     */
    public void startSimulation() {
        if (!this.simulationRunning) {
            this.simulationRunning = true;
            this.notifyObservers();
        }
    }

    /**
     * Stops the simulation for the factory.
     */
    public void stopSimulation() {
        if (this.simulationRunning) {
            this.simulationRunning = false;
            this.notifyObservers();
        }
    }

    /**
     * Checks if the simulation is currently running.
     * 
     * @return True if the simulation is running, false otherwise.
     */
    public boolean isSimulationRunning() { return this.simulationRunning; }

    /**
     * Defines the behavior of all components in the factory.
     */
    public void behave() {
        for (Component c : this.components) {
            c.behave();
            // System.out.println(this);
        }
    }

    /**
     * Finds the nearest charging station to a given component.
     * 
     * @param component The {@link Component} for which to find the nearest {@link ChargingStation}.
     * @return The nearest {@link ChargingStation}, or null if none exists.
     */
    public ChargingStation getChargingStation(Component component) {
        Component nearestStation = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Component c : components) {
            if (c.isCharging()) {
                double d = component.distTo(c);
                if (d <= minDist) {
                    minDist = d;
                    nearestStation = c;
                }
            }
        }
        return (ChargingStation)nearestStation;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(Position position) {
        for (Component component : components) {
            if (component.isObstacle(position)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y) {
        for (Component component : components) {
            if (component.isObstacle(x, y)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y, int size) {
        return isObstacle(x, y, size, size);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObstacle(int x, int y, int width, int height) {
        for (Component component : components) {
            if (component.isObstacle(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns a string representation of the factory.
     * 
     * @return String representation of the factory.
     */
    @Override
    public String toString() {
        return getName() + " containing " + components.toString();
    }
}
