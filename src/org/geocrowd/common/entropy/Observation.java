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
package org.geocrowd.common.entropy;

// TODO: Auto-generated Javadoc
/**
 * The Class Observation.
 * 
 * @author Leyla
 */

/**
 * This class is used to compute location entropy. It stores a user and the
 * number of time the user check in a location
 */
public class Observation {
	
	/** The user id. */
	private int userId;
	
	/** The observe count. */
	private int observeCount;

	/**
	 * Instantiates a new observation.
	 * 
	 * @param u
	 *            the u
	 */
	public Observation(int u) {
		userId = u;
		observeCount = 1;
	}

	/**
	 * Gets the observation count.
	 * 
	 * @return the observation count
	 */
	public int getObservationCount() {
		return observeCount;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Inc observe count.
	 */
	public void incObserveCount() {
		observeCount++;
	}
}
