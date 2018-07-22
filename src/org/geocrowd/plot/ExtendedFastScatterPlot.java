package org.geocrowd.plot;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.ui.RectangleEdge;

public class ExtendedFastScatterPlot extends FastScatterPlot {

	/**
    *
    */
	private static final long serialVersionUID = 1L;

	int[] sizes;
	Paint[] colors;
	int[] shapes;

	public ExtendedFastScatterPlot(float[][] data, NumberAxis domainAxis,
			NumberAxis rangeAxis, int[] sizes, Paint[] colors, int[] shapes) {
		super(data, domainAxis, rangeAxis);
		this.sizes = sizes;
		this.colors = colors;
		this.shapes = shapes;
	}

	@Override
	public void render(Graphics2D g2, Rectangle2D dataArea,
			PlotRenderingInfo info, CrosshairState crosshairState) {

		if (this.getData() != null) {
			for (int i = 0; i < this.getData()[0].length; i++) {
				float x = this.getData()[0][i];
				float y = this.getData()[1][i];
				int size = this.sizes[i];
				int transX = (int) this.getDomainAxis().valueToJava2D(x,
						dataArea, RectangleEdge.BOTTOM);
				int transY = (int) this.getRangeAxis().valueToJava2D(y,
						dataArea, RectangleEdge.LEFT);
				g2.setPaint(this.colors[i]);
				if (1 == this.shapes[i]) {
					g2.fillRect(transX, transY, size, size);
				} else {
					g2.fillOval(transX, transY, size, size);
				}
			}
		}
	}
}
