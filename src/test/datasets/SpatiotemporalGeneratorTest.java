package test.datasets;

import static org.junit.Assert.*;

import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.Vector;

import org.geocrowd.Distribution1DEnum;
import org.geocrowd.Distribution2DEnum;
import org.geocrowd.ArrivalRateEnum;
import org.geocrowd.datasets.synthetic.Distribution2DGenerator;
import org.geocrowd.datasets.synthetic.TimeInstancesGenerator;
import org.geocrowd.datasets.synthetic.ArrivalRateGenerator;
import org.geocrowd.datasets.synthetic.WorkerIDGenerator;
import org.geocrowd.dtype.Rectangle;
import org.junit.Test;

import com.sun.org.glassfish.external.statistics.Stats;

/**
 * Generate dataset
 * 
 * @author HT186010
 * 
 */
public class SpatiotemporalGeneratorTest {

	@Test
	public final void testGenerate2DPoints() {
		
		ArrivalRateGenerator.time_instances_per_cycle = 7;
//		int instances = ArrivalRateGenerator.time_instances_per_cycle * 260;
		int instances = 1;
		TimeInstancesGenerator.gaussianCluster = 1;
		TimeInstancesGenerator ig = new TimeInstancesGenerator(instances,
				ArrivalRateEnum.CONSTANT, ArrivalRateEnum.CONSTANT, 1000, 500,
				new Rectangle(0, 0, 99, 99), Distribution2DEnum.GAUSSIAN_2D,
				Distribution2DEnum.GAUSSIAN_2D, "./res/dataset/worker/",
				"./res/dataset/task/");
	}
}