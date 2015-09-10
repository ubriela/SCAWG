package org.geocrowd.datasets.synthetic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import org.geocrowd.dtype.ValueFreq;


public class ValueFileReader {
	public String filePath = "";
	public double min, max;

	public ValueFileReader(String filePath) {
		super();
		this.filePath = filePath;
		min = Double.MAX_VALUE;
		max = -Double.MAX_VALUE;
	}

	/**
	 * Read values line by line
	 * 
	 * @return
	 */
	public Vector<Double> parse() {
		Vector<Double> values = new Vector<Double>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				final double value = Double.parseDouble(strLine);

				// update min, max
				if (min > value)
					min = value;
				if (max < value) {
					max = value;
				}

				values.add(value);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return values;
	}

	/**
	 * Read values and their frequencies line by line
	 * 
	 * @return
	 */
	public List<ValueFreq<Double>> parse2() {
		List<ValueFreq<Double>> valueFreqs = new Vector<ValueFreq<Double>>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				String[] vf = strLine.split("\t");
				double value = Double.valueOf(vf[0]);
				int freq = Integer.valueOf(vf[1]);

				// update min, max
				if (min > value)
					min = value;
				if (max < value) {
					max = value;
				}

				valueFreqs.add(new ValueFreq<Double>(value, freq));
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return valueFreqs;
	}
}