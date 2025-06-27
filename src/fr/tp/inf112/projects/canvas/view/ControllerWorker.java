package fr.tp.inf112.projects.canvas.view;

import java.awt.Component;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public abstract class ControllerWorker extends SwingWorker<Short, String> {
	
	private static final Logger LOGGER = Logger.getLogger(ControllerWorker.class.getName());

	private final Component component;
	
	private final String operation;
	
	public ControllerWorker(final Component component,
							final String operation) {
		this.component = component;
		this.operation= operation; 
	}
	
	@Override
	protected void done() {
		try {
			get();
		}
		catch (InterruptedException ex) {
		}
		catch (ExecutionException ex) {
			final String message = "A problem occur while " + operation + ".";
			LOGGER.log(Level.SEVERE, message, ex);

			JOptionPane.showMessageDialog(component, message + " Please contact support.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
