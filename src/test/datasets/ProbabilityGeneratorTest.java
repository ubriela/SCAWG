package test.datasets;

import org.geocrowd.synthetic.RouletteWheelGenerator;
import org.junit.Test;


public class ProbabilityGeneratorTest {

	@Test
	public final void test() {
		Double[] N = { new Double(1), new Double(2), new Double(3),
				new Double(4) };
		int[] M = new int[N.length];
		RouletteWheelGenerator<Double> pg = new RouletteWheelGenerator(N);
		for (int i = 0; i < 100000; i++)
			M[pg.nextValue()]++;

		for (int i : M)
			System.out.println(i);
	}
}