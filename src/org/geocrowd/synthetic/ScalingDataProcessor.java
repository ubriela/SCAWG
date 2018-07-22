package org.geocrowd.synthetic;

import java.util.ArrayList;

import org.geocrowd.common.distribution.TemporalDistributionEnum;
import org.geocrowd.GeocrowdConstants;

/**
 * Generate large workers and tasks from distributions of the corresponding
 * small datasets
 * 
 * @author ubriela
 *
 */
public class ScalingDataProcessor extends TimeInstancesGenerator {

	public ScalingDataProcessor(int instances, TemporalDistributionEnum workerCycle,
								TemporalDistributionEnum taskCycle, int wMean, int tMean, String workerPath,
								String taskPath) {
		super(instances, workerCycle, taskCycle, wMean, tMean, workerPath,
				taskPath);
		generateData();
	}

	@Override
	protected void generateData() {
		ArrayList<Integer> workerCounts = computeWorkerCounts();
		ArrayList<Integer> taskCounts = computeTaskCounts();

		for (int i = 0; i < instances; i++) {
			// update time instance
			Distribution2DGenerator.time = i;

			// worker
			Distribution2DGenerator wdg = new Distribution2DGenerator(
					workerPath + "workers" + i + ".txt");
			wdg.distributionIndicator = 0;
			wdg.generate2DDataset(workerCounts.get(i),
					GeocrowdConstants.WORKER_SCALE_FILE_PATH);

			// task
			Distribution2DGenerator tdg = new Distribution2DGenerator(taskPath
					+ "tasks" + i + ".txt");
			tdg.distributionIndicator = 1;
			tdg.generate2DDataset(taskCounts.get(i),
					GeocrowdConstants.TASK_SCALE_FILE_PATH);
		}
	}
}
