//	http://lembra.wordpress.com/2011/08/02/sum-mean-median-and-standard-deviation-using-lists-in-java/

package org.geocrowd.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

import org.geocrowd.dtype.ValueFreq;
import org.geocrowd.dtype.ValueRank;
import org.geocrowd.dtype.ValueScore;

/**
 * Includes functions for statistic
 * 
 * @author HT186010
 * 
 */
public class Stats<T> {
	public double sum(List<T> a) {
		if (a.size() > 0) {
			double sum = 0;

			for (T i : a) {
				try {
					sum += Double.parseDouble(i.toString());
				} catch (Exception e) {
					System.out.println("sum " + i.toString());
				}
			}
			return sum;
		}
		return 0;
	}

	public double mean(List<T> a) {
		double sum = sum(a);
		double mean = 0;

		if (sum > 0) {
			mean = sum / (a.size() * 1.0);
		}
		return mean;
	}

	public double median(List<T> a) {
		int middle = a.size() / 2;

		if (a.size() % 2 == 1) {
			return (double) (Double) a.get(middle);
		} else {
			return ((double) (Double) a.get(middle - 1) + (double) (Double) a
					.get(middle)) / 2.0;
		}
	}

	public double variancePopulation(List<T> a) {
		double sum = 0;
		double mean = mean(a);

		for (T i : a)
			sum += Math.pow(Double.parseDouble(i.toString()) - mean, 2);
		return sum / a.size();
	}

	public double varianceSample(List<T> a) {
		double sum = 0;
		double mean = mean(a);

		for (T i : a)
			sum += Math.pow(((double) (Double) i - mean), 2);
		return sum / (a.size() - 1);
	}

	/**
	 * Return weight mean, weight is frequency
	 * http://en.wikipedia.org/wiki/Weighted_variance#Weighted_sample_variance
	 * 
	 * @param list
	 * @return
	 */
	public double weightedMean(List<ValueFreq> list) {
		double wm = 0.0;
		double sumOfWeight = 0.0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ValueFreq vr = (ValueFreq) it.next();
			double weight = vr.getFreq();
			sumOfWeight += weight;
			wm += Double.parseDouble(vr.getValue().toString()) * weight;
		}
		return wm / sumOfWeight;
	}

	/**
	 * Return the weighted mean, of a list such that its' weights equal to its
	 * values
	 * 
	 * @param list
	 * @return
	 */
	public double weightedMeanSelf(List<T> list) {
		double wm = 0.0;
		double sumOfWeight = 0.0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			double value = Double.parseDouble(it.next().toString());
			double weight = value;
			sumOfWeight += weight;
			wm += value * weight;
		}
		return wm / sumOfWeight;
	}

	/**
	 * Return bias factor of a frequency list
	 * 
	 * @param valueFreqs
	 * @return
	 */
	public double biasFactor(List<T> list) {
		// TODO Auto-generated method stub
		// System.out.println(entropyD(probsFromValues(list)));
		return Math.pow(2, Math.log(list.size()) / Math.log(2)
				- entropy(probsFromValues(list))) - 1;
	}

	/**
	 * Calculate weighted variance
	 * 
	 * @param list
	 * @return
	 */
	public double weightedVarianceSample(List<ValueFreq> list) {
		// weighted mean
		double wm = weightedMean(list);

		// calculate sum of weight
		double sumOfWeight = 0.0;

		// weighted variance
		double variance = 0.0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ValueFreq vr = (ValueFreq) it.next();
			double weight = vr.getFreq();
			sumOfWeight += weight;
			variance += weight
					* Math.pow(Double.parseDouble(vr.getValue().toString())
							- wm, 2);
		}
		return variance / (sumOfWeight - 1);
	}

	public double sdPopulation(List<T> a) {
		double sum = 0;
		double mean = mean(a);

		for (T i : a)
			sum += Math.pow(((double) (Double) i - mean), 2);
		return Math.sqrt(sum / a.size()); // population
	}

	public double sdSample(List<T> a) {
		double sum = 0;
		double mean = mean(a);

		for (T i : a)
			sum += Math.pow(((double) (Double) i - mean), 2);
		return Math.sqrt(sum / (a.size() - 1)); // sample
	}

	/**
	 * Values are sorted
	 * 
	 * @param values
	 * @return
	 */
	public int distinctCount(T[] values) {
		if (values == null || values.length == 0)
			return 0;
		int repeated = 0;
		T last;
		last = values[0];
		for (int i = 1; i < values.length; i++) {
			T tmp = values[i];
			if ((double) (Double) tmp == (double) (Double) last)
				repeated++;
			last = tmp;
		}
		return values.length - repeated;
	}

	public int emptyCount(List<Integer> a) {
		int count = 0;
		Iterator<Integer> it = a.iterator();
		while (it.hasNext()) {
			Integer i = it.next();
			if (i == 0)
				count++;
		}
		return count;
	}

	public int maxInteger(List<Integer> a) {
		Iterator<Integer> it = a.iterator();
		int max = Integer.MIN_VALUE;
		while (it.hasNext()) {
			Integer i = it.next();
			if (i > max) {
				max = i;
			}
		}
		return max;
	}

	public double max(List<T> a) {
		Iterator<T> it = a.iterator();
		double max = Double.MIN_VALUE;
		while (it.hasNext()) {
			T i = it.next();
			if ((Double) i > max) {
				max = (Double) i;
			}
		}
		return max;
	}

	/**
	 * Finds and returns the biggest int in an int[]
	 * 
	 * @param ints
	 * @return
	 */
	public static int maxInt(int[] ints) {
		int len = ints.length;
		int max = ints[0];
		for (int i = 1; i < len; i++) {
			if (ints[i] > max)
				max = ints[i];
		}
		return max;
	}

	/**
	 * Returns the first index containing the maximum value.
	 * 
	 * @param ints
	 * @return
	 */
	public static int findFirstMaxIntIndex(int[] ints) {
		int max = ints[0];
		int maxIndex = 0;
		for (int i = 1; i < ints.length; i++) {
			if (ints[i] > max) {
				max = ints[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	/**
	 * Count the number of distinct values
	 * 
	 * @param values
	 * @return
	 */
	public static int distinctCount(ArrayList values) {
		if (values == null || values.size() == 0)
			return 0;
		Iterator it = values.iterator();
		int repeated = 0;
		double last = 0;
		if (it.hasNext())
			last = (double) (Double) it.next();
		while (it.hasNext()) {
			double tmp = (double) (Double) it.next();
			if (tmp < last)
				System.out.println("not sorted!!");
			if ((double) tmp == (double) last)
				repeated++;
			last = tmp;
		}
		return values.size() - repeated;
	}

	/**
	 * Get the top freq values from a list fo values
	 * 
	 * @param values
	 * @param bias
	 * @return
	 */
	public Vector<ValueFreq<T>> getTopFreq(T[] values, int bias) {
		Vector<ValueFreq<T>> valuesFreqs = new Vector<ValueFreq<T>>();
		T last = values[0];
		int freq = 0;
		for (T val : values) {
			if ((double) (Double) last == (double) (Double) val) {
				freq++;
			} else {
				valuesFreqs.add(new ValueFreq<T>(last, freq));
				freq = 1;
			}
			last = val;
		}

		valuesFreqs.add(new ValueFreq<T>(last, freq));

		// sort by freq
		Collections.sort(valuesFreqs);

		// get top freq
		// if number of elements <= 200 --> all are bias values
		if (values.length <= bias)
			return valuesFreqs;
		else
		// get the top bias freq
		{
			Vector<ValueFreq<T>> biasValues = new Vector<ValueFreq<T>>();
			for (int i = 0; i < bias; i++)
				biasValues.add(valuesFreqs.get(i));
			return biasValues;
		}
	}

	/**
	 * Get value-frequency list from a list of values, these values should be
	 * sorted
	 * 
	 * @param values
	 * @return
	 */
	public List<ValueFreq<T>> getValueFreqs(T[] values) {
		LinkedList<ValueFreq<T>> valueFreqs = new LinkedList<ValueFreq<T>>();
		T last = values[0];
		int freq = 0;
		for (T val : values) {
			if ((double) (Double) last == (double) (Double) val) {
				freq++;
			} else {
				valueFreqs.add(new ValueFreq<T>(last, freq));
				freq = 1;
			}
			last = val;
		}

		valueFreqs.add(new ValueFreq<T>(last, freq));
		return valueFreqs;
	}

	/**
	 * Get value/freq from a list instead of an array
	 * 
	 * @param values
	 * @return
	 */
	public List<ValueFreq<T>> getValueFreqs2(List<T> values) {
		LinkedList<ValueFreq<T>> valueFreqs = new LinkedList<ValueFreq<T>>();
		T last = values.get(0);
		int freq = 0;
		for (T val : values) {
			if ((double) (Double) last == (double) (Double) val) {
				freq++;
			} else {
				valueFreqs.add(new ValueFreq<T>(last, freq));
				freq = 1;
			}
			last = val;
		}

		valueFreqs.add(new ValueFreq<T>(last, freq));
		return valueFreqs;
	}

	/**
	 * Get frequency list from a list of values, these values should be sorted
	 * 
	 * @param values
	 * @return
	 */
	public List<Integer> getValueFreqs(List<T> values) {
		LinkedList<Integer> freqs = new LinkedList<Integer>();
		T last = values.get(0);
		int freq = 0;
		for (T val : values) {
			if ((double) (Double) last == (double) (Double) val) {
				freq++;
			} else {
				freqs.add(freq);
				freq = 1;
			}
			last = val;
		}

		freqs.add(freq);
		return freqs;
	}

	/**
	 * Get the top freq values from a list of value-frequencies
	 * 
	 * @param values
	 * @param bias
	 * @return
	 */
	public List<ValueFreq<T>> getTopFreqsUsingPriorityQueue(
			List<ValueFreq<T>> valueFreqs, int bias) {
		// sort by value
		PriorityQueue<ValueFreq<T>> pq = new PriorityQueue<ValueFreq<T>>(bias,
				new Comparator<ValueFreq<T>>() {
					public int compare(ValueFreq bv1, ValueFreq bv2) {
						return bv1.getFreq() - bv2.getFreq();
					}
				});

		// maintain a priority query
		for (int i = 0; i < valueFreqs.size(); i++) {
			if (i < bias) {
				pq.add(valueFreqs.get(i));
			} else {
				pq.add(valueFreqs.get(i));
				pq.poll();
			}
		}

		LinkedList<ValueFreq<T>> biasValues = new LinkedList<ValueFreq<T>>();
		Iterator it = pq.iterator();
		while (it.hasNext()) {
			ValueFreq<T> bv = (ValueFreq<T>) it.next();
			biasValues.add(new ValueFreq<T>(bv.getValue(), bv.getFreq()));
		}
		return biasValues;
	}

	public List<ValueScore<T>> getTopScoresUsingPriorityQueue(
			List<ValueScore<T>> valueScores, int bias) {
		// sort by value
		PriorityQueue<ValueScore<T>> pq = new PriorityQueue<ValueScore<T>>(
				bias, new Comparator<ValueScore<T>>() {
					public int compare(ValueScore bv1, ValueScore bv2) {
						if (bv1.getScore() - bv2.getScore() > 0)
							return 1;
						else if (bv1.getScore() == bv2.getScore())
							return 0;
						else
							return -1;
					}
				});

		// maintain a priority query
		for (int i = 0; i < valueScores.size(); i++) {
			if (i < bias) {
				pq.add(valueScores.get(i));
			} else {
				pq.add(valueScores.get(i));
				pq.poll();
			}
		}

		LinkedList<ValueScore<T>> topValueScores = new LinkedList<ValueScore<T>>();
		Iterator it = pq.iterator();
		while (it.hasNext()) {
			ValueScore<T> bv = (ValueScore<T>) it.next();
			topValueScores.add(new ValueScore<T>(bv.getValue(), bv.getScore()));
		}
		return topValueScores;
	}

	/**
	 * Get the top values from a list of value-frequencies
	 * 
	 * @param values
	 * @param bias
	 * @return
	 */
	public List<ValueFreq<T>> getTopValueUsingPriorityQueue(
			List<ValueFreq<T>> valueFreqs, int bias) {
		// sort by values
		PriorityQueue<ValueFreq<T>> pq = new PriorityQueue<ValueFreq<T>>(bias,
				new Comparator<ValueFreq<T>>() {
					public int compare(ValueFreq bv1, ValueFreq bv2) {
						if ((double) (Double) bv1.getValue() < (double) (Double) bv2
								.getValue())
							return -1;
						else if ((double) (Double) bv1.getValue() == (double) (Double) bv2
								.getValue())
							return 0;
						else
							return 1;
					}
				});

		// maintain a priority queue
		for (int i = 0; i < valueFreqs.size(); i++) {
			if (i < bias) {
				pq.add(valueFreqs.get(i));
			} else {
				pq.add(valueFreqs.get(i));
				pq.poll();
			}
		}

		LinkedList<ValueFreq<T>> biasValues = new LinkedList<ValueFreq<T>>();
		Iterator it = pq.iterator();
		while (it.hasNext()) {
			ValueFreq<T> bv = (ValueFreq<T>) it.next();
			biasValues.add(new ValueFreq<T>(bv.getValue(), bv.getFreq()));
		}
		return biasValues;
	}

	/**
	 * Calculate sum square error of a list
	 * 
	 * @param valueFreqs
	 * @return
	 */
	public double SSE(List<T> freqs) {
		double sum = 0;
		double mean = mean(freqs);

		for (T i : freqs)
			sum += Math.pow(Double.parseDouble(i.toString()) - mean, 2);
		return sum;
	}

	/**
	 * Calculate mean square error of a list
	 * 
	 * @param valueFreqs
	 * @return
	 */
	// public double MSE(List<T> freqs) {
	// double sum = 0;
	// double mean = mean(freqs);
	//
	// for (T i : freqs)
	// sum += Math.pow(Double.parseDouble(i.toString()) - mean, 2);
	// return sum / freqs.size();
	// }

	/**
	 * Calculate a frequency list
	 * 
	 * @param vf
	 * @return
	 */
	public List<Integer> getFreqs(List<ValueFreq<T>> vf) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator it = vf.iterator();
		while (it.hasNext()) {
			ValueFreq bv = (ValueFreq) it.next();
			list.add(bv.getFreq());
		}
		return list;
	}

	/**
	 * Calculate a value list
	 * 
	 * @param vf
	 * @return
	 */
	public List<T> getDistinctValues(List<ValueFreq<T>> vf) {
		ArrayList<T> list = new ArrayList<T>();
		Iterator it = vf.iterator();
		while (it.hasNext()) {
			ValueFreq<T> bv = (ValueFreq) it.next();
			list.add(bv.getValue());
		}
		return list;
	}

	/**
	 * Return all values
	 * 
	 * @param vf
	 * @return
	 */
	public List<T> getValues(List<ValueFreq<T>> vf) {
		ArrayList<T> list = new ArrayList<T>();
		Iterator it = vf.iterator();
		while (it.hasNext()) {
			ValueFreq<T> bv = (ValueFreq) it.next();
			for (int i = 0; i < bv.getFreq(); i++)
				list.add(bv.getValue());
		}
		return list;
	}

	/**
	 * Return max difference between two succeeding values
	 * 
	 * @param vf
	 * @return
	 */
	public double getMaxDiff(List<T> values) {
		double maxDiff = Double.MIN_VALUE;
		double lastVal = 0.0;
		Iterator it = values.iterator();
		while (it.hasNext()) {
			double val = (Double) it.next();
			double diff = Math.abs(val - lastVal);
			if (maxDiff < diff) {
				maxDiff = diff;
			}
			lastVal = val;
		}
		return maxDiff;
	}

	public List<Integer> getFreqs(List<ValueFreq<T>> valueFreqs, int start,
			int end) {
		List<ValueFreq<T>> tmp_valueFreqs = valueFreqs.subList(start, end);
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator it = tmp_valueFreqs.iterator();
		while (it.hasNext()) {
			ValueFreq<T> bv = (ValueFreq<T>) it.next();
			list.add(bv.getFreq());
		}
		return list;
	}

	/**
	 * Get the probabilities from a list of frequencies
	 * 
	 * @param freqs
	 * @return
	 */
	public List<Double> probsFromFreqs(List<Integer> freqs) {
		ArrayList<Double> probs = new ArrayList<Double>();
		Stats<Integer> stat = new Stats<Integer>();
		double sum = stat.sum(freqs);
		Iterator it = freqs.iterator();
		while (it.hasNext()) {
			double p = Double.parseDouble(it.next().toString()) / sum;
			probs.add(p);
		}
		return probs;
	}

	/**
	 * Get the probabilities from a list of frequencies
	 * 
	 * @param freqs
	 * @return
	 */
	public List<Double> probsFromValues(List<T> values) {
		ArrayList<Double> probs = new ArrayList<Double>();
		double sum = sum(values);
		Iterator it = values.iterator();
		while (it.hasNext()) {
			double p = Double.parseDouble(it.next().toString()) / sum;
			probs.add(p);
		}
		return probs;
	}

	/**
	 * Calculate entropy of a list of probabilities
	 * 
	 * @param freqs
	 * @return
	 */
	public double entropy(List<Double> probs) {
		double entropy = 0.0;
		Iterator it = probs.iterator();
		while (it.hasNext()) {
			double p = (Double) it.next();
			if (p != 0) // if p = 0 --> log (0) = NAN
				entropy -= p * Math.log(p) / Math.log(2);
		}
		return entropy;
	}

	/**
	 * Calculate sum of expected squared error
	 * 
	 * @param current_probs
	 * @param selectivity
	 * @return
	 */
	public double ESE(List<Double> current_probs, double selectivity) {
		// TODO Auto-generated method stub
		Iterator it = current_probs.iterator();
		double sumOfSquareError = 0.0;
		while (it.hasNext()) {
			double p = (Double) it.next();
			sumOfSquareError += p * Math.pow(p - selectivity, 2);
		}
		return sumOfSquareError;
	}

	/**
	 * Entropy of two values
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double entropyTwoValues(double x, double y) {
		// TODO Auto-generated method stub
		return -(x / (x + y)) * Math.log(x / (x + y)) / Math.log(2)
				- (y / (x + y)) * Math.log(y / (x + y)) / Math.log(2);
	}

	/**
	 * Entropy of two probabilities
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public double entropyTwoProbs(double p1, double p2) {
		// TODO Auto-generated method stub
		return -p1 * Math.log(p1) / Math.log(2) - p2 * Math.log(p2)
				/ Math.log(2);
	}

	/**
	 * Calculate skewness The higher value, the higher the skew is
	 * 
	 * @param list
	 * @return
	 */
	public double skewness(List<T> list) {
		double skew = 0.0;
		double mean = mean(list);
		double sd = sdPopulation(list);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			T t = (T) it.next();
			skew += Math.pow((Double.parseDouble(t.toString()) - mean) / sd, 3);
		}
		return skew / (list.size() - 1);
	}

	/**
	 * Check the values are skew or not
	 * http://www.tc3.edu/instruct/sbrown/stat/shape.htm
	 * http://web.simmons.edu/~benoit/lis403/6.descriptive_stats.pdf
	 * 
	 * The test statistic tells you whether the whole population is probably
	 * skewed, but not by how much: the bigger the number, the higher the
	 * probability.
	 * 
	 * If value < -2, the population is very likely skewed negatively (though
	 * you don�t know by how much).
	 * 
	 * If value is between -2 and +2, you can�t reach any conclusion about the
	 * skewness of the population: it might be symmetric, or it might be skewed
	 * in either direction.
	 * 
	 * If value > 2, the population is very likely skewed positively (though you
	 * don�t know by how much).
	 * 
	 * @param list
	 * @return
	 */
	public double skewStatistic(List<T> list) {
		double N = list.size();
		double skew = skewness(list);
		double SES = Math.sqrt((6 * N * (N - 1))
				/ ((N - 2) * (N + 1) * (N + 3)));
		return skew / SES;
	}

	/**
	 * Measure the nonuniform of data distribution
	 * 
	 * @param list
	 * @return
	 */
	public double nonUniformK(List<T> list) {
		double N = list.size();
		double maxEntropy = Math.log(N) / Math.log(2);
		double entropy = entropy(probsFromValues(list));
		if (maxEntropy <= entropy)
			return 0.0;
		return Math.pow(2, maxEntropy - entropy) - 1;
	}

	/**
	 * Calculate mean square eror
	 * 
	 * http://en.wikipedia.org/wiki/Mean_squared_error
	 * 
	 * @param values
	 * @return
	 */
	public double MSE(List<RealEstimate<T>> values) {
		double mse = 0.0; // mean squared error
		Iterator it = values.iterator();
		while (it.hasNext()) {
			RealEstimate<T> re = (RealEstimate<T>) it.next();
			mse += Math.pow(
					Double.parseDouble(re.real.toString())
							- Double.parseDouble(re.estimate.toString()), 2);
		}
		mse /= values.size();
		return mse;
	}

	/**
	 * Calculate the root-mean-square error (RMSE)
	 * 
	 * http://en.wikipedia.org/wiki/Root_mean_square_deviation
	 * 
	 * @param real
	 * @param estimate
	 * @return
	 */
	public double RMSE(List<RealEstimate<T>> values) {
		return Math.sqrt(MSE(values));
	}

	/**
	 * RMSD divided by the range of observed values
	 * 
	 * @param values
	 * @return
	 */
	public double normRMSE(List<RealEstimate<T>> values) {
		if (values.size() == 0)
			return 0;
		double mse = 0.0; // mean squared error
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		Iterator it = values.iterator();
		while (it.hasNext()) {
			RealEstimate<T> re = (RealEstimate<T>) it.next();
			mse += Math.pow(
					Double.parseDouble(re.real.toString())
							- Double.parseDouble(re.estimate.toString()), 2);

			// update max & min
			if (Double.parseDouble(re.real.toString()) > max)
				max = Double.parseDouble(re.real.toString());
			if (Double.parseDouble(re.real.toString()) < min)
				min = Double.parseDouble(re.real.toString());
		}
		mse /= values.size();
		return Math.sqrt(mse) / (max - min);
	}

	/**
	 * http://en.wikipedia.org/wiki/Mean_absolute_error
	 * 
	 * Calculate mean absolute error
	 * 
	 * @param values
	 * @return
	 */
	public double meanAbsError(List<RealEstimate<T>> values) {
		double mae = 0.0; // mean absolute error
		Iterator it = values.iterator();
		while (it.hasNext()) {
			RealEstimate<T> re = (RealEstimate<T>) it.next();
			mae += Math.abs(Double.parseDouble(re.real.toString())
					- Double.parseDouble(re.estimate.toString()));
		}
		return mae / values.size();
	}

	/**
	 * http://en.wikipedia.org/wiki/Approximation_error \
	 * 
	 * Calculate total relative error
	 * 
	 * @param values
	 * @return
	 */
	public double totalRelativeError(List<RealEstimate<T>> values) {
		double totalRE = 0.0;
		Iterator it = values.iterator();
		while (it.hasNext()) {
			RealEstimate<T> re = (RealEstimate<T>) it.next();
			totalRE += Math
					.abs((Double.parseDouble(re.real.toString()) - Double
							.parseDouble(re.estimate.toString()))
							/ Double.parseDouble(re.real.toString()));
		}
		return totalRE;
	}

	/**
	 * Residual sum of squares
	 * http://en.wikipedia.org/wiki/Residual_sum_of_squares
	 * 
	 * @param values
	 * @return
	 */
	public double RSS(List<RealEstimate<T>> values) {
		double rss = 0.0;
		Iterator it = values.iterator();
		while (it.hasNext()) {
			RealEstimate<T> re = (RealEstimate<T>) it.next();
			rss += Math.pow((Double) re.getEstimate() - (Double) re.getReal(),
					2);
		}
		return rss;
	}

	/**
	 * Calculate sample Person correlation of two random variable from their
	 * samples
	 * 
	 * http://en.wikipedia.org/wiki/Pearson_product-
	 * moment_correlation_coefficient
	 * 
	 * @param X
	 * @param Y
	 * @return
	 */
	public double personCC(List<T> X, List<T> Y) {
		double cc = 0.0; // correlation coefficient
		double meanX = mean(X);
		double meanY = mean(Y);
		double ssdX = sdSample(X); // sample standard deviation
		double ssdY = sdSample(Y);

		Iterator itX = X.iterator();
		Iterator itY = Y.iterator();
		int size = 0;
		while (itX.hasNext() && itY.hasNext()) {
			T x = (T) itX.next();
			T y = (T) itY.next();
			double ssX = ((Double) x - meanX) / ssdX; // standard score
			double ssY = ((Double) y - meanY) / ssdY;

			cc += ssX * ssY;
			size++;
		}
		return cc / (size - 1);
	}

	/**
	 * Measure of statistical dependence between two variables
	 * 
	 * http://en.wikipedia.org/wiki/Spearman's_rank_correlation_coefficient
	 * 
	 * @param X
	 * @param Y
	 * @return
	 */
	public double spearmanCC(List<T> X, List<T> Y) {
		LinkedList<ValueRank> valueRankX = new LinkedList<ValueRank>();
		LinkedList<ValueRank> valueRankY = new LinkedList<ValueRank>();
		Iterator itX = X.iterator();
		Iterator itY = Y.iterator();
		int size = 0;
		while (itX.hasNext() && itY.hasNext()) {
			size++;
			valueRankX.add(new ValueRank<T>((T) itX.next(), size));
			valueRankY.add(new ValueRank<T>((T) itY.next(), size));
		}

		Collections.sort(valueRankX);
		Collections.sort(valueRankY);

		itX = valueRankX.iterator();
		itY = valueRankY.iterator();

		LinkedList<Double> rankX = new LinkedList<Double>();
		LinkedList<Double> rankY = new LinkedList<Double>();
		while (itX.hasNext() && itY.hasNext()) {
			ValueRank vrx = (ValueRank) itX.next();
			ValueRank vry = (ValueRank) itY.next();
			rankX.add(vrx.getRank());
			rankY.add(vry.getRank());
		}

		Stats<Double> stat = new Stats<Double>();
		return stat.personCC(rankX, rankY);
	}

	/**
	 * Total area of a distribution
	 * 
	 * @param valueFreqs
	 * @return
	 */
	public double area(List<ValueFreq<T>> valueFreqs) {
		double totalArea = 0.0;
		Iterator it = valueFreqs.iterator();
		T lastValue = null;
		int lastFreq = 0;
		if (it.hasNext()) {
			ValueFreq<T> vf = (ValueFreq<T>) it.next();
			lastValue = vf.getValue();
			lastFreq = vf.getFreq();
		}
		while (it.hasNext()) {
			ValueFreq<T> vf = (ValueFreq<T>) it.next();

			totalArea += 0.5
					* (vf.getFreq() + lastFreq)
					* ((Double) vf.getValue() - Double.valueOf(lastValue
							.toString()));
			lastValue = vf.getValue();
			lastFreq = vf.getFreq();
		}
		return totalArea;

	}

	public List<Double> areas(List<ValueFreq<T>> valueFreqs) {
		List<Double> areas = new ArrayList<Double>();
		Iterator it = valueFreqs.iterator();
		T lastValue = null;
		int lastFreq = 0;
		if (it.hasNext()) {
			ValueFreq<T> vf = (ValueFreq<T>) it.next();
			lastValue = vf.getValue();
			lastFreq = vf.getFreq();
		}
		while (it.hasNext()) {
			ValueFreq<T> vf = (ValueFreq<T>) it.next();

			double area = 0.5
					* (vf.getFreq() + lastFreq)
					* ((Double) vf.getValue() - Double.valueOf(lastValue
							.toString()));
			areas.add(area);
			lastValue = vf.getValue();
			lastFreq = vf.getFreq();
		}
		return areas;
	}

	/**
	 * Get sum of all freqs
	 * 
	 * @param valueFreqs
	 * @return
	 */
	public int totalNumbers(List<ValueFreq<T>> valueFreqs) {
		int totalNumber = 0;
		Iterator it = valueFreqs.iterator();
		while (it.hasNext()) {
			ValueFreq vf = (ValueFreq) it.next();
			totalNumber += vf.getFreq();
		}
		return totalNumber;
	}
}