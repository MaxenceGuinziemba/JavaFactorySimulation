package fr.tp.inf112.projects.robotsim.controller;

import fr.tp.inf112.projects.canvas.controller.CanvasViewerController;
import fr.tp.inf112.projects.canvas.model.Canvas;
import fr.tp.inf112.projects.canvas.model.CanvasPersistenceManager;
import fr.tp.inf112.projects.robotsim.model.Factory;
import fr.tp.inf112.projects.canvas.controller.Observer;

/**
 * Controller for the simulator, managing the interaction between the factory model
 * and the canvas viewer.
 * 
 * <p>
 * Implements the {@link CanvasViewerController} interface to provide functionality
 * for managing the canvas, starting/stopping animations, and handling observers.
 * </p>
 * 
 * <p>
 * This controller delegates simulation logic to the {@link Factory} model.
 * </p>
 * 
 * @author team-24
 */
public class SimulatorController implements CanvasViewerController {
    private Factory factoryModel;
    private CanvasPersistenceManager persistenceManager;

    /**
     * Constructs a {@code SimulatorController} with the specified factory model
     * and persistence manager.
     * 
     * @param factoryModel       The factory model to control.
     * @param persistenceManager The persistence manager for saving/loading canvases.
     */
    public SimulatorController(Factory factoryModel, CanvasPersistenceManager persistenceManager) {
        this.factoryModel = factoryModel;
        this.persistenceManager = persistenceManager;
    }

    /**
     * Returns the current canvas.
     * 
     * @return The {@link Canvas} associated with the factory model.
     */
    @Override
    public Canvas getCanvas() {
        return (Canvas) this.factoryModel;
    }

    /**
     * Sets the canvas to the specified model if it is an instance of {@link Factory}.
     * 
     * @param canvasModel The new canvas model.
     */
    @Override
    public void setCanvas(Canvas canvasModel) {
        if (factoryModel != null && canvasModel instanceof Factory) {
            this.factoryModel = (Factory) canvasModel;
        }
    }

    /**
     * Returns the persistence manager.
     * 
     * @return The {@link CanvasPersistenceManager}.
     */
    @Override
    public CanvasPersistenceManager getPersistenceManager() {
        return this.persistenceManager;
    }

    /**
     * Starts the animation by running the factory simulation in a loop.
     */
    @Override
    public void startAnimation() {
        this.factoryModel.startSimulation();
        while (factoryModel.isSimulationRunning()) {
            factoryModel.behave();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Stops the animation by stopping the factory simulation.
     */
    @Override
    public void stopAnimation() {
        this.factoryModel.stopSimulation();
    }

    /**
     * Checks if the animation is currently running.
     * 
     * @return {@code true} if the simulation is running, {@code false} otherwise.
     */
    @Override
    public boolean isAnimationRunning() {
        return this.factoryModel.isSimulationRunning();
    }

    /**
     * Adds an observer to the factory model.
     * 
     * @param observer The observer to add.
     * @return {@code true} if the observer was added, {@code false} otherwise.
     */
    @Override
    public boolean addObserver(Observer observer) {
        return this.factoryModel.addObserver(observer);
    }

    /**
     * Removes an observer from the factory model.
     * 
     * @param observer The observer to remove.
     * @return {@code true} if the observer was removed, {@code false} otherwise.
     */
    @Override
    public boolean removeObserver(Observer observer) {
        return this.factoryModel.removeObserver(observer);
    }
}
