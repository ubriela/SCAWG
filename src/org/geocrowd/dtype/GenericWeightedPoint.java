package org.geocrowd.dtype;

/**
 * This class is used for weighted point, or it can be used for a situation for
 * multiple overlapped points
 * 
 * @author ubriela
 * 
 * @param <Tx>
 * @param <Ty>
 */
public class GenericWeightedPoint<Tx, Ty> extends GenericPoint<Tx, Ty> {
	double weight = 0.0;

	public GenericWeightedPoint(Tx x, Ty y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public GenericWeightedPoint(Tx x, Ty y, double weight) {
		super(x, y);
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Get weighted point
	 * 
	 * @return
	 */
	public WeightedPoint weightedPoint() {
		// TODO Auto-generated method stub
		return new WeightedPoint((Double) X, (Double) Y, weight);
	}

}
