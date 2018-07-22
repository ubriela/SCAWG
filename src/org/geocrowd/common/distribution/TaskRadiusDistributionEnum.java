package org.geocrowd.common.distribution;

/**
 * Distribution to generate task radius. The task radius specifies the circular area around the sensing tasks
 * such that only enclosed workers are eligible to perform the tasks.
 */
public enum TaskRadiusDistributionEnum {
	RANDOM,
	CONSTANT
}
