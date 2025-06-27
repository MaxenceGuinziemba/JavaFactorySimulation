package fr.tp.inf112.projects.canvas.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Set;

import fr.tp.inf112.projects.canvas.model.Canvas;
import fr.tp.inf112.projects.canvas.model.Figure;
import fr.tp.inf112.projects.canvas.model.OvalShape;
import fr.tp.inf112.projects.canvas.model.PolygonShape;
import fr.tp.inf112.projects.canvas.model.RectangleShape;
import fr.tp.inf112.projects.canvas.model.Shape;
import fr.tp.inf112.projects.canvas.model.Style;

public class CanvasPainter {
	
	private final CanvasViewer canvasViewer;
	
	public CanvasPainter(final CanvasViewer canvasViewer) {
		this.canvasViewer = canvasViewer;
	}
	
	public void paint(final Graphics painter,
					  final Scale scalingFactor) {
		paintCanvas(painter, scalingFactor);
		paintFigures(painter, scalingFactor);
	}
	
	private Canvas getCanvasModel() {
		return canvasViewer.getCanvasModel();
	}
	
	private void paintCanvas(final Graphics painter,
							 final Scale scalingFactor) {
		final Canvas canvasModel = getCanvasModel();
		
		if (painter instanceof Graphics2D) {
			setStroke((Graphics2D) painter, canvasModel.getStyle());
		}
		
		final int scaledWidth = scalingFactor.scaleX(canvasModel.getWidth());
		final int scaledHeight = scalingFactor.scaleY(canvasModel.getHeight());
		
		painter.drawRect(0, 0, scaledWidth, scaledHeight);
		
		if (setBackgroundColor(painter, canvasModel.getStyle())) {
			painter.fillRect(0, 0, scaledWidth, scaledHeight);
		}
	}
	
	private void paintFigures(final Graphics painter,
							  final Scale scalingFactor) {
		final Canvas canvasModel = getCanvasModel();
		
		for (final Figure figure : canvasModel.getFigures()) {
			paint(painter, scalingFactor, figure);
		}
	}
	
	private void paint(final Graphics painter,
					   final Scale scalingFactor,
					   final Figure figure) {
		final Color actualColor = painter.getColor();

		// Set the stroke
		final Stroke actualStroke;
		
		if (painter instanceof Graphics2D) {
			final Graphics2D painter2D = (Graphics2D) painter;
			actualStroke = painter2D.getStroke();
			setStroke(painter2D, figure.getStyle());
		}
		else {
			actualStroke = null;
		}
		
		final Point figPosition = new Point(figure.getxCoordinate(), figure.getyCoordinate());
		final Point scaledFigPosition = scalingFactor.scale(figPosition);

		// Write the label with the stroke
		paintLabel(painter, scaledFigPosition, figure);

		// Draw the figure with the stroke and fill if needed
		final Style style = figure.getStyle();
		
		final Shape figShape = figure.getShape();
		
		if (figShape instanceof RectangleShape) {
			paint(painter, scalingFactor, scaledFigPosition, style, (RectangleShape) figShape);
		}
		else if (figShape instanceof OvalShape) {
			paint(painter, scalingFactor, scaledFigPosition, style, (OvalShape) figShape);
		}
		else if (figShape instanceof PolygonShape) {
			paint(painter, scalingFactor, style, (PolygonShape) figShape);
		}
		else {
			throw new IllegalArgumentException("Unknown figure shape '" + figShape + "'!");
		}
		
		if (actualStroke != null) {
			((Graphics2D)painter).setStroke(actualStroke);
		}

		painter.setColor(actualColor);
	}
	
	private void paintLabel(final Graphics painter,
							final Point scaledFigPosition,
							final Figure figure) {
		if (figure.getName() != null || !figure.getName().isEmpty()) {
			painter.drawString(figure.getName(),
							   (int) scaledFigPosition.getX(), 
							   (int) scaledFigPosition.getY() - 4);
		}
	}
	
	private void paint(final Graphics painter,
					   final Scale scalingFactor,
					   final Point scaledPosition,
					   final Style style,
					   final RectangleShape shape) {
		final int scaledxCoordinate = (int) scaledPosition.getX();
		final int scaledyCoordinate = (int) scaledPosition.getY();
		final int scaledWidth = scalingFactor.scaleX(shape.getWidth());
		final int scaledHeight = scalingFactor.scaleY(shape.getHeight());

		painter.drawRect(scaledxCoordinate, scaledyCoordinate, scaledWidth, scaledHeight);
		
		final boolean fillFigure = setBackgroundColor(painter, style);

		if (fillFigure) {
			painter.fillRect(scaledxCoordinate, scaledyCoordinate, scaledWidth, scaledHeight);
		}
	}
	
	private void paint(final Graphics painter,
					   final Scale scalingFactor,
					   final Point scaledPosition,
					   final Style style,
					   final OvalShape shape) {
		final int scaledxCoordinate = (int) scaledPosition.getX();
		final int scaledyCoordinate = (int) scaledPosition.getY();
		final int scaledWidth = scalingFactor.scaleX(shape.getWidth());
		final int scaledHeight = scalingFactor.scaleY(shape.getHeight());

		painter.drawOval( scaledxCoordinate, scaledyCoordinate, scaledWidth, scaledHeight);
		
		final boolean fillFigure = setBackgroundColor(painter, style);
		
		if ( fillFigure ) {
			painter.fillOval(scaledxCoordinate, scaledyCoordinate, scaledWidth, scaledHeight);
		}
	}
	
	private void paint(final Graphics painter,
					   final Scale scalingFactor,
					   final Style style,
					   final PolygonShape shape) {
		final Set<fr.tp.inf112.projects.canvas.model.Point> points = shape.getPoints();
		final int[] xCoordPoints = scalingFactor.scaleX(points);
		final int[] yCoordPoints = scalingFactor.scaleY(points);
		
		painter.drawPolygon(xCoordPoints, yCoordPoints, points.size());
		
		final boolean fillFigure = setBackgroundColor(painter, style);
		
		if (fillFigure) {
			painter.fillPolygon(xCoordPoints, yCoordPoints, points.size());
		}
	}
	
	private void setStroke(final Graphics2D painter2D,
						   final Style style) {
		if (style != null) {
			setStroke(painter2D, style.getStroke());
		}
	}
			
	private void setStroke(final Graphics2D painter2D,
						   final fr.tp.inf112.projects.canvas.model.Stroke stroke) {
		if (stroke != null) {
			final Stroke awtStroke = mapStroke(stroke);
					
			if (awtStroke != null) {
				painter2D.setStroke(awtStroke);
			}
				
			if (stroke.getColor() != null) {
				painter2D.setColor(mapColor(stroke.getColor()));
			}
		}
	}
	
	private Stroke mapStroke(final fr.tp.inf112.projects.canvas.model.Stroke stroke) {
		if (stroke == null || stroke.getThickness() <= 0) {
			return null;
		}
		
		return new BasicStroke(stroke.getThickness(),
							   BasicStroke.CAP_ROUND,
							   BasicStroke.JOIN_MITER,
							   10.0f,
							   stroke.getDashPattern(), 
							   0.0f);
	}

	private boolean setBackgroundColor(final Graphics painter,
									   final Style figStyle) {
		if (figStyle == null) {
			return false;
		}
		
		return setColor(painter, figStyle.getBackgroundColor());
	}
	
	private boolean setColor(final Graphics painter,
							 final fr.tp.inf112.projects.canvas.model.Color color) {
		final Color awtColor = mapColor(color);
		final boolean fillFigure = awtColor != null;
		
		if (fillFigure) {
			painter.setColor(awtColor);
		}
		
		return fillFigure;
	}

	private Color mapColor(final fr.tp.inf112.projects.canvas.model.Color color) {
		if (color ==  null) {
			return null;
		}
		
		if (color.getRedComponent() < 0 || color.getGreenComponent() < 0 || color.getBlueComponent() < 0) {
			return null;
		}
		
		return new Color(color.getRedComponent(), color.getGreenComponent(), color.getBlueComponent());
	}
}
