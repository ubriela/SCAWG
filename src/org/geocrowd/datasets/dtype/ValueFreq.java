package org.geocrowd.datasets.dtype;

/**
 * This class store a pair of biased value and its frequency. It allows sorting
 * a list of biased values by frequency
 * 
 * @author HT186010
 * 
 * @param <T>
 */
public class ValueFreq<T> implements Comparable<ValueFreq<T>> {
	private T value;
	private int freq;

	public ValueFreq(T value, int freq) {
		super();
		this.value = value;
		this.freq = freq;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	@Override
	public int compareTo(ValueFreq<T> o) {
		// TODO Auto-generated method stub
		return (o.freq - this.freq); // sort by freq in descending order
	}
}
