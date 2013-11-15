package hcmut.clustering.model;

public class Cluster {

    /* All points belonged to this cluster*/
    private Points points;

    /**
     * Cluster Constructor
     * @param neighbors
     */
	public Cluster(Points neighbors) {
		this.points = new Points();
		
		for (int i = 0; i < neighbors.size(); i++) {
            this.add(neighbors.getPoint(i));
		}
	}

    /**
     * Get points belonged to this cluster
     * @return points
     */
    public Points getPoints() {
        return points;
    }

    /**
     * Get size of this cluster
     * @return size
     */
	public int size() {
		return this.points.size();
	}

    /**
     * Add a point to this cluster
     * @param point
     */
	public void add(Point point) {
		this.points.add(point);
		point.setClustered(true);
	}

    /**
     * Expand cluster
     * @param eps
     * @param minPts
     * @param points
     */
	public void expandCluster(double eps, int minPts, Points points) {
		for (int i = 0; i < this.points.size(); i++) {
			Point point = this.points.getPoint(i);
			
			if (!point.isVisited()) {
                point.setVisited(true);
				Points localNeighbors = point.regionQuery(eps, points);
				if (localNeighbors.size() >= minPts)
                    this.points.addAll(localNeighbors);
			}
			
			if (!point.isClustered())
				this.add(point);
		}
	}
}
