package test.datasets;

import java.util.Vector;

import org.geocrowd.WorkerType;
import org.geocrowd.common.crowd.GenericWorker;
import org.geocrowd.common.crowd.RegionWorker;
import org.geocrowd.common.crowd.WorkerFactory;
import org.geocrowd.common.crowd.WorkingRegion;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.datasets.synthetic.grid.DataProvider;
import org.geocrowd.datasets.synthetic.grid.EquiSizedGrid;
import org.geocrowd.dtype.Point;
import org.geocrowd.dtype.Rectangle;
import org.geocrowd.dtype.ValueFreq;
import org.junit.Test;

public class UtilsTest {

	@Test
	public final void testWorker() {
		GenericWorker w = new WorkerFactory().getWorker(WorkerType.REGION, 1, 1);
		
		System.out.println(w);
		
		RegionWorker sw = (RegionWorker)w;
		sw.setMbr(new WorkingRegion(2, 2, 2, 2));
		
		System.out.println(sw);
		System.out.println(w);
	}
	
	@Test
	public final void testBinarySearch() {
		Utils util = new Utils();
		double[] arr = {0, 1, 2, 3, 4};
		double find = 5;
		System.out.println(util.binarySearchFloor(arr, find));
	}
	
	@Test
	public final void testBinarySearchBias() {
		Vector<ValueFreq<Double>> biasValues = new Vector<ValueFreq<Double>>();
		biasValues.add(new ValueFreq<Double>(0.0, 0));
		biasValues.add(new ValueFreq<Double>(1.0, 1));
		biasValues.add(new ValueFreq<Double>(2.0, 2));
		biasValues.add(new ValueFreq<Double>(3.0, 3));
		biasValues.add(new ValueFreq<Double>(4.0, 4));
		biasValues.add(new ValueFreq<Double>(5.0, 5));
		biasValues.add(new ValueFreq<Double>(6.0, 6));
		biasValues.add(new ValueFreq<Double>(7.0, 7));
		biasValues.add(new ValueFreq<Double>(8.0, 8));
		biasValues.add(new ValueFreq<Double>(9.0, 9));

		double find = 3.5;
		Utils util = new Utils();
		System.out.println(util.binarySearchCeilBias(biasValues, find));
	}
	
	@Test
	public final void testEquiSizedGrid() {
		DataProvider md = new DataProvider(
				"./dataset/scale/worker_dist.txt", 2);
		Rectangle boundary = new Rectangle(new Point(md.min_x, md.min_y),
				new Point(md.max_x, md.max_y));
		EquiSizedGrid equiSizedGrid = new EquiSizedGrid(boundary,
				md.dim_size_x, md.dim_size_y);
		System.out.println("Size: " + md.points.size());
		equiSizedGrid.populate(md.points);
		int[][] stats = equiSizedGrid.histCount();

		// Utils.print(stats);
		Utils.createHeatMap(stats, "worker-hist.jpg");
	}
}
