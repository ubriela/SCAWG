package org.geocrowd.datasets.plot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.geocrowd.datasets.dtype.DataTypeEnum;
import org.geocrowd.datasets.dtype.Point;
import org.geocrowd.datasets.synthetic.DataProvider;
import org.tc33.jheatchart.HeatChart;



/**
 * Visualize points in a map
 * 
 * @author HT186010
 * 
 */
public class PlottingPoint extends JPanel {
	List<Point> points;
	int draw = 0;
	public static int scale_x = 400;
	public static int scale_y = 400;

	final int PAD = 0;

	/**
	 * 
	 * @param points
	 * @param draw: the number of points to plot
	 */
	public PlottingPoint(List<Point> points, int draw) {
		super();
		this.points = points;
		this.draw = draw;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth();
		int h = getHeight();
		// Draw ordinate.
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
		// Draw abcissa.
		g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));

		// Mark data points.
		g2.setPaint(Color.red);
		Iterator<Point> it = points.iterator();
		double length_x = getMax_X() - getMin_X();
		double length_y = getMax_Y() - getMin_Y();
		int loop = draw;
		while (it.hasNext() && loop-- >= 0) {
			Point point = (Point) it.next();
			double x = PAD + (point.getX() - getMin_X()) * scale_x / length_x;
			double y = h - PAD - (point.getY() - getMin_Y()) * scale_y
					/ length_y;
			g2.draw(new Ellipse2D.Double(x, y, 8, 8));
		}
	}

	private double getMax_X() {
		double max = -Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getX() > max)
				max = points.get(i).getX();
		}
		return max;
	}

	private double getMin_X() {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getX() < min)
				min = points.get(i).getX();
		}
		return min;
	}

	private double getMax_Y() {
		double max = -Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getY() > max)
				max = points.get(i).getY();
		}
		return max;
	}

	private double getMin_Y() {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getY() < min)
				min = points.get(i).getY();
		}
		return min;
	}
	
	/**
	 * Create heatmap image from a matrix
	 * http://www.tc33.org/projects/jheatchart/
	 * @param data
	 * @param fileName
	 */
	public static void createHeatMap(double[][] data, String fileName) {

		// Create our heat map chart using our data.
		HeatChart map = new HeatChart(data);

		// Customise the chart.
		map.setTitle("Title");
		map.setXAxisLabel("X");
		map.setYAxisLabel("Y");

		// Output the chart to a file.
		try {
			map.saveToFile(new File("./res/graph/hists/twod/" + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DataProvider md = new DataProvider("./res/dataset/twod/test.txt", DataTypeEnum.NORMAL_POINT);
		PlottingPoint pp = new PlottingPoint(md.points, 50);
		f.add(pp);
		f.setSize(PlottingPoint.scale_x, PlottingPoint.scale_y);
		f.setLocation(20, 20);
		f.pack();
		f.setVisible(true);
	}
}