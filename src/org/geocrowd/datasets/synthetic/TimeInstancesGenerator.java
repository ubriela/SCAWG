package org.geocrowd.datasets.synthetic;

import java.util.ArrayList;

import org.geocrowd.Distribution2DEnum;
import org.geocrowd.ArrivalRateEnum;
import org.geocrowd.dtype.Range;
import org.geocrowd.dtype.Rectangle;

public class TimeInstancesGenerator {

	public static int gaussianCluster = 1;
	
	private int instances = 0;
	private Distribution2DEnum workerDist;
	private Distribution2DEnum taskDist;
	private ArrivalRateEnum workerCycle;
	private ArrivalRateEnum taskCycle;
	private int wMean = 0;
	private int tMean = 0;
	
	private String workerPath = "";
	private String taskPath = "";
	
	public Rectangle boundary = null;

	public TimeInstancesGenerator(int instances, ArrivalRateEnum wc, ArrivalRateEnum tc, int wMean, int tMean, Rectangle boundary, Distribution2DEnum wd, Distribution2DEnum td, String workerPath, String taskPath) {
		super();
		this.instances = instances;
		this.workerCycle = wc;
		this.taskCycle = tc;
		this.wMean = wMean;
		this.tMean = tMean;
		this.boundary = boundary;
		this.workerDist = wd;
		this.taskDist = td;
		this.workerPath = workerPath;
		this.taskPath = taskPath;
		
		generateData();
	}
	
	/**
	 * Similar to Random Walk Model (unpredictable movement of particles in physics)
	 */
	private void generateData() {
		ArrayList<Integer> workerCounts = ArrivalRateGenerator.generateCounts(instances, wMean, workerCycle);
		ArrayList<Integer> taskCounts = ArrivalRateGenerator.generateCounts(instances, tMean, taskCycle);
		
		for (int i : workerCounts)
			System.out.print(i + "\t");
		System.out.println();
		for (int i : taskCounts)
			System.out.print(i + "\t");
		System.out.println();
		ArrayList<Long> seeds = new ArrayList<>();
		// compute seed for gaussian cluster
		Distribution2DGenerator.gaussianCluster = gaussianCluster;
		for (int i = 0; i < gaussianCluster; i++)
			seeds.add((long) UniformGenerator.randomValue(new Range(0, 1000000), true));
		Distribution2DGenerator.seeds = seeds;
		
		for (int i = 0; i < instances; i++) {
			// update time instance
			Distribution2DGenerator.time = i;
			
			// worker
			Distribution2DGenerator wdg = new Distribution2DGenerator(workerPath + "workers" + i + ".txt");
			wdg.distributionIndicator = 0;
			wdg.varianceX = boundary.getHighPoint().getX();
			wdg.varianceY = boundary.getHighPoint().getY();
			wdg.generate2DDataset(workerCounts.get(i), boundary, workerDist);
			
			// task
			Distribution2DGenerator tdg = new Distribution2DGenerator(taskPath + "tasks" + i + ".txt");
			tdg.distributionIndicator = 1;
			tdg.varianceX = boundary.getHighPoint().getX();
			tdg.varianceY = boundary.getHighPoint().getY();
			tdg.generate2DDataset(taskCounts.get(i), boundary, taskDist);
		}
	}
}
