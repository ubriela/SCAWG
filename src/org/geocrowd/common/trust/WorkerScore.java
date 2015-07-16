package org.geocrowd.common.trust;

import java.util.ArrayList;
import java.util.List;

public class WorkerScore {

	public static final float MAX_REWARD = 10;

	/** The worker's score **/
	private float mScore;

	private List<TransactionScore> mTransactionScoreList;

	/**
	 * @param score
	 * @param rating
	 * @param transactionSize
	 * @param fastResponse
	 * @param quality
	 * @param distanceTraveled
	 * @param spatialProximityCoverage
	 */
	public WorkerScore() {
		super();

		mTransactionScoreList = new ArrayList<TransactionScore>();
	}

	private void calculateScore() {
		mScore = 0;
		for (TransactionScore ts : mTransactionScoreList) {
			mScore += ts.getTransactionScore();
		}
	}

	public void addTransactionScore(float rating, float transactionSize,
			float fastResponse, float quality, float distanceTraveled,
			float spatialProximityCoverage) {

		mTransactionScoreList.add(new TransactionScore(rating, transactionSize,
				fastResponse, quality, distanceTraveled,
				spatialProximityCoverage));
	}

	/**
	 * @return the score
	 */
	public float getScore() {
		calculateScore();

		return mScore;
	}

}
