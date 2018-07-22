package org.geocrowd.common.distribution;

/**
 * Supported worker types.
 */
public enum WorkerTypeEnum {
	GENERIC_WORKER,		// Generic worker has basic properties such as location, start/end time.
	REGION_WORKER,		// In addition to the basic properties, region worker has a region where he/she is willing to travel within
	SENSING_WORKER,		// In addition to the basic properties,
	EXPERT,
	TRUST
}