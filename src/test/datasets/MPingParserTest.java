package test.datasets;

import org.geocrowd.TaskCategoryEnum;
import org.geocrowd.TaskType;
import org.geocrowd.WorkerType;
import org.geocrowd.datasets.synthesis.mping.MPingProcessor;
import org.junit.Test;

public class MPingParserTest {

	/**
	 * Test extract coords.
	 */
	@Test
	public void testGenerateWorkers_privgeocrowd() {
		MPingProcessor prep = new MPingProcessor(20,
				WorkerType.EXPERT, TaskType.EXPERT,
					TaskCategoryEnum.RANDOM);
		
		// CA: 32.1713906, -124.3041035, 41.998434033, -114.0043464333
		// Los Angeles: 33.699476,-118.570633, 34.319887,-118.192978
		// Bay area: 37.246147,-122.67746, 37.990176,-121.839752
		// SF: 37.711049,-122.51524, 37.832899,-122.360744
		// Yelp: 
//		prep.filterInput("dataset/real/mping/mping.txt", 18.074126, -168.645640, 72.819746, -56.057754);
		prep.computeBoundary();
//		prep.extractCoords("dataset/real/gowalla/gowalla_CA");
		prep.extractWorkersInstances("dataset/real/mping/mping.txt", "dataset/real/mping/worker/mping_workers", 200);
	}
}