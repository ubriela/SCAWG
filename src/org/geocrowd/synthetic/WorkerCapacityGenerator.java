package org.geocrowd.synthetic;

import org.geocrowd.common.distribution.WorkerCapacityDistributionEnum;
import org.geocrowd.dtype.Range;

/**
 * Worker capacity is generated based on several criteria. For example, the
 * capacity of the worker is large if he/she is active.
 * 
 * @author ubriela
 *
 */
public class WorkerCapacityGenerator {
	private static final int MINIMUM_CAPACITY = 0;

	private int maximumCapacity;

	/**
	 * Some workers tend to be more active than others. Thus, we introduce the concept of worker’s activeness,
	 * which indicates the probability of one worker to be online and ready to perform tasks. The worker’s activeness
	 * is a value between 0 and 1
	 */
	private double activeness; // This variable but not in used yet. Provide a place holder for implementation.

	public WorkerCapacityGenerator(int maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public int nextWorkerCapacity(WorkerCapacityDistributionEnum capacityType) {
		switch (capacityType) {
			case CONSTANT:
				return maximumCapacity;
			case RANDOM:
				// randomly between [0, maximumCapacity)
				return (int) UniformGenerator.randomValue(
					new Range(MINIMUM_CAPACITY, maximumCapacity), true) + 1;
			case ACTIVENESS_BASED:
				// The higher worker activeness, the higher capacity.
				return (int) activeness * maximumCapacity;
			default:
				throw new IllegalArgumentException();
		}
	}
}
