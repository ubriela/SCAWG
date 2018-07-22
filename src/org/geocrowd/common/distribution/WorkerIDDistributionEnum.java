package org.geocrowd.common.distribution;

/**
 * Distributions used to generate worker id. Worker’s identity can be used to differentiate workers from each others.
 * Worker’s ID can be helpful to enhance task assignment in SC applications, and/or to avoid repetitive activation of
 * the same worker in a short time.
 */
public enum WorkerIDDistributionEnum {
	UNIFORM,
	ZIFFIAN, 
	GAUSSIAN
}
