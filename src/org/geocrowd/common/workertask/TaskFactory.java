package org.geocrowd.common.workertask;

import org.geocrowd.common.distribution.TaskTypeEnum;

/**
 * This class uses Factory pattern. This type of design pattern comes under
 * creational pattern as this pattern provides one of the best ways to create an
 * object.
 * 
 * In Factory pattern, we create object without exposing the creation logic to
 * the client and refer to newly created object using a common interface.
 * 
 * @author ubriela
 *
 */
public class TaskFactory {
	public static GenericTask getTask(TaskTypeEnum taskType, double lat, double lng) {
		if (taskType == null) {
			return null;
		}
		if (taskType == TaskTypeEnum.GENERIC_TASK) {
			return new GenericTask(lat, lng);
		} else if (taskType == TaskTypeEnum.EXPERT_TASK) {
			return new ExpertTask(lat, lng);
		} else if (taskType == TaskTypeEnum.REWARD_TASK) {
			return new RewardTask(lat, lng);
		} else if (taskType == TaskTypeEnum.SENSING_TASK) {
			return new SensingTask(lat, lng);
		}

		return null;
	}
}
