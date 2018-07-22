package org.geocrowd.synthetic.grid;

import java.util.Iterator;
import java.util.List;

import org.geocrowd.dtype.Point;

public abstract class Grid2D {
	protected double max_x, max_y, min_x, min_y; // bounds of the "universe"
	protected double X[] = null; // partition of X dimension
	protected double Y[] = null; // partition of Y dimension
	protected double size = 0; // total number of points
	protected double pointsPerArea = 0; // assuming uniform distributed over the
										// universe
	protected double totalArea = 0;

	protected int dim_size_x, dim_size_y; // fixed size of both dimensions
	
	public Grid2D() {
		
	}
	
	public double[] getX() {
		return X;
	}

	public double[] getY() {
		return Y;
	}
	
	
	/**
	 * Create a grid cell from a list of points
	 * 
	 * @param points
	 */
	public void populate(List<Point> points) {
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			Point point = (Point) it.next();
			this.put(point);
			size++;
		}

		// update points per area
		pointsPerArea = (double) this.size / (this.totalArea);
	}
	
	/**
	 * Split coordinates/dimension
	 */
	public abstract void split();

	/**
	 * Add a point
	 * 
	 * @param point
	 */
	public abstract void put(Point point);
	
	
	public String toString() {
		return "(" + min_x + "," + min_y + ")->(" + max_x + "," + max_y + ")";
	}

}
