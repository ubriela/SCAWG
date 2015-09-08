package org.geocrowd.common.crowd;

import org.geocrowd.WorkerType;
import org.geocrowd.datasets.synthetic.GenericProcessor;
import org.geocrowd.datasets.synthetic.WorkerIDGenerator;
import org.geocrowd.datasets.synthetic.WorkingRegionGenerator;

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
	
	public static GenericWorker getWorker(WorkerType workerType, double lat, double lng) {
		if (workerType == null) {
			return null;
		}

		if (workerType == WorkerType.GENERIC) {
			return new GenericWorker(lat, lng);
		} else if (workerType == WorkerType.EXPERT) {
			return new ExpertWorker(lat, lng);
		} else if (workerType == WorkerType.REGION) {
			return new RegionWorker(lat, lng);
		} else if (workerType == WorkerType.TRUST) {
			return new TrustWorker(lat, lng);
		} else if (workerType == WorkerType.SENSING) {
			return new SensingWorker(lat, lng);
		}

		return null;
	}
}
