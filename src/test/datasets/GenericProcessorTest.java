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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import org.geocrowd.DatasetEnum;
import org.geocrowd.Distribution1DEnum;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.common.crowd.ExpertWorker;
import org.geocrowd.common.entropy.Coord;
import org.geocrowd.common.entropy.Observation;
import org.geocrowd.datasets.synthesis.gowalla.GowallaProcessor;
import org.geocrowd.datasets.synthetic.GenericProcessor;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class PreProcessTest.
 */
public class GenericProcessorTest {
    
    
    public static void main(String[] args){
        GenericProcessorTest preTest = new GenericProcessorTest();
        preTest.testGenerateSynWorkersTasks();
    }


	/**
	 * Test generate syn workers tasks.
	 */
	@Test
	public void testGenerateSynWorkersTasks() {

		GenericProcessor prep = new GenericProcessor();
		GenericProcessor.DATA_SET = DatasetEnum.SKEWED;
		GeocrowdConstants.TIME_INSTANCE = 20;

		prep.computeBoundary();
		prep.readBoundary();
		prep.createGrid();
		
		// generating workers
		prep.workerIdDist = Distribution1DEnum.UNIFORM_1D;
		prep.generateSynWorkers(true, true);
		
		// generate tasks
		prep.generateSynTasks();

		// generating location density
//		prep.saveLocationDensity(prep.computeLocationDensity());
//		prep.regionEntropy();
	}
	
	/**
	 * Test generate syn workers tasks.
	 */
	@Test
	public void testGenerateSynWorkersTasksGeorge() {

		GenericProcessor prep = new GenericProcessor();
		GenericProcessor.DATA_SET = DatasetEnum.SKEWED;
		GeocrowdConstants.TIME_INSTANCE = 20;

		prep.computeBoundary();
		prep.readBoundary();
		prep.createGrid();
		
		// generating workers
		prep.workerIdDist = Distribution1DEnum.UNIFORM_1D;
		prep.generateSynWorkers(true, true);
		
		// generate tasks
		prep.generateSynTasks();

		// generating location density
//		prep.saveLocationDensity(prep.computeLocationDensity());
//		prep.regionEntropy();
	}
}
