package test.datasets;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.geocrowd.datasets.dtype.DataTypeEnum;
import org.geocrowd.datasets.plot.PlottingGraph;
import org.geocrowd.datasets.plot.PlottingPoint;
import org.geocrowd.datasets.plot.ScatterPlot;
import org.geocrowd.datasets.synthetic.DataProvider;
import org.jfree.ui.RefineryUtilities;
import org.junit.Test;



public class PlottingGraphTest {
	
	@Test
	public final void testPlotHeatmap() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DataProvider md = new DataProvider("./res/dataset/twod/test.txt", DataTypeEnum.NORMAL_POINT);
		PlottingPoint pp = new PlottingPoint(md.points, 50);
		f.add(pp);
		f.setSize(PlottingPoint.scale_x, PlottingPoint.scale_y);
		f.setLocation(20, 20);
		f.setVisible(true);
	}
	
	@Test
	public final void testPlotHistogram() {
//		DataProvider dp = new DataProvider("./dataset/out/test.txt", DataTypeEnum.VALUE_FREQ);
//		PlottingGraph p = new PlottingGraph();
//		PlottingGraph.filePrefix = "./res/graph/hists/oned/";
//		p.createHistogramFromValFreqs(dp.valueFreqs, 100000, "", "Values", "Percent (%)", "rzipf_uniform_1000.jpeg", true);
	}
	
	@Test
	public final void testScatter() {
		DataProvider md = new DataProvider("./res/dataset/twod/test.txt", DataTypeEnum.NORMAL_POINT);
        final ScatterPlot demo = new ScatterPlot("Fast Scatter Plot Demo", md.points);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
	}
}