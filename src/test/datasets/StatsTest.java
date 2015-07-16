package test.datasets;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.geocrowd.common.utils.Stats;
import org.geocrowd.datasets.dtype.DataTypeEnum;
import org.geocrowd.datasets.dtype.ValueFreq;
import org.geocrowd.datasets.synthetic.DataProvider;
import org.junit.Test;


public class StatsTest {

	@Test
	public void testStat() {
		Stats<Double> stat = new Stats<Double>();
		Double[] x = {2.0, 2.0, 4.0, 5.0, 5.0, 5.0};
		List<Double> X = Arrays.asList(x);
		System.out.println(stat.varianceSample(X));
		
		LinkedList<ValueFreq> vfs = new LinkedList<ValueFreq>();
		vfs.add(new ValueFreq(2, 2));
		vfs.add(new ValueFreq(4, 1));
		vfs.add(new ValueFreq(5, 3));
		System.out.println("Weighed variance: " + stat.weightedVarianceSample(vfs));
		System.out.println("Weighed mean: " + stat.weightedVarianceSample(vfs));
		
		
		Integer[] y = {2, 2, 2, 2, 2};
		List<Integer> freqs = Arrays.asList(y);
		System.out.println("entropy: " + stat.entropy(stat.probsFromFreqs(freqs)));
		
		Double[] values = {8.0, 2.0};
		List<Double> list = Arrays.asList(values);
		System.out.println("Weighed mean self: " + stat.weightedMeanSelf(list));
		
	}
	@Test
	public void testSkewness() {
		DataProvider dp = new DataProvider("./res/dataset/oned/rzipf_zipf_ran_1000.txt", DataTypeEnum.VALUE_FREQ);
		Stats<Double> stat = new Stats<Double>();
		System.out.println("#tuples " + dp.values.size());
		System.out.println("skewness " + stat.skewness(dp.values));
		System.out.println("skew statistic " + stat.skewStatistic(dp.values));
	}
	
	@Test
	public void testPearsonCorrelation() {
		DataProvider dp = new DataProvider("./res/dataset/twod/charminar10000.txt", DataTypeEnum.NORMAL_POINT);
		dp.getXY();
		Stats<Double> stat = new Stats<Double>();
		
		System.out.println("PearsonCorrelation " + stat.personCC(dp.xCoords, dp.yCoords));
	}
	
	@Test
	public void testSpearmanCorrelation() {
		Stats<Double> stat = new Stats<Double>();
		LinkedList<Double> X = new LinkedList();
		LinkedList<Double> Y = new LinkedList();
		X.add(0.8);
		X.add(1.2);
		X.add(1.2);
		X.add(2.3);
		X.add(18.0);
		
		
		Y.add(18.0);
		Y.add(2.3);
		Y.add(1.2);
		Y.add(1.2);
		Y.add(0.8);
		System.out.println("SpearmanCorrelation " + stat.spearmanCC(X, Y));
		
		DataProvider dp = new DataProvider("./res/dataset/twod/charminar10000.txt", DataTypeEnum.NORMAL_POINT);
		dp.getXY();
		System.out.println("SpearmanCorrelation " + stat.spearmanCC(dp.xCoords, dp.yCoords));
	}
}