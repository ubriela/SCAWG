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

import java.util.Random;

import org.geocrowd.GeocrowdConstants;
import org.geocrowd.datasets.dtype.MBR;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericTask.
 * 
 * @author HT186011
 */
public class GenericTask {

	/** The lat. */
	private double lat;

	/** The lng. */
	private double lng;

	/** The entry time. */
	private int entryTime;

	/** The assigned. */
	private int assigned = 0; // how many times this task is assigned

	/** The expired. */
	private boolean expired = false;

	/** The entropy. */
	private double entropy;

	/**
	 * the required number of assigned workers
	 */
	private int k;

	/**
	 * Instantiates a new generic task.
	 */
	public GenericTask() {

	}

	/**
	 * Instantiates a new generic task.
	 * 
	 * @param lt
	 *            the lt
	 * @param ln
	 *            the ln
	 * @param entry
	 *            the entry
	 * @param ent
	 *            the ent
	 */
	public GenericTask(double lt, double ln, int entry, double ent) {
		lat = lt;
		lng = ln;
		entryTime = entry;
		entropy = ent;
	}

	/**
	 * Gets the entropy.
	 * 
	 * @return the entropy
	 */
	public double getEntropy() {
		return entropy;
	}

	/**
	 * Gets the entry time.
	 * 
	 * @return the entry time
	 */
	public int getEntryTime() {
		return entryTime;
	}

	/**
	 * Gets the lat.
	 * 
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Gets the lng.
	 * 
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * Inc assigned.
	 */
	public void incAssigned() {
		assigned++;
	}

	/**
	 * check if the task is covered by a MBR.
	 * 
	 * @param mbr
	 *            the mbr
	 * @return true, if is covered by
	 */
	public boolean isCoveredBy(MBR mbr) {
		if ((lat >= mbr.minLat) && (lat <= mbr.maxLat) && (lng >= mbr.minLng)
				&& (lng <= mbr.maxLng))
			return true;
		return false;
	}

	/**
	 * Checks if is expired.
	 * 
	 * @return true, if is expired
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * Sets the expired.
	 */
	public void setExpired() {
		expired = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "lat: " + lat + "   lng: " + lng + "   time: " + entryTime
				+ "    assigned: " + assigned + "    expired : " + expired;
	}

	/**
	 *
	 * @param k
	 */
	public void setK(int k) {
		if (GeocrowdConstants.IS_RANDOM_K) {
			this.k = k;
		} else {
			Random r = new Random();
			this.k = r.nextInt(k) + 1;
		}

	}

	/**
	 * Gets the K.
	 *
	 * @return the k
	 */
	public int getK() {
		return k;
	}
}