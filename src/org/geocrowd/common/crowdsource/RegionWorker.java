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
package org.geocrowd.common.crowdsource;

import org.geocrowd.datasets.dtype.MBR;

// TODO: Auto-generated Javadoc
/**
 * Each worker is associated with a working region (e.g., MBR)
 * 
 * @author HT186011
 *
 */
public class RegionWorker extends GenericWorker {
	
	/** The mbr. */
	private MBR mbr;
	
	/**
	 * Instantiates a new region worker.
	 */
	public RegionWorker() {
		super();
	}
	
	/**
	 * Instantiates a new region worker.
	 * 
	 * @param userID
	 *            the user id
	 * @param lat
	 *            the lat
	 * @param lng
	 *            the lng
	 * @param maxTaskNo
	 *            the max task no
	 * @param mbr
	 *            the mbr
	 */
	public RegionWorker(String userID, double lat, double lng, int maxTaskNo, MBR mbr) {
		super(userID, lat, lng, maxTaskNo);
		
		this.mbr = mbr;
	}

	/**
	 * Gets the mbr.
	 * 
	 * @return the mbr
	 */
	public MBR getMBR() {
		return mbr;
	}

	/**
	 * Sets the max lat.
	 * 
	 * @param l
	 *            the new max lat
	 */
	public void setMaxLat(double l) {
		mbr.maxLat = l;
	}

	/**
	 * Sets the max lng.
	 * 
	 * @param l
	 *            the new max lng
	 */
	public void setMaxLng(double l) {
		mbr.maxLng = l;
	}

	/**
	 * Sets the min lat.
	 * 
	 * @param l
	 *            the new min lat
	 */
	public void setMinLat(double l) {
		mbr.minLat = l;
	}

	/**
	 * Sets the min lng.
	 * 
	 * @param l
	 *            the new min lng
	 */
	public void setMinLng(double l) {
		mbr.minLng = l;
	}
}
