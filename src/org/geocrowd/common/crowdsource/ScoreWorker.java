/*******************************************************************************
* @ Year 2015
*
* Contributors:
* Giorgos Constantinou
*******************************************************************************/
package org.geocrowd.common.crowdsource;

import org.geocrowd.common.trust.WorkerScore;
import org.geocrowd.datasets.dtype.MBR;

/**
 * Each worker is associated with a score (or trust value)
 * 
 * @author Giorgos Constantinou
 *
 */
public class ScoreWorker extends RegionWorker {
	
	/** The worker's score */
	private WorkerScore score;
	
	/**
	 * Instantiates a new score worker.
	 */
	public ScoreWorker() {
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
	public ScoreWorker(String userID, double lat, double lng, int maxTaskNo, MBR mbr, float score) {
		super(userID, lat, lng, maxTaskNo, mbr);
		
		this.score = new WorkerScore();
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
