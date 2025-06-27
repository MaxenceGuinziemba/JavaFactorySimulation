package fr.tp.inf112.projects.canvas.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class StopAnimationMenuItem extends JMenuItem implements ActionListener {
	
	private final CanvasViewer canvasViewer;

	public StopAnimationMenuItem( final CanvasViewer canvasViewer ) {
		super( "Stop Animation" );
		this.canvasViewer = canvasViewer;
		addActionListener( this );
		
		setEnabled( canvasViewer.canControlModel() && canvasViewer.isAnimationRunning() );
	}

	@Override
	public void actionPerformed( ActionEvent evt ) {
		canvasViewer.stopAnimation();
	}
	
	@Override
	public void repaint() {
		if ( canvasViewer != null ) {
			setEnabled( canvasViewer.canControlModel() && canvasViewer.isAnimationRunning() );
		}
		
		super.repaint();
	}
}
