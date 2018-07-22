package org.geocrowd.synthetic;

import java.util.Hashtable;
import java.util.Random;

import org.apache.commons.math3.distribution.ZipfDistribution;
import org.geocrowd.common.distribution.WorkerIDDistributionEnum;
import org.geocrowd.dtype.Range;

/**
 * We generate the workerid at a particular time snapshot based on how active
 * the user is.
 */
public class WorkerIDGenerator {
	private double mean = 0.5;
	private double st = 0.2;
	private static Hashtable<Integer, Double> workerIdToActiveness = null;
	private static RouletteWheelGenerator<Double> generator = null;

	public WorkerIDGenerator(WorkerIDDistributionEnum activenessDist, int count) {
		if (workerIdToActiveness == null) {
			workerIdToActiveness = new Hashtable<>();
			for (int workerId = 0; workerId < count; workerId++) {
				double activeness = -1;
				switch (activenessDist) {
				case UNIFORM: // uniform one-dimensional dataset
					Random r = new Random(System.nanoTime());
					activeness = r.nextDouble();
					break;
				case ZIFFIAN: // zipf distribution
					ZipfDistribution zipf = new ZipfDistribution(count, 1);
					int rand = (int) UniformGenerator.randomValue(new Range(0, count), true);
					activeness = zipf.probability(rand);
					break;
				case GAUSSIAN:
					Random rd = new Random(System.nanoTime());

					while (activeness < 0 || activeness > 1)
						activeness = mean + st * rd.nextGaussian();
					break;
				}

				workerIdToActiveness.put(workerId, activeness);
			}

			Double[] arr = new Double[workerIdToActiveness.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = workerIdToActiveness.get(i);
			generator = new RouletteWheelGenerator<Double>(arr);
		}
	}

	public int nextWorkerId() {
		int idx = generator.nextValue();
		return idx;
	}

	public static Hashtable<Integer, Double> getWorkerIdToActiveness() {
		return workerIdToActiveness;
	}

}