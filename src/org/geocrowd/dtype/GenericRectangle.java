package org.geocrowd.dtype;

/**
 * A query type used in H-tree
 * 
 * @author HT186010
 * 
 * @param <Tx>
 * @param <Ty>
 */
public class GenericRectangle<Tx, Ty> extends Comparator<Tx, Ty> {
	private GenericPoint<Tx, Ty> lowPoint;
	private GenericPoint<Tx, Ty> highPoint;

	public GenericRectangle() {
		super();
	}

	public GenericRectangle(Tx x1, Ty y1, Tx x2, Ty y2) {
		super();
		this.lowPoint = new GenericPoint<Tx, Ty>(x1, y1);
		this.highPoint = new GenericPoint<Tx, Ty>(x2, y2);
	}

	public GenericRectangle(GenericPoint<Tx, Ty> lowPoint,
			GenericPoint<Tx, Ty> highPoint) {
		super();
		this.lowPoint = lowPoint;
		this.highPoint = highPoint;
	}

	public GenericPoint<Tx, Ty> getLowPoint() {
		return lowPoint;
	}

	public GenericPoint<Tx, Ty> getHighPoint() {
		return highPoint;
	}

	public void setLowPoint(GenericPoint<Tx, Ty> lowPoint) {
		this.lowPoint = lowPoint;
	}

	public void setHighPoint(GenericPoint<Tx, Ty> highPoint) {
		this.highPoint = highPoint;
	}

	public Tx midPointX(double percent) {
		return (Tx) new Double(percent * deltaX() + (Double) lowPoint.getX());
	}

	public Ty midPointY(double percent) {
		return (Ty) new Double(percent * deltaY() + (Double) lowPoint.getY());
	}

	/**
	 * Does this rectangle cover the other rec
	 * 
	 * @param rec
	 * @return
	 */
	public boolean isCover(GenericRectangle<Tx, Ty> rec) {
		if (compare(lowPoint.getX(), rec.lowPoint.getX()) != 1
				&& compare_y(lowPoint.getY(), rec.lowPoint.getY()) != 1
				&& compare(highPoint.getX(), rec.highPoint.getX()) != -1
				&& compare_y(highPoint.getY(), rec.highPoint.getY()) != -1)
			return true;
		return false;
	}

	/**
	 * Does this rectangle include the other rec inside
	 * 
	 * @param rec
	 * @return
	 */
	public boolean isInclude(GenericRectangle<Tx, Ty> rec) {
		if (compare(lowPoint.getX(), rec.lowPoint.getX()) == -1
				&& compare_y(lowPoint.getY(), rec.lowPoint.getY()) == -1
				&& compare(highPoint.getX(), rec.highPoint.getX()) == 1
				&& compare_y(highPoint.getY(), rec.highPoint.getY()) == 1)
			return true;
		return false;
	}

	/**
	 * Does this rectangle cover a point
	 * 
	 * @param rec
	 * @return
	 */
	public boolean isCover(GenericPoint<Tx, Ty> point) {
		if (compare(lowPoint.getX(), point.getX()) != 1
				&& compare_y(lowPoint.getY(), point.getY()) != 1
				&& compare(highPoint.getX(), point.getX()) != -1
				&& compare_y(highPoint.getY(), point.getY()) != -1)
			return true;
		return false;
	}

	/**
	 * Does this rectangle include a point inside
	 * 
	 * @param rec
	 * @return
	 */
	public boolean isInclude(GenericPoint<Tx, Ty> point) {
		if (compare(lowPoint.getX(), point.getX()) == -1
				&& compare_y(lowPoint.getY(), point.getY()) == -1
				&& compare(highPoint.getX(), point.getX()) == 1
				&& compare_y(highPoint.getY(), point.getY()) == 1)
			return true;
		return false;
	}

	/**
	 * Does this rectangle contain this point
	 * 
	 * @param point
	 * @return
	 */
	public boolean isContain(GenericPoint<Tx, Ty> point) {
		if (compare(lowPoint.getX(), point.getX()) != 1
				&& compare_y(lowPoint.getY(), point.getY()) != 1
				&& compare(highPoint.getX(), point.getX()) == 1
				&& compare_y(highPoint.getY(), point.getY()) == 1)
			return true;
		return false;
	}

	public double area() {
		return (deltaX() * deltaY());
	}

	public double deltaX() {
		return (double) (Double) highPoint.getX()
				- (double) (Double) lowPoint.getX();
	}

	public double deltaY() {
		return (double) (Double) highPoint.getY()
				- (double) (Double) lowPoint.getY();
	}

	/**
	 * Calculate intersected area bwn two rectangles
	 * 
	 * @param rec
	 * @return
	 */
	public double intersectArea(GenericRectangle<Tx, Ty> rec) {
		// need to check this because there is some time a problem with two
		if (area() == 0 || rec.area() == 0 || !isIntersect(rec))
			return 0.0;
		Tx min_x = min(lowPoint.getX(), rec.getLowPoint().getX());
		Ty min_y = min_y(lowPoint.getY(), rec.getLowPoint().getY());
		Tx max_x = max(highPoint.getX(), rec.getHighPoint().getX());
		Ty max_y = max_y(highPoint.getY(), rec.getHighPoint().getY());
		double deltaMaxX = (double) (Double) max_x - (double) (Double) min_x;
		double deltaMaxY = (double) (Double) max_y - (double) (Double) min_y;

		return (area() + rec.area() + (deltaMaxX - deltaX())
				* (deltaMaxY - rec.deltaY()) + (deltaMaxX - rec.deltaX())
				* (deltaMaxY - deltaY()) - deltaMaxX * deltaMaxY);
	}

	/**
	 * If this rectangle intersects rec --> return true
	 * 
	 * @param rec
	 * @return
	 */
	public boolean isIntersect(GenericRectangle<Tx, Ty> rec) {
		Tx min_x = min(lowPoint.getX(), rec.getLowPoint().getX());
		Ty min_y = min_y(lowPoint.getY(), rec.getLowPoint().getY());
		Tx max_x = max(highPoint.getX(), rec.getHighPoint().getX());
		Ty max_y = max_y(highPoint.getY(), rec.getHighPoint().getY());

		if (((double) (Double) max_x - (double) (Double) min_x < deltaX()
				+ rec.deltaX())
				&& ((double) (Double) max_y - (double) (Double) min_y < deltaY()
						+ rec.deltaY()))
			return true; // intersect
		return false;
	}

	public void debug() {
		System.out.println("(" + lowPoint.getX() + "," + lowPoint.getY()
				+ ")->(" + highPoint.getX() + "," + highPoint.getY() + ")");
	}

	/**
	 * Get standard rectangle
	 * @return
	 */
	public Rectangle rectangle() {
		// TODO Auto-generated method stub
		return new Rectangle(lowPoint.point(), highPoint.point());
	}
}
