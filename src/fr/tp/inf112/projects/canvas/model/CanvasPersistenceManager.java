package fr.tp.inf112.projects.canvas.model;

import java.io.IOException;

/**
 * Interface providing methods to manage the persistence of canvas models into a data store such as files, databases or any other permanent storage. 
 * Required by the canvas viewer when the canvas must be saved or read from the storage.
 * 
 * @author Dominique Blouin
 *
 */
public interface CanvasPersistenceManager {
	
	/**
	 * Reads a canvas model from the data store of this persistence manager from the ID of the required canvas model.
	 * 
	 * @param canvasId A non {@code null} {@code String} identifying this canvas model uniquely in the data store of this persistence manager.
	 * 
	 * @return The read canvas model
	 * 
	 * @throws IOException When a problem occurs when reading the canvas (e.g. wrong {@code canvasId}, unavailable data store
	 * or locked resource, etc.).
	 */
    Canvas read(String canvasId)
	throws IOException;

	/**
	 * Persists the specified canvas model into the data store of this persistence manager.
	 * 
	 * @param canvasModel The canvas model object.
	 * 
	 * @throws IOException When a problem occurs when persisting the canvas (e.g. wrong {@code canvasId}, unavailable data store
	 * or locked resource, etc.).
	 */
	void persist(Canvas canvasModel)
	throws IOException;

	/**
	 * Deletes the specified canvas model from the data store of this persistence manager.
	 * 
	 * @param canvasModel The canvas model object.
	 * 
	 * @return A {@code boolean} stating if the specified canvas model was removed or not from the data store, according to its existence in the data store.
	 * 
	 * @throws IOException When a problem occurs when deleting the canvas (e.g. wrong {@code canvasId}, unavailable data store
	 * or locked resource, etc.).
	 */
	boolean delete(Canvas canvasModel)
	throws IOException;
	
	/**
	 * Provides an object responsible for choosing a canvas to be retrieved from the data store by the canvas persistence manager. This object may open a 
	 * view containing a list of existing canvas from which the user should select the one he wants to view.
	 * @return A non {@code null} {@code CanvasChooser} object.
	 */
	CanvasChooser getCanvasChooser();
}
