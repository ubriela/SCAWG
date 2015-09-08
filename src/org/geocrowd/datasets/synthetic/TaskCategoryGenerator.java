package org.geocrowd.datasets.synthetic;

import org.geocrowd.TaskCategoryEnum;
import org.geocrowd.dtype.Range;

/**
 * Generate a random task category between [0, taskCategory)
 * @author ubriela
 *
 */
public class TaskCategoryGenerator {
	private int taskCategory = 0;

	public TaskCategoryGenerator(int taskCategory) {
		this.taskCategory = taskCategory;
	}

	public int nextTaskCategory(TaskCategoryEnum categoryType) {
		switch (categoryType) {
		case RANDOM:
			return (int) UniformGenerator.randomValue(
					new Range(0, taskCategory), true);
		default:
			break;
		}
		return 0;
	}
}
