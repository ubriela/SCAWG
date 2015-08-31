package org.geocrowd.common.crowd;

import org.geocrowd.TaskType;

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
	public static GenericTask getTask(TaskType taskType) {
		if (taskType == null) {
			return null;
		}
		if (taskType == TaskType.GENERIC) {
			return new GenericTask();
		} else if (taskType == TaskType.EXPERT) {
			return new ExpertTask();
		} else if (taskType == TaskType.REWARD) {
			return new RewardTask();
		} else if (taskType == TaskType.SENSING) {
			return new SensingTask();
		}

		return null;
	}
}
