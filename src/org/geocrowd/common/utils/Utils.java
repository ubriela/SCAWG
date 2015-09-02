/**
 * *****************************************************************************
 * @ Year 2013 This is the source code of the following papers.
 * 
 * 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla
 * Kazemi, Cyrus Shahabi.
 * 
 *
 * Please contact the author Hien To, ubriela@gmail.com if you have any
 * question.
 * 
 * Contributors: Hien To - initial implementation
 ******************************************************************************
 */
package org.geocrowd.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.geocrowd.DatasetEnum;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.common.crowd.ExpertTask;
import org.geocrowd.common.crowd.ExpertWorker;
import org.geocrowd.common.crowd.GenericTask;
import org.geocrowd.common.crowd.GenericWorker;
import org.geocrowd.common.crowd.WorkingRegion;
//import org.geocrowd.common.crowd.WorkingRegion;
import org.geocrowd.datasets.params.GowallaConstants;
import org.geocrowd.datasets.params.YelpConstants;
import org.geocrowd.dtype.GenericPoint;
import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Rectangle;
import org.geocrowd.dtype.ValueFreq;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

	/**
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distance(double lat1, double lon1, double lat2,
			double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist);
	}

	/**
	 * distance between two point
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distance_point(double lat1, double lon1, double lat2,
			double lon2) {
		return Math.sqrt(Math.pow((lat2 - lat1), 2)
				+ Math.pow((lon2 - lon1), 2));
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	public static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	/**
	 * Compute Mean Contribution Distance
	 * 
	 * Ref: On the "localness" of user-generated content.
	 * 
	 * @param contributer
	 *            the contributer
	 * @param contributions
	 *            the contributions
	 * @return the double
	 */
	public static double MCD(Point contributer, ArrayList<Point> contributions) {
		double sum = 0;
		Iterator<Point> it = contributions.iterator();
		while (it.hasNext()) {
			Point pt = it.next();
			sum += distance(contributer.getX(), contributer.getY(), pt.getX(),
					pt.getY());
		}

		if (contributions.size() == 0) {
			return 0.0;
		}
		return sum / contributions.size();
	}

	private static void getSubsets(List<Integer> superSet, int k, int idx,
			Set<Integer> current, List<Set<Integer>> solution) {
		// successful stop clause
		if (current.size() == k) {
			solution.add(new HashSet<>(current));
			return;
		}
		// unseccessful stop clause
		if (idx == superSet.size()) {
			return;
		}
		Integer x = superSet.get(idx);
		current.add(x);
		// "guess" x is in the subset
		getSubsets(superSet, k, idx + 1, current, solution);
		current.remove(x);
		// "guess" x is not in the subset
		getSubsets(superSet, k, idx + 1, current, solution);
	}

	private static void getSubsets2(List<Integer> superSet, int k, int idx,
			Set<Integer> current, List<LinkedList<Integer>> solution) {
		// successful stop clause
		if (current.size() == k) {
			LinkedList<Integer> ll = new LinkedList<>(current);
			Collections.sort(ll);
			solution.add(ll);
			return;
		}
		// unseccessful stop clause
		if (idx == superSet.size()) {
			return;
		}
		Integer x = superSet.get(idx);
		current.add(x);
		// "guess" x is in the subset
		getSubsets2(superSet, k, idx + 1, current, solution);
		current.remove(x);
		// "guess" x is not in the subset
		getSubsets2(superSet, k, idx + 1, current, solution);
	}

	public static List<LinkedList<Integer>> getSubsets2(List<Integer> superSet,
			int k) {
		List<LinkedList<Integer>> res = new ArrayList<>();
		getSubsets2(superSet, k, 0, new HashSet<Integer>(), res);
		return res;
	}

	public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
		List<Set<Integer>> res = new ArrayList<>();
		getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
		return res;
	}

	/**
	 * find the lower bound
	 * 
	 * @param search
	 * @param find
	 * @return
	 */
	public static final int binarySearchFloor(double[] search, double find) {
		int start, end, midPt;
		start = 0;
		end = search.length - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if (search[midPt] == find) {
				while (midPt - 1 >= 0 && search[midPt - 1] == find)
					midPt--;
				return midPt;
			} else if (search[midPt] < find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return end;
	}

	/**
	 * find upper index of rec query
	 * 
	 * @param search
	 * @param find
	 * @return
	 */
	public static final int binarySearchCeil(double[] search, double find) {
		int start, end, midPt;
		start = 0;
		end = search.length - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if (search[midPt] == find) {
				while (midPt + 1 <= search.length - 1
						&& search[midPt + 1] == find)
					midPt++;
				return midPt;
			} else if (search[midPt] < find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return start;
	}

	/**
	 * find the value
	 * 
	 * @param <T>
	 * 
	 * @param biasValuesV
	 * @param find
	 * @return if found --> return index, if not return -1
	 */
	public static final <T> int binarySearchBias(
			List<ValueFreq<T>> biasValuesV, T find) {
		int start, end, midPt;
		start = 0;
		end = biasValuesV.size() - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if ((double) (Double) biasValuesV.get(midPt).getValue() == (double) (Double) find) {
				return midPt;
			} else if ((double) (Double) biasValuesV.get(midPt).getValue() < (double) (Double) find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return -1;
	}

	/**
	 * find the lower bound
	 * 
	 * @param <T>
	 * 
	 * @param biasValuesV
	 * @param find
	 * @return
	 */
	public static final <T> int binarySearchFloorBias(
			List<ValueFreq<T>> biasValuesV, double find) {
		int start, end, midPt;
		start = 0;
		end = biasValuesV.size() - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if ((double) (Double) biasValuesV.get(midPt).getValue() == find) {
				return midPt;
			} else if ((double) (Double) biasValuesV.get(midPt).getValue() < find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return end;
	}

	/**
	 * find the lower bound, exclude the value
	 * 
	 * @param <T>
	 * 
	 * @param biasValuesV
	 * @param find
	 * @return
	 */
	public static final <T> int binarySearchFloorBiasExclude(
			List<ValueFreq<T>> biasValuesV, double find) {
		int start, end, midPt;
		start = 0;
		end = biasValuesV.size() - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if ((double) (Double) biasValuesV.get(midPt).getValue() == find) {
				return midPt - 1;
			} else if ((double) (Double) biasValuesV.get(midPt).getValue() < find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return end;
	}

	/**
	 * find upper index
	 * 
	 * @param <T>
	 * 
	 * @param biasValuesV
	 * @param find
	 * @return
	 */
	public static final <T> int binarySearchCeilBias(
			List<ValueFreq<T>> biasValuesV, double find) {
		int start, end, midPt;
		start = 0;
		end = biasValuesV.size() - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if ((double) (Double) biasValuesV.get(midPt).getValue() == find) {
				return midPt;
			} else if ((double) (Double) biasValuesV.get(midPt).getValue() < find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return start;
	}

	/**
	 * find upper index, exclude the value
	 * 
	 * @param <T>
	 * 
	 * @param biasValuesV
	 * @param find
	 * @return
	 */
	public static final <T> int binarySearchCeilBiasExclude(
			List<ValueFreq<T>> biasValuesV, double find) {
		int start, end, midPt;
		start = 0;
		end = biasValuesV.size() - 1;
		while (start <= end) {
			midPt = (start + end) / 2;
			if ((double) (Double) biasValuesV.get(midPt).getValue() == find) {
				return midPt + 1;
			} else if ((double) (Double) biasValuesV.get(midPt).getValue() < find) {
				start = midPt + 1;
			} else {
				end = midPt - 1;
			}
		}

		return start;
	}

	/**
	 * sort a list of points by x or y coordinate
	 * 
	 * @param dim
	 *            = 1 --> x, =2 --> y
	 * @param points
	 */
	public static final void sort(final int dim, List<GenericPoint> points) {
		Collections.sort(points, new Comparator<GenericPoint>() {
			@Override
			public final int compare(GenericPoint p1, GenericPoint p2) {
				switch (dim) {
				case 1:
					if ((double) (Double) p1.getX() < (double) (Double) p2
							.getX()) {
						return -1;
					} else if ((double) (Double) p1.getX() == (double) (Double) p2
							.getX()) {
						return 0;
					} else
						return 1;
				case 2:
					if ((double) (Double) p1.getY() < (double) (Double) p2
							.getY()) {
						return -1;
					} else if ((double) (Double) p1.getY() == (double) (Double) p2
							.getY()) {
						return 0;
					} else
						return 1;
				}
				return 1;
			}
		});
	}

	/**
	 * print a matrix of int
	 * 
	 * @param stats
	 */
	public static void print(int[][] stats) {
		int total = 0;
		for (int j = stats.length - 1; j >= 0; j--) {
			for (int i = 0; i < stats[0].length; i++) {
				total += stats[j][i];
				System.out.print(stats[i][j] + " ");
			}
			System.out.println("\n");
		}
		System.out.println("Total number of points: " + total);
	}

	/**
	 * print a matrix of double
	 * 
	 * @param stats
	 */
	public static void print(double[][] stats) {
		for (int i = 0; i < stats.length; i++) {
			for (int j = 0; j < stats[0].length; j++) {
				System.out.print(stats[i][j] + " ");
			}
			System.out.println("\n");
		}
	}

	/**
	 * Print a list of double
	 * 
	 * @param y
	 */
	public static void print(double[] y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < y.length; i++) {
			System.out.printf(y[i] + " ");
		}
		System.out.println();
	}

	/**
	 * print a list of double with format in a same line
	 * 
	 * @param y
	 */
	public static void printFormat(double[] y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < y.length; i++) {
			System.out.printf("%.3f", y[i]);
			System.out.print("\t");
		}
		System.out.println();
	}

	/**
	 * print a list of double with format in different lines
	 * 
	 * @param y
	 */
	public static void printFormat2(double[] y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < y.length; i++) {
			System.out.printf("%.3f", y[i]);
			System.out.println();
		}
	}

	/**
	 * convert an array list of double to an array
	 * 
	 * @param al
	 * @return
	 */
	public static double[] arrayListToArray(ArrayList al) {
		int num = al.size();
		double[] dl = new double[num];
		for (int i = 0; i < num; i++)
			dl[i] = ((Double) al.get(i)).doubleValue();
		return dl;
	}

	/**
	 * convert a vector to an array
	 * 
	 * @param values
	 * @return
	 */
	public static Double[] listToArray(List values) {
		// TODO Auto-generated method stub
		int num = values.size();
		Double[] dl = new Double[num];
		for (int i = 0; i < num; i++)
			dl[i] = ((Double) values.get(i)).doubleValue();
		return dl;
	}

	/**
	 * Converts a double ddd.dddddddd to sss.s
	 * 
	 * @param num
	 * @param numberOfDecimalPlaces
	 * @return
	 */
	public static String formatNumber(double num, int numberOfDecimalPlaces) {
		NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(numberOfDecimalPlaces);
		f.setMinimumFractionDigits(numberOfDecimalPlaces);
		return f.format(num);
	}

	/**
	 * Rotate a matrix
	 * 
	 * @param m
	 * @return
	 */
	public static double[][] rotate(double[][] m) {
		double[][] rotateM = new double[m[0].length][m.length];
		for (int j = 0; j < m[0].length; j++)
			for (int i = 0; i < m.length; i++)
				rotateM[j][i] = m[i][j];

		return rotateM;
	}

	/**
	 * Generating random permutations by swapping
	 * 
	 * @param a
	 */
	public static int[] randomPermutation(int[] a) {
		int[] b = (int[]) a.clone();
		Random r = new Random();
		for (int k = b.length - 1; k > 0; k--) {
			r.setSeed(System.nanoTime());
			int w = (int) Math.floor(r.nextDouble() * (k + 1));
			int temp = b[w];
			b[w] = b[k];
			b[k] = temp;
		}
		return b;
	}

	public static String createKeyString(GenericPoint point) {
		return point.getX().toString() + ";" + point.getY().toString();
	}

	public static String createKeyString(Point point) {
		return point.getX() + ";" + point.getY();
	}

	/**
	 * Writefile.
	 * 
	 * @param s
	 *            the s
	 * @param filename
	 *            the filename
	 */
	public static void writefile(String s, String filename) {

		File file = new File(filename);
		FileWriter writer;
		try {
			writer = new FileWriter(file, true);
			PrintWriter printer = new PrintWriter(writer);
			// printer.append("\n\n\n"+s);
			printer.print(s);
			printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Writefile2.
	 * 
	 * @param s
	 *            the s
	 * @param filename
	 *            the filename
	 */
	public static void writefile2(String s, String filename) {
		try {
			FileWriter writer = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(s);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zipf's law from Wiki
	 * 
	 * @param N
	 * @param k
	 * @param s
	 * @return
	 */
	public static double zipf_pmf(int n, int k, double s) {
		double sum = 0;
		for (int i = 1; i <= n; i++)
			sum += (1.0 / Math.pow(i, s));
		return (1.0 / Math.pow(k, s)) / sum;
	}

	public static String datasetToWorkerPath(DatasetEnum dataset) {
		switch (dataset) {
		case GOWALLA:
			return "dataset/real/gowalla/worker/gowalla_workers";
		case FOURSQUARE:
			return "dataset/real/foursquare/worker/workers";
		case SKEWED:
			return "dataset/skew/worker/skew_workers";
		case UNIFORM:
			return "dataset/uni/worker/uni_workers";
		}
		return "";
	}

	public static String datasetToWorkerPointPath() {
		return "res/dataset/worker/workers";
	}

	public static String datasetToTaskPointPath() {
		return "res/dataset/task/tasks";
	}

	public static String datasetToTaskPath(DatasetEnum dataset) {
		switch (dataset) {
		case GOWALLA:
			return "dataset/real/gowalla/task/gowalla_tasks";
		case FOURSQUARE:
			return "dataset/real/foursquare/task/foursquare_tasks";
		case SKEWED:
			return "dataset/skew/task/skew_tasks";
		case UNIFORM:
			return "dataset/uni/task/uni_tasks";
		}
		return "";
	}

	public static String datasetToBoundary(DatasetEnum dataset) {
		switch (dataset) {
		case FOURSQUARE:
			return "dataset/real/foursquare/foursquare_boundary.txt";
		case GOWALLA:
			return "dataset/real/gowalla/gowalla_CA_boundary.txt";
		case SKEWED:
			return "dataset/skew/skew_boundary.txt";
		case UNIFORM:
			return "dataset/uni/uni_boundary.txt";
		case SMALL_TEST:
			return "dataset/small/small_boundary.txt";
		case YELP:
			return "dataset/real/yelp/yelp_boundary.txt";
		}
		return "";
	}

	public static int datasetToResolution(DatasetEnum dataset) {
		// System.out.println(dataset);
		switch (dataset) {
		case GOWALLA:
			return GowallaConstants.gowallaResolution;
		case SKEWED:
			return GeocrowdConstants.skewedResolution;
		case UNIFORM:
			return GeocrowdConstants.uniResolution;
		case SMALL_TEST:
			return GeocrowdConstants.smallResolution;
		case YELP:
			return YelpConstants.yelpResolution;
		}
		return 0;
	}
	
	/**
	 * Compute mbr.
	 * 
	 * @param points
	 *            the points
	 * @return the mbr
	 */
	public static Rectangle computeMBR(ArrayList<Point> points) {
		double minLat = Double.MAX_VALUE;
		double maxLat = (-1) * Double.MAX_VALUE;
		double minLng = Double.MAX_VALUE;
		double maxLng = (-1) * Double.MAX_VALUE;
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			Point pt = it.next();
			Double lat = pt.getX();
			Double lng = pt.getY();

			if (lat < minLat)
				minLat = lat;
			if (lat > maxLat)
				maxLat = lat;
			if (lng < minLng)
				minLng = lng;
			if (lng > maxLng)
				maxLng = lng;
		}

		return new Rectangle(minLat, minLng, maxLat, maxLng);
	}

	// http://en.wikipedia.org/wiki/Zipf's_law
	// public static double zipf_pmf(double k, double s, int N) {
	// double sum = 0.0;
	// for (int i = 0; i < N + 1; i++)
	// sum += Math.pow(i, -s);
	// return (1.0 / Math.pow(k, s)) / sum;
	// }
}
