package org.geocrowd.dtype;

public class ValueRank <T> implements Comparable<ValueRank> {
	T value;
	double rank;	//	or weighted
	public ValueRank(T value, int rank) {
		super();
		this.value = value;
		this.rank = rank;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(ValueRank vr) {
		// TODO Auto-generated method stub
		if ((double)(Double) value > (double)(Double) vr.getValue()) {
			return 1;
		} else if ((double)(Double) value == (double)(Double) vr.getValue()) {
			return 0;
		} else {
			return -1;
		}
	}
}
