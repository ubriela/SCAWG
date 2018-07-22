package org.geocrowd.synthetic;

import org.geocrowd.common.distribution.TaskRewardDistributionEnum;
import org.geocrowd.dtype.Range;

/**
 * Generate task reward following a particular distribution.
 */
public class TaskRewardGenerator {

	private static final double MINIMUM_TASK_REWARD = 0.0;

	private double maximumTaskReward = 0;

	public TaskRewardGenerator(double maximumTaskReward) {
		this.maximumTaskReward = maximumTaskReward;
	}

	public double nextTaskReward(TaskRewardDistributionEnum rewardDistribution) {
		switch (rewardDistribution) {
			case CONSTANT:
				return maximumTaskReward;
			case RANDOM:
				return UniformGenerator.randomValue(
					new Range(MINIMUM_TASK_REWARD, maximumTaskReward), false);
			default:
				throw new IllegalArgumentException();
		}
	}
}