package org.geocrowd.datasets.synthetic;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

import jmetal.util.RandomGenerator;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;
import org.geocrowd.Distribution1DEnum;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.dtype.Range;

/**
 * We generate the workerid at a particular time snapshot based on how active
 * the user is.
 * 
 * @author ubriela
 *
 */
public class WorkerIDGenerator {
	public int count = 0;
	public double mean = 0.5;
	public double st = 0.2;
	public static Hashtable<Integer, Double> workerActiveness = new Hashtable<>();
	RouletteWheelGenerator<Double> generator = null;

	public WorkerIDGenerator(Distribution1DEnum activenessDist, int count) {
		this.count = count;

		for (int workerId = 0; workerId < count; workerId++) {
			double activeness = -1;
			switch (activenessDist) {
			case UNIFORM_1D: // uniform one-dimensional dataset
				Random r = new Random(System.nanoTime());
				activeness = r.nextDouble();
				break;
			case ZIFFIAN_1D: // zipf distribution
				ZipfDistribution zipf = new ZipfDistribution(count, 1);
				int rand = (int) UniformGenerator.randomValue(new Range(0,
						count), true);
				activeness = zipf.probability(rand);
				break;
			case GAUSSIAN_1D:
				Random rd = new Random(System.nanoTime());
				
				while (activeness < 0 || activeness > 1)
					activeness = mean + st * rd.nextGaussian();
				break;
			}
			
			workerActiveness.put(workerId, activeness);
		}
		
		Double[] arr = new Double[workerActiveness.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = workerActiveness.get(i);
		generator = new RouletteWheelGenerator<Double>(arr);
	}

	public int nextWorkerId() {
		int idx = generator.nextValue();
		return idx;
	}
}