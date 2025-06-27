package fr.tp.inf112.projects.canvas.view;

import javax.swing.JMenu;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {
	
	public FileMenu( final CanvasViewer canvasViewer ) {
		super( "File" );

		add( new OpenCanvasMenuItem( canvasViewer ) );
		add( new SaveCanvasMenuItem( canvasViewer ) );
		add( new QuitMenuItem( canvasViewer ) );
	}
	
	@Override
	public void repaint() {
		super.repaint();
		
		for ( int index = 0; index < getItemCount(); index++ ) {
			getItem( index ).repaint();
		}
	}
}
