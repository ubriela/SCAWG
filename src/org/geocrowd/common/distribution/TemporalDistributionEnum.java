package org.geocrowd.common.distribution;

/**
 * Specify the temporal arrival patterns for the workers and tasks over time.
 */
public enum TemporalDistributionEnum {
	CONSTANT,
	LINEARLY_DECREASING,
	LINEARLY_INCREASING,
	COSINE,
	POISSON,
	ZIPFIAN
}