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
import org.geocrowd.datasets.synthetic.ArrivalRateGenerator;
import org.geocrowd.datasets.synthetic.GenericProcessor;
import org.geocrowd.datasets.synthetic.ScalingDataProcessor;
import org.geocrowd.datasets.synthetic.TimeInstancesGenerator;
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
		
		ArrivalRateGenerator.time_instances_per_cycle = 7;
//		int instances = ArrivalRateGenerator.time_instances_per_cycle * 260;
		int instances = 1;
		TimeInstancesGenerator.gaussianCluster = 1;
		TimeInstancesGenerator ti = new TimeInstancesGenerator(instances,
				ArrivalRateEnum.CONSTANT, ArrivalRateEnum.CONSTANT, 500, 1000,
				new Rectangle(0, 0, 99, 99), Distribution2DEnum.GAUSSIAN_2D,
				Distribution2DEnum.GAUSSIAN_2D, "./res/dataset/worker/",
				"./res/dataset/task/");
	}
	
	@Test
	public final void testGenerateScale2DPoints() {
		
		ArrivalRateGenerator.time_instances_per_cycle = 7;
		int instances = ArrivalRateGenerator.time_instances_per_cycle * 8;
		ScalingDataProcessor scale = new ScalingDataProcessor(instances,
				ArrivalRateEnum.POISSON, ArrivalRateEnum.ZIPFIAN, 500, 1000, "./res/dataset/worker/",
				"./res/dataset/task/");
	}
	
	/**
	 * Test generate syn workers tasks.
	 */
	@Test
	public void testGenerateSynWorkersTasks() {
		ArrivalRateGenerator.time_instances_per_cycle = 7;
		int instances = ArrivalRateGenerator.time_instances_per_cycle * 8;
		GenericProcessor prep = new GenericProcessor(instances, 100000, DatasetEnum.SCALE,
				WorkerIDEnum.GAUSSIAN, WorkerType.EXPERT,
				WorkingRegionEnum.CONSTANT, WorkerCapacityEnum.CONSTANT,
				TaskType.SENSING, TaskCategoryEnum.RANDOM,
				TaskRadiusEnum.RANDOM, TaskRewardEnum.RANDOM, TaskDurationEnum.RANDOM);
	}
}
