/*******************************************************************************
* @ Year 2015
*
* Contributors:
* Giorgos Constantinou
*******************************************************************************/
package org.geocrowd.common.crowd;

import org.geocrowd.common.trust.WorkerScore;

/**
 * Each worker is associated with a score (or trust value)
 * 
 * @author Giorgos Constantinou
 *
 */
public class TrustWorker extends RegionWorker {
	
	/** The worker's score */
	private WorkerScore score;
	
	/**
	 * Instantiates a new score worker.
	 */
	public TrustWorker() {
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
	 * @param score
	 *            the score
	 */
	public TrustWorker(String userID, double lat, double lng, int maxTaskNo, WorkingRegion mbr, float score) {
		super(userID, lat, lng, maxTaskNo, mbr);
		
		this.score = new WorkerScore();
	}

	public TrustWorker(double lat, double lng) {
		super(lat, lng);
	}

	/**
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public WorkerScore getScore() {
		return score;
	}

	/**
	 * Sets the score.
	 * 
	 * @param score the score to set
	 */
	public void setScore(WorkerScore score) {
		this.score = score;
	}
}
