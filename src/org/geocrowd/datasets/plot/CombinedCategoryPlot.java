package org.geocrowd.datasets.plot;

import java.awt.Font;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class CombinedCategoryPlot extends ApplicationFrame {

	public CombinedCategoryPlot(String titel) {
		super(titel);

		final JFreeChart chart = createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 350));
		setContentPane(chartPanel);
	}

	public CategoryDataset createDatasetTeam1() {

		final DefaultCategoryDataset result = new DefaultCategoryDataset();

		double[] run = new double[] { 5, 4, 3, 6, 7, 8, 3, 6, 3, 6, };

		for (int i = 0; i < run.length; i++) {
			result.addValue(run[i], "Team 1", "" + (i + 1));
		}
		return result;
	}

	public CategoryDataset createDatasetTeam2() {

		final DefaultCategoryDataset result = new DefaultCategoryDataset();

		double[] run = new double[] { 3, 2, 2, 6, 8, 4, 1, 8, 9, 11 };

		for (int i = 0; i < run.length; i++) {
			result.addValue(run[i], "Team 2", "" + (i + 1));
		}
		return result;
	}

	private JFreeChart createChart() {

		final CategoryDataset dataset1 = createDatasetTeam1();
		final NumberAxis rangeAxis1 = new NumberAxis("Run");
		rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final BarRenderer renderer1 = new BarRenderer();
		renderer1
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		final CategoryPlot subplot1 = new CategoryPlot(dataset1, null,
				rangeAxis1, renderer1);
		subplot1.setDomainGridlinesVisible(true);

		final CategoryDataset dataset2 = createDatasetTeam2();
		final NumberAxis rangeAxis2 = new NumberAxis("Run");
		rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final BarRenderer renderer2 = new BarRenderer();
		renderer2
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		final CategoryPlot subplot2 = new CategoryPlot(dataset2, null,
				rangeAxis2, renderer2);
		subplot2.setDomainGridlinesVisible(true);

		final CategoryAxis domainAxis = new CategoryAxis("Over");
		final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(
				domainAxis);

		plot.add(subplot1, 1);
		plot.add(subplot2, 1);

		final JFreeChart chart = new JFreeChart("Score Bord", new Font(
				"SansSerif", Font.BOLD, 12), plot, true);
		return chart;
	}

	public static void main(final String[] args) {

		final String title = "Combined Category Plot Demo";
		final CombinedCategoryPlot demo = new CombinedCategoryPlot(title);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}