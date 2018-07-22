package org.geocrowd.plot;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.geocrowd.common.utils.Stats;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.dtype.ValueFreq;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * 
 * @author ubriela
 *
 */
public class PlottingGraph {

	static boolean show = true;
	static boolean toolTips = true;
	static boolean urls = false;
	static int x_dim = 800;
	static int y_dim = 600;
	static PlotOrientation orientation = PlotOrientation.VERTICAL;
	public static String filePrefix;

	/**
	 * Plotting histogram from value list
	 * 
	 * @param values
	 * @param bucketNo
	 * @param fileName
	 */
	public static void createHistogramFromVals(List<Double> values, int bucketNo,
			String title, String xLabel, String yLabel, String fileName,
			final boolean aPlotTransparent) {
		Double[] data = Utils.listToArray(values);
		double[] tempArray = new double[data.length];
		int j = 0;
		for (Double d : data) {
			tempArray[j++] = (double) d;
		}
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		dataset.addSeries("Histogram", tempArray, bucketNo);

		// Create the category chart and save it as an image
		JFreeChart chart = ChartFactory.createHistogram(title, xLabel, yLabel,
				dataset, orientation, show, toolTips, urls);
		if (aPlotTransparent) {
			final Plot plot = chart.getPlot();
			plot.setBackgroundPaint(new Color(255, 255, 255, 0));
			plot.setBackgroundImageAlpha(0.0f);
		}
		
		LegendTitle lt = chart.getLegend();
		lt.setItemFont(new Font("Arial", Font.PLAIN, 18)); 

		saveChart(chart, fileName);
	}
	
	/**
	 * Plotting histogram from value/frequency list
	 * 
	 * @param values
	 * @param bucketNo
	 * @param fileName
	 */
	public static void createHistogramFromValFreqs(List<ValueFreq<Double>> valueFreqs, int bucketNo,
			String title, String xLabel, String yLabel, String fileName,
			final boolean aPlotTransparent) {
		Stats stat = new Stats();
		List<Double> values = stat.getValues(valueFreqs);
		double[] tempArray = new double[values.size()];
		int j = 0;
		for (Double d : values) {
			tempArray[j++] = (double) d;
		}
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		dataset.addSeries("Histogram", tempArray, bucketNo);

		// Create the category chart and save it as an image
		JFreeChart chart = ChartFactory.createHistogram(title, xLabel, yLabel,
				dataset, orientation, show, toolTips, urls);
		if (aPlotTransparent) {
			final Plot plot = chart.getPlot();
			plot.setBackgroundPaint(new Color(255, 255, 255, 0));
			plot.setBackgroundImageAlpha(0.0f);
		}
		
		LegendTitle lt = chart.getLegend();
		lt.setItemFont(new Font("Arial", Font.PLAIN, 18)); 

		saveChart(chart, fileName);
	}

	/**
	 * Create category chart
	 * 
	 * @param fileName
	 */
	public static void createCategoryChart(List<CategoryData> data,
			String title, String xLabel, String yLabel, String fileName,
			final boolean aPlotTransparent) {

		DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();

		// put all category data into categoryDataset
		Iterator<CategoryData> it = data.iterator();
		while (it.hasNext()) {
			CategoryData cd = (CategoryData) it.next();
			categoryDataset.addValue(cd.getValue(), cd.getRowKey(),
					cd.getColumnKey());
		}
		
		// Create the category chart and save it as an image
		JFreeChart chart = ChartFactory.createBarChart3D(title, xLabel, yLabel,
				categoryDataset, orientation, show, toolTips, urls);
		
		if (aPlotTransparent) {
			final Plot plot = chart.getPlot();
			plot.setBackgroundPaint(new Color(255, 255, 255, 0));
			plot.setBackgroundImageAlpha(0.0f);
		}

		LegendTitle lt = chart.getLegend();
		lt.setItemFont(new Font("Arial", Font.PLAIN, 18));
		
		saveChart(chart, fileName);
	}

	/**
	 * Save a chart into a file as an image
	 * 
	 * @param chart
	 * @param fileName
	 */
	public static void saveChart(JFreeChart chart, String fileName) {
		try {
			/**
			 * This utility saves the JFreeChart as a JPEG
			 * 
			 * @First Parameter: FileName
			 * @Second Parameter: Chart To Save
			 * @Third Parameter: Height Of Picture
			 * @Fourth Parameter: Width Of Picture
			 */
			ChartUtilities.saveChartAsJPEG(new File(filePrefix + fileName),
					chart, x_dim, y_dim);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem occurred creating chart.");
		}
	}

	/**
	 * Create bar chart from category dataset
	 * 
	 * @param dataset
	 * @param title
	 * @param categoryLabel
	 * @param valueLabel
	 * @param fileName
	 */
	public static void createBarChart(CategoryDataset dataset, String title,
			String categoryLabel, String valueLabel, String fileName) {

		final JFreeChart chart = ChartFactory.createBarChart(title,
				categoryLabel, valueLabel, dataset, orientation, show,
				toolTips, urls);
		saveChart(chart, fileName);
	}

	/**
	 * Create a combined category of 2 charts
	 * 
	 * @param dataset1
	 * @param dataset2
	 * @param title
	 * @param axis1
	 * @param axis2
	 * @param domainAxis
	 * @param fileName
	 */
	public static void createCombinedCategoryChart(CategoryDataset dataset1,
			CategoryDataset dataset2, String title, String axis1, String axis2,
			String domainAxis, String fileName) {
		final NumberAxis rangeAxis1 = new NumberAxis(axis1);
		rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final BarRenderer renderer1 = new BarRenderer();
		renderer1
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		final CategoryPlot subplot1 = new CategoryPlot(dataset1, null,
				rangeAxis1, renderer1);
		subplot1.setDomainGridlinesVisible(true);

		final NumberAxis rangeAxis2 = new NumberAxis(axis2);
		rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final BarRenderer renderer2 = new BarRenderer();
		renderer2
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		final CategoryPlot subplot2 = new CategoryPlot(dataset2, null,
				rangeAxis2, renderer2);
		subplot2.setDomainGridlinesVisible(true);

		final CategoryAxis dAxis = new CategoryAxis(domainAxis);
		final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(
				dAxis);

		plot.add(subplot1, 1);
		plot.add(subplot2, 1);

		final JFreeChart chart = new JFreeChart(title, new Font("SansSerif",
				Font.BOLD, 12), plot, true);

		saveChart(chart, fileName);
	}

	/**
	 * Create a lines chart
	 * 
	 * @param dataset
	 * @param title
	 * @param xLabel
	 * @param yLabel
	 * @param fileName
	 */
	public static void createLineChart(CategoryDataset dataset, String title,
			String xLabel, String yLabel, String fileName) {
		int colCount = dataset.getColumnCount();
		int rowCount = dataset.getRowCount();
		List colKeys = dataset.getColumnKeys();
		List rowKeys = dataset.getRowKeys();
		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		for (int i = 0; i < colCount; i++) {
			XYSeries series = new XYSeries(colKeys.get(i).toString());
			for (int j = 0; j < rowCount; j++) {
				double x = Double.parseDouble(rowKeys.get(j).toString());
				double y = (Double) dataset.getValue(j, i);
				series.add(x, y);
			}
			seriesCollection.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYLineChart(title, xLabel,
				yLabel, seriesCollection, orientation, show, toolTips, urls);

		// customize the chart
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		// final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		// rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		saveChart(chart, fileName);
	}
}
