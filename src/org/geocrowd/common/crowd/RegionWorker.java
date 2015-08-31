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
package org.geocrowd.common.crowd;



// TODO: Auto-generated Javadoc
/**
 * Each worker is associated with a working region (e.g., MBR)
 * 
 * @author HT186011
 *
 */
public class RegionWorker extends GenericWorker {
	
	/** The mbr. */
	private WorkingRegion mbr;
	
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
	 * @param capacity
	 *            the max task no
	 * @param mbr
	 *            the mbr
	 */
	public RegionWorker(String userID, double lat, double lng, int capacity, WorkingRegion mbr) {
		super(userID, lat, lng, capacity);
		
		this.mbr = mbr;
	}

	/**
	 * Gets the mbr.
	 * 
	 * @return the mbr
	 */
	public WorkingRegion getMbr() {
		return mbr;
	}
	
	

	public void setMbr(WorkingRegion mbr) {
		this.mbr = mbr;
	}

	/**
	 * Sets the max lat.
	 * 
	 * @param l
	 *            the new max lat
	 */
	public void setMaxLat(double l) {
		mbr.setMaxLat(l);
	}

	/**
	 * Sets the max lng.
	 * 
	 * @param l
	 *            the new max lng
	 */
	public void setMaxLng(double l) {
		mbr.setMaxLng(l);
	}

	/**
	 * Sets the min lat.
	 * 
	 * @param l
	 *            the new min lat
	 */
	public void setMinLat(double l) {
		mbr.setMinLat(l);
	}

	/**
	 * Sets the min lng.
	 * 
	 * @param l
	 *            the new min lng
	 */
	public void setMinLng(double l) {
		mbr.setMinLng(l);
	}
}
