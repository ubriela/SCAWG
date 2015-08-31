/*******************************************************************************
* @ Year 2013
* This is the source code of the following papers. 
* 
* 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla Kazemi, Cyrus Shahabi.
* 
* 
* Please contact the author Hien To, ubriela@gmail.com if you have any question.
*
* Contributors:
* Hien To - initial implementation
*******************************************************************************/
package org.geocrowd.datasets.synthetic;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Range;
import org.geocrowd.dtype.Rectangle;

// TODO: Auto-generated Javadoc
/**
 * Provide various of methods for generating uniform datasets as well as
 * queries.
 * 
 * @author HT186010
 */
public class UniformGenerator {

	/**
	 * Generate a list of random distinct values.
	 * 
	 * @param n
	 *            the n
	 * @param boundary
	 *            the boundary
	 * @param isInteger
	 *            the is integer
	 * @return the hash set
	 */
	public static HashSet<Double> randomDistinctValues(int n, Range boundary, boolean isInteger) {
		HashSet<Double> values = new HashSet<Double>();
		while (values.size() < n) {
			values.add(randomValue(boundary, isInteger));
		}
		return values;
	}

	/**
	 * Generate a one-dimensional random range query.
	 * 
	 * @param number
	 *            the number
	 * @param offset
	 *            the offset
	 * @param isFixOffset
	 *            the is fix offset
	 * @param values
	 *            the values
	 * @param boundary
	 *            the boundary
	 * @return the vector
	 */
	public static Vector<Range> randomRangesWithOffsets(int number,
			double offset, boolean isFixOffset, List<Double> values,
			Range boundary) {
		Vector<Range> ranges = new Vector<Range>();
		int size = values.size();
		Random generator = new Random();
		generator.setSeed(System.nanoTime());
		double _offset = 0.0;
		if (isFixOffset) {
			_offset = offset;
		} else {
			
			_offset = generator.nextDouble() * offset;
		}
		for (int i = 0; i < number; i++) {
			int r = generator.nextInt(size);
			double start, end;
			start = Math.max(boundary.getStart(), values.get(r) - _offset);
			end = Math.min(boundary.getEnd(), values.get(r) + _offset);
			Range range = new Range(start, end);
			ranges.add(range);
		}
		return ranges;
	}

	/**
	 * generate random rectangles such that their the lower-left points and
	 * high-right points are from the data points.
	 * 
	 * @param number
	 *            the number
	 * @param points
	 *            the points
	 * @return the vector
	 */
	public static Vector<Rectangle> randomRectanglesWithinDataPoints(
			int number, Vector<Point> points) {
		Vector<Rectangle> recs = new Vector<Rectangle>();
		int size = points.size();
		Random generator = new Random();
		generator.setSeed(System.nanoTime());
		for (int i = 0; i < number; i++) {
			
			int index_1 = generator.nextInt(size);
			int index_2 = generator.nextInt(size);
			double x1, y1, x2, y2;
			x1 = points.get(index_1).getX();
			y1 = points.get(index_1).getY();
			x2 = points.get(index_2).getX();
			y2 = points.get(index_2).getY();
			Rectangle rec = null;
			if (x1 < x2 && y1 < y2)
				rec = new Rectangle(x1, y1, x2, y2);
			else if (x2 < x1 && y2 < y1)
				rec = new Rectangle(x2, y2, x1, y1);
			else {
				i--;
				continue;
			}
			recs.add(rec);
		}
		return recs;
	}

	/**
	 * Generate a random list of values.
	 * 
	 * @param n
	 *            the n
	 * @param min_x
	 *            the min_x
	 * @param max_x
	 *            the max_x
	 * @param isInteger
	 *            the is integer
	 * @return the vector
	 */
	public static Vector<Double> randomSequence(int n, double min_x,
			double max_x, boolean isInteger) {
		Vector<Double> result = new Vector<Double>();
		Random r = new Random();
		r.setSeed(System.nanoTime());
		for (int i = 0; i < n; i++) {
			if (isInteger)
				result.add(Math.floor(r.nextDouble() * (max_x - min_x) + min_x));
			else
				result.add(r.nextDouble() * (max_x - min_x) + min_x);
		}
		return result;
	}

	/**
	 * Generate a random value between min, max.
	 * 
	 * @param boundary
	 *            the boundary
	 * @param isInteger
	 *            the is integer
	 * @return the double
	 */
	public static double randomValue(Range boundary, boolean isInteger) {
		Random r = new Random();
		r.setSeed(System.nanoTime());
		if (isInteger)
			return (Math.round(r.nextDouble() * boundary.delta()
					+ boundary.getStart()));
		else
			return (r.nextDouble() * boundary.delta() + boundary.getStart());
	}
	
	/**
	 * Random number depends on seed
	 * @param seed
	 * @return
	 */
	public static double randomGenerator(long seed) {
	    Random generator = new Random(seed);
	    return generator.nextDouble();
	}
	
	/**
	 * Generate a list of random value in a list, the values can be overlapped.
	 * 
	 * @param test_size
	 *            the test_size
	 * @param values
	 *            the values
	 * @return the vector
	 */
	public static Vector<Double> randomValues(int test_size, List<Double> values) {
		// TODO Auto-generated method stub
		Random r = new Random();
		r.setSeed(System.nanoTime());
		Vector<Double> list = new Vector<Double>();
		for (int i = 0; i < test_size; i++)
			list.add(values.get(r.nextInt(values.size())));
		return list;
	}
	
	
	
	
	/**
	 * Generate a random point within the universe
	 * 
	 * @param boundary
	 * @return
	 */
	public static Point randomPoint(Rectangle boundary, boolean isInteger) {
		Random r = new Random();
		r.setSeed(System.nanoTime());
		double x1 = r.nextDouble() * boundary.deltaX() + boundary.getLowPoint().getX();
		double y1 = r.nextDouble() * boundary.deltaY() + boundary.getLowPoint().getY();

		if (isInteger)
			return new Point(Math.round(x1), Math.round(y1));
		else
			return new Point(x1, y1);
	}
	
	/**
	 * Generate a random point within the universe
	 * 
	 * @param boundary
	 * @return
	 */
	public static Point randomPoint(Rectangle boundary, boolean isInteger, long seed) {
		Random r = new Random();
		r.setSeed(seed);
		double x1 = r.nextDouble() * boundary.deltaX() + boundary.getLowPoint().getX();
		double y1 = r.nextDouble() * boundary.deltaY() + boundary.getLowPoint().getY();

		if (isInteger)
			return new Point(Math.round(x1), Math.round(y1));
		else
			return new Point(x1, y1);
	}
	

	/**
	 * Generate a random rectangle within the universe
	 * 
	 * @param boundary
	 * @return
	 */
	public static Rectangle randomRectangle(Rectangle boundary) {
		// Random range query (rectangle)
		double x1, y1, x2, y2;
		Random r = new Random();
		r.setSeed(System.nanoTime());
		while (true) {
			x1 = r.nextDouble() * boundary.deltaX()
					+ boundary.getLowPoint().getX();
			x2 = r.nextDouble() * (boundary.getHighPoint().getX() - x1) + x1;
			y1 = r.nextDouble() * boundary.deltaY()
					+ boundary.getLowPoint().getY();
			y2 = r.nextDouble() * (boundary.getHighPoint().getY() - y1) + y1;

			if (x2 > x1 && y2 > y1)
				break;
		}

		return new Rectangle(x1, y1, x2, y2);
	}

	/**
	 * Generate a random rectangle within the universe that satisfies a
	 * threshold (each dimension >= threshold * dimension size)
	 * 
	 * @param boundary
	 * @param threshold
	 * @return
	 */
	public static Rectangle randomRectangle(Rectangle boundary, double threshold) {
		// Random range query (rectangle)
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		Random r = new Random();
		r.setSeed(System.nanoTime());
		while (true) {
			x1 = r.nextDouble() * boundary.deltaX()
					+ boundary.getLowPoint().getX();
			x2 = r.nextDouble() * (boundary.getHighPoint().getX() - x1) + x1;
			y1 = r.nextDouble() * (boundary.deltaY())
					+ boundary.getLowPoint().getY();
			y2 = r.nextDouble() * (boundary.getHighPoint().getY() - y1) + y1;

			if ((x2 - x1) / (boundary.deltaX()) < threshold
					|| (y2 - y1) / (boundary.deltaY()) < threshold)
				break;
		}
		return new Rectangle(x1, y1, x2, y2);
	}

	/**
	 * Generate a number of random rectangles within the Universe
	 * 
	 * @param number
	 * @param boundary
	 * @return
	 */
	public static Vector<Rectangle> randomRectangles(int number,
			Rectangle boundary) {
		Vector<Rectangle> rectangles = new Vector<Rectangle>();

		for (int i = 0; i < number; i++) {
			rectangles.add(UniformGenerator.randomRectangle(boundary));
		}
		return rectangles;
	}

	/**
	 * Generate a number of random rectangles within the Universe that satisfies
	 * a threshold
	 * 
	 * @param number
	 * @param boundary
	 * @param threshold
	 * @return
	 */
	public static Vector<Rectangle> randomRectangles(int number,
			Rectangle boundary, double threshold) {
		Vector<Rectangle> rectangles = new Vector<Rectangle>();

		for (int i = 0; i < number; i++) {
			rectangles.add(UniformGenerator.randomRectangle(boundary,
					threshold));
		}
		return rectangles;
	}

	/**
	 * generate a random rectangle query within the universe such that its
	 * center is in our data points and its size is bounded by an offset
	 * 
	 * @param number
	 * @param offset_x
	 * @param offset_y
	 * @param isFixOffset
	 * @param points
	 * @param boundary
	 * @param isInteger
	 * @return
	 */
	public static Vector<Rectangle> randomRectanglesWithOffsets(int number,
			double offset_x, double offset_y, boolean isFixOffset,
			List<Point> points, Rectangle boundary, boolean isInteger) {
		Vector<Rectangle> recs = new Vector<Rectangle>();
		int size = points.size();
		Random generator = new Random();
		generator.setSeed(System.nanoTime());
		double _offset_x = 0.0, _offset_y = 0.0;
		if (isFixOffset) {
			_offset_x = offset_x;
			_offset_y = offset_y;
		} else {
			
			_offset_x = generator.nextDouble() * offset_x;
			_offset_y = generator.nextDouble() * offset_y;
		}
		for (int i = 0; i < number; i++) {
			int r = generator.nextInt(size);
			double x1, y1, x2, y2;
			x1 = Math.max(boundary.getLowPoint().getX(), points.get(r).getX()
					- _offset_x);
			y1 = Math.max(boundary.getLowPoint().getY(), points.get(r).getY()
					- _offset_y);
			x2 = Math.min(boundary.getHighPoint().getX(), points.get(r).getX()
					+ _offset_x);
			y2 = Math.min(boundary.getHighPoint().getY(), points.get(r).getY()
					+ _offset_y);
			Rectangle rec = new Rectangle(x1, y1, x2, y2);
			if (isInteger)
				rec.roundingRectangle();
			recs.add(rec);
		}
		return recs;
	}
}
