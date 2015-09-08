package org.geocrowd.datasets.synthetic;

import org.geocrowd.TaskRadiusEnum;
import org.geocrowd.dtype.Range;

/**
 * A function to return task radius for crowdsensing tasks
 * @author ubriela
 *
 */

public class TaskRadiusGenerator {

	private double maxTaskRadius = 0;

	public TaskRadiusGenerator(double maxTaskRadius) {
		this.maxTaskRadius = maxTaskRadius;
	}

	public double nextTaskRadius(TaskRadiusEnum radiusDistribution) {
		switch (radiusDistribution) {
		case CONSTANT:
			return maxTaskRadius;
		case RANDOM:
			return UniformGenerator.randomValue(
					new Range(0, maxTaskRadius), false);
		default:
			break;
		}
		return 0;
	}
}