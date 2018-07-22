package org.geocrowd.synthetic;

import java.util.ArrayList;

import org.geocrowd.common.distribution.SpatialDistributionEnum;
import org.geocrowd.common.distribution.TemporalDistributionEnum;
import org.geocrowd.dtype.Range;
import org.geocrowd.dtype.Rectangle;

public class TimeInstancesGenerator {

	public static int gaussianCluster = 1;

	protected int instances = 0;
	protected SpatialDistributionEnum workerDist;
	protected SpatialDistributionEnum taskDist;
	protected TemporalDistributionEnum workerCycle;
	protected TemporalDistributionEnum taskCycle;
	protected int wMean = 0;
	protected int tMean = 0;

	protected String workerPath = "";
	protected String taskPath = "";

	private Rectangle boundary = null;

	/**
	 * 
	 * @param instances
	 *            : the number of time instances
	 * @param wc
	 *            : worker's arrival rate
	 * @param tc
	 *            : task's arrival rate
	 * @param wMean
	 *            : mean of worker count
	 * @param tMean
	 *            : mean of task count
	 * @param boundary
	 *            : domain
	 * @param wd
	 *            : worker distribution
	 * @param td
	 *            : task distribution
	 * @param workerPath
	 *            : output directory of tasks
	 * @param taskPath
	 *            : output directory of workers
	 */
	public TimeInstancesGenerator(int instances, TemporalDistributionEnum wc,
								  TemporalDistributionEnum tc, int wMean, int tMean, Rectangle boundary,
								  SpatialDistributionEnum wd, SpatialDistributionEnum td, String workerPath,
								  String taskPath) {
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
	 * 
	 * @param instances
	 *            : the number of time instances
	 * @param wc
	 *            : worker's arrival rate
	 * @param tc
	 *            : task's arrival rate
	 * @param wMean
	 *            : mean of worker count
	 * @param tMean
	 *            : mean of task count
	 * @param workerPath
	 *            : output directory of tasks
	 * @param taskPath
	 *            : output directory of workers
	 */
	public TimeInstancesGenerator(int instances, TemporalDistributionEnum workerCycle,
								  TemporalDistributionEnum taskCycle, int wMean, int tMean, String workerPath,
								  String taskPath) {
		super();
		this.instances = instances;
		this.workerCycle = workerCycle;
		this.taskCycle = taskCycle;
		this.wMean = wMean;
		this.tMean = tMean;
		this.workerPath = workerPath;
		this.taskPath = taskPath;
	}

	/**
	 * 
	 * @return list of worker counts for time instances
	 */
	protected ArrayList<Integer> computeWorkerCounts() {
		ArrayList<Integer> workerCounts = ArrivalRateGenerator.generateTimeArrivals(
				instances, wMean, workerCycle);

		for (int i : workerCounts)
			System.out.print(i + "\t");
		System.out.println();
		return workerCounts;
	}

	/**
	 * 
	 * @return list of task counts for time instances
	 */
	protected ArrayList<Integer> computeTaskCounts() {
		ArrayList<Integer> taskCounts = ArrivalRateGenerator.generateTimeArrivals(
				instances, tMean, taskCycle);
		for (int i : taskCounts)
			System.out.print(i + "\t");
		System.out.println();
		return taskCounts;
	}

	/**
	 * Similar to Random Walk Model (unpredictable movement of particles in
	 * physics)
	 */
	protected void generateData() {

		ArrayList<Integer> workerCounts = computeWorkerCounts();
		ArrayList<Integer> taskCounts = computeTaskCounts();

		ArrayList<Long> seeds = new ArrayList<>();
		// compute seed for Gaussian cluster
		Distribution2DGenerator.gaussianCluster = gaussianCluster;
		for (int i = 0; i < gaussianCluster; i++)
			seeds.add((long) UniformGenerator.randomValue(
					new Range(0, 1000000), true));
		Distribution2DGenerator.seeds = seeds;

		for (int i = 0; i < instances; i++) {
			// update time instance
			Distribution2DGenerator.time = i;

			// worker
			Distribution2DGenerator wdg = new Distribution2DGenerator(
					workerPath + "workers" + i + ".txt");
			wdg.distributionIndicator = 0;
			wdg.varianceX = boundary.getHighPoint().getX() - boundary.getLowPoint().getX();
			wdg.varianceY = boundary.getHighPoint().getY() - boundary.getLowPoint().getY();;
			wdg.generate2DDataset(workerCounts.get(i), boundary, workerDist);

			// task
			Distribution2DGenerator tdg = new Distribution2DGenerator(taskPath
					+ "tasks" + i + ".txt");
			tdg.distributionIndicator = 1;
			tdg.varianceX = boundary.getHighPoint().getX()/10;
			tdg.varianceY = boundary.getHighPoint().getY()/10;
			tdg.generate2DDataset(taskCounts.get(i), boundary, taskDist);
		}
	}
}
