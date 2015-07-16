package org.geocrowd.datasets.dtype;


/**
 * A generic two-dimensional data point. It allows sorting a list of points by x
 * and then y coordinates
 * 
 * @author HT186010
 * 
 * @param <Tx>
 * @param <Ty>
 */
public class GenericPoint<Tx, Ty> extends Comparator<Tx, Ty> implements
		Comparable<GenericPoint<Tx, Ty>> {
	protected Tx X;
	protected Ty Y;

	public GenericPoint(Tx x, Ty y) {
		super();
		X = x;
		Y = y;
	}

	public Tx getX() {
		return X;
	}

	public Ty getY() {
		return Y;
	}

	@Override
	public int compareTo(GenericPoint<Tx, Ty> point) {
		// TODO Auto-generated method stub
		if (compare(X, point.getX()) != 0)
			// descending order of x, then y
			return compare(point.getX(), X);
		else if (compare_y(Y, point.getY()) != 0)
			return compare_y(point.getY(), Y);
		else
			return 0;
	}

	/**
	 * Get standard point
	 * @return
	 */
	public Point point() {
		// TODO Auto-generated method stub
		return new Point((Double) X, (Double) Y);
	}

	public void debug() {
		System.out.println("select * from distinctint where Lat = " + X.toString() + " and Lon = " + Y.toString() + ";");
		
	}

}
