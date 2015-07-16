package org.geocrowd.datasets.synthetic;

import java.util.Arrays;
import java.util.Random;

import org.geocrowd.common.utils.Stats;
import org.geocrowd.common.utils.Utils;

/**
 * We have a list of value N1, N2, ...Nn. Each represent the count Pi is the
 * probability that Ni appears. Sum(Pi) = 1 This generator will generate a
 * random integer i bwn 1->N such that i will appear with probability Pi
 * 
 * @author HT186010
 * 
 */
public class ProbabilityGenerator<T> extends Stats<T>{
	private double[] S = null;
	private double sum;
	private double min = 0.0;

	public ProbabilityGenerator(T[] N) {

		// normalized N
		sum = sum(Arrays.asList(N));
		double tmp = 0.0;
		min = (Double) N[0];
		S = new double[N.length + 1];
		S[0] = 0;
		for (int i = 0; i < N.length; i++) {
			tmp += (Double)N[i];
			S[i + 1] = tmp;
		}
	}

	public int nextValue() {
		// Generate a random number bwn 0 and sum
		Random r = new Random();
		r.setSeed(System.nanoTime());
		double ran = r.nextDouble() * sum;
		
		//	return the desired bucket
		return Utils.binarySearchFloor(S, ran);
	}
}