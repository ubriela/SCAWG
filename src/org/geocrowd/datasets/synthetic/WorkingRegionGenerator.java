package org.geocrowd.datasets.synthetic;

import org.geocrowd.WorkingRegionEnum;
import org.geocrowd.common.crowd.GenericWorker;
import org.geocrowd.common.crowd.WorkingRegion;
import org.geocrowd.datasets.params.GeocrowdConstants;
import org.geocrowd.dtype.Range;

/**
 * Generate working region for a particular worker
 * 
 * @author ubriela
 *
 */
public class WorkingRegionGenerator {
	
	//	max width/height
	private double minLat = 0;
	private double minLng = 0;
	private double maxLat = 0;
	private double maxLng = 0;

	public WorkingRegionGenerator(double minLat, double minLng, double maxLat, double maxLng) {
		super();

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

	public WorkingRegion nextWorkingRegion(GenericWorker w, WorkingRegionEnum workingRegionType) {
		double rangeX = 0;
		double rangeY = 0;
		double maxRangeX = (maxLat - minLat) * GeocrowdConstants.MAX_WORKING_REGION;
		double maxRangeY = (maxLng - minLng) * GeocrowdConstants.MAX_WORKING_REGION;
		switch (workingRegionType) {
		case CONSTANT:
			rangeX = maxRangeX;
			rangeY = maxRangeY;
		case RANDOM:
			rangeX = UniformGenerator.randomValue(new Range(0, maxRangeX),
					false);
			rangeY = UniformGenerator.randomValue(new Range(0, maxRangeY),
					false);
		}
		 
		return checkMBR(WorkingRegion.createMBR(w.getLat(),
				w.getLng(), rangeX, rangeY));
	}

}
