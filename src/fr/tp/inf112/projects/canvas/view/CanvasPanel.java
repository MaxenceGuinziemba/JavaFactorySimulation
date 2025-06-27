package fr.tp.inf112.projects.canvas.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.tp.inf112.projects.canvas.model.Canvas;

@SuppressWarnings("serial")
public class CanvasPanel extends JPanel {
	
	private CanvasViewer canvasViewer;
	
	private CanvasPainter figuresPainter;

	public CanvasPanel(final CanvasViewer canvasViewer) {
		super();
		
		this.canvasViewer = canvasViewer;
		
		figuresPainter = new CanvasPainter(canvasViewer);
	}

//	public void setCanvasViewer(final CanvasViewer canvasViewer) {
//		this.canvasViewer = canvasViewer;
//		
//		figuresPainter = new CanvasPainter(canvasViewer);
//	}
	
	private Canvas getCanvasModel() {
		return canvasViewer == null ? null : canvasViewer.getCanvasModel();
	}

	@Override
	public void paint(final Graphics painter) {
		super.paint(painter);
		
		final Canvas canvasModel = getCanvasModel();
		
		if (canvasModel != null) {
			final double scalingFactorWidth = (double) getWidth() / canvasModel.getWidth();
			final double scalingFactorHeight = (double) getHeight() / canvasModel.getHeight();
	
			figuresPainter.paint(painter, new Scale(scalingFactorWidth, scalingFactorHeight));
		}
	}
}
