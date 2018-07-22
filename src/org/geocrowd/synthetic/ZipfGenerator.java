package org.geocrowd.synthetic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.math3.distribution.ZipfDistribution;


/**
 * This class provides various methods for creating different types of zipf
 * distribution
 * 
 * @author ubriela
 * 
 */
public class ZipfGenerator {

	/**
	 * increasing spreads following a Zipf distribution
	 * @param n
	 * @param range
	 * @param isInteger
	 * @return
	 */
	public static HashSet<Double> zipfIncValues(int n, boolean isInteger) {
		// TODO Auto-generated method stub
		HashSet<Double> values = new HashSet<Double>();
		ZipfDistribution zipf = new ZipfDistribution(2, 1);
		double factor = 1 / zipf.probability(n);
		for (int i = 1; i <= n; i++) {
			if (isInteger) {
				double val = (double) Math.round(zipf.probability(i) * factor);
				values.add(val);
			}
			else
				values.add(zipf.probability(i) * factor);
		}
		return values;
	}
	

	/**
	 * decreasing spreads following a Zipf distribution, computed from zipf_inc
	 * @param n
	 * @param isInteger
	 * @return
	 */
	public static HashSet<Double> zipfDecValues(int n, boolean isInteger) {
		// TODO Auto-generated method stub
		HashSet<Double> values = new HashSet<Double>();
		HashSet<Double> zipfInc = zipfIncValues(n, isInteger);
		Iterator<Double> it = zipfInc.iterator();
		while (it.hasNext()) {
			values.add(n + 1 - it.next());
		}
		return values;
	}

	/**
	 * zipf_inc for the first n/2 elements followed by zipf_dec
	 * @param n
	 * @param isInteger
	 * @return
	 */
	public static HashSet<Double> zipfCuspMin(int n, boolean isInteger) {
		// TODO Auto-generated method stub
		HashSet<Double> zipfInc = zipfIncValues(n/2, isInteger);
		HashSet<Double> zipfDec = zipfDecValues(n/2, isInteger);
		
		HashSet<Double> values = new HashSet<Double>();
		
		//	add all values of the first hashset to the result
		Iterator<Double> itInc = zipfInc.iterator();
		while (itInc.hasNext()) {
			values.add(itInc.next());
		}
		
		//	find max value of the first hashset
		Double max = Collections.max(zipfInc);
		
		//	add all modified values of the second hashset to the result
		Iterator<Double> itDec = zipfDec.iterator();
		while (itDec.hasNext()) {
			values.add(max + itDec.next());
		}
		
		return values;
	}
	

	/**
	 * zipf_dec for the first n/2 elements followed by zipf_inc
	 * @param n
	 * @param isInteger
	 * @return
	 */
	public static HashSet<Double> zipfCuspMax(int n, boolean isInteger) {
		// TODO Auto-generated method stub
		HashSet<Double> zipfDec = zipfDecValues(n/2, isInteger);
		HashSet<Double> zipfInc = zipfIncValues(n/2, isInteger);
		
		HashSet<Double> values = new HashSet<Double>();
		
		//	add all values of the second hashset to the result
		Iterator<Double> itDec = zipfDec.iterator();
		while (itDec.hasNext()) {
			values.add(itDec.next());
		}
		
		//	find max value of the first hashset
		Double max = Collections.max(zipfInc);
		
		//	add all modified values of the first hashset to the result
		Iterator<Double> itInc = zipfInc.iterator();
		while (itInc.hasNext()) {
			values.add(max + itInc.next());
		}
		
		return values;
	}

	/**
	 * spreads following a Zipf distribution and randomly assigned to attribute values
	 * @param n
	 * @param isInteger
	 * @return
	 */
	public static HashSet<Double> zipfRan(int n, boolean isInteger) {
		// TODO Auto-generated method stub
		HashSet<Double> zipfInc = zipfIncValues(n, isInteger);
		Set sortedValues = new TreeSet(zipfInc);
		
		//	find the differences between two consecutive values
		Vector<Double> diffs = new Vector<Double>();
		Iterator it = sortedValues.iterator();
		Double last = (Double) it.next();
		while (it.hasNext()) {
			Double current = (Double) it.next();
			diffs.add(current - last);
			last = current;
		}
		
		//	shuffle the differences
		Random r = new Random(System.nanoTime());
		Collections.shuffle(diffs, r);
		
		//	create result from the shuffled list
		HashSet<Double> values = new HashSet<>();
		last = 0.0;
		for (int i = 0; i < diffs.size(); i++) {
			last += diffs.get(i);
			values.add(last);
		}
		return values;
	}

}
