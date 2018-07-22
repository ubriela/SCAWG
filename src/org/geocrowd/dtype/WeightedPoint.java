package org.geocrowd.dtype;

public class WeightedPoint extends Point {
	double weight = 0.0;
	public WeightedPoint(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	public WeightedPoint(double x, double y, double weight) {
		super(x, y);
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

}
