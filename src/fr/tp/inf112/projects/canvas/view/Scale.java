package fr.tp.inf112.projects.canvas.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public class Scale {
	
	private final double xScalingFactor;

	private final double yScalingFactor;
	
	public Scale( 	final double xScalingFactor,
					final double yScalingFactor ) {
		this.xScalingFactor = xScalingFactor;
		this.yScalingFactor = yScalingFactor;
	}
	
	public Point scale( final Point position ) {
		return new Point( scaleX( position.getX() ), scaleY( position.getY() ) );
	}
	
	public Dimension scale(final Dimension dimension) {
		return new Dimension(scaleX( dimension.getWidth() ), scaleY( dimension.getHeight()));
	}
	
	public int scaleX( final double value ) {
		return (int) scale( value, xScalingFactor );
	}
 
	public int scaleY( final double value ) {
		return (int) scale( value, yScalingFactor );
	}

	private static double scale( 	final double value,
									final double scalingFactor ) {
		return value * scalingFactor;
	}

	public int[] scaleX( final Collection<fr.tp.inf112.projects.canvas.model.Point> points ) {
		final Collection<Integer> values = new ArrayList<>( points.size() );

		for ( fr.tp.inf112.projects.canvas.model.Point point : points ) {
			values.add(  point.getxCoordinate() );
		}
		
		return scale( values, xScalingFactor );
	}

	public int[] scaleY(final Collection<fr.tp.inf112.projects.canvas.model.Point> points) {
		final Collection<Integer> values = new ArrayList<>(points.size());

		for (fr.tp.inf112.projects.canvas.model.Point point : points) {
			values.add(point.getyCoordinate());
		}
		
		return scale(values, yScalingFactor);
	}

	private static int[] scale( final Collection<Integer> values,
								final double scalingFactor) {
		final int[] scaledValues = new int[values.size()];
		int index = 0;
		
		for (final Integer value : values) {
			scaledValues[ index++ ] = (int) scale(value, scalingFactor);
		}
		
		return scaledValues;
	}
}
