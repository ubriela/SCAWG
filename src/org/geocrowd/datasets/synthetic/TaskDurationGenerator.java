package org.geocrowd.datasets.synthetic;

import org.geocrowd.TaskDurationEnum;
import org.geocrowd.dtype.Range;


/**
 * A function to return task duration
 * @author ubriela
 *
 */

public class TaskDurationGenerator {

	private int maxTaskDuration = 0;

	public TaskDurationGenerator(int maxTaskDuration) {
		this.maxTaskDuration = maxTaskDuration;
	}

	public int nextTaskDuration(TaskDurationEnum durationDistribution) {
		switch (durationDistribution) {
		case CONSTANT:
			return maxTaskDuration;
		case RANDOM:
			return (int) UniformGenerator.randomValue(
					new Range(0, maxTaskDuration), false);
		default:
			break;
		}
		return 0;
	}
}