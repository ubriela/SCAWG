package org.geocrowd.common.distribution;

/**
 * Supported task types.
 */
public enum TaskTypeEnum {
	GENERIC_TASK,		// Generic task has basic properties: location, start/end time.
	REWARD_TASK,		// In addition to the basic properties, a reward task has  and a reward.
	SENSING_TASK,		// In addition to the basic properties, a sensing task has a radius/region.
	EXPERT_TASK			// In addition to the basic properties, an expert task has the basic properties and
}