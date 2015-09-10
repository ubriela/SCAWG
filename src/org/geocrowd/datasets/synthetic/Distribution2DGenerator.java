package org.geocrowd.datasets.synthetic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;
import org.geocrowd.Distribution1DEnum;
import org.geocrowd.Distribution2DEnum;
import org.geocrowd.common.utils.MurmurHash;
import org.geocrowd.common.utils.Stats;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.datasets.params.GeocrowdConstants;
import org.geocrowd.datasets.synthetic.grid.DataProvider;
import org.geocrowd.datasets.synthetic.grid.EquiSizedGrid;
import org.geocrowd.datasets.synthetic.grid.GridCellMem;
import org.geocrowd.dtype.DataTypeEnum;
import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Range;
import org.geocrowd.dtype.Rectangle;
import org.geocrowd.dtype.ValueFreq;
import org.geocrowd.dtype.WeightedPoint;

/**
 * Generate various type of dataset, such as uniform distribution data,
 * charminar data, zipf distribution data, and sampling data
 * 
 * @author HT186010
 * 
 */
public class Distribution2DGenerator {
	public static int time = 0;
	public static int gaussianCluster = 4;
	public static ArrayList<Long> seeds;
	// to distinguish between worker and task distributions
	public int distributionIndicator = 0;

	private String filePath = "";

	public Distribution2DGenerator() {
	}

	public Distribution2DGenerator(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Generate uniform points
	 * 
	 * @param size
	 * @param boundary
	 * @return
	 */
	private Vector<Point> generateUniformPoints(int size, Rectangle boundary) {
		Vector<Point> points = new Vector<Point>();

		for (int i = 0; i < size; i++) {
			Point point = UniformGenerator.randomPoint(boundary, false);
			points.add(point);
		}

		return points;
	}

	/**
	 * Each Gaussian cluster has n/gaussianCluster data points
	 * 
	 * @param n
	 * @param boundary
	 * @return
	 */
	public double varianceX = -1;
	public double varianceY = -1;

	private Vector<Point> generateMultivarDataset(int size, Rectangle boundary) {
		Vector<Point> points = new Vector<Point>();
		if (size == 0)
			return points;

		for (int c = 0; c < gaussianCluster; c++) {
			Point mPoint = UniformGenerator.randomPoint(boundary, false,
					seeds.get(c) + distributionIndicator); // same centroid for
															// all time
															// instances
			double[] means = { mPoint.getX(), mPoint.getY() };
			// mPoint.debug();
			// System.out.println(boundary.getHighPoint().getX());

			double[][] covariances = { { boundary.getHighPoint().getX(), 0 },
					{ 0, boundary.getHighPoint().getY() } };

			/**
			 * If variance is not set from outside
			 */
			if (varianceX > -1) {
				covariances[0][0] = varianceX * Math.pow(c + 1, 2);
				covariances[1][1] = varianceY * Math.pow(c + 1, 2);
			}

			MultivariateNormalDistribution mvd = new MultivariateNormalDistribution(
					means, covariances);
			int samples = 0;
			if (c == gaussianCluster - 1)
				samples = size - ((int) (size / gaussianCluster))
						* (gaussianCluster - 1);
			else
				samples = size / gaussianCluster;
			if (samples == 0)
				continue;
			double[][] data = mvd.sample(samples);
			for (int i = 0; i < samples; i++) {
				Point point = new Point(data[i][0], data[i][1]);
				points.add(point);
			}
		}
		return points;
	}

	/**
	 * Generate random weighted point
	 * 
	 * @param n
	 * @param boundary
	 * @param weightRange
	 * @param isDistinct
	 * @return
	 */
	private Vector<WeightedPoint> generateRandomWeightedPoints(int n,
			Rectangle boundary, Range weightRange, boolean isInteger,
			boolean isDistinct) {
		Vector<WeightedPoint> points = new Vector<WeightedPoint>();
		HashSet<Long> hash = new HashSet<Long>();
		int num = 0;
		while (true) {
			Point point = UniformGenerator.randomPoint(boundary, isInteger);
			String keyString = Utils.createKeyString(point);
			long hashedKey = MurmurHash.hash64(keyString);
			if (!hash.contains(hashedKey) || !isDistinct) {
				hash.add(hashedKey);
				// random distribution in weight
				double weight = 0.0;
				weight = UniformGenerator.randomValue(weightRange, true);
				points.add(new WeightedPoint(point.getX(), point.getY(), weight));
				num++;
				if (num > n)
					return points;
			}
		}
	}

	/**
	 * Generate zipf weighted point
	 * 
	 * @param n
	 * @param boundary
	 * @param weightRange
	 * @return
	 */
	private Vector<WeightedPoint> generateZipfWeightedPoints(int n,
			Rectangle boundary, Range weightRange, boolean isInteger,
			boolean isDistinct) {
		// TODO Auto-generated method stub
		Vector<WeightedPoint> points = new Vector<WeightedPoint>();
		HashSet<Long> hash = new HashSet<Long>();
		int num = 0;
		while (true) {
			Point point = UniformGenerator.randomPoint(boundary, isInteger);
			String keyString = Utils.createKeyString(point);
			long hashedKey = MurmurHash.hash64(keyString);
			if (!hash.contains(point) || !isDistinct) {
				hash.add(hashedKey);
				double weight = 0.0;
				// generate weight list that follows zipf distribution
				// rounding the weight to integer value
				int start = (int) Math.round(weightRange.getStart());
				int end = (int) Math.round(weightRange.getEnd());

				// create a list of count
				ZipfDistribution zipf = new ZipfDistribution(2, 1);
				Double[] probList = new Double[end - start + 1]; // probability
																	// list
																	// associate
																	// with
																	// weight
																	// list
				double[] weights = new double[end - start + 1]; // weight
																// list

				for (int i = start; i <= end; i++) {
					weights[i - start] = i; // populate a list of weights
					probList[i - start] = zipf.probability(i); // get the
																// probability
					// of each weight,
					// smaller weight
					// appears more
					// often.
				}
				RouletteWheelGenerator<Double> pg = new RouletteWheelGenerator<Double>(
						probList);
				int idx = pg.nextValue(); // get the position of the weight
											// in
											// the weight list
				weight = weights[idx];

				points.add(new WeightedPoint(point.getX(), point.getY(), weight));
				num++;
				if (num > n)
					return points;
			}
		}
	}

	/**
	 * Generate two dimensional data that follows zipf distribution
	 * 
	 * @param size
	 * @param boundary
	 * @return
	 */
	private Vector<Point> generateZipfPoints(int size, Rectangle boundary) {
		Vector<Point> points = new Vector<Point>();
		ZipfDistribution zipf = new ZipfDistribution(2, 1);
		Random r = new Random();
		for (int i = 1; i <= size; i++) {
			double x = i * boundary.deltaX() / size
					+ boundary.getLowPoint().getX();
			r.setSeed(System.nanoTime());
			double y = zipf.probability(i) * r.nextDouble() * boundary.deltaY()
					+ boundary.getLowPoint().getY();
			points.add(new Point(x, y));
		}

		return points;
	}

	/**
	 * Generate zipf distribution
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @param b
	 * @return
	 */
	private Vector<Double> generateOneDimZipfValues(int n, double min,
			double max, int bucket_size, boolean isInteger, boolean isRandomDist) {
		// TODO Auto-generated method stub
		Vector<Double> values = new Vector<Double>();
		double scale = (max - min) / bucket_size;
		ZipfDistribution zipf = new ZipfDistribution(2, 1);

		// create a probability list
		Double[] probList = new Double[bucket_size];
		for (int i = 1; i <= bucket_size; i++) {
			probList[i - 1] = zipf.probability(i);
			// System.out.println(probList[i - 1]);
		}

		RouletteWheelGenerator<Double> pg = new RouletteWheelGenerator<Double>(
				probList);

		int count = 0;
		while (true) {
			if (count++ == n)
				break;
			int indices = pg.nextValue(); // get the desired bucket
			int index = indices % bucket_size; // for sure

			// generate a random point in this range
			double x1 = index * scale + min;
			double x2 = (index + 1) * scale + min;
			double value = UniformGenerator
					.randomValue(new Range(x1, x2), true);

			// add the point to
			if (isInteger)
				value = Math.round(value);

			values.add(value);
		}

		// permute the values
		if (isRandomDist && isInteger) {
			int N = (int) Math.round(max - min + 1);
			int[] a = new int[N];
			for (int i = 0; i < N; i++) {
				a[i] = i;
			}
			int[] b = Utils.randomPermutation(a);

			Vector<Double> permutedValues = new Vector<Double>();
			Iterator it = values.iterator();
			while (it.hasNext()) {
				Double value = (Double) it.next();
				int value_index = (int) (value - min) % N;
				double permuted_value = b[value_index] % N + Math.floor(min);
				permutedValues.add(permuted_value);
			}
			return permutedValues;
		}

		return values;
	}

	/**
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @param isInteger
	 * @param isRandomDist
	 *            : means the frequencies are randomly permuted
	 * @param valueDistribution
	 * @return
	 */
	private Vector<ValueFreq<Double>> generateOneDimZipfFreq2(int n,
			double min, double max, boolean isInteger, boolean isRandomDist,
			int valueDistribution) {
		/*
		 * generate N values in a range that follow a distribution refer to
		 * Section 8: Experiment setup of the paper
		 * "Improved histograms for selectivity estimation of range predicates"
		 * #1: uniform #2: zipf_inc #3: zipf_dec #4: cusp_min #5 cusp_max #6:
		 * zipf_ran
		 */
		Vector<ValueFreq<Double>> valueFreqs = new Vector<ValueFreq<Double>>();
		HashSet<Double> values = null;
		switch (valueDistribution) {
		case 1: // uniform
			values = UniformGenerator.randomDistinctValues(1000, new Range(min,
					max), isInteger);
			break;
		case 2: // zipf_inc
			values = ZipfGenerator.zipfIncValues(n, isInteger);
			break;
		case 3: // zipf_dec
			values = ZipfGenerator.zipfDecValues(n, isInteger);
			break;
		case 4: // cusp_min
			values = ZipfGenerator.zipfCuspMin(n, isInteger);
			break;
		case 5: // cusp_max
			values = ZipfGenerator.zipfCuspMax(n, isInteger);
			break;
		case 6: // zipf_ran
			values = ZipfGenerator.zipfRan(n, isInteger);
			break;
		}

		Set sortedValues = new TreeSet(values);

		ZipfDistribution zipf = new ZipfDistribution(2, 1);
		double factor = 1 / zipf.probability(n);

		if (!isRandomDist) {
			Iterator it = sortedValues.iterator();
			for (int i = 1; i <= 1000 && it.hasNext(); i++) {
				// assign each value to its corresponding frequency
				ValueFreq vf = new ValueFreq(it.next(), (int) Math.round(zipf
						.probability(i) * factor));
				valueFreqs.add(vf);
			}
		} else {
			List<Integer> freqs = new Vector<Integer>();
			for (int i = 1; i <= 1000; i++) {
				freqs.add((int) Math.round(zipf.probability(i) * factor));
			}
			// int[] randFreqs = Utils.randomPermutation(freqs);
			Collections.shuffle(freqs);
			Iterator it = sortedValues.iterator();
			for (int i = 0; i < n && it.hasNext(); i++) {
				// assign each value to its corresponding frequency
				ValueFreq vf = new ValueFreq(it.next(), freqs.get(i));
				valueFreqs.add(vf);
			}
		}
		return valueFreqs;
	}

	/**
	 * generate charminar points
	 * 
	 * @param size
	 * @param boundary
	 * @param dim_size_x
	 * @param dim_size_y
	 * @return
	 */
	private Vector<Point> generateCharminarPoints(int size, Rectangle boundary,
			int dim_size_x, int dim_size_y) {
		Vector<Point> points = new Vector<Point>();
		double scale_x = boundary.deltaX() / dim_size_x;
		double scale_y = boundary.deltaY() / dim_size_y;

		// create a list of count
		Double[] probList = new Double[dim_size_x * dim_size_y];
		for (int j = 0; j < dim_size_y; j++)
			for (int i = 0; i < dim_size_x; i++) {
				probList[j * dim_size_x + i] = 1.0 / ((i + 2) * (j + 2));
			}
		RouletteWheelGenerator<Double> pg = new RouletteWheelGenerator<Double>(
				probList);

		int count = 0;
		while (true) {
			int indices = pg.nextValue();
			int index_x = indices % dim_size_x;
			int index_y = indices / dim_size_x;

			// generate a random point in this cell(index_x, index_y)
			double x1 = index_x * scale_x + boundary.getLowPoint().getX();
			double x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			double y1 = index_y * scale_y + boundary.getLowPoint().getY();
			double y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			points.add(UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), false));
			if (count++ == size)
				break;

			// upper left
			index_y = dim_size_y - index_y;
			x1 = index_x * scale_x + boundary.getLowPoint().getX();
			x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			y1 = index_y * scale_y + boundary.getLowPoint().getY();
			y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			points.add(UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), false));
			if (count++ == size)
				break;

			// upper right
			index_x = dim_size_x - index_x;
			x1 = index_x * scale_x + boundary.getLowPoint().getX();
			x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			y1 = index_y * scale_y + boundary.getLowPoint().getY();
			y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			points.add(UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), false));
			if (count++ == size)
				break;

			// below right
			index_y = dim_size_y - index_y;
			x1 = index_x * scale_x + boundary.getLowPoint().getX();
			x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			y1 = index_y * scale_y + boundary.getLowPoint().getY();
			y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			points.add(UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), false));
			if (count++ == size)
				break;

		}

		return points;
	}

	/**
	 * generate charminar weighted points
	 * 
	 * @param n
	 * @param boundary
	 * @param dim_size_x
	 * @param dim_size_y
	 * @return
	 */
	private Vector<WeightedPoint> generateCharminarWeightedPoints(int n,
			Rectangle boundary, int dim_size_x, int dim_size_y,
			boolean isInteger) {
		Vector<WeightedPoint> points = new Vector<WeightedPoint>();
		double scale_x = boundary.deltaX() / dim_size_x;
		double scale_y = boundary.deltaY() / dim_size_y;

		// create a list of count
		Double[] probList = new Double[dim_size_x * dim_size_y];
		for (int j = 0; j < dim_size_y; j++)
			for (int i = 0; i < dim_size_x; i++) {
				probList[j * dim_size_x + i] = 1.0 / ((i + 2) * (j + 2));
			}
		RouletteWheelGenerator<Double> pg = new RouletteWheelGenerator<Double>(
				probList);

		int count = 0;
		while (true) {
			int indices = pg.nextValue();
			int index_x = indices % dim_size_x;
			int index_y = indices / dim_size_x;

			// generate a random point in this cell(index_x, index_y)
			double x1 = index_x * scale_x + boundary.getLowPoint().getX();
			double x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			double y1 = index_y * scale_y + boundary.getLowPoint().getY();
			double y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			Point p1 = UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), isInteger);
			points.add(new WeightedPoint(p1.getX(), p1.getY(), 1.0));
			if (count++ == n)
				break;

			// upper left
			index_y = dim_size_y - index_y;
			x1 = index_x * scale_x + boundary.getLowPoint().getX();
			x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			y1 = index_y * scale_y + boundary.getLowPoint().getY();
			y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			Point p2 = UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), isInteger);
			points.add(new WeightedPoint(p1.getX(), p1.getY(), 1.0));
			if (count++ == n)
				break;

			// upper right
			index_x = dim_size_x - index_x;
			x1 = index_x * scale_x + boundary.getLowPoint().getX();
			x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			y1 = index_y * scale_y + boundary.getLowPoint().getY();
			y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			Point p3 = UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), isInteger);
			points.add(new WeightedPoint(p1.getX(), p1.getY(), 1.0));
			if (count++ == n)
				break;

			// below right
			index_y = dim_size_y - index_y;
			x1 = index_x * scale_x + boundary.getLowPoint().getX();
			x2 = (index_x + 1) * scale_x + boundary.getLowPoint().getX();
			y1 = index_y * scale_y + boundary.getLowPoint().getY();
			y2 = (index_y + 1) * scale_y + boundary.getLowPoint().getY();
			Point p4 = UniformGenerator.randomPoint(new Rectangle(x1, y1, x2,
					y2), isInteger);
			points.add(new WeightedPoint(p1.getX(), p1.getY(), 1.0));
			if (count++ == n)
				break;

		}

		return points;
	}

	/**
	 * simple random sampling over the whole population
	 * 
	 * @param n
	 * @param points
	 * @return
	 */
	private Vector<Point> simpleRandomSampling(int n, Vector<Point> points) {
		if (points.size() < n) {
			System.out
					.println("#points should be larger than number of samples");
			return null;
		}
		Vector<Point> sample_points = new Vector<Point>();
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			r.setSeed(System.nanoTime());
			sample_points.add(points.get(r.nextInt(points.size())));
		}
		return sample_points;
	}

	/**
	 * Simple random sampling from a larger set
	 * 
	 * @param n
	 * @param outFilePath
	 */
	public void generateSampledDataset(int n, String outFilePath) {
		PointFileReader pointFileReader = new PointFileReader(filePath);
		writePointsToFile(simpleRandomSampling(n, pointFileReader.parse()),
				outFilePath);
	}

	/**
	 * 
	 * @param size
	 * @param boundary
	 * @param uni_x_count
	 *            : the number of distinct values in x coord
	 * @param uni_y_count
	 *            : the number of distinct values in y coord
	 * @param isInteger
	 * @return
	 */
	private Vector<Point> generateNonDistinctDataset(int size,
			Rectangle boundary, int uni_x_count, int uni_y_count,
			boolean isInteger) {
		Vector<Point> points = new Vector<Point>();
		Vector<Double> distinct_x = UniformGenerator.randomSequence(
				uni_x_count, boundary.getLowPoint().getX(), boundary
						.getHighPoint().getX(), isInteger);
		Vector<Double> distinct_y = UniformGenerator.randomSequence(
				uni_y_count, boundary.getLowPoint().getY(), boundary
						.getHighPoint().getY(), isInteger);

		Random r = new Random();
		r.setSeed(System.nanoTime());
		for (int i = 1; i <= size; i++) {
			Point point = new Point(distinct_x.get(r.nextInt(uni_x_count)),
					distinct_y.get(r.nextInt(uni_y_count)));
			points.add(point);
		}

		return points;
	}

	/**
	 * Generate two-dimensional datasets
	 * 
	 * @param size
	 *            : the number of data points
	 * @param min_x
	 * @param max_x
	 * @param min_y
	 * @param max_y
	 * @param dist
	 */
	public void generate2DDataset(int size, Rectangle boundary,
			Distribution2DEnum dist) {
		Vector<Point> points = null;

		switch (dist) {
		case UNIFORM_2D: // uniform two-dimensional distribution
			points = generateUniformPoints(size, boundary);
			break;
		case ZIPFIAN_2D: // zipf two-dimensional distribution
			points = generateZipfPoints(size, boundary);
			break;
		case CHARMINAR_2D: // charminar two-dimensional dataset
			points = generateCharminarPoints(size, boundary, 100, 100);
			break;
		case GAUSSIAN_2D: // multivariate gaussian distribution
			points = generateMultivarDataset(size, boundary);
			break;
		case MIXTURE_GAUSSIAN_UNIFORM:
			points = generateMixture(size, boundary);
			break;
		case UNIFORM_INT_2D: // non-distinct two-dimensional dataset
			points = generateNonDistinctDataset(size, boundary, 100, 100, true);
			break;
		}
		writePointsToFile(points, filePath);
		// writePointsToFileWithKey(points, filePath + ".key.txt");
	}

	/**
	 * 
	 * @param size
	 *            : the number of data points
	 * @param filePathIn
	 *            : file with small data points
	 */
	public void generate2DDataset(Integer size, String filePathIn) {

		DataProvider md = new DataProvider(filePathIn, 2);
		Rectangle boundary = new Rectangle(new Point(md.min_x, md.min_y),
				new Point(md.max_x, md.max_y));
		EquiSizedGrid equiSizedGrid = new EquiSizedGrid(boundary,
				md.dim_size_x, md.dim_size_y);
		equiSizedGrid.populate(md.points);
		int[][] stats = equiSizedGrid.histCount();

		Integer[] arr = new Integer[stats[0].length * stats.length];
		for (int i = 0; i < stats.length; i++)
			for (int j = 0; j < stats[0].length; j++)
				arr[i * stats[0].length + j] = stats[i][j];
		RouletteWheelGenerator rwGen = new RouletteWheelGenerator<Integer>(arr);

		Vector<Point> points = new Vector<Point>();

		UniformGenerator uniGenerator = new UniformGenerator();
		for (int i = 0; i < size; i++) {
			int index = rwGen.nextValue();
			int x = index / stats.length;
			int y = index - x * stats.length;
			GridCellMem cell = equiSizedGrid.getGrid()[x][y];

			Point pt = uniGenerator.randomPoint(cell, false);
			points.add(pt);
		}

		writePointsToFile(points, filePath);
	}

	/**
	 * Half Gaussian half uniform
	 * 
	 * @param size
	 * @param boundary
	 * @return
	 */
	private Vector<Point> generateMixture(int size, Rectangle boundary) {
		Vector<Point> points = new Vector<Point>();
		int gaussian_count = 9 * size / 10;
		int uniform_count = size - gaussian_count;
		Vector<Point> gaussian_points = generateMultivarDataset(gaussian_count,
				boundary);
		Vector<Point> uniform_points = generateUniformPoints(uniform_count,
				boundary);
		points.addAll(gaussian_points);
		points.addAll(uniform_points);
		return points;
	}

	/**
	 * Generate two-dimensional dataset
	 * 
	 * @param n
	 * @param boundary
	 * @param dist
	 * @param isInteger
	 * @param smallWeight
	 * @param largeWeight
	 *            dist = 1 --> uniform distribution, dist = 2 --> zipf
	 *            distribution, dist = 3 --> charminar dataset
	 */
	public void generateWeightedDataset(int dist, int n, Rectangle boundary,
			Range weightRange, boolean isInteger, boolean isDistinct) {
		Vector<WeightedPoint> points = null;

		switch (dist) {
		case 1: // random weight distribution distribution
			points = generateRandomWeightedPoints(n, boundary, weightRange,
					isInteger, isDistinct);
			break;
		case 2: // zipf weight distribution
			points = generateZipfWeightedPoints(n, boundary, weightRange,
					isInteger, isDistinct);
			break;
		case 3: // charminar two-dimensional dataset
			points = generateCharminarWeightedPoints(n, boundary, 100, 100,
					isInteger);
			break;
		case 4: // non-distinct two-dimensional dataset
			// points = generateNonDistinctDataset(n, boundary, 100, 100, true);
			break;
		}
		writeWeightedPointsToFile(points, filePath);
	}

	/**
	 * Write a list of points to a file
	 * 
	 * @param points
	 */
	private void writePointsToFile(Vector<Point> points, String outFilePath) {
		// Create file
		try {
			FileWriter fstream = new FileWriter(outFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			StringBuffer sb = new StringBuffer();
			Iterator<Point> it = points.iterator();
			while (it.hasNext()) {
				Point point = (Point) it.next();
				sb.append(point.getX() + GeocrowdConstants.delimiter.toString()
						+ point.getY());

				if (it.hasNext())
					sb.append("\n");
			}
			out.write(sb.toString());
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		// System.out.println("Dataset created!");
	}

	/**
	 * Write a list of points to a file with a primary key
	 * 
	 * @param points
	 */
	private void writePointsToFileWithKey(Vector<Point> points,
			String outFilePath) {
		// Create file
		try {
			FileWriter fstream = new FileWriter(outFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			StringBuffer sb = new StringBuffer();
			int i = 1;

			Iterator<Point> it = points.iterator();
			while (it.hasNext()) {
				Point point = (Point) it.next();
				sb.append(i++ + GeocrowdConstants.delimiter.toString()
						+ +point.getX()
						+ GeocrowdConstants.delimiter.toString()
						+ +point.getY());

				if (it.hasNext())
					sb.append('\n');
			}
			out.write(sb.toString());
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Dataset created!");
	}

	/**
	 * Write a list of weighted points to a file
	 * 
	 * @param points
	 */
	private void writeWeightedPointsToFile(Vector<WeightedPoint> points,
			String outFilePath) {
		// Create file
		try {
			FileWriter fstream = new FileWriter(outFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("50\t50");

			Iterator<WeightedPoint> it = points.iterator();
			while (it.hasNext()) {
				WeightedPoint point = (WeightedPoint) it.next();
				out.write("\n");
				out.write(point.getX() + "\t" + point.getY() + "\t"
						+ point.getWeight());
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Dataset created!");

	}

	/**
	 * Write a list of integer to a file
	 * 
	 * @param points
	 */
	private void writeIntegersToFile(Vector<Double> values, String outFilePath) {
		// Create file
		try {
			FileWriter fstream = new FileWriter(outFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			Iterator<Double> it = values.iterator();
			if (it.hasNext())
				out.write(((Double) it.next()).toString());
			while (it.hasNext()) {
				Double value = (Double) it.next();
				out.write("\n");
				out.write(value.toString());
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Dataset created!");
	}

	private void writeValuesToFile(List<ValueFreq<Double>> valueFreqs,
			String outFilePath) {
		// Create file
		try {
			FileWriter fstream = new FileWriter(outFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			Iterator<ValueFreq<Double>> it = valueFreqs.iterator();
			if (it.hasNext()) {
				ValueFreq<Double> vf = (ValueFreq<Double>) it.next();
				out.write(vf.getValue() + "\t" + vf.getFreq());
			}
			while (it.hasNext()) {
				ValueFreq<Double> vf = it.next();
				out.write("\n");
				out.write(vf.getValue() + "\t" + vf.getFreq());
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Dataset created!");
	}

	/**
	 * Write a list of integer to a file with a primary key
	 * 
	 * @param values
	 * @param string
	 */
	private void writeIntegersToFileWithKey(Vector<Double> values,
			String outFilePath) {
		// TODO Auto-generated method stub
		try {
			FileWriter fstream = new FileWriter(outFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			Iterator<Double> it = values.iterator();
			int i = 1;
			if (it.hasNext())
				out.write(i++ + "\t" + ((Double) it.next()).toString());
			while (it.hasNext()) {
				Double value = (Double) it.next();
				out.write("\n");
				out.write(i++ + "\t" + value.toString());
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Dataset created!");
	}

}
