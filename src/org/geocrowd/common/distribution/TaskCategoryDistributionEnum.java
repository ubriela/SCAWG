package org.geocrowd.common.distribution;

/**
 * Every spatial task has a category. This enum specifies the distribution used to generate different task categories.
 * Task category has the same value domain as that of the worker's expertise.
 */
public enum TaskCategoryDistributionEnum {
	RANDOM		// Task category is randomly generated between a range.
}
