/*******************************************************************************
 * @ Year 2013
 * This is the source code of the following papers. 
 * 
 * 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla Kazemi, Cyrus Shahabi.
 * 
 * 
 * Please contact the author Hien To, ubriela@gmail.com if you have any question.
 *
 * Contributors:
 * Hien To - initial implementation
 *******************************************************************************/
package test.datasets;

import org.geocrowd.DatasetEnum;
import org.geocrowd.TaskCategoryEnum;
import org.geocrowd.TaskDurationEnum;
import org.geocrowd.TaskRadiusEnum;
import org.geocrowd.TaskRewardEnum;
import org.geocrowd.TaskType;
import org.geocrowd.WorkerCapacityEnum;
import org.geocrowd.WorkerIDEnum;
import org.geocrowd.WorkerType;
import org.geocrowd.WorkingRegionEnum;
import org.geocrowd.datasets.synthetic.GenericProcessor;
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

	/**
	 * Test generate syn workers tasks.
	 */
	@Test
	public void testGenerateSynWorkersTasks() {

		GenericProcessor prep = new GenericProcessor(1, 100000, DatasetEnum.SKEWED,
				WorkerIDEnum.GAUSSIAN, WorkerType.EXPERT,
				WorkingRegionEnum.CONSTANT, WorkerCapacityEnum.CONSTANT,
				TaskType.EXPERT, TaskCategoryEnum.RANDOM,
				TaskRadiusEnum.RANDOM, TaskRewardEnum.RANDOM, TaskDurationEnum.RANDOM);

		// generating location density
		// prep.saveLocationDensity(prep.computeLocationDensity());
		// prep.regionEntropy();
	}
}
