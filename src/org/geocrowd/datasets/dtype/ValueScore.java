package org.geocrowd.datasets.dtype;

public class ValueScore<T> implements Comparable<ValueScore<T>> {
	private T value;
	private double score;

	public ValueScore(T value, double score) {
		super();
		this.value = value;
		this.score = score;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public int compareTo(ValueScore<T> o) {
		// sort by score in descending order
		if (o.score > this.score)
			return 1;
		if (o.score == this.score)
			return 0;
		else
			return -1;
	}
}
