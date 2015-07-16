package org.geocrowd.datasets.dtype;

import org.moeaframework.core.Solution;


public class WeightedSolution implements Comparable<WeightedSolution> {

	double weight = 0;
	Solution sol = null;
			
	


	public WeightedSolution(double weight, Solution sol) {
		this.weight = weight;
		this.sol = sol;
	}


	@Override
	public int compareTo(WeightedSolution o) {
		if (this.weight < o.weight)
			return 1;
		else if (this.weight > o.weight)
			return -1;
		else 
			return 0;
	}
}
