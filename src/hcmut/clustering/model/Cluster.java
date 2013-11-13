package hcmut.clustering.model;

public class Cluster {
	private Point centroid;
	private Points elementList;
	
	public Cluster(Point centroid, Points elementList) {
		this.centroid = centroid;
		this.elementList = new Points();
		
		for (int i = 0; i < elementList.getNumberOfPoints(); i++) {
			Point temp = elementList.getPointAtIndex(i);
			this.elementList.add(temp);
			temp.setClustered(true);
		}
	}
	
	public Object getCentroid() {
		return this.centroid;
	}
	
	public int getNumberOfElements() {
		return this.elementList.getNumberOfPoints();
	}
	
	public void add(Point point) {
		this.elementList.add(point);
		point.setClustered(true);
	}
	
	public static void expandCluster(Point point, Points neighborPts, Cluster cluster, double eps, int minPts, Points data) {
		
		for (int i = 0; i < neighborPts.getNumberOfPoints(); i++) {
			Point tempPoint = neighborPts.getPointAtIndex(i);
			
			if (!tempPoint.isVisited()) {
				tempPoint.setVisited(true);
				
				Points tempNeighborPts = Points.regionQuerry(tempPoint, eps, data);
				
				if (tempNeighborPts.getNumberOfPoints() >= minPts)
					neighborPts.addAll(tempNeighborPts);
			}
			
			if (!tempPoint.isClustered())
				cluster.add(tempPoint);
		}
	}
}
