package hcmut.clustering.model;

import java.util.ArrayList;

public class Clusters {
	private ArrayList<Cluster> clusterList;
	
	public Clusters() {
		clusterList = new ArrayList<Cluster>();
	}
	
	public int getNumberOfCluster() {
		return clusterList.size();
	}
	
	public Cluster getClusterAtIndex(int index) {
		return clusterList.get(index);
	}
	
	public void addCluster(Cluster cluster) {
		clusterList.add(cluster);
	}
}
