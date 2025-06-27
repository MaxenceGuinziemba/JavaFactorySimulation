package fr.tp.inf112.projects.canvas.model.impl;

import fr.tp.inf112.projects.canvas.model.CanvasChooser;
import fr.tp.inf112.projects.canvas.model.CanvasPersistenceManager;

public abstract class AbstractCanvasPersistenceManager implements CanvasPersistenceManager {
	
	private final CanvasChooser canvasChooser;

	public AbstractCanvasPersistenceManager(final CanvasChooser canvasChooser) {
		this.canvasChooser = canvasChooser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CanvasChooser getCanvasChooser() {
		return canvasChooser;
	}
}
