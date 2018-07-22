//http://diveintodata.org/2009/09/13/zipf-distribution-generator-in-java/
package org.geocrowd.synthetic;

import java.util.Random;

/**
 * Zipf distribution with a skew factor
 * @author HT186010
 *
 */
public class ZipfDistribution_bak {
	private Random rnd = new Random(System.nanoTime());
	private int size;
	private double skew;
	private double bottom = 0;

	public ZipfDistribution_bak(int size, double skew) {
		this.size = size;
		this.skew = skew;

		for (int i = 1; i < size; i++) {
			this.bottom += (1 / Math.pow(i, this.skew));
		}
	}

	// the next() method returns an rank id. The frequency of returned rank ids
	// are follows Zipf distribution.
	public int next() {
		int rank;
		double friquency = 0;
		double dice;

		rnd.setSeed(System.nanoTime());
		rank = rnd.nextInt(size);
		friquency = (1.0d / Math.pow(rank, this.skew)) / this.bottom;
		rnd.setSeed(System.nanoTime());
		dice = rnd.nextDouble();

		while (!(dice < friquency)) {
			rnd.setSeed(System.nanoTime());
			rank = rnd.nextInt(size);
			rnd.setSeed(System.nanoTime());
			friquency = (1.0d / Math.pow(rank, this.skew)) / this.bottom;
			dice = rnd.nextDouble();
		}

		return rank;
	}

	// This method returns a probability that the given rank occurs.
	public double getProbability(int rank) {
		return (1.0d / Math.pow(rank, this.skew)) / this.bottom;
	}

	public static void main(String[] args) {
		ZipfDistribution_bak zipf = new ZipfDistribution_bak(2, .5);
		for (int i = 1; i <= 20; i++)
			System.out.println(zipf.getProbability(i));
	}
}