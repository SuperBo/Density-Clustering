package hcmut.clustering.model;
import java.util.ArrayList;
import java.util.Iterator;

import weka.core.Instances;


public class Points implements Iterable<Point>{
	
	private ArrayList<Point> pointList;

	public Points() {
		this.pointList = new ArrayList<Point>();
	}
	
	public Points(Instances data) {
		this.pointList = new ArrayList<Point>();
		
		for (int i = 0; i < data.numInstances(); i++)
			this.pointList.add(new Point(data.instance(i)));
	}
	
	public Point getPointAtIndex(int index) {
		return this.pointList.get(index);
	}
	
	public ArrayList<Point> getPointList() {
		return this.pointList;
	}
	
	public void add(Point point) {
		this.pointList.add(point);
	}
	
	public void addAll(Points points) {
		for (int i = 0; i < points.getNumberOfPoints(); i++) {
			Point temp = points.getPointAtIndex(i);
			if (!this.pointList.contains(temp))
				this.pointList.add(temp);
		}
	}
	
	public int getNumberOfPoints() {
		return this.pointList.size();
	}
	
	public void clear() {
		this.pointList.clear();
	}
	
	public static Points regionQuerry(Point centroid, double eps, Points data) {
		Points region = new Points();
		for (int i = 0; i < data.getNumberOfPoints(); i++) {
			Point point = data.getPointAtIndex(i);
			if (centroid.distanceToAnotherPoint(point) <= eps)
				region.add(point);
		}
		return region;
	}

    @Override
    public Iterator<Point> iterator() {
        return pointList.iterator();
    }
}
