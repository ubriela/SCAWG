package org.geocrowd.synthetic;

import org.geocrowd.common.distribution.TaskCategoryDistributionEnum;
import org.geocrowd.dtype.Range;

/**
 * Spatial task may have a category. Different categories of tasks may require workers with various skill sets or
 * expertise. Task category has the same value domain as that of the worker's expertise.
 */
public class TaskCategoryGenerator {
	private static int MINIMUM_TASK_CATEGORY = 0;

	private int maximumTaskCategory;

	public TaskCategoryGenerator(int maximumTaskCategory) {
		this.maximumTaskCategory = maximumTaskCategory;
	}

	public int nextTaskCategory(TaskCategoryDistributionEnum taskTypeDistribution) {
		switch (taskTypeDistribution) {
			case RANDOM:
				return (int) UniformGenerator.randomValue(
					new Range(MINIMUM_TASK_CATEGORY, this.maximumTaskCategory), true);
			default:
				throw new IllegalArgumentException();
		}
	}
}
