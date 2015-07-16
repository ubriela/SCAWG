package org.geocrowd.datasets.synthetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.geocrowd.WTArrivalEnum;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.datasets.dtype.Range;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class WTCountGenerator {

	public static int time_instances_per_cycle = 7;
	public static int cosine_height_scale = 1;

	/**
	 * 
	 * @param instances : the number of time instances
	 * @param mean
	 * @param arrival_f : arrival distribution
	 * @return
	 */
	public static ArrayList<Integer> generateCounts(int instances, int mean,
			WTArrivalEnum arrival_f) {
		ArrayList<Integer> counts = new ArrayList<>();

		switch (arrival_f) {
		case CONSTANT:
			for (int i = 0; i < instances; i++)
				counts.add(mean);
			break;
		case INCREASING:
			for (int i = 1; i <= instances; i++)
				counts.add(2 * mean * i / instances);
			break;
		case DECREASING:
			for (int i = instances; i > 0; i--)
				counts.add(2 * mean * i / instances);
			break;
		case COSINE:
			for (int i = 1; i <= instances; i++) {
				int val = mean
						- (int) (mean / cosine_height_scale * Math
								.sin((i / (time_instances_per_cycle + 0.0)) * 2
										* Math.PI));
				counts.add(val);
			}
			break;
		case POISSON:
			PoissonDistribution pd = new PoissonDistribution(mean);
			for (int i = 1; i <= instances; i++)
				counts.add(pd.sample());
			break;
		case ZIPFIAN:
			for (int i = 1; i <= instances; i++) {
					counts.add((int)(mean * Utils.zipf_pmf(instances, i, 1)));
			}
			java.util.Collections.shuffle(counts);
			break;
		}
		return counts;
	}
}
