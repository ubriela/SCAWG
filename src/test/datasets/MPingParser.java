package test.datasets;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import org.geocrowd.DatasetEnum;
import org.geocrowd.common.distribution.SpatialDistributionEnum;
import org.geocrowd.common.distribution.TaskDurationDistributionEnum;
import org.geocrowd.common.distribution.TaskRadiusDistributionEnum;
import org.geocrowd.common.distribution.TaskRewardDistributionEnum;
import org.geocrowd.common.distribution.TaskTypeEnum;
import org.geocrowd.common.distribution.TaskCategoryDistributionEnum;
import org.geocrowd.common.distribution.TemporalDistributionEnum;
import org.geocrowd.common.distribution.WorkerCapacityDistributionEnum;
import org.geocrowd.common.distribution.WorkerIDDistributionEnum;
import org.geocrowd.common.distribution.WorkerTypeEnum;
import org.geocrowd.common.distribution.WorkingRegionDistributionEnum;
import org.geocrowd.common.workertask.GenericWorker;
import org.geocrowd.MPingConstants;
import org.geocrowd.synthesis.MPingProcessor;
import org.geocrowd.synthetic.ArrivalRateGenerator;
import org.geocrowd.synthetic.GenericProcessor;
import org.geocrowd.synthetic.TimeInstancesGenerator;
import org.geocrowd.dtype.Rectangle;

public class MPingParser {

	public static void main(String[] args) {
		String param = args[0];
		int instances = Integer.valueOf(args[1]);

		if (param.equals("gen_worker")) {
//			MPingProcessor prep = new MPingProcessor(20, WorkerTypeEnum.EXPERT,
//					TaskTypeEnum.EXPERT_TASK, TaskCategoryDistributionEnum.RANDOM);
//
//			prep.computeBoundary();
//			prep.extractWorkersInstances("dataset/real/mping/mping.txt",
//					"dataset/real/mping/worker/mping_workers", instances);
			
			MPingProcessor prep = new MPingProcessor(instances,
													 WorkerTypeEnum.EXPERT, TaskTypeEnum.SENSING_TASK,
													 TaskCategoryDistributionEnum.RANDOM);

			// generating workers from mping
			Hashtable<Date, ArrayList<GenericWorker>> hashTable = prep
					.generateRealWorkers(MPingConstants.dataset);
			prep.saveRealWorkersMax(hashTable);
		} else if (param.equals("gen_task")) {
			
			MPingProcessor prep = new MPingProcessor(instances,
													 WorkerTypeEnum.EXPERT, TaskTypeEnum.SENSING_TASK,
													 TaskCategoryDistributionEnum.RANDOM);
			
			
			ArrivalRateGenerator.time_instances_per_cycle = 1;
			TimeInstancesGenerator.gaussianCluster = Integer.valueOf(args[2]);
			int tMean = Integer.valueOf(args[3]);
			TimeInstancesGenerator ti = new TimeInstancesGenerator(instances,
																   TemporalDistributionEnum.CONSTANT, TemporalDistributionEnum.CONSTANT, 1000, tMean,
																   new Rectangle(GenericProcessor.minLat, GenericProcessor.minLng, GenericProcessor.maxLat, GenericProcessor.maxLng), SpatialDistributionEnum.GAUSSIAN_2D,
																   SpatialDistributionEnum.GAUSSIAN_2D, "./res/dataset/worker/",
																   "./res/dataset/task/");
			
			
			GenericProcessor gp = new GenericProcessor(instances, 10000, DatasetEnum.MPING,
													   WorkerIDDistributionEnum.GAUSSIAN, WorkerTypeEnum.GENERIC_WORKER,
													   WorkingRegionDistributionEnum.CONSTANT, WorkerCapacityDistributionEnum.CONSTANT,
													   TaskTypeEnum.SENSING_TASK, TaskCategoryDistributionEnum.RANDOM,
													   TaskRadiusDistributionEnum.CONSTANT, TaskRewardDistributionEnum.CONSTANT, TaskDurationDistributionEnum.CONSTANT);

		}
	}

}
