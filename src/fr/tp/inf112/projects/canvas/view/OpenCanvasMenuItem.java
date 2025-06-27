package fr.tp.inf112.projects.canvas.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class OpenCanvasMenuItem extends JMenuItem implements ActionListener {
	
	private final CanvasViewer canvasViewer;

	public OpenCanvasMenuItem( final CanvasViewer canvasViewer ) {
		super( "Open Canvas" );
		
		this.canvasViewer = canvasViewer;
		
		addActionListener( this );
	}

	@Override
	public void actionPerformed( ActionEvent evt ) {
		canvasViewer.openCanvas();
	}
	
	@Override
	public void repaint() {
		if (canvasViewer != null) {
			setEnabled(canvasViewer.canOpenCanvas());
		}
		
		super.repaint();
	}
}
