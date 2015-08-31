package org.geocrowd.datasets.synthetic;

import java.util.HashSet;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.geocrowd.Distribution1DEnum;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.dtype.Range;

public class WorkerIDGenerator {
	Distribution1DEnum dist;
	int count = 0;
	int min = 0;
	int max = 0;
	HashSet<Integer> distinct = new HashSet<>();

	public WorkerIDGenerator(Distribution1DEnum dist, int count, int min,
			int max) {
		this.dist = dist;
		this.count = count;
		this.min = min;
		this.max = max;
	}

	public int nextWorkerId() {
		int workerId = 0;
		switch (dist) {
		case UNIFORM_1D: // uniform one-dimensional dataset
			Random r = new Random();
			r.setSeed(System.nanoTime());
			workerId = (int) Math.floor(r.nextDouble() * (max - min) + min);
			break;
		case ZIFFIAN_1D: // zipf distribution
			int rand = (int) UniformGenerator.randomValue(new Range(0, max
					- min), true);
			workerId = (int) ((max - min) * Utils.zipf_pmf(count, rand, 1));
			break;
		case GAUSSIAN_1D:
			NormalDistribution nd = new NormalDistribution((max - min) / 2,
					(max - min) / 4);
			while (true) {
				workerId = (int) nd.sample();
				if (workerId <= max && workerId >= min && !distinct.contains(workerId))
					break;
			}
			break;
		}
		
		distinct.add(workerId);
		return workerId;
	}
}