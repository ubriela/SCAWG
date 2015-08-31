package org.geocrowd.dtype;

/**
 * This class store the result of estimator
 * 
 * @author ht186010
 * 
 */
public class Cardinality {
	private double selectivity; // selectivity factor
	private double confidence; // out of 100

	public Cardinality(double selectivity, double confidence) {
		super();
		this.selectivity = selectivity;
		this.confidence = confidence;
	}

	public double getSelectivity() {
		return selectivity;
	}

	public double getConfidence() {
		return confidence;
	}

	public void debug() {
		System.out.println(selectivity + " " + confidence);
	}
}
