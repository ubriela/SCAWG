package org.geocrowd.common.crowdsource;


/**
 * The Class ExpiringTask.
 *
 * @author Giorgos Constantinou
 *
 * Each task has a reward upon completion
 */
public class ExpiringTask extends GenericTask {

	/** The expiry time. */
	private int mExpiryTime;

    /**
     * Instantiates a new expiring task.
     *
     * @param expiryTime the expiryTime
     */
    public ExpiringTask(int expiryTime) {
        this.mExpiryTime = expiryTime;
    }

    /**
     * Instantiates a new expiring task.
     *
     * @param lt the lt
     * @param ln the ln
     * @param entry the entry
     * @param ent the ent
     */
    public ExpiringTask(double lt, double ln, int entry, int expiry, double ent) {
        super(lt, ln, entry, ent);
        
        this.mExpiryTime = expiry;
    }

	/**
	 * @return the expiryTime
	 */
	public int getExpiryTime() {
		return mExpiryTime;
	}

	/**
	 * @param expiryTime the expiryTime to set
	 */
	public void setExpiryTime(int expiryTime) {
		mExpiryTime = expiryTime;
	}

	
    
    


}
