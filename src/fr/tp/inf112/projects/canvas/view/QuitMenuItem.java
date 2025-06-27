package fr.tp.inf112.projects.canvas.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class QuitMenuItem extends JMenuItem implements ActionListener {
	
	private final CanvasViewer canvasViewer;

	public QuitMenuItem( final CanvasViewer canvasViewer ) {
		super( "Quit" );
		
		this.canvasViewer = canvasViewer;
		
		addActionListener( this );
	}

	@Override
	public void actionPerformed( ActionEvent evt ) {
		canvasViewer.quit();
	}
}
