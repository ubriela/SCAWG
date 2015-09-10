package org.geocrowd.common.crowd;

public class SensingWorker extends GenericWorker {

    public SensingWorker(String userID, double lat, double lng, int maxTaskNo) {
        this.lat = lat;
        this.lng = lng;
        this.capacity = maxTaskNo;
        this.id = userID;
    }

    public SensingWorker() {
		// TODO Auto-generated constructor stub
    	super();
	}

	public SensingWorker(double lat, double lng) {
		super(lat, lng);
	}
}
