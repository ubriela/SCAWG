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

import org.geocrowd.common.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericWorker.
 * 
 * @author ubriela
 */
public class GenericWorker {
	
	/** The user id. */
	public String id;
	
	/** The lat. */
	public double lat;
	
	/** The lng. */
	public double lng;
	
	/** The maximum number of tasks the worker can perform. */
	public int capacity;
	

	/**
	 * Instantiates a new generic worker.
	 */
	public GenericWorker() {
		super();
	}
	
	/**
	 * Instantiates a new generic worker.
	 * 
	 * @param workerId
	 *            the user id
	 * @param lat
	 *            the lat
	 * @param lng
	 *            the lng
	 * @param capacity
	 *            the max task no
	 */
	public GenericWorker(String workerId, double lat, double lng, int capacity) {
		super();
		this.id = workerId;
		this.lat = lat;
		this.lng = lng;
		this.capacity = capacity;
	}
	
	/**
	 * distance to a task in km.
	 * 
	 * @param worker
	 *            the worker
	 * @param task
	 *            the task
	 * @return the double
	 */
	public double distanceToTask(GenericTask task) {
		return Utils.distance(lat,lng,task.getLat(),task.getLng());
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	public double getLat() {
		return lat;
	}
	
	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	public double getLng() {
		return lng;
	}
	
	
	
	
	public void setId(String workerId) {
		this.id = workerId;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Gets the max task no.
	 * 
	 * @return the max task no
	 */
	public int getCapacity() {
//		return 1;
		return capacity;
	}
	
	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Inc max task no.
	 */
	public void incMaxTaskNo() {
		capacity++;
	}
	
}
