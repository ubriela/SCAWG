package org.geocrowd.common.entropy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.geocrowd.DatasetEnum;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.common.utils.Utils;

/**
 * This class uses Mediator pattern to reduce communication complexity between multiple
 * objects or classes. This pattern provides a mediator class which normally
 * handles all the communications between different classes and supports easy
 * maintenance of the code by loose coupling. Mediator pattern falls under
 * behavioral pattern category.
 * 
 * @author ubriela
 *
 */

public class EntropyUtility {

	/**
	 * Compute location entropy for each location and save into a file.
	 * 
	 * @param hashTable
	 *            a list of locations with corresponding entropy
	 */
	public static void computeLocationEntropy(
			Hashtable<Integer, ArrayList<Observation>> hashTable, String filePath) {
		try {
			FileWriter writer = new FileWriter(filePath);
			BufferedWriter out = new BufferedWriter(writer);

			Iterator<Integer> itr = hashTable.keySet().iterator();
			while (itr.hasNext()) {
				int pointId = itr.next();
				ArrayList<Observation> obs = hashTable.get(pointId);
				int totalObservation = 0;
				double entropy = 0;
				for (Observation o : obs) {
					totalObservation += o.getObservationCount();
				}
				for (Observation o : obs) {
					int observeCount = o.getObservationCount();
					double p = (double) observeCount / totalObservation;
					entropy -= p * Math.log(p) / Math.log(2);
				}
				out.write(pointId + "," + entropy + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Save synthetic location density to a file
	 * 
	 * @param densities
	 *            the densities
	 */
	public void saveLocationDensity(
			Hashtable<Integer, Hashtable<Integer, Integer>> densities, DatasetEnum DATA_SET) {
		String locationDensityFileName = datasetToLocationDensity(DATA_SET);

		try {
			StringBuffer sb = new StringBuffer();
			FileWriter writer = new FileWriter(locationDensityFileName);
			BufferedWriter out = new BufferedWriter(writer);

			Iterator row_it = densities.keySet().iterator();
			while (row_it.hasNext()) {
				int row = (Integer) row_it.next();
				Iterator col_it = densities.get(row).keySet().iterator();
				while (col_it.hasNext()) {
					int col = (Integer) col_it.next();
					if (col_it.hasNext())
						sb.append(row + "," + col + ","
							+ densities.get(row).get(col) + "\n");
				}
			}
			out.write(sb.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String datasetToLocationDensity(DatasetEnum dataset) {
		switch (dataset) {
		case GOWALLA:
			return "dataset/real/gowalla/gowalla_loc_density.txt";
		case FOURSQUARE:
			return "dataset/real/foursquare/foursquare_loc_density.txt";
		case SKEWED:
			return "dataset/skew/skew_loc_density.txt";
		case UNIFORM:
			return "dataset/uni/uni_loc_density.txt";
		case SMALL_TEST:
			return "dataset/small/small_loc_entropy.txt";
		}
		return "";
	}
	
	public static String datasetToEntropyPath(DatasetEnum dataset) {
		switch (dataset) {
		case GOWALLA:
			return "dataset/real/gowalla/gowalla_entropy.txt";
		case FOURSQUARE:
			return "dataset/real/foursquare/foursquare_entropy.txt";
		case YELP:
			return "dataset/real/yelp/yelp_entropy.txt";
		case SKEWED:
			return "dataset/skew/skew_entropy.txt";
		case UNIFORM:
			return "dataset/uni/uni_entropy.txt";
		}
		return "";
	}
	
	/**
	 * Compute region entropy and save to a specified file
	 * 
	 * @param regionOccurances
	 */
	public static void dumpRegionEntropy(
			Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Integer>>> regionOccurances, DatasetEnum DATA_SET) {
		try {
			String entropyPath = EntropyUtility.datasetToEntropyPath(DATA_SET);
			FileWriter writerEntropy = new FileWriter(entropyPath);
			BufferedWriter outEntropy = new BufferedWriter(writerEntropy);

			String densityPath = EntropyUtility.datasetToLocationDensity(DATA_SET);
			FileWriter writerDensity = new FileWriter(densityPath);
			BufferedWriter outDensity = new BufferedWriter(writerDensity);

			for (Integer row : regionOccurances.keySet()) {
				for (Integer col : regionOccurances.get(row).keySet()) {
					Hashtable<Integer, Integer> obs = regionOccurances.get(row)
							.get(col);
					int totalObservation = 0;
					double entropy = 0;
					for (Integer val : obs.values()) {
						totalObservation += val;
					}

					for (Integer val : obs.values()) {
						if (val != 0) {
							// System.out.println(totalObservation + " " + val);
							double p = (val + 0.0) / totalObservation;
							entropy -= p * Math.log(p) / Math.log(2);
						}
					}
					
					if (entropy == 0)
						continue;
					
					outEntropy.write(row + "," + col + "," + entropy + "\n");
					outDensity.write(row + "," + col + "," + totalObservation
							+ "\n");
				}
			}
			outEntropy.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
