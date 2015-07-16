/*******************************************************************************
* @ Year 2013
* This is the source code of the following papers. 
* 
* 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla Kazemi, Cyrus Shahabi.
* 
* 
* Please contact the author Hien To, ubriela@gmail.com if you have any question.
*
* Contributors:
* Hien To - initial implementation
*******************************************************************************/
package org.geocrowd.datasets.dtype;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * A range query in two-dimensional system.
 * 
 * @author HT186010
 */
public class Rectangle implements Serializable {
	
	/** The low point. */
	private Point lowPoint;
	
	/** The high point. */
	private Point highPoint;

	/**
	 * Instantiates a new rectangle.
	 * 
	 * @param min_x
	 *            the min_x
	 * @param min_y
	 *            the min_y
	 * @param max_x
	 *            the max_x
	 * @param max_y
	 *            the max_y
	 */
	public Rectangle(double min_x, double min_y, double max_x, double max_y) {
		super();
		this.lowPoint = new Point(min_x, min_y);
		this.highPoint = new Point(max_x, max_y);
	}

	/**
	 * Instantiates a new rectangle.
	 * 
	 * @param lowPoint
	 *            the low point
	 * @param highPoint
	 *            the high point
	 */
	public Rectangle(Point lowPoint, Point highPoint) {
		super();
		this.lowPoint = lowPoint;
		this.highPoint = highPoint;
	}

	/**
	 * Area.
	 * 
	 * @return the double
	 */
	public double area() {
		return (deltaX() * deltaY());
	}

	/**
	 * Debug.
	 */
	public void debug() {
		System.out.println(lowPoint.getX() + ":" + lowPoint.getY() + ":"
				+ highPoint.getX() + ":" + highPoint.getY());
		// System.out.println("select lat, lon from distinctint where lat>="
		// + lowPoint.getX() + " and lat <=" + highPoint.getX()
		// + " and lon >=" + lowPoint.getY() + " and lon <="
		// + highPoint.getY() + ";");
	}

	/**
	 * Delta x.
	 * 
	 * @return the double
	 */
	public double deltaX() {
		return highPoint.getX() - lowPoint.getX();
	}

	/**
	 * Delta y.
	 * 
	 * @return the double
	 */
	public double deltaY() {
		return highPoint.getY() - lowPoint.getY();
	}

	/**
	 * Gets the high point.
	 * 
	 * @return the high point
	 */
	public final Point getHighPoint() {
		return highPoint;
	}

	/**
	 * Gets the low point.
	 * 
	 * @return the low point
	 */
	public final Point getLowPoint() {
		return lowPoint;
	}

	/**
	 * Calculate intersected area bwn two rectangles.
	 * 
	 * @param rec
	 *            the rec
	 * @return the double
	 */
	public double intersectArea(Rectangle rec) {
		// if this rectangle area == 0 or the rec's area == 0 or not intersect
		if (area() == 0 || rec.area() == 0 || !isIntersect(rec))
			return 0.0;

		double min_x = Math.min(lowPoint.getX(), rec.getLowPoint().getX());
		double min_y = Math.min(lowPoint.getY(), rec.getLowPoint().getY());
		double max_x = Math.max(highPoint.getX(), rec.getHighPoint().getX());
		double max_y = Math.max(highPoint.getY(), rec.getHighPoint().getY());
		double deltaMaxX = max_x - min_x;
		double deltaMaxY = max_y - min_y;

		return (area() + rec.area() + (deltaMaxX - deltaX())
				* (deltaMaxY - rec.deltaY()) + (deltaMaxX - rec.deltaX())
				* (deltaMaxY - deltaY()) - deltaMaxX * deltaMaxY);
	}

	/**
	 * Does this rectangle cover a point.
	 * 
	 * @param point
	 *            the point
	 * @return true, if is cover
	 */
	public boolean isCover(Point point) {
		if (lowPoint.getX() <= point.getX() && lowPoint.getY() <= point.getY()
				&& highPoint.getX() >= point.getX()
				&& highPoint.getY() >= point.getY())
			return true;
		return false;
	}

	/**
	 * Does this rectangle cover the other rec.
	 * 
	 * @param rec
	 *            the rec
	 * @return true, if is cover
	 */
	public boolean isCover(Rectangle rec) {
		if (lowPoint.getX() <= rec.lowPoint.getX()
				&& lowPoint.getY() <= rec.lowPoint.getY()
				&& highPoint.getX() >= rec.highPoint.getX()
				&& highPoint.getY() >= rec.highPoint.getY())
			return true;
		return false;
	}

	/**
	 * If this rectangle intersects rec --> return true.
	 * 
	 * @param rec
	 *            the rec
	 * @return true, if is intersect
	 */
	public boolean isIntersect(Rectangle rec) {
		double min_x = Math.min(lowPoint.getX(), rec.getLowPoint().getX());
		double min_y = Math.min(lowPoint.getY(), rec.getLowPoint().getY());
		double max_x = Math.max(highPoint.getX(), rec.getHighPoint().getX());
		double max_y = Math.max(highPoint.getY(), rec.getHighPoint().getY());

		if ((max_x - min_x < deltaX() + rec.deltaX())
				&& (max_y - min_y < deltaY() + rec.deltaY()))
			return true;	//	intersect
		return false;
	}

	/**
	 * rounding the rectangle to integer coords.
	 */
	public void roundingRectangle() {
		lowPoint.setX(Math.floor(lowPoint.getX()));
		lowPoint.setY(Math.floor(lowPoint.getY()));
		highPoint.setX(Math.ceil(highPoint.getX()));
		highPoint.setY(Math.ceil(highPoint.getY()));
	}

	// public String toString() {
	// return "(" + lowPoint.getX() + "," + lowPoint.getY() + ")->("
	// + highPoint.getX() + "," + highPoint.getY() + ")";
	// }

	/**
	 * Sets the high point.
	 * 
	 * @param highPoint
	 *            the new high point
	 */
	public void setHighPoint(Point highPoint) {
		this.highPoint = highPoint;
	}

	/**
	 * Sets the low point.
	 * 
	 * @param lowPoint
	 *            the new low point
	 */
	public void setLowPoint(Point lowPoint) {
		this.lowPoint = lowPoint;
	}
}
