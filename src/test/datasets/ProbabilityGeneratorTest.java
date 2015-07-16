package test.datasets;

import static org.junit.Assert.*;

import org.geocrowd.datasets.synthetic.ProbabilityGenerator;
import org.junit.Test;


public class ProbabilityGeneratorTest {

	@Test
	public final void test() {
		Double[] N = { new Double(1), new Double(2), new Double(3),
				new Double(4) };
		int[] M = new int[N.length];
		ProbabilityGenerator<Double> pg = new ProbabilityGenerator(N);
		for (int i = 0; i < 100000; i++)
			M[pg.nextValue()]++;

		for (int i : M)
			System.out.println(i);
	}
}