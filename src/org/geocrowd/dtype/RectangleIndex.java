package org.geocrowd.dtype;

import java.util.Vector;

/**
 * Mark the positions of a range query in a grid
 * 
 * @author HT186010
 * 
 */
public class RectangleIndex {
	int x1, y1, x2, y2;

	public RectangleIndex(int x1, int y1, int x2, int y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Get a list of grid cell's indices which are not in a small rectangle
	 * 
	 * @param smallRec
	 * @return
	 */
	public Vector<PointIndex> subtractIndex(RectangleIndex smallRec) {
		Vector<PointIndex> indices = new Vector<PointIndex>();
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++) {
				if (smallRec.getX1() <= i && i <= smallRec.getX2()
						&& smallRec.getY1() <= j && j <= smallRec.getY2())
					continue;
				PointIndex pointIndex = new PointIndex(i, j);
				indices.add(pointIndex);
			}
		return indices;
	}

	public Vector<PointIndex> getIndices() {
		Vector<PointIndex> indices = new Vector<PointIndex>();
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++) {
				PointIndex pointIndex = new PointIndex(i, j);
				indices.add(pointIndex);
			}
		return indices;
	}

	public String toString() {
		return "(" + x1 + "," + y1 + ")->(" + x2 + "," + y2 + ")";
	}

	public void debug() {
		System.out.println(x1 + ":" + y1 + ":" + x2 + ":" + y2);
	}

	public boolean isValid() {
		if (0 <= x1 && x1 <= x2 && 0 <= y1 && y1 <= y2)
			return true;
		return false;
	}
}
