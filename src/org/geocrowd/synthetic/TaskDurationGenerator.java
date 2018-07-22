package org.geocrowd.synthetic;

import org.geocrowd.common.distribution.TaskDurationDistributionEnum;
import org.geocrowd.dtype.Range;


/**
 * A function to return task duration
 * @author ubriela
 *
 */

public class TaskDurationGenerator {

	private static int MINIMUM_TASK_DURATION = 0;

	private int maximumTaskDuration;

	public TaskDurationGenerator(int maxTaskDuration) {
		this.maximumTaskDuration = maxTaskDuration;
	}

	public int nextTaskDuration(TaskDurationDistributionEnum durationDistribution) {
		switch (durationDistribution) {
			case CONSTANT:
				return maximumTaskDuration;
			case RANDOM:
				return (int) UniformGenerator.randomValue(
					new Range(MINIMUM_TASK_DURATION, maximumTaskDuration), false);
			default:
				throw new IllegalArgumentException();
		}
	}
}