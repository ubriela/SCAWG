package org.geocrowd.common.crowd;


/**
 * The Class RewardedTask.
 *
 * @author Giorgos Constantinou
 *
 * Each task has a reward upon completion
 */
public class RewardTask extends ExpiringTask {

    /**
     * The reward.
     */
    private double mReward;		// of the task reward

    /**
     * Instantiates a new rewarded task.
     *
     * @param reward the reward
     */
    public RewardTask(int expiryTime, double reward) {
    	super(expiryTime);
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
    public RewardTask(double lt, double ln, int entry, int expiry, double ent, double reward) {
        super(lt, ln, entry, expiry, ent);
        this.mReward = reward;
    }

	public RewardTask() {
		// TODO Auto-generated constructor stub
		super();
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

    
}
