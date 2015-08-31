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

import java.util.Random;

import org.geocrowd.GeocrowdConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericTask.
 * 
 * @author ubriela
 */
public class GenericTask {

	/** The lat. */
	private double lat;

	/** The lng. */
	private double lng;

	/** The arrival time. */
	private int arrivalTime;

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
	 * @param lat
	 *            the lat
	 * @param lng
	 *            the lng
	 * @param arrival
	 *            the arrival
	 * @param ent
	 *            the ent
	 */
	public GenericTask(double lat, double lng, int arrival, double entropy) {
		this.lat = lat;
		this.lng = lng;
		arrivalTime = arrival;
		this.entropy = entropy;
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
	 * Gets the arrival time.
	 * 
	 * @return the arrival time
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Gets the lat.
	 * 
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	
	

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
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
	public boolean isCoveredBy(WorkingRegion mbr) {
		if ((lat >= mbr.getMinLat()) && (lat <= mbr.getMaxLat()) && (lng >= mbr.getMinLng())
				&& (lng <= mbr.getMaxLng()))
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
		return "lat: " + lat + "   lng: " + lng + "   time: " + arrivalTime
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