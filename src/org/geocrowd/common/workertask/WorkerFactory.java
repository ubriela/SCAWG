package org.geocrowd.common.workertask;

import org.geocrowd.common.distribution.WorkerTypeEnum;

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
public class WorkerFactory {
	
	public static GenericWorker getWorker(WorkerTypeEnum workerType, double lat, double lng) {
		if (workerType == null) {
			return null;
		}

		if (workerType == WorkerTypeEnum.GENERIC_WORKER) {
			return new GenericWorker(lat, lng);
		} else if (workerType == WorkerTypeEnum.EXPERT) {
			return new ExpertWorker(lat, lng);
		} else if (workerType == WorkerTypeEnum.REGION_WORKER) {
			return new RegionWorker(lat, lng);
		} else if (workerType == WorkerTypeEnum.TRUST) {
			return new TrustWorker(lat, lng);
		} else if (workerType == WorkerTypeEnum.SENSING_WORKER) {
			return new SensingWorker(lat, lng);
		}

		return null;
	}
}
