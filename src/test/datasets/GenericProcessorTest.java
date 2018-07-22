/*******************************************************************************
 * @ Year 2013
 * 
 * 
 * Please contact the author Hien To, ubriela@gmail.com if you have any question.
 *
 * Contributors:
 * Hien To - initial implementation
 *******************************************************************************/
package test.datasets;

import org.geocrowd.DatasetEnum;
import org.geocrowd.common.distribution.SpatialDistributionEnum;
import org.geocrowd.common.distribution.TaskCategoryDistributionEnum;
import org.geocrowd.common.distribution.TaskDurationDistributionEnum;
import org.geocrowd.common.distribution.TaskRadiusDistributionEnum;
import org.geocrowd.common.distribution.TaskRewardDistributionEnum;
import org.geocrowd.common.distribution.TaskTypeEnum;
import org.geocrowd.common.distribution.TemporalDistributionEnum;
import org.geocrowd.common.distribution.WorkerCapacityDistributionEnum;
import org.geocrowd.common.distribution.WorkerIDDistributionEnum;
import org.geocrowd.common.distribution.WorkerTypeEnum;
import org.geocrowd.common.distribution.WorkingRegionDistributionEnum;
import org.geocrowd.synthetic.ArrivalRateGenerator;
import org.geocrowd.synthetic.GenericProcessor;
import org.geocrowd.synthetic.ScalingDataProcessor;
import org.geocrowd.synthetic.TimeInstancesGenerator;
import org.geocrowd.dtype.Rectangle;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class PreProcessTest.
 */
public class GenericProcessorTest {

	public static void main(String[] args) {
		GenericProcessorTest preTest = new GenericProcessorTest();
		preTest.testGenerateSynWorkersTasks();
	}

	@Test
	public final void testGenerate2DPoints() {
		
		ArrivalRateGenerator.time_instances_per_cycle = 1;
//		int instances = ArrivalRateGenerator.time_instances_per_cycle * 260;
		int instances = 1;
		TimeInstancesGenerator.gaussianCluster = 1;
		TimeInstancesGenerator ti = new TimeInstancesGenerator(instances,
															   TemporalDistributionEnum.CONSTANT, TemporalDistributionEnum.CONSTANT, 100000, 2000,
															   new Rectangle(0, 0, 99, 99), SpatialDistributionEnum.GAUSSIAN_2D,
															   SpatialDistributionEnum.GAUSSIAN_2D, "./res/dataset/worker/",
															   "./res/dataset/task/");
	}
	
	@Test
	public final void testGenerateScale2DPoints() {
		
		ArrivalRateGenerator.time_instances_per_cycle = 7;
		int instances = ArrivalRateGenerator.time_instances_per_cycle * 8;
		ScalingDataProcessor scale = new ScalingDataProcessor(instances,
															  TemporalDistributionEnum.POISSON, TemporalDistributionEnum.ZIPFIAN, 500, 1000, "./res/dataset/worker/",
															  "./res/dataset/task/");
	}
	
	/**
	 * Test generate syn workers tasks.
	 */
	@Test
	public void testGenerateSynWorkersTasks() {
		ArrivalRateGenerator.time_instances_per_cycle = 7;
		int instances = ArrivalRateGenerator.time_instances_per_cycle * 4;
		GenericProcessor prep = new GenericProcessor(instances, 10000, DatasetEnum.FOURSQUARE,
													 WorkerIDDistributionEnum.GAUSSIAN, WorkerTypeEnum.GENERIC_WORKER,
													 WorkingRegionDistributionEnum.CONSTANT, WorkerCapacityDistributionEnum.CONSTANT,
													 TaskTypeEnum.SENSING_TASK, TaskCategoryDistributionEnum.RANDOM,
													 TaskRadiusDistributionEnum.CONSTANT, TaskRewardDistributionEnum.CONSTANT, TaskDurationDistributionEnum.CONSTANT);
	}
}