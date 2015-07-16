package org.geocrowd.datasets.dtype;


public interface TwoDimQuery<Tx, Ty> {
	double magicNumber = 4.5;
	
	/**
	 * Estimate the number of points equal to a value
	 * @param point
	 * @return
	 */
	public Cardinality equal(GenericPoint<Tx, Ty> point);
	
	/**
	 * Calculate exact number of points equal to a value
	 * @param point
	 * @return
	 */
	
	public int equalExact(GenericPoint<Tx, Ty> point);
	/**
	 * Estimate the number of points in a rectangle query
	 * 
	 * @param query
	 * @param isUsingArea
	 * @return
	 */
	public Cardinality rectangleQuery(GenericRectangle<Tx, Ty> query,
			boolean isUsingArea);

	/**
	 * Calculate exact number of points in a rectangle query
	 * 
	 * @param query
	 * @return
	 */
	public int rectangleQueryExact(GenericRectangle<Tx, Ty> query);
}
