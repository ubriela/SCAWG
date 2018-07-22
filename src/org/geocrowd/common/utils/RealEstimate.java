package org.geocrowd.common.utils;

public class RealEstimate<T> {
	T real;
	T estimate;
	
	public RealEstimate(T real, T estimate) {
		super();
		this.real = real;
		this.estimate = estimate;
	}
	public T getReal() {
		return real;
	}
	public void setReal(T real) {
		this.real = real;
	}
	public T getEstimate() {
		return estimate;
	}
	public void setEstimate(T estimate) {
		this.estimate = estimate;
	}
}
