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
package org.geocrowd.common.workertask;

import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Rectangle;


// TODO: Auto-generated Javadoc
/**
 * The Class MBR.
 * 
 * @author Leyla
 */
public class WorkingRegion extends Rectangle {
	
	/**
	 * Instantiates a new mbr.
	 * 
	 * @param lR
	 *            the l r
	 * @param lC
	 *            the l c
	 * @param hR
	 *            the h r
	 * @param hC
	 *            the h c
	 */
	public WorkingRegion(double minLat, double minLng, double maxLat, double maxLng) {
		super(minLat, minLng, maxLat, maxLng);
	}

	public WorkingRegion(Rectangle mbr) {
		// TODO Auto-generated constructor stub
		super(mbr.getLowPoint().getX(), mbr.getLowPoint().getY(), mbr.getHighPoint().getX(), mbr.getHighPoint().getY());
	}
	
	/**
	 * Creates and return mbr.
	 * 
	 * @param centerLat
	 *            the center lat
	 * @param centerLng
	 *            the center lng
	 * @param rangeX
	 *            the range x
	 * @param rangeY
	 *            the range y
	 * @return the mbr
	 */
	public static WorkingRegion createMBR(double centerLat, double centerLng,
			double rangeX, double rangeY) {
		WorkingRegion mbr = new WorkingRegion(centerLat - (rangeX / 2), centerLng - (rangeY / 2),
				centerLat + (rangeX / 2), centerLng + (rangeY / 2));
		return mbr;
	}
	
	/**
	 * Instantiates a new mbr.
	 * 
	 * @param m
	 *            the m
	 */
//	public WorkingRegion(WorkingRegion m) {
//		minLat = m.minLat;
//		maxLat = m.maxLat;
//		minLng = m.minLng;
//		maxLng = m.maxLng;
//	}

	/**
	 * Gets the max lat.
	 * 
	 * @return the max lat
	 */
	public double getMaxLat() {
		return getHighPoint().getX();
	}

	/**
	 * Gets the max lng.
	 * 
	 * @return the max lng
	 */
	public double getMaxLng() {
		return getHighPoint().getY();
	}

	/**
	 * Gets the min lat.
	 * 
	 * @return the min lat
	 */
	public double getMinLat() {
		return getLowPoint().getX();
	}

	/**
	 * Gets the min lng.
	 * 
	 * @return the min lng
	 */
	public double getMinLng() {
		return getLowPoint().getY();
	}

	/**
	 * Prints the.
	 */
	public void print() {
		System.out.println("minLat:" + getMinLat() + "   maxLat:" + getMaxLat()
				+ "   minLng:" + getMinLng() + "   maxLng:" + getMaxLng());
	}

	/**
	 * Sets the max lat.
	 * 
	 * @param maxLat
	 *            the new max lat
	 */
	public void setMaxLat(double maxLat) {
		getHighPoint().setX(maxLat);;
	}

	/**
	 * Sets the max lng.
	 * 
	 * @param maxLng
	 *            the new max lng
	 */
	public void setMaxLng(double maxLng) {
		getHighPoint().setY(maxLng);
	}
	
	
	/**
	 * Sets the min lat.
	 * 
	 * @param m
	 *            the new min lat
	 */
	public void setMinLat(double minLat) {
		getLowPoint().setX(minLat);
	}
	
	/**
	 * Sets the min lng.
	 * 
	 * @param minLng
	 *            the new min lng
	 */
	public void setMinLng(double minLng) {
		getLowPoint().setY(minLng);
	}
	
	/**
	 * Sets the mbr.
	 * 
	 * @param minLat
	 *            the minLat
	 * @param minLng
	 *            the minLng
	 * @param maxLat
	 *            the maxLat
	 * @param maxLng
	 *            the maxLng
	 */
	public void setMBR(double minLat, double minLng, double maxLat, double maxLng) {
		setLowPoint(new Point(minLat, minLng));
		setHighPoint(new Point(maxLat, maxLng));
	}

}
