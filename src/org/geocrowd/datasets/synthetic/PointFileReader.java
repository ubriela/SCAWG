package org.geocrowd.datasets.synthetic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.WeightedPoint;



/**
 * Read two-dimensional data points from a file
 * 
 * @author HT186010
 * 
 */
public class PointFileReader {
	public String filePath = "";

	public Character delimiter = '\t';
	
	double max_x, max_y, min_x, min_y;

	public double getMax_x() {
		return max_x;
	}

	public double getMax_y() {
		return max_y;
	}

	public double getMin_x() {
		return min_x;
	}

	public double getMin_y() {
		return min_y;
	}

	public PointFileReader(String filePath) {
		super();
		this.filePath = filePath;
		min_x = min_y = Double.MAX_VALUE;
		max_x = max_y = -Double.MAX_VALUE;

	}

	/**
	 * Read the boundaries from the input file and return a list of points
	 * 
	 * @return
	 */
	public Vector<Point> parse() {
		Vector<Point> points = new Vector<Point>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			strLine = br.readLine();

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				String[] tmp_dims = strLine.split(delimiter.toString());
				final double x = Double.parseDouble(tmp_dims[0]);
				final double y = Double.parseDouble(tmp_dims[1]);

				// update min_x, max_x
				if (min_x > x)
					min_x = x;
				if (max_x < x) {
					max_x = x;
				}

				// update min_y, max_y
				if (min_y > y)
					min_y = y;
				if (max_y < y)
					max_y = y;

				Point point = new Point(x, y);

				// System.out.println(point.getX() + " " + point.getY());

				points.add(point);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return points;
	}

	/**
	 * similar to parse, but include the weight for each point
	 * @return
	 */
	public List<WeightedPoint> parseWeightPoints() {
		// TODO Auto-generated method stub
		Vector<WeightedPoint> points = new Vector<WeightedPoint>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			strLine = br.readLine();
			String[] dims = strLine.split(delimiter.toString());

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				String[] tmp_dims = strLine.split(delimiter.toString());
				final double x = Double.parseDouble(tmp_dims[0]);
				final double y = Double.parseDouble(tmp_dims[1]);
				final double w = Double.parseDouble(tmp_dims[2]);	//	weight

				// update min_x, max_x
				if (min_x > x)
					min_x = x;
				if (max_x < x) {
					max_x = x;
				}

				// update min_y, max_y
				if (min_y > y)
					min_y = y;
				if (max_y < y)
					max_y = y;

				WeightedPoint point = new WeightedPoint(x, y, w);

				// System.out.println(point.getX() + " " + point.getY());

				points.add(point);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return points;
	}
}
