package fr.tp.inf112.projects.canvas.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class CanvasViewerMenuBar extends JMenuBar {
	
	private final JMenu fileMenu;

	private final JMenu animateMenu;

	public CanvasViewerMenuBar( final CanvasViewer canvasViewer ) {
		super();

		fileMenu = new FileMenu( canvasViewer );
		add( fileMenu );
		
		animateMenu = new AnimateMenu( canvasViewer );
		add( animateMenu );
	}
	
	@Override
	public void repaint() {
		if ( fileMenu != null ) {
			fileMenu.repaint();
		}

		if ( animateMenu != null ) {
			animateMenu.repaint();
		}
	}
}
