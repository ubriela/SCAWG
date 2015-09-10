package org.geocrowd.datasets.synthetic.grid;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Rectangle;

/**
 * Memory-based grid cell of a two-dimensional grid
 * 
 * @author HT186010
 * 
 */
public class GridCellMem extends Rectangle implements Serializable {

	private LinkedList<Point> points = null;

	public GridCellMem(Point lowPoint, Point highPoint) {
		super(lowPoint, highPoint);
 		points = new LinkedList<Point>();
 	}

	public LinkedList<Point> getPoints() {
		return points;
	}
	
	

	public void setPoints(LinkedList<Point> points) {
		this.points = points;
	}
	
	public void setPoints(List<Point> points) {
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			this.points.add(it.next());
		}
	}

	public int size() {
		return points.size();
	}

	public void add(Point point) {
		points.add(point);
	}

	/**
	 * Sort the points by x or y dimension
	 * 
	 * @param dim
	 *            = 1 --> x dim = 2 --> y
	 */
	public void sort(final int dim) {
		Collections.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				switch (dim) {
				case 1:
					if (p1.getX() < p2.getX()) {
						return -1;
					} else if (p1.getX() == p2.getX()) {
						return 0;
					} else
						return 1;
				case 2:
					if (p1.getY() < p2.getY()) {
						return -1;
					} else if (p1.getY() == p2.getY()) {
						return 0;
					} else
						return 1;
				}
				return 1;
			}
		});
	}
}