package org.geocrowd.datasets.synthetic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.geocrowd.common.utils.Stats;
import org.geocrowd.datasets.dtype.DataTypeEnum;
import org.geocrowd.datasets.dtype.GenericPoint;
import org.geocrowd.datasets.dtype.GenericWeightedPoint;
import org.geocrowd.datasets.dtype.Point;
import org.geocrowd.datasets.dtype.ValueFreq;
import org.geocrowd.datasets.dtype.WeightedPoint;
import org.geocrowd.datasets.synthetic.PointFileReader;
import org.geocrowd.datasets.synthetic.ValueFileReader;


/**
 * This class provide a list of points from a file
 * 
 * @author HT186010
 * 
 */
public class DataProvider {

	// two-dimension
	public double max_x, max_y, min_x, min_y;
	public List<Point> points;
	public List<Double> xCoords;
	public List<Double> yCoords;
	public List<WeightedPoint> weightedPoints;

	// one-dimension
	public List<Double> values;		//	this can be distinct values or all values with overlapped data
	public List<ValueFreq<Double>> valueFreqs;
	public double min, max;

	public DataProvider(String filePath, DataTypeEnum datatype) {
		switch (datatype) {
		case VALUE_FREQ:	//	value - one dimension
			ValueFileReader valueFreqFileReader = new ValueFileReader(filePath);
			valueFreqs = valueFreqFileReader.parse2();
			Stats stat = new Stats();
			values = stat.getValues(valueFreqs);
			min = valueFreqFileReader.min;
			max = valueFreqFileReader.max;
			break;
		case VALUE_LIST:	//	get list values
			ValueFileReader valueFileReader = new ValueFileReader(filePath);
			values = valueFileReader.parse();
			min = valueFileReader.min;
			max = valueFileReader.max;
			break;
		case NORMAL_POINT:	//	standard point
			PointFileReader pointFileReader = new PointFileReader(filePath);
			points = pointFileReader.parse();
			max_x = pointFileReader.getMax_x();
			max_y = pointFileReader.getMax_y();
			min_x = pointFileReader.getMin_x();
			min_y = pointFileReader.getMin_y();
			break;
		case WEIGHT_POINT:	//	weighted point
			PointFileReader weightedPointFileReader = new PointFileReader(filePath);
			points = weightedPointFileReader.parse();
			weightedPoints = weightedPointFileReader.parseWeightPoints();
			max_x = weightedPointFileReader.getMax_x();
			max_y = weightedPointFileReader.getMax_y();
			min_x = weightedPointFileReader.getMin_x();
			min_y = weightedPointFileReader.getMin_y();
			break;
		}
	}

	/**
	 * convert a list of Point to a list of generic points
	 * 
	 * @return
	 */
	public LinkedList<GenericPoint> getGenericPoints() {
		LinkedList<GenericPoint> list = new LinkedList<GenericPoint>();
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			Point point = (Point) it.next();
			GenericPoint pt = new GenericPoint<Double, Double>(point.getX(),
					point.getY());
			list.add(pt);
		}

		return list;
	}
	
	/**
	 * Convert a list of weighted points to a list of generic weighted points
	 * @return
	 */
	public LinkedList<GenericWeightedPoint> getGenericWeightedPoints() {
		LinkedList<GenericWeightedPoint> list = new LinkedList<GenericWeightedPoint>();
		Iterator<WeightedPoint> it = weightedPoints.iterator();
		while (it.hasNext()) {
			WeightedPoint point = (WeightedPoint) it.next();
			GenericWeightedPoint pt = new GenericWeightedPoint<Double, Double>(point.getX(),
					point.getY(), point.getWeight());
			list.add(pt);
		}

		return list;
	}
	
	/**
	 * Get java standard points
	 * @return
	 */
	public LinkedList<java.awt.Point> getStandardPoints() {
		LinkedList<java.awt.Point> standardPoints = new LinkedList<java.awt.Point> ();
		Iterator it = points.iterator();
		while (it.hasNext()) {
			Point p = (Point) it.next();
			java.awt.Point sp = new java.awt.Point();
			sp.setLocation(Math.round(p.getX()), Math.round(p.getY()));
			standardPoints.add(new java.awt.Point());
		}
		return standardPoints;
	}
	
	/**
	 * Get X coords
	 * @return
	 */
	public void getXY() {
		xCoords = new LinkedList<Double>();
		yCoords = new LinkedList<Double>();
		Iterator it = points.iterator();
		while (it.hasNext()) {
			Point point = (Point) it.next();
			xCoords.add(point.getX());
			yCoords.add(point.getY());
		}
	}


	public void printStat() {
		System.out.println("Number of points: " + points.size());
		System.out.println("Min_x: " + min_x);
		System.out.println("Max_x: " + max_x);
		System.out.println("Min_y: " + min_y);
		System.out.println("Max_y: " + max_y);
	}

}
