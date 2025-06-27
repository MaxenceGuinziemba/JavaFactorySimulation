package fr.tp.inf112.projects.canvas.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class SaveCanvasMenuItem extends JMenuItem implements ActionListener {
	
	private final CanvasViewer canvasViewer;

	public SaveCanvasMenuItem(final CanvasViewer canvasViewer) {
		super("Save Canvas");
		
		this.canvasViewer = canvasViewer;
		
		addActionListener(this);
		
		setEnabled(canvasViewer.canSaveCanvas());
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		canvasViewer.saveCanvas(true);
	}
	
	@Override
	public void repaint() {
		if (canvasViewer != null) {
			setEnabled(canvasViewer.canSaveCanvas());
		}
		
		super.repaint();
	}
}
