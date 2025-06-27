package fr.tp.inf112.projects.canvas.controller;

import fr.tp.inf112.projects.canvas.model.Canvas;
import fr.tp.inf112.projects.canvas.model.CanvasPersistenceManager;

/**
 * Represents the controller used by the canvas viewer to obtain the canvas model data and to control the figures animation.
 * 
 * @author Dominique Blouin
 *
 */
public interface CanvasViewerController extends Observable {

    /**
     * Returns the canvas model associated with this controller.
     * @return A non {@code null} {@code Canvas} object.
     */
    Canvas getCanvas();

    /**
     * Sets the canvas model of this controller. Invoked when users open an existing model from the canvas viewer.
     * @param canvasModel A non {@code null} {@code Canvas} object.
     */
    void setCanvas(Canvas canvasModel);

    /**
     * Returns the persistence manager to be used to persist this canvas model into a data store.
     * @return A non {@code null} {@code CanvasPersistenceManager} implementation for the desired data storage 
     * kind (file, database, etc.).
     */
    CanvasPersistenceManager getPersistenceManager();

    /**
     * Starts the animation of the canvas model associated with this controller. For example, moving, resizing, 
     * adding or removing figures.
     */
    void startAnimation();

    /**
     * Stops the animation of the canvas model associated with this controller.
     */
    void stopAnimation();

    /**
     * States if the canvas model associated with this controller is being animated or not.
     * @return A {@code boolean} stating if the canvas is being animated or not.
     */
    boolean isAnimationRunning();
}
