/*******************************************************************************
 * @ Year 2013
 * 
 * 
 * Please contact the author Hien To, ubriela@gmail.com if you have any question.
 *
 * Contributors:
 * Hien To - initial implementation
 *******************************************************************************/
package org.geocrowd.common.crowd;

import java.util.Random;

import org.geocrowd.datasets.params.GeocrowdConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericTask.
 * 
 * @author ubriela
 */
public class GenericTask {

	private double id;
	
	/** The lat. */
	private double lat;

	/** The lng. */
	private double lng;

	/** The arrival time. */
	private int arrivalTime;

	/** The expiry time. */
	private int expiryTime;

	/** The expired. */
	private boolean expired = false;

	/** The entropy. */
	private double entropy;

	/**
	 * the required number of assigned workers
	 */
	private int requirement;

	/**
	 * how many times this task is assigned/performed. assigned = k means the
	 * task is completed
	 */
	private int assigned = 0;

	/**
	 * Instantiates a new generic task.
	 */
	public GenericTask() {

	}
	
	
	public GenericTask(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
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

	/**
	 * Gets the K.
	 *
	 * @return the k
	 */
	public int getRequirement() {
		return requirement;
	}
	
	

	public void setRequirement(int requirement) {
		this.requirement = requirement;
	}

	/**
	 * @return the expiryTime
	 */
	public int getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime
	 *            the expiryTime to set
	 */
	public void setExpiryTime(int expiryTime) {
		this.expiryTime = expiryTime;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLat() + GeocrowdConstants.delimiter_dataset + getLng() + GeocrowdConstants.delimiter_dataset
				+ getArrivalTime() + GeocrowdConstants.delimiter_dataset + getExpiryTime();
	}
	
}