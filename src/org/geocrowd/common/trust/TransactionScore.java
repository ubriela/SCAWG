package org.geocrowd.common.trust;

public class TransactionScore {

	public static final float MAX_REWARD = 10;

	/** The score for this transaction **/
	private float mTransactionScore;

	// -- From e-Commerce

	/** Feedback left from requesters after each submission from workers **/
	private float mRating;

	/** The reward given for a performed task **/
	private float mTransactionSize;

	// -- From SC context - Non Spatial

	/**
	 * Ratio of the time left to execute the task with the total active period
	 * of the task
	 **/
	private float mFastResponse;

	/**
	 * Data specific. e.g. video quality is HD, image has high resolution. Can
	 * be extended using video analysis, image processing, text analysis etc
	 **/
	private float mQuality;

	// -- From SC context - Spatial

	/**
	 * The distance traveled to the task related to the award of the task. The
	 * start point is recorded at the time a task assignment is accepted by the
	 * worker.
	 **/
	private float mDistanceTraveled;

	/**
	 * The proximity of the worker to the task. For media tasks, e.g. video,
	 * image, the coverage of the data can be used (FOV)
	 **/
	private float mSpatialProximityCoverage;

	/**
	 * @param score
	 * @param rating
	 * @param transactionSize
	 * @param fastResponse
	 * @param quality
	 * @param distanceTraveled
	 * @param spatialProximityCoverage
	 */
	public TransactionScore(float rating, float transactionSize,
			float fastResponse, float quality, float distanceTraveled,
			float spatialProximityCoverage) {
		super();
		mRating = rating;
		mTransactionSize = transactionSize;
		mFastResponse = fastResponse;
		mQuality = quality;
		mDistanceTraveled = distanceTraveled;
		mSpatialProximityCoverage = spatialProximityCoverage;

		calculateTransactionScore();
	}

	private void calculateTransactionScore() {
		mTransactionScore = computeWeightedRating() + computeWeightedFRT()
				+ computeWeightedQuality() + computeWeightedDistanceTraveled()
				+ computeWeightedSpatialProximityCoverage();
	}

	private float computeWeightedRating() {
		return (mRating * mTransactionSize / MAX_REWARD);
	}

	private float computeWeightedQuality() {
		return mQuality;
	}

	private float computeWeightedFRT() {
		return mFastResponse;
	}

	private float computeWeightedDistanceTraveled() {
		return mDistanceTraveled / mTransactionSize;
	}

	private float computeWeightedSpatialProximityCoverage() {
		return mSpatialProximityCoverage;
	}

	/**
	 * @return the rating
	 */
	public float getRating() {
		return mRating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(float rating) {
		mRating = rating;
	}

	/**
	 * @return the transactionSize
	 */
	public float getTransactionSize() {
		return mTransactionSize;
	}

	/**
	 * @param transactionSize
	 *            the transactionSize to set
	 */
	public void setTransactionSize(float transactionSize) {
		mTransactionSize = transactionSize;
	}

	/**
	 * @return the fastResponse
	 */
	public float getFastResponse() {
		return mFastResponse;
	}

	/**
	 * @param fastResponse
	 *            the fastResponse to set
	 */
	public void setFastResponse(float fastResponse) {
		mFastResponse = fastResponse;
	}

	/**
	 * @return the quality
	 */
	public float getQuality() {
		return mQuality;
	}

	/**
	 * @param quality
	 *            the quality to set
	 */
	public void setQuality(float quality) {
		mQuality = quality;
	}

	/**
	 * @return the distanceTraveled
	 */
	public float getDistanceTraveled() {
		return mDistanceTraveled;
	}

	/**
	 * @param distanceTraveled
	 *            the distanceTraveled to set
	 */
	public void setDistanceTraveled(float distanceTraveled) {
		mDistanceTraveled = distanceTraveled;
	}

	/**
	 * @return the spatialProximityCoverage
	 */
	public float getSpatialProximityCoverage() {
		return mSpatialProximityCoverage;
	}

	/**
	 * @param spatialProximityCoverage
	 *            the spatialProximityCoverage to set
	 */
	public void setSpatialProximityCoverage(float spatialProximityCoverage) {
		mSpatialProximityCoverage = spatialProximityCoverage;
	}

	/**
	 * @return the score
	 */
	public float getTransactionScore() {
		calculateTransactionScore();

		return mTransactionScore;
	}

}
