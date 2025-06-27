package fr.tp.inf112.projects.canvas.controller;

/**
 * Ifor the Observer-Observable pattern.
 * @author Dominique Blouin
 *
 */
public interface Observer {

    /**
     * Message received by the observer when any of the model element has changed.
     * 
     */
    void modelChanged();
}
