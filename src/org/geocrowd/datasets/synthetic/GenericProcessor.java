package org.geocrowd.datasets.synthetic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.geocrowd.DatasetEnum;
import org.geocrowd.Distribution1DEnum;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.TaskType;
import org.geocrowd.WorkerType;
import org.geocrowd.common.crowd.ExpertTask;
import org.geocrowd.common.crowd.ExpertWorker;
import org.geocrowd.common.crowd.TaskFactory;
import org.geocrowd.common.crowd.WorkerFactory;
import org.geocrowd.common.crowd.WorkingRegion;
import org.geocrowd.common.entropy.EntropyUtility;
import org.geocrowd.common.utils.TaskUtility;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.dtype.Range;

public class GenericProcessor {

	public static Character delimiter = '\t';

	public Distribution1DEnum workerIdDist = Distribution1DEnum.UNIFORM_1D;

	/** The min lat. */
	public static double minLat = Double.MAX_VALUE;

	/** The max lat. */
	public static double maxLat = (-1) * Double.MAX_VALUE;

	/** The min lng. */
	public static double minLng = Double.MAX_VALUE;

	/** The max lng. */
	public static double maxLng = (-1) * Double.MAX_VALUE;

	/** The row count. */
	public static int rowCount = 0; // number of rows for the grid

	/** The col count. */
	public static int colCount = 0; // number of cols for the grid

	/** The time counter. */
	public static int timeCounter = 0; // works as the clock for task generation

	/** The resolution. */
	public int resolution = 0;

	/** The data set. */
	public static DatasetEnum DATA_SET;

	/**
	 * Check boundary mbr.
	 * 
	 * @param mbr
	 *            the mbr
	 */
	private void checkBoundaryMBR(WorkingRegion mbr) {
		if (mbr.getMinLat() < minLat)
			mbr.setMinLat(minLat);
		if (mbr.getMaxLat() > maxLat)
			mbr.setMaxLat(maxLat);
		if (mbr.getMinLng() < minLng)
			mbr.setMinLng(minLng);
		if (mbr.getMaxLng() > maxLng)
			mbr.setMaxLng(maxLng);
	}

	/**
	 * Giving a set of points, compute the MBR covering all the points.
	 * 
	 * @param datafile
	 *            the datafile
	 */
	public void computeBoundary() {
		String workersPath = Utils.datasetToWorkerPointPath();
		for (int i = 0; i < GeocrowdConstants.TIME_INSTANCE; i++) {
			try {
				FileReader reader = new FileReader(workersPath + i + ".txt");
				BufferedReader in = new BufferedReader(reader);
				while (in.ready()) {
					String line = in.readLine();
					String[] parts = line.split(delimiter.toString());
					Double lat = Double.parseDouble(parts[0]);
					Double lng = Double.parseDouble(parts[1]);

					if (lat < minLat)
						minLat = lat;
					if (lat > maxLat)
						maxLat = lat;
					if (lng < minLng)
						minLng = lng;
					if (lng > maxLng)
						maxLng = lng;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		dumpBoundary();
	}

	/**
	 * dump boundary to file
	 */
	protected void dumpBoundary() {
		String boundaryPath = Utils.datasetToBoundary(DATA_SET);
		try {
			FileWriter writer = new FileWriter(boundaryPath);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(minLat + " " + minLng + " " + maxLat + " " + maxLng);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Boundary [minLat:" + minLat + "   maxLat:" + maxLat
				+ "   minLng:" + minLng + "   maxLng:" + maxLng + "]");

		WorkingRegion mbr = new WorkingRegion(minLat, minLng, maxLat, maxLng);
		double x = Utils.distance(minLat, minLng, maxLat, minLng);
		double y = Utils.distance(minLat, minLng, minLat, maxLng);
		System.out.println("Area: " + x * y);
		System.out.println("Region MBR size: " + TaskUtility.diagonalLength(mbr));
	}

	/**
	 * Generate SYN dataset.
	 * 
	 * @param outputFile
	 *            : output
	 * @param inputFile
	 *            : the workers are formed into four Gaussian clusters
	 * @param isConstantMBR
	 *            the is constant mbr
	 * @param isConstantMaxT
	 *            the is constant max t
	 * @maxT is randomly generated
	 */
	private void generateSyncWorkersFromDataPoints(String outputFile,
			String inputFile, boolean isConstantMBR, boolean isConstantMaxT) {
		double maxRangeX = (maxLat - minLat) * (GeocrowdConstants.MaxRangePerc);
		double maxRangeY = (maxLng - minLng) * GeocrowdConstants.MaxRangePerc;
		// generate worker id

		try {
			FileWriter writer = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(writer);
			StringBuffer sb = new StringBuffer();
			FileReader reader = new FileReader(inputFile);
			BufferedReader in = new BufferedReader(reader);
			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(delimiter.toString());
				int maxT = 0;
				if (isConstantMaxT)
					maxT = GeocrowdConstants.MaxTasksPerWorker;
				else
					maxT = (int) UniformGenerator.randomValue(new Range(0,
							GeocrowdConstants.MaxTasksPerWorker), true) + 1;
				double rangeX = 0;
				double rangeY = 0;
				if (isConstantMBR) {
					rangeX = maxRangeX;
					rangeY = maxRangeY;
				} else {
					rangeX = UniformGenerator.randomValue(new Range(0,
							maxRangeX), false);
					rangeY = UniformGenerator.randomValue(new Range(0,
							maxRangeY), false);
				}

				int exp = (int) UniformGenerator.randomValue(new Range(0,
						GeocrowdConstants.TaskCategoryNo), true);
				ExpertWorker w = (ExpertWorker) WorkerFactory
						.getWorker(WorkerType.EXPERT);
				w.setLat(Double.parseDouble(parts[0]));
				w.setLng(Double.parseDouble(parts[1]));
				w.setCapacity(maxT);
				WorkingRegion mbr = WorkingRegion.createMBR(w.getLat(),
						w.getLng(), rangeX, rangeY);
				checkBoundaryMBR(mbr);
				w.setMbr(mbr);
				w.addExpertise(exp);

				WorkerIDGenerator widGenerator = new WorkerIDGenerator(
						workerIdDist, 1000);
				int workerId = widGenerator.nextWorkerId();
				w.setActiveness(widGenerator.workerActiveness.get(workerId));

				sb.append(workerId + "," + w.getLat() + "," + w.getLng() + ","
						+ w.getCapacity() + "," + w.getActiveness() + ",["
						+ mbr.getMinLat() + "," + mbr.getMinLng() + ","
						+ mbr.getMaxLat() + "," + mbr.getMaxLng() + "],[" + exp
						+ "]\n");
			}
			out.write(sb.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate SYN dataset.
	 * 
	 * @param outputFile
	 *            : output
	 * @param inputFile
	 *            : distributing tasks into four Gaussian clusters
	 */
	private void generateSyncTasksFromDataPoints(String outputFile,
			String inputFile) {
		int taskCount = 0;
		try {
			FileWriter writer = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(writer);
			FileReader reader = new FileReader(inputFile);
			BufferedReader in = new BufferedReader(reader);
			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(delimiter.toString());
				int taskCategory = (int) UniformGenerator.randomValue(
						new Range(0, GeocrowdConstants.TaskCategoryNo), true);
				ExpertTask t = (ExpertTask) TaskFactory
						.getTask(TaskType.EXPERT);
				t.setLat(Double.parseDouble(parts[0]));
				t.setLng(Double.parseDouble(parts[1]));
				t.setArrivalTime(timeCounter);
				t.setEntropy(-1);
				t.setCategory(taskCategory);

				out.write(t.getLat() + "," + t.getLng() + ","
						+ t.getArrivalTime() + "," + t.getEntropy() + ","
						+ t.getCategory() + "\n");
				taskCount++;
			}
			out.close();
		} catch (Exception e) {
		}
		// System.out.println(taskCount);
	}

	/**
	 * Generate syn tasks.
	 */
	public void generateSynTasks() {
		timeCounter = 0;
		String outputFileFrefix = Utils.datasetToTaskPath(DATA_SET);
		System.out.println("Tasks:");
		for (int i = 0; i < GeocrowdConstants.TIME_INSTANCE; i++) {
			generateSyncTasksFromDataPoints(outputFileFrefix + i + ".txt",
					GeocrowdConstants.inputTaskFilePath + i + ".txt");
			timeCounter++;
		}
	}

	/**
	 * Generate syn workers.
	 * 
	 * @param isConstantMBR
	 *            the is constant mbr
	 * @param isConstantMaxT
	 *            the is constant max t
	 */
	public void generateSynWorkers(boolean isConstantMBR, boolean isConstantMaxT) {
		String outputFileFrefix = Utils.datasetToWorkerPath(DATA_SET);
		System.out.println("Workers:");
		for (int i = 0; i < GeocrowdConstants.TIME_INSTANCE; i++) {
			generateSyncWorkersFromDataPoints(outputFileFrefix + i + ".txt",
					GeocrowdConstants.inputWorkerFilePath + i + ".txt",
					isConstantMBR, isConstantMaxT);
		}
	}

	public void debug() {
		System.out.println("minLat " + minLat);
		System.out.println("minLng " + minLng);
		System.out.println("maxLat " + maxLat);
		System.out.println("maxLng " + maxLng);
		System.out.println("resolution " + resolution);
	}

	/**
	 * Location/Region entropy process
	 */

	/**
	 * Compute sync location density (i.e., the number of points) from a list of
	 * worker/task files
	 * 
	 * @return the hashtable
	 */
	public Hashtable<Integer, Hashtable<Integer, Integer>> computeLocationDensity() {
		String workerFilePath = Utils.datasetToWorkerPointPath();
		Hashtable<Integer, Hashtable<Integer, Integer>> densities = new Hashtable<Integer, Hashtable<Integer, Integer>>();

		for (int i = 0; i < GeocrowdConstants.TIME_INSTANCE; i++) {
			try {
				FileReader file = new FileReader(workerFilePath + i + ".txt");
				BufferedReader in = new BufferedReader(file);
				while (in.ready()) {
					String line = in.readLine();
					String[] parts = line.split(delimiter.toString());
					Double lat = Double.parseDouble(parts[0]);
					Double lng = Double.parseDouble(parts[1]);
					int row = latToRowIdx(lat);
					int col = lngToColIdx(lng);
					if (densities.containsKey(row)) {
						if (densities.get(row).containsKey(col))
							densities.get(row).put(col,
									densities.get(row).get(col) + 1);
						else
							densities.get(row).put(col, 1);
					} else {
						Hashtable<Integer, Integer> rows = new Hashtable<Integer, Integer>();
						rows.put(col, 1);
						densities.put(row, rows);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(densities.size());
		return densities;
	}

	/**
	 * Read data from multiple time instances. Then,
	 */
	public void regionEntropy() {
		readBoundary();
		createGrid();

		// compute occurrences of each location id
		// each location id is associated with a grid cell
		Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> occurances = readEntropyData();

		Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> regionOccurances = regionOccurances(occurances);

		// compute entropy of each location id
		EntropyUtility.dumpRegionEntropy(regionOccurances, DATA_SET);
	}

	/**
	 * compute grid granularity.
	 * 
	 * @param dataset
	 *            the dataset
	 */
	public void createGrid() {
		resolution = Utils.datasetToResolution(DATA_SET);
		rowCount = colCount = resolution;
		System.out
				.println("rowcount: " + rowCount + "    colCount:" + colCount);
	}

	/**
	 * Read dataset boundary from file.
	 * 
	 * @param dataset
	 *            the dataset
	 */
	public void readBoundary() {
		String boundaryFile = Utils.datasetToBoundary(DATA_SET);
		try {
			FileReader reader = new FileReader(boundaryFile);
			BufferedReader in = new BufferedReader(reader);
			if (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(" ");
				minLat = Double.valueOf(parts[0]);
				minLng = Double.valueOf(parts[1]);
				maxLat = Double.valueOf(parts[2]);
				maxLng = Double.valueOf(parts[3]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * compute REGION entropy
	 * 
	 * @param datasetfile
	 *            the datasetfile
	 * @return a hashtable <row, <col, [observations]>>
	 */
	private Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> readEntropyData() {

		String workerFilePath = Utils.datasetToWorkerPath(DATA_SET);
		Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> hashTable = new Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>>();
		for (int i = 0; i < GeocrowdConstants.TIME_INSTANCE; i++) {
			try {
				FileReader file = new FileReader(workerFilePath + i + ".txt");
				BufferedReader in = new BufferedReader(file);
				while (in.ready()) {
					String line = in.readLine();
					String[] parts = line.split(",");
					Integer userID = Integer.parseInt(parts[0]);
					Double lat = Double.parseDouble(parts[1]);
					Double lng = Double.parseDouble(parts[2]);
					int row = latToRowIdx(lat);
					int col = lngToColIdx(lng);

					if (!hashTable.containsKey(row)) {
						Hashtable<Integer, Hashtable<Integer, Integer>> cols = new Hashtable<Integer, Hashtable<Integer, Integer>>();
						Hashtable<Integer, Integer> obs = new Hashtable<Integer, Integer>();
						obs.put(userID, 1);
						cols.put(col, obs);
						hashTable.put(row, cols);
					} else {
						Hashtable<Integer, Hashtable<Integer, Integer>> cols = hashTable
								.get(row);
						if (!cols.containsKey(col)) {
							Hashtable<Integer, Integer> obs = new Hashtable<Integer, Integer>();
							obs.put(userID, 1);
							cols.put(col, obs);
						} else {
							Hashtable<Integer, Integer> obs = cols.get(col);
							if (obs.containsKey(userID))
								obs.put(userID, obs.get(userID) + 1);
							else
								obs.put(userID, 1);
							cols.put(col, obs);
						}
						hashTable.put(row, cols);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hashTable;
	}

	/**
	 * Gets the col idx.
	 * 
	 * @param lng
	 *            the lng
	 * @return the col idx
	 */
	public int lngToColIdx(double lng) {
		return (int) (resolution * (lng - minLng) / (maxLng - minLng));
	}

	public double colIdxToLng(int col) {
		return col * (maxLng - minLng) / resolution + minLng;
	}

	/**
	 * Gets the row idx.
	 * 
	 * @param lat
	 *            the lat
	 * @return the row idx
	 */
	public int latToRowIdx(double lat) {
		return (int) (resolution * (lat - minLat) / (maxLat - minLat));
	}

	public double rowIdxToLat(int row) {
		return row * (maxLat - minLat) / resolution + minLat;
	}

	private Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> regionOccurances(
			Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> occurances) {

		Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> regionOccurances = new Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>>();

		/**
		 * init regionOccurances
		 */
		for (Integer row : occurances.keySet()) {
			if (!regionOccurances.containsKey(row))
				regionOccurances.put(row,
						new Hashtable<Integer, Hashtable<Integer, Integer>>());
			for (Integer col : occurances.get(row).keySet()) {
				if (!regionOccurances.get(row).containsKey(col))
					regionOccurances.get(row).put(col,
							new Hashtable<Integer, Integer>());
				for (Integer userid : occurances.get(row).get(col).keySet()) {
					if (!regionOccurances.get(row).get(col).containsKey(userid))
						regionOccurances.get(row).get(col).put(userid, 0);
				}
			}
		}

		/**
		 * For every cell, search for all cells that cover this
		 */
		for (Integer row : occurances.keySet()) {
			Hashtable<Integer, Hashtable<Integer, Integer>> cols = occurances
					.get(row);
			for (Integer col : cols.keySet()) {
				Hashtable<Integer, Integer> obs = cols.get(col);
				double lat = rowIdxToLat(row);
				double lng = colIdxToLng(col);

				for (Integer row2 : occurances.keySet()) {
					Hashtable<Integer, Hashtable<Integer, Integer>> cols2 = occurances
							.get(row2);
					for (Integer col2 : cols2.keySet()) {
						// Hashtable<Integer, Integer> obs2 = cols.get(col2);
						double lat2 = rowIdxToLat(row2);
						double lng2 = colIdxToLng(col2);

						// System.out.println(lat +" "+ lng +" "+ lat2 +" "+
						// lng2);
						if (Utils.distance(lat, lng, lat2, lng2) < GeocrowdConstants.radius) {
							// System.out.println(Utils.distance(lat, lng, lat2,
							// lng2));
							for (Integer userid : obs.keySet()) {
								if (regionOccurances.get(row2).get(col2)
										.containsKey(userid)) {
									regionOccurances
											.get(row2)
											.get(col2)
											.put(userid,
													regionOccurances.get(row2)
															.get(col2)
															.get(userid)
															+ obs.get(userid));
								} else {
									regionOccurances.get(row2).get(col2)
											.put(userid, obs.get(userid));
									// System.out.println(regionOccurances.get(row2).get(col2));
								}
							}
						}
					}
				}
			}
		}
		return regionOccurances;
	}

}
