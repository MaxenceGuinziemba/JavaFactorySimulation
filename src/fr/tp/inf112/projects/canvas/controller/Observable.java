package fr.tp.inf112.projects.canvas.controller;

/**
 * Interface for the Observer-Observable pattern.
 * 
 * @author Dominique Blouin
 *
 */
public interface Observable {
	
	/**
	 * Add an observer to this observable. 
	 * 
	 * @param observer the observer to be added.
	 * @return {@code true} if the observer was added, otherwise {@code false} if this observer had already been added.
	 */
	boolean addObserver(Observer observer);

	/**
	 * Remove an observer from this observable.
	 * @param observer
	 * @return {@code true} if the observer was removed, i.e. it was found within the set of registered observers, otherwise {@code false}.
	 */
	boolean removeObserver(Observer observer);
}
