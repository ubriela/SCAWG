package org.geocrowd.common.crowd;

public class SensingWorker extends GenericWorker {

    /**
     * The entry time.
     */
    private int onlineTime;

    public SensingWorker(String userID, double lat, double lng, int maxTaskNo, int onlineTime) {
        this.lat = lat;
        this.lng = lng;
        this.capacity = maxTaskNo;
        this.id = userID;
        this.onlineTime = onlineTime;
    }

    public SensingWorker() {
		// TODO Auto-generated constructor stub
    	super();
	}

	public int getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(int onlineTime) {
        this.onlineTime = onlineTime;
    }
}
