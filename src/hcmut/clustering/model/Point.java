package hcmut.clustering.model;
import weka.core.Instance;

public class Point extends Instance {

	private static final long serialVersionUID = 1L;

    private boolean isNoise;
	private boolean isVisited;
	private boolean isClustered;

    private double reachDist = 0;
	
	public Point(Instance instance) {
		super(instance);
		setVisited(false);
		setNoise(false);
		setClustered(false);
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public boolean isNoise() {
		return isNoise;
	}

	public void setNoise(boolean isNoise) {
		this.isNoise = isNoise;
	}

	public boolean isClustered() {
		return isClustered;
	}

    public double getReachDist() {
        return reachDist;
    }

    public void setReachDist(double reachDist) {
        this.reachDist = reachDist;
    }

	public void setClustered(boolean isClustered) {
		this.isClustered = isClustered;
	}

	public double getAttribute(int attIndex) {
		return this.value(attIndex);
	}
	
	public void setAttribute(int attIndex, double value) {
		this.setValue(attIndex, value);
	}
	
	public double distance(Point anotherPoint) {
		double sum = 0;
		for (int i = 0; i < this.numAttributes(); i++)
			sum += Math.pow(this.getAttribute(i) - anotherPoint.getAttribute(i), 2);
		return Math.sqrt(sum);
	}

    public Points regionQuery(double eps, Points points) {
        Points neighbors = new Points();
        for (int i = 0; i < points.size(); i++) {
            Point point = points.getPoint(i);
            if (this.distance(point) <= eps) {
                neighbors.add(point);
            }
        }
        return neighbors;
    }
}