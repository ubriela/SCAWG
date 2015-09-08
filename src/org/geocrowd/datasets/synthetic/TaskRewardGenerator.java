package org.geocrowd.datasets.synthetic;

import org.geocrowd.TaskRewardEnum;
import org.geocrowd.dtype.Range;

/**
 * A function to return task reward
 * @author ubriela
 *
 */

public class TaskRewardGenerator {

	private double maxTaskReward = 0;

	public TaskRewardGenerator(double maxTaskReward) {
		this.maxTaskReward = maxTaskReward;
	}

	public double nextTaskReward(TaskRewardEnum rewardDistribution) {
		switch (rewardDistribution) {
		case CONSTANT:
			return maxTaskReward;
		case RANDOM:
			return UniformGenerator.randomValue(
					new Range(0, maxTaskReward), false);
		default:
			break;
		}
		return 0;
	}
}