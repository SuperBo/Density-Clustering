package hcmut.clustering.engine;

import hcmut.clustering.model.Clusters;
import hcmut.clustering.model.Cluster;
import hcmut.clustering.model.Points;
import hcmut.clustering.model.Point;

public class DBSCAN {

    /**
     * Private properties declaration
     */
    private double eps;
    private int minPts;
    private Points points;
    private Clusters clusters;

    /**
     * Default constructor
     */
    public DBSCAN() {
        clusters = new Clusters();
    }

    /**
     * Constructor with parameters
     * @param points
     * @param eps
     * @param minPts
     */
    public DBSCAN(Points points, double eps, int minPts) {
        this();
        this.eps = eps;
        this.minPts = minPts;
        this.points = points;
    }

    /**
     * Set arguments of DBSCAN engine
     * @param points
     * @param eps
     * @param minPts
     */
    public void setArguments(Points points, double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
        this.points = points;
    }

    /**
     * Get clusters extracted by this algorithm
     * @return
     */
    public Clusters getClusters() {
        return clusters;
    }

    /**
     * Construct clusters by this algorithm
     */
    public void constructCluster() {
        for (int i = 0; i < points.size(); i++) {

            Point point = points.getPoint(i);

            if (!point.isVisited()) {
                point.setVisited(true);

                Points neighbors = point.regionQuery(eps, points);

                if (neighbors.size() < minPts) {
                    point.setNoise(true);
                }
                else {
                    Cluster cluster = new Cluster(neighbors);
                    clusters.addCluster(cluster);
                    cluster.expandCluster(eps, minPts, points);
                }
            }
        }
    }

}
