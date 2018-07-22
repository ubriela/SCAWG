package org.geocrowd.synthetic.grid;

import java.util.Iterator;
import java.util.Vector;

import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.PointIndex;
import org.geocrowd.dtype.Rectangle;
import org.geocrowd.dtype.RectangleIndex;

/**
 * Equi-partitioning histogram for two-dimension data
 * 
 * @author HT186010
 * 
 */
abstract class Grid2DMem extends Grid2D {
	protected GridCellMem[][] grid = null; // the "universe"
	protected int universeSize = 0; // total number of grid cells
	protected double gridPadX = 0.0001;
	protected double gridPadY = 0.0001;

	public Grid2DMem(Rectangle boundary, int dim_size_x, int dim_size_y) {
		super();
		this.max_x = boundary.getHighPoint().getX();
		this.max_y = boundary.getHighPoint().getY();
		this.min_x = boundary.getLowPoint().getX();
		this.min_y = boundary.getLowPoint().getY();
		this.dim_size_x = dim_size_x;
		this.dim_size_y = dim_size_y;
		this.totalArea = (max_x - min_x) * (max_y - min_y);
		this.universeSize = dim_size_x * dim_size_y;
		this.gridPadX *= (max_x - min_x) / dim_size_x;
		this.gridPadY *= (max_y - min_y) / dim_size_y;

		// initialize grid
		initialize();
	}

	
	
	public GridCellMem[][] getGrid() {
		return grid;
	}



	/**
	 * Initialize the grid as well as the partitions of coordinates
	 */
	protected void initialize() {
		grid = new GridCellMem[dim_size_x][dim_size_y];

		X = new double[dim_size_x + 1];
		Y = new double[dim_size_y + 1];
	}

	/**
	 * Count the number of each elements in each cell (historgram)
	 * 
	 * @return
	 */
	public int[][] histCount() {
		int[][] stats = new int[dim_size_x][dim_size_y];
		for (int i = 0; i < dim_size_x; i++)
			for (int j = 0; j < dim_size_y; j++)
				if (grid[i][j] != null)
					stats[i][j] = grid[i][j].size();
				else
					stats[i][j] = 0;
		return stats;
	}

	/**
	 * stats count in a rectangle index
	 * 
	 * @param recIndex
	 * @return
	 */
	public int statRectangleIndex(RectangleIndex recIndex) {
		int total = 0;
		if (0 <= recIndex.getX1() && recIndex.getX1() <= recIndex.getX2()
				&& recIndex.getX2() < dim_size_x && 0 <= recIndex.getY1()
				&& recIndex.getY1() <= recIndex.getY2()
				&& recIndex.getY2() < dim_size_y) {
			for (int i = recIndex.getX1(); i <= recIndex.getX2(); i++)
				for (int j = recIndex.getY1(); j <= recIndex.getY2(); j++)
					if (grid[i][j] != null)
						total += grid[i][j].size();

		} else {
			System.out.println("Invalid index: " + recIndex.getX1() + ":"
					+ recIndex.getX2() + "," + recIndex.getY1() + ":"
					+ recIndex.getY2());
			return 0;
		}
		return total;
	}

	/**
	 * count the exact number of points in the intersected area of a rectangle
	 * and a grid cell
	 * 
	 * @param rec
	 * @param index_x
	 * @param index_y
	 * @return
	 */
	public int statPartialRectangle(Rectangle rec, int index_x, int index_y) {
		int count = 0;
		// System.out.println(index_x + " " + index_y);
		GridCellMem cell = grid[index_x][index_y];
		if (cell == null)
			return 0;

		// if the rectangle cover the grid cell --> return the size of the grid
		// cell
		if (rec.isCover(cell))
			return cell.size();

		Iterator<Point> it = cell.getPoints().iterator();
		while (it.hasNext()) {
			Point point = (Point) it.next();
			if (rec.isCover(point))
				count++;
		}
		return count;
	}

	/**
	 * count the exact number of points in the intersected area of a rectangle
	 * and a grid
	 * 
	 * @param rec
	 * @param index_x1
	 * @param index_y1
	 * @param index_x2
	 * @param index_y2
	 * @return
	 */
	public int statPartialGridCount(Rectangle rec, int index_x1, int index_y1,
			int index_x2, int index_y2) {
		int count = 0;
		for (int i = index_x1; i <= index_x2; i++)
			for (int j = index_y1; j <= index_y2; j++)
				count += statPartialRectangle(rec, i, j);
		return count;
	}

	/**
	 * Convert a grid of cells to a list of cells
	 * 
	 * @return
	 */
	public Vector<Integer> statListCount() {
		Vector<Integer> list = new Vector<Integer>();
		for (int i = 0; i < dim_size_x; i++)
			for (int j = 0; j < dim_size_y; j++) {
				if (grid[i][j] != null)
					list.add(Integer.valueOf(grid[i][j].size()));
				else
					list.add(Integer.valueOf(0));
			}
		return list;
	}

	public void merge() {
	};

	/**
	 * Rounding up to upper bound the number of points in a rectangle query
	 * 
	 * @param rec
	 * @return
	 */
	public int rectangleQueryRoundUp(Rectangle rec) {
		// TODO Auto-generated method stub
		RectangleIndex recIndex = getUpperBoundIndex(rec);
		if (0 <= recIndex.getX1() && recIndex.getX1() <= recIndex.getX2()
				&& recIndex.getX2() < dim_size_x && 0 <= recIndex.getY1()
				&& recIndex.getY1() <= recIndex.getY2()
				&& recIndex.getY2() < dim_size_y)
			return statRectangleIndex(recIndex);
		else
			return 0;
	}

	/**
	 * Rounding to lower bound the number of points in a rectangle query
	 * 
	 * @param rec
	 * @return
	 */
	public int rectangleQueryRoundOff(Rectangle rec) {
		// TODO Auto-generated method stub
		RectangleIndex recIndex = getLowerBoundIndex(rec);
		if (0 <= recIndex.getX1() && recIndex.getX1() <= recIndex.getX2()
				&& recIndex.getX2() < dim_size_x && 0 <= recIndex.getY1()
				&& recIndex.getY1() <= recIndex.getY2()
				&& recIndex.getY2() < dim_size_y)
			return statRectangleIndex(recIndex);
		else
			return 0;
	}

	/**
	 * Calculate exact number of points in a rectangle query
	 * 
	 * @param rec
	 * @return
	 */
	public int rectangleQueryExact(Rectangle rec) {
		RectangleIndex recIndex = getUpperBoundIndex(rec);
		// recIndex.debug();
		return statPartialGridCount(rec, recIndex.getX1(), recIndex.getY1(),
				recIndex.getX2(), recIndex.getY2());
	}

	/**
	 * Estimate the number of points in a rectangle query, assuming uniform
	 * distributed over the whole universe. The total estimation equals to the
	 * summation of points in both fully-covered and the partial-covered grid
	 * cells. This type of estimation are less accurate than the
	 * rectangleQueryAreaEstimate
	 * 
	 * @param rec
	 * @return
	 */
	public int rectangleQueryEstimate(Rectangle rec) {
		// TODO Auto-generated method stub

		RectangleIndex recIndex = getLowerBoundIndex(rec);
		int fullGridsCount = 0;
		int querySize = 0;
		// if there exists fully-covered grid cells (or lower bound rectangle)
		if (0 <= recIndex.getX1() && recIndex.getX1() <= recIndex.getX2()
				&& recIndex.getX2() < dim_size_x && 0 <= recIndex.getY1()
				&& recIndex.getY1() <= recIndex.getY2()
				&& recIndex.getY2() < dim_size_y) {
			fullGridsCount = statRectangleIndex(recIndex);
			querySize = (recIndex.getX2() - recIndex.getX1() + 1)
					* (recIndex.getY2() - recIndex.getY1() + 1);
		}

		double partialArea = rec.area() - totalArea * (double) querySize
				/ universeSize;
		// debug
		debugPartialArea(partialArea, rec, recIndex, querySize);

		int partialGridsCount = statPerArea(partialArea);

		return fullGridsCount + partialGridsCount;
	}

	/**
	 * Estimate the number of points in a rectangle query, assuming uniform
	 * distributed over each grid cell. The total estimation equals to the
	 * summation of points in both fully-covered and the partial-covered grid
	 * cells
	 * 
	 * @param rec
	 * @return
	 */
	public int rectangleQueryAreaEstimate(Rectangle rec) {
		// TODO Auto-generated method stub
		// counting the number of points in the grid
		// cells that fully covered by the query
		int fullGridsCount = 0;
		// counting the number of points in the
		// grid cells that partially covered by
		// the query
		double partialGridsCount = 0;
		RectangleIndex recLower = getLowerBoundIndex(rec);
		RectangleIndex recUpper = getUpperBoundIndex(rec);

		/**
		 * a list of grid cells that will be checked
		 */
		Vector<PointIndex> indices = null;//new Vector<PointIndex>();

		// if there exists fully-covered grid cells (or lower bound rectangle)
		if (0 <= recLower.getX1() && recLower.getX1() <= recLower.getX2()
				&& recLower.getX2() < dim_size_x && 0 <= recLower.getY1()
				&& recLower.getY1() <= recLower.getY2()
				&& recLower.getY2() < dim_size_y) {
			fullGridsCount = statRectangleIndex(recLower);
			// indices of the grid cells that are partially covered by the query
			indices = recUpper.subtractIndex(recLower);

		} else {
			// check all grid cells in the upper bound rectangle
			indices = recUpper.getIndices();
		}

		Iterator<PointIndex> it = indices.iterator();
		while (it.hasNext()) {
			PointIndex index = (PointIndex) it.next();
			if (grid[index.getX()][index.getY()] != null) {
				GridCellMem cell = grid[index.getX()][index.getY()];
				// if the query fully covers the current grid cell
				if (rec.isCover(cell)) {
					partialGridsCount += cell.size();
				} else {
					// calculate partial area and then partial count
					double partialArea = rec.intersectArea(cell);
					// debug
					debugPartialArea(partialArea, rec, recLower, recUpper, cell);

					if (partialArea > 0)
						partialGridsCount += statPerArea(partialArea,
								index.getX(), index.getY());
				}

			}
		}

		return fullGridsCount + (int) Math.ceil(partialGridsCount);
	}

	private void debugPartialArea(double partialArea, Rectangle rec,
			RectangleIndex recLower, RectangleIndex recUpper, GridCellMem cell) {
		// TODO Auto-generated method stub
		if (partialArea < 0) {
			System.out.println("---------------" + partialArea);
			rec.debug();
			recLower.debug();
			recUpper.debug();
			cell.debug();
		}
	}

	private void debugPartialArea(double partialArea, Rectangle rec,
			RectangleIndex recIndex, int querySize) {
		// TODO Auto-generated method stub
		if (partialArea < 0) {
			System.out.println("--------------- area < 0");
			rec.debug();
			recIndex.debug();
			System.out.println(rec.area() + "/" + totalArea
					* (double) querySize / universeSize);
			System.out.println(querySize + "/" + universeSize);
			System.out.println(totalArea);

		}
	}

	/**
	 * estimate number of points in an area
	 * 
	 * @param partialArea
	 * @return
	 */
	public int statPerArea(double partialArea) {
		return (int) Math.ceil(pointsPerArea * partialArea);
	}

	/**
	 * estimate number of points in an area within a grid cell
	 * 
	 * @param area
	 * @return
	 */
	public double statPerArea(double partialArea, int x, int y) {
		return grid[x][y].size() * partialArea / grid[x][y].area();
	}

	/**
	 * Get upper bounded grid of a random rectangle
	 * 
	 * @param rec
	 * @return
	 */
	public abstract RectangleIndex getUpperBoundIndex(Rectangle rec);

	/**
	 * Get lower bounded grid of a random rectangle
	 * 
	 * @param rec
	 * @return
	 */
	public abstract RectangleIndex getLowerBoundIndex(Rectangle rec);
}
