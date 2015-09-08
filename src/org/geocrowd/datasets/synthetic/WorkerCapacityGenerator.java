package org.geocrowd.datasets.synthetic;

import org.geocrowd.WorkerCapacityEnum;
import org.geocrowd.dtype.Range;

/**
 * Worker capacity is generated based on several criteria. For example, the
 * capacity of the worker is large if he/she is active.
 * 
 * @author ubriela
 *
 */
public class WorkerCapacityGenerator {
	private int maxCapacity = 0;
	private double activeness = 0;

	public WorkerCapacityGenerator(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public void setActiveness(double activeness) {
		this.activeness = activeness;
	}

	public int nextWorkerCapacity(WorkerCapacityEnum capacityType) {
		switch (capacityType) {
		case CONSTANT:
			return maxCapacity;
		case RANDOM:
			// randomly between [0, maxCapacity)
			return (int) UniformGenerator.randomValue(
					new Range(0, maxCapacity), true) + 1;
		case ACTIVENESS_BASED:
			// The higher worker activeness, the higher capacity.
			return (int) activeness * maxCapacity;
		}
		return 0;
	}
}
