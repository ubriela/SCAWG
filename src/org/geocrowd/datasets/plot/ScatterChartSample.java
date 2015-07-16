package org.geocrowd.datasets.plot;

import org.geocrowd.DatasetEnum;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.datasets.dtype.DataTypeEnum;
import org.geocrowd.datasets.dtype.Point;
import org.geocrowd.datasets.synthetic.DataProvider;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ScatterChartSample extends Application {

	public static int time = 10;
	public static DatasetEnum dataset = DatasetEnum.SKEWED;
	public static boolean plotTask = true;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Scatter Chart Sample");
		final NumberAxis xAxis = new NumberAxis(-50, 150, 5);
		final NumberAxis yAxis = new NumberAxis(-50, 150, 5);
		
//		32.1713906, -124.3041035, 41.998434033, -114.0043464333
//		final NumberAxis xAxis = new NumberAxis(32.1713906, 41.998434033, 1);
//		final NumberAxis yAxis = new NumberAxis(-124.3041035, -114.0043464333, 1);
		final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(
				xAxis, yAxis);
		xAxis.setLabel("X)");
		yAxis.setLabel("Y");
		sc.setTitle("Worker/Task distribution");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Worker");

		DataProvider md = new DataProvider(Utils.datasetToWorkerPointPath()
				+ time + ".txt", DataTypeEnum.NORMAL_POINT);
		for (Point p : md.points) {
			series1.getData().add(new XYChart.Data(p.getX(), p.getY()));
		}

		if (plotTask) {
			XYChart.Series series2 = new XYChart.Series();
			series2.setName("Task");
			DataProvider md2 = new DataProvider(Utils.datasetToTaskPointPath()
					+ time + ".txt", DataTypeEnum.NORMAL_POINT);
			for (Point p : md2.points) {
				series2.getData().add(new XYChart.Data(p.getX(), p.getY()));
			}
			sc.getData().addAll(series1, series2);
		} else {
			sc.getData().addAll(series1);
		}

		Scene scene = new Scene(sc, 500, 500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
