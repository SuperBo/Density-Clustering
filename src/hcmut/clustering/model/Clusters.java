package hcmut.clustering.model;

import java.util.ArrayList;

public class Clusters {

    /* All clusters */
	private ArrayList<Cluster> clusters;

    /**
     * Clusters Constructor
     */
	public Clusters() {
        clusters = new ArrayList<Cluster>();
	}

    /**
     * Get number of clusters
     * @return number of clusters
     */
	public int size() {
		return clusters.size();
	}

    /**
     * Get cluster by index
     * @param index
     * @return cluster at index
     */
	public Cluster getCluster(int index) {
		return clusters.get(index);
	}

    /**
     * Add cluster
     * @param cluster
     */
	public void addCluster(Cluster cluster) {
        clusters.add(cluster);
	}
}
