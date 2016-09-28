package test.datasets;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import org.geocrowd.ArrivalRateEnum;
import org.geocrowd.DatasetEnum;
import org.geocrowd.Distribution2DEnum;
import org.geocrowd.TaskCategoryEnum;
import org.geocrowd.TaskDurationEnum;
import org.geocrowd.TaskRadiusEnum;
import org.geocrowd.TaskRewardEnum;
import org.geocrowd.TaskType;
import org.geocrowd.WorkerCapacityEnum;
import org.geocrowd.WorkerIDEnum;
import org.geocrowd.WorkerType;
import org.geocrowd.WorkingRegionEnum;
import org.geocrowd.common.crowd.GenericWorker;
import org.geocrowd.datasets.params.GowallaConstants;
import org.geocrowd.datasets.params.MPingConstants;
import org.geocrowd.datasets.synthesis.gowalla.GowallaProcessor;
import org.geocrowd.datasets.synthesis.mping.MPingProcessor;
import org.geocrowd.datasets.synthetic.ArrivalRateGenerator;
import org.geocrowd.datasets.synthetic.GenericProcessor;
import org.geocrowd.datasets.synthetic.TimeInstancesGenerator;
import org.geocrowd.dtype.Rectangle;

public class MPingParser {

	public static void main(String[] args) {
		String param = args[0];
		int instances = Integer.valueOf(args[1]);

		if (param.equals("gen_worker")) {
//			MPingProcessor prep = new MPingProcessor(20, WorkerType.EXPERT,
//					TaskType.EXPERT, TaskCategoryEnum.RANDOM);
//
//			prep.computeBoundary();
//			prep.extractWorkersInstances("dataset/real/mping/mping.txt",
//					"dataset/real/mping/worker/mping_workers", instances);
			
			MPingProcessor prep = new MPingProcessor(instances,
					WorkerType.EXPERT, TaskType.SENSING,
					TaskCategoryEnum.RANDOM);

			// generating workers from mping
			Hashtable<Date, ArrayList<GenericWorker>> hashTable = prep
					.generateRealWorkers(MPingConstants.dataset);
			prep.saveRealWorkersMax(hashTable);
		} else if (param.equals("gen_task")) {
			
			MPingProcessor prep = new MPingProcessor(instances,
					WorkerType.EXPERT, TaskType.SENSING,
					TaskCategoryEnum.RANDOM);
			
			
			ArrivalRateGenerator.time_instances_per_cycle = 1;
			TimeInstancesGenerator.gaussianCluster = Integer.valueOf(args[2]);
			int tMean = Integer.valueOf(args[3]);
			TimeInstancesGenerator ti = new TimeInstancesGenerator(instances,
					ArrivalRateEnum.CONSTANT, ArrivalRateEnum.CONSTANT, 1000, tMean,
					new Rectangle(GenericProcessor.minLat, GenericProcessor.minLng, GenericProcessor.maxLat, GenericProcessor.maxLng), Distribution2DEnum.GAUSSIAN_2D,
					Distribution2DEnum.GAUSSIAN_2D, "./res/dataset/worker/",
					"./res/dataset/task/");
			
			
			GenericProcessor gp = new GenericProcessor(instances, 10000, DatasetEnum.MPING,
					WorkerIDEnum.GAUSSIAN, WorkerType.GENERIC,
					WorkingRegionEnum.CONSTANT, WorkerCapacityEnum.CONSTANT,
					TaskType.SENSING, TaskCategoryEnum.RANDOM,
					TaskRadiusEnum.CONSTANT, TaskRewardEnum.CONSTANT, TaskDurationEnum.CONSTANT);

		}
	}

}
