package hcmut.clustering.model;
import java.util.ArrayList;
import java.util.Iterator;

import weka.core.Instances;

public class Points implements Iterable<Point> {

    /** Points list **/
	private ArrayList<Point> points;

    /** Default Constructor **/
	public Points() {
		this.points = new ArrayList<Point>();
	}

    /**
     * Constructor with data
     * @param data
     */
	public Points(Instances data) {
		this.points = new ArrayList<Point>();
		
		for (int i = 0; i < data.numInstances(); i++)
			this.points.add(new Point(data.instance(i)));
	}

    /**
     * Get point at index
     * @param index
     * @return point
     */
	public Point getPoint(int index) {
		return this.points.get(index);
	}

    /**
     * Get all points
     * @return points
     */
	public ArrayList<Point> getAllPoints() {
		return this.points;
	}

    /**
     * Get number of points belong to this collection
     * @return number of points
     */
    public int size() {
        return this.points.size();
    }

    /**
     * Add point to this collection
     * @param point
     */
	public void add(Point point) {
		this.points.add(point);
	}

    /**
     * Add another collection of points to this collection
     * @param points
     */
	public void addAll(Points points) {
		for (int i = 0; i < points.size(); i++) {
			Point temp = points.getPoint(i);
			if (!this.points.contains(temp))
				this.points.add(temp);
		}
	}

    /**
     * Clear this collection
     */
	public void clear() {
		this.points.clear();
	}

    @Override
    public Iterator<Point> iterator() {
        return points.iterator();
    }
}
