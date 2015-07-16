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

import java.util.ArrayList;
import java.util.Iterator;

import org.geocrowd.common.utils.Utils;


// TODO: Auto-generated Javadoc
/**
 * The Class MBR.
 * 
 * @author Leyla
 */
public class MBR {
	
	/**
	 * Compute mbr.
	 * 
	 * @param points
	 *            the points
	 * @return the mbr
	 */
	public static MBR computeMBR(ArrayList<Point> points) {
		double minLat = Double.MAX_VALUE;
		double maxLat = (-1) * Double.MAX_VALUE;
		double minLng = Double.MAX_VALUE;
		double maxLng = (-1) * Double.MAX_VALUE;
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			Point pt = it.next();
			Double lat = pt.getX();
			Double lng = pt.getY();

			if (lat < minLat)
				minLat = lat;
			if (lat > maxLat)
				maxLat = lat;
			if (lng < minLng)
				minLng = lng;
			if (lng > maxLng)
				maxLng = lng;
		}

		return new MBR(minLat, minLng, maxLat, maxLng);
	}
	

	/**
	 * Similar to computeMBR but for PointTime
	 * @param pts
	 * @return
	 */
	public static MBR computeMBR2(ArrayList<PointTime> pts) {
		double minLat = Double.MAX_VALUE;
		double maxLat = (-1) * Double.MAX_VALUE;
		double minLng = Double.MAX_VALUE;
		double maxLng = (-1) * Double.MAX_VALUE;
		Iterator<PointTime> it = pts.iterator();
		while (it.hasNext()) {
			Point pt = it.next();
			Double lat = pt.getX();
			Double lng = pt.getY();

			if (lat < minLat)
				minLat = lat;
			if (lat > maxLat)
				maxLat = lat;
			if (lng < minLng)
				minLng = lng;
			if (lng > maxLng)
				maxLng = lng;
		}

		return new MBR(minLat, minLng, maxLat, maxLng);
	}
	
	/**
	 * Creates the mbr.
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
	public static MBR createMBR(double centerLat, double centerLng,
			double rangeX, double rangeY) {
		MBR mbr = new MBR(centerLat - (rangeX / 2), centerLng - (rangeY / 2),
				centerLat + (rangeX / 2), centerLng + (rangeY / 2));
		return mbr;
	}
	
	/** The min lat. */
	public double minLat;
	
	/** The max lat. */
	public double maxLat;
	
	/** The min lng. */
	public double minLng;

	/** The max lng. */
	public double maxLng;

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
	public MBR(double lR, double lC, double hR, double hC) {
		minLat = lR;
		maxLat = hR;
		minLng = lC;
		maxLng = hC;
	}

	/**
	 * Instantiates a new mbr.
	 * 
	 * @param m
	 *            the m
	 */
	public MBR(MBR m) {
		minLat = m.minLat;
		maxLat = m.maxLat;
		minLng = m.minLng;
		maxLng = m.maxLng;
	}

	/**
	 * Area.
	 * 
	 * @return the double
	 */
	public double area() {
		MBR mbr = new MBR(minLat, minLng, maxLat, maxLng);
		double x = Utils.distance(minLat, minLng, maxLat, minLng);
		double y = Utils.distance(minLat, minLng, minLat, maxLng);
		return x*y;
	}

	/**
	 * Diagonal length.
	 * 
	 * @return the double
	 */
	public double diagonalLength() {
		return Utils.distance(minLat, minLng, maxLat, maxLng);
	}

	/**
	 * Gets the max lat.
	 * 
	 * @return the max lat
	 */
	public double getMaxLat() {
		return maxLat;
	}

	/**
	 * Gets the max lng.
	 * 
	 * @return the max lng
	 */
	public double getMaxLng() {
		return maxLng;
	}

	/**
	 * Gets the min lat.
	 * 
	 * @return the min lat
	 */
	public double getMinLat() {
		return minLat;
	}

	/**
	 * Gets the min lng.
	 * 
	 * @return the min lng
	 */
	public double getMinLng() {
		return minLng;
	}

	/**
	 * Prints the.
	 */
	public void print() {
		System.out.println("minLat:" + minLat + "   maxLat:" + maxLat
				+ "   minLng:" + minLng + "   maxLng:" + maxLng);
	}

	/**
	 * Sets the max lat.
	 * 
	 * @param m
	 *            the new max lat
	 */
	public void setMaxLat(double m) {
		maxLat = m;
	}

	/**
	 * Sets the max lng.
	 * 
	 * @param m
	 *            the new max lng
	 */
	public void setMaxLng(double m) {
		maxLng = m;
	}
	
	/**
	 * Sets the mbr.
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
	public void setMBR(double lR, double lC, double hR, double hC) {
		minLat = lR;
		maxLat = hR;
		minLng = lC;
		maxLng = hC;
	}
	
	/**
	 * Sets the min lat.
	 * 
	 * @param m
	 *            the new min lat
	 */
	public void setMinLat(double m) {
		minLat = m;
	}
	
	/**
	 * Sets the min lng.
	 * 
	 * @param m
	 *            the new min lng
	 */
	public void setMinLng(double m) {
		minLng = m;
	}

}
