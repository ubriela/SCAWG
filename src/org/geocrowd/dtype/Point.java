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
package org.geocrowd.dtype;

// TODO: Auto-generated Javadoc
/**
 * two dimensional data type.
 * 
 * @author HT186010
 */
public class Point {
	
	/** The y. */
	private double X, Y;

	/**
	 * Instantiates a new point.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public Point(double x, double y) {
		super();
		X = x;
		Y = y;
	}

	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public double getX() {
		return X;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public double getY() {
		return Y;
	}

	/**
	 * Sets the x.
	 * 
	 * @param x
	 *            the new x
	 */
	public void setX(double x) {
		X = x;
	}

	/**
	 * Sets the y.
	 * 
	 * @param y
	 *            the new y
	 */
	public void setY(double y) {
		Y = y;
	}
	
	
	public void debug() {
		System.out.println(X  + "\t" + Y);
	}
}
