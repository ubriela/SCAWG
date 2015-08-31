package org.geocrowd.common.crowd;

import org.geocrowd.WorkerType;

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
	public static GenericWorker getWorker(WorkerType workerType) {
		if (workerType == null) {
			return null;
		}

		if (workerType == WorkerType.GENERIC) {
			return new GenericWorker();
		} else if (workerType == WorkerType.EXPERT) {
			return new ExpertWorker();
		} else if (workerType == WorkerType.REGION) {
			return new RegionWorker();
		} else if (workerType == WorkerType.TRUST) {
			return new TrustWorker();
		} else if (workerType == WorkerType.SENSING) {
			return new SensingWorker();
		}

		return null;
	}
}
