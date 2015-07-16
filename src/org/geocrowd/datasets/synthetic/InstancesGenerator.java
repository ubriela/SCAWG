package org.geocrowd.datasets.synthetic;

import java.util.ArrayList;

import org.geocrowd.Distribution2DEnum;
import org.geocrowd.WTArrivalEnum;
import org.geocrowd.datasets.dtype.Range;
import org.geocrowd.datasets.dtype.Rectangle;

public class InstancesGenerator {

	public static int gaussianCluster = 1;
	
	private int instances = 0;
	private Distribution2DEnum workerDist;
	private Distribution2DEnum taskDist;
	private WTArrivalEnum workerCycle;
	private WTArrivalEnum taskCycle;
	private int wMean = 0;
	private int tMean = 0;
	
	private String workerPath = "";
	private String taskPath = "";
	
	public Rectangle boundary = null;

	public InstancesGenerator(int instances, WTArrivalEnum wc, WTArrivalEnum tc, int wMean, int tMean, Rectangle boundary, Distribution2DEnum wd, Distribution2DEnum td, String workerPath, String taskPath) {
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
	
	private void generateData() {
		ArrayList<Integer> workerCounts = WTCountGenerator.generateCounts(instances, wMean, workerCycle);
		ArrayList<Integer> taskCounts = WTCountGenerator.generateCounts(instances, tMean, taskCycle);
		
		for (int i : workerCounts)
			System.out.print(i + "\t");
		System.out.println();
		for (int i : taskCounts)
			System.out.print(i + "\t");
		System.out.println();
		ArrayList<Long> seeds = new ArrayList<>();
		// compute seed for gaussian cluster
		DatasetGenerator.gaussianCluster = gaussianCluster;
		for (int i = 0; i < gaussianCluster; i++)
			seeds.add((long) UniformGenerator.randomValue(new Range(0, 1000000), true));
		DatasetGenerator.seeds = seeds;
		
		for (int i = 0; i < instances; i++) {
			// update time instance
			DatasetGenerator.time = i;
			
			// worker
			DatasetGenerator wdg = new DatasetGenerator(workerPath + "workers" + i + ".txt");
			wdg.datatype = 0;
			wdg.varianceX = boundary.getHighPoint().getX();
			wdg.varianceY = boundary.getHighPoint().getY();
			wdg.generate2DDataset(workerCounts.get(i), boundary, workerDist);
			
			// task
			DatasetGenerator tdg = new DatasetGenerator(taskPath + "tasks" + i + ".txt");
			tdg.datatype = 1;
			tdg.varianceX = boundary.getHighPoint().getX();
			tdg.varianceY = boundary.getHighPoint().getY();
			tdg.generate2DDataset(taskCounts.get(i), boundary, taskDist);
		}
	}
	
}
