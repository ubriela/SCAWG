package org.geocrowd.synthetic;

import static java.util.Collections.shuffle;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.geocrowd.common.distribution.TemporalDistributionEnum;
import org.geocrowd.common.utils.Utils;

/**
 * Generate time arrivals following a particular temporal distribution.
 */
public class ArrivalRateGenerator {

	public static int time_instances_per_cycle = 7;
	public static int cosine_height_scale = 1;

	/**
	 * 
	 * @param numberOfInstances : the number of time instances
	 * @param meanTimeArrival: average arrival time
	 * @param arrivalDistribution : arrival distribution
	 * @return
	 */
	public static ArrayList<Integer> generateTimeArrivals(int numberOfInstances,
														  int meanTimeArrival,
														  TemporalDistributionEnum arrivalDistribution) {
		ArrayList<Integer> counts = new ArrayList<>();

		switch (arrivalDistribution) {
		case CONSTANT:
			for (int i = 0; i < numberOfInstances; i++)
				counts.add(meanTimeArrival);
			break;
		case LINEARLY_INCREASING:
			for (int i = 1; i <= numberOfInstances; i++)
				counts.add(2 * meanTimeArrival * i / numberOfInstances);
			break;
		case LINEARLY_DECREASING:
			for (int i = numberOfInstances; i > 0; i--)
				counts.add(2 * meanTimeArrival * i / numberOfInstances);
			break;
		case COSINE:
			for (int i = 1; i <= numberOfInstances; i++) {
				int val = meanTimeArrival -
					(int) (meanTimeArrival / cosine_height_scale * Math.sin((i / (time_instances_per_cycle + 0.0)) * 2 * Math.PI));
				counts.add(val);
			}
			break;
		case POISSON:
			PoissonDistribution pd = new PoissonDistribution(meanTimeArrival);
			for (int i = 1; i <= numberOfInstances; i++)
				counts.add(pd.sample());
			break;
		case ZIPFIAN:
			for (int i = 1; i <= numberOfInstances; i++) {
					counts.add((int)(meanTimeArrival * Utils.zipf_pmf(numberOfInstances, i, 1)));
			}
			shuffle(counts);
			break;
		}
		return counts;
	}
}
