package org.geocrowd.common.utils;

import org.geocrowd.common.crowd.ExpertTask;
import org.geocrowd.common.crowd.ExpertWorker;
import org.geocrowd.common.crowd.GenericTask;
import org.geocrowd.common.crowd.GenericWorker;
import org.geocrowd.common.crowd.WorkingRegion;

public class TaskUtility {

	/**
	 * distance to a task in km.
	 * 
	 * @param worker
	 *            the worker
	 * @param task
	 *            the task
	 * @return the double
	 */
	public static double distanceToTask(GenericWorker worker, GenericTask task) {
		return Utils.distance(worker.getLat(), worker.getLng(), task.getLat(),
				task.getLng());
	}

	/**
	 * check if the task is covered by a MBR.
	 * 
	 * @param mbr
	 *            the mbr
	 * @return true, if is covered by
	 */
	public static boolean isTaskCoveredBy(GenericTask task, WorkingRegion mbr) {
		if ((task.getLat() >= mbr.getMinLat())
				&& (task.getLat() <= mbr.getMaxLat())
				&& (task.getLng() >= mbr.getMinLng())
				&& (task.getLng() <= mbr.getMaxLng()))
			return true;
		return false;
	}

	/**
	 * Checks if is exact match.
	 * 
	 * @param t
	 *            the t
	 * @return true, if is exact match
	 */
	public static boolean isExactMatch(ExpertWorker w, ExpertTask t) {
		if (w.getExpertiseSet().contains(t.getCategory()))
			return true;
		return false;
	}

	/**
	 * Area in geographical coord
	 * 
	 * @return the double
	 */
	public static double areaGIS(WorkingRegion wr) {
		double x = Utils.distance(wr.getMinLat(), wr.getMinLng(),
				wr.getMaxLat(), wr.getMinLng());
		double y = Utils.distance(wr.getMinLat(), wr.getMinLng(),
				wr.getMinLat(), wr.getMaxLng());
		return x * y;
	}

	/**
	 * Diagonal length.
	 * 
	 * @return the double
	 */
	public static double diagonalLength(WorkingRegion wr) {
		return Utils.distance(wr.getMinLat(), wr.getMinLng(), wr.getMaxLat(),
				wr.getMaxLng());
	}
}
