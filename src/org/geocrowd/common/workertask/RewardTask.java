package org.geocrowd.common.workertask;

import org.geocrowd.GeocrowdConstants;

/**
 * The Class RewardedTask.
 *
 * @author Giorgos Constantinou
 *
 * Each task has a reward upon completion
 */
public class RewardTask extends GenericTask {

    /**
     * The reward.
     */
    private double mReward;		// of the task reward

    /**
     * Instantiates a new rewarded task.
     *
     * @param reward the reward
     */
    public RewardTask(double reward) {
    	super();
        this.mReward = reward;
    }

    /**
     * Instantiates a new rewarded task.
     *
     * @param lt the lt
     * @param ln the ln
     * @param entry the entry
     * @param ent the ent
     */
    public RewardTask(double lt, double ln, int entry, double ent, double reward) {
        super(lt, ln, entry, ent);
        this.mReward = reward;
    }

	public RewardTask() {
		// TODO Auto-generated constructor stub
		super();
	}

	public RewardTask(double lat, double lng) {
		super(lat, lng);
	}

	/**
	 * @return the reward
	 */
	public double getReward() {
		return mReward;
	}

	/**
	 * @param reward the reward to set
	 */
	public void setReward(double reward) {
		mReward = reward;
	}

	@Override
	public String toString() {
		return super.toString() + GeocrowdConstants.delimiter_dataset + mReward;
	}
}
