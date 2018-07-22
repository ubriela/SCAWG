package org.geocrowd.synthetic;

import org.geocrowd.common.distribution.WorkingRegionDistributionEnum;
import org.geocrowd.common.workertask.GenericWorker;
import org.geocrowd.common.workertask.WorkingRegion;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.dtype.Range;

/**
 * Generate working region for a particular worker.
 */
public class WorkingRegionGenerator {
	
	//	max width/height
	private double minLat = 0;
	private double minLng = 0;
	private double maxLat = 0;
	private double maxLng = 0;

	public WorkingRegionGenerator(double minLat, double minLng, double maxLat, double maxLng) {
		this.minLat = minLat;
		this.minLng = minLng;
		this.maxLat = maxLat;
		this.maxLng = maxLng;
	}

	public WorkingRegion nextWorkingRegion(GenericWorker w, WorkingRegionDistributionEnum workingRegionType) {
		double rangeX = 0;
		double rangeY = 0;
		double maxRangeX = (maxLat - minLat) * GeocrowdConstants.MAX_WORKING_REGION;
		double maxRangeY = (maxLng - minLng) * GeocrowdConstants.MAX_WORKING_REGION;
		switch (workingRegionType) {
			case CONSTANT:
				rangeX = maxRangeX;
				rangeY = maxRangeY;
				break;
			case RANDOM:
				rangeX = UniformGenerator.randomValue(new Range(0, maxRangeX), false);
				rangeY = UniformGenerator.randomValue(new Range(0, maxRangeY), false);
				break;
			default:
				throw new IllegalArgumentException();
		}
		 
		return checkMBR(WorkingRegion.createMBR(w.getLat(), w.getLng(), rangeX, rangeY));
	}

	/**
	 * Check boundary mbr to make sure the coordinates are within the domain
	 *
	 * @param mbr
	 *            the mbr
	 */
	private WorkingRegion checkMBR(WorkingRegion mbr) {
		if (mbr.getMinLat() < minLat)
			mbr.setMinLat(minLat);
		if (mbr.getMaxLat() > maxLat)
			mbr.setMaxLat(maxLat);
		if (mbr.getMinLng() < minLng)
			mbr.setMinLng(minLng);
		if (mbr.getMaxLng() > maxLng)
			mbr.setMaxLng(maxLng);
		return mbr;
	}

}
