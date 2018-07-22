package org.geocrowd.synthetic.grid;

import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Rectangle;
import org.geocrowd.dtype.RectangleIndex;


/**
 * Equi-sized or equi-width histogram for two-dimension data
 * 
 * @author HT186010
 * 
 */
public class EquiSizedGrid extends Grid2DMem {
	double scale_x = 0;
	double scale_y = 0;

	public EquiSizedGrid(Rectangle boundary, int dim_size_x, int dim_size_y) {
		super(boundary, dim_size_x, dim_size_y);
		// TODO Auto-generated constructor stub

		scale_x = (max_x - min_x) / dim_size_x;
		scale_y = (max_y - min_y) / dim_size_y;

		this.split();
	}

	/**
	 * Split coordinates with equal size in each dimensions
	 */
	@Override
	public void split() {
		// TODO Auto-generated method stub
		for (int i = 0; i <= dim_size_x; i++) {
			X[i] = min_x + i * scale_x;
		}

		for (int i = 0; i <= dim_size_y; i++) {
			Y[i] = min_y + i * scale_y;
		}
	}

	/**
	 * 2 I/Os access
	 */
	@Override
	public void put(Point point) {
		// TODO Auto-generated method stub
		int index_x = 0;
		int index_y = 0;

		double x = (point.getX() - min_x) / scale_x;
		double y = (point.getY() - min_y) / scale_y;
		index_x = (int) Math.floor(x);
		index_y = (int) Math.floor(y);

		if (index_x >= dim_size_x)
			index_x = dim_size_x - 1;
		if (index_y >= dim_size_y)
			index_y = dim_size_y - 1;

		if (grid[index_x][index_y] == null) {

			double x1 = index_x * scale_x + min_x;
			double x2 = (index_x + 1) * scale_x + min_x;
			double y1 = index_y * scale_y + min_y;
			double y2 = (index_y + 1) * scale_y + min_y;
			grid[index_x][index_y] = new GridCellMem(new Point(x1, y1),
					new Point(x2, y2));
		}

		grid[index_x][index_y].add(point);
	}

	/**
	 * Get the gridcell this method is not in use yet!
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public GridCellMem get(double x, double y) {

		if (x >= min_x && x <= max_x && y >= min_y && y <= max_y) {
			int index_x;
			int index_y;
			index_x = (int) Math.floor((x - min_x) / dim_size_x);
			index_y = (int) Math.floor((y - min_y) / dim_size_y);
			return grid[index_x][index_y];

		} else
			return null;
	}

	public double getScale_x() {
		return scale_x;
	}

	public double getScale_y() {
		return scale_y;
	}

	public double gridCellArea() {
		return scale_x * scale_y;
	}

	/**
	 * Get rectangle index from a rectangle using rounding
	 * @param rec
	 * @return
	 */
	public RectangleIndex getRectangleIndex(Rectangle rec) {
		// TODO Auto-generated method stub
		int index_x1 = (int) Math.round((rec.getLowPoint().getX() - min_x)
				/ scale_x);
		int index_y1 = (int) Math.round((rec.getLowPoint().getY() - min_y)
				/ scale_y);

		int index_x2 = (int) Math.round((rec.getHighPoint().getX() - min_x)
				/ scale_x);
		int index_y2 = (int) Math.round((rec.getHighPoint().getY() - min_y)
				/ scale_y);
		
		if (index_x2 >= dim_size_x)
			index_x2--;
		if (index_y2 >= dim_size_y)
			index_y2--;

		return new RectangleIndex(index_x1, index_y1, index_x2, index_y2);
	}

	@Override
	public RectangleIndex getUpperBoundIndex(Rectangle rec) {
		// TODO Auto-generated method stub
		int index_x1 = (int) Math.ceil((rec.getLowPoint().getX() - min_x)
				/ scale_x) - 1;
		int index_y1 = (int) Math.ceil((rec.getLowPoint().getY() - min_y)
				/ scale_y) - 1;

		int index_x2 = (int) Math.floor((rec.getHighPoint().getX() - min_x)
				/ scale_x);
		int index_y2 = (int) Math.floor((rec.getHighPoint().getY() - min_y)
				/ scale_y);

		// this case sometimes happends
		if (index_x1 == -1)
			index_x1 = 0;
		if (index_y1 == -1)
			index_y1 = 0;
		if (index_x2 >= dim_size_x)
			index_x2--;
		if (index_y2 >= dim_size_y)
			index_y2--;

		return new RectangleIndex(index_x1, index_y1, index_x2, index_y2);
	}

	@Override
	public RectangleIndex getLowerBoundIndex(Rectangle rec) {
		// TODO Auto-generated method stub
		int index_x1 = (int) Math.ceil((rec.getLowPoint().getX() - min_x)
				/ scale_x);
		int index_y1 = (int) Math.ceil((rec.getLowPoint().getY() - min_y)
				/ scale_y);
		int index_x2 = (int) Math.floor((rec.getHighPoint().getX() - min_x)
				/ scale_x);
		if (index_x2 >= 1)
			index_x2--;
		int index_y2 = (int) Math.floor((rec.getHighPoint().getY() - min_y)
				/ scale_y);
		if (index_y2 >= 1)
			index_y2--;

		return new RectangleIndex(index_x1, index_y1, index_x2, index_y2);
	}
}
