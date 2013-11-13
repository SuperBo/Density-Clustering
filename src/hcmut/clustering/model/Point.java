package hcmut.clustering.model;
import weka.core.Instance;

public class Point extends Instance{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isVisited;
	
	private boolean isNoise;
	
	private boolean isClustered;
	
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

	public void setClustered(boolean isClustered) {
		this.isClustered = isClustered;
	}

	public double getAttribute(int attIndex) {
		return this.value(attIndex);
	}
	
	public void setAttribute(int attIndex, double value) {
		this.setValue(attIndex, value);
	}
	
	public double distanceToAnotherPoint(Point anotherPoint) {
		double sum = 0;
		for (int i = 0; i < this.numAttributes(); i++)
			sum += Math.pow(this.getAttribute(i) - anotherPoint.getAttribute(i), 2);
		return Math.sqrt(sum);
	}
}