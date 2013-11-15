package hcmut.clustering.engine;

import hcmut.clustering.model.Clusters;
import hcmut.clustering.model.Point;
import hcmut.clustering.model.Points;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class OPTICS {

    /**
     * Properties declaration
     */
    private Points points;
    private double eps;
    private int minPts;
    private Clusters clusters;
    private ArrayList<Point> orderedList;

    /**
     * Default Constructor
     */
    public OPTICS() {}

    /**
     * Constructor
     * @param points
     * @param eps
     * @param MinPts
     */
    public OPTICS(Points points, double eps, int MinPts) {
        this.points = points;
        this.eps = eps;
        this.minPts = minPts;
        this.clusters = new Clusters();
        orderedList = new ArrayList<Point>();
    }

    /**
     * Construct clusters methods
     */
    public void constructClusters(){
        for (int i = 0; i < points.size(); i++) {
            Point point = points.getPoint(i);

            if (!point.isVisited()) {
                point.setVisited(true);

                Points neighbors = point.regionQuery(eps, points);

                orderedList.add(point);

                PriorityQueue<Point> seeds = new PriorityQueue<Point>(points.size(), new Comparator<Point>() {
                    @Override
                    public int compare(Point o1, Point o2) {
                        if (o1.getReachDist() < o2.getReachDist()) {
                            return -1;
                        }
                        else if (o1.getReachDist() > o2.getReachDist()) {
                            return 1;
                        }
                        return 0;
                    }
                });

                if (neighbors.size() >= minPts) {
                    update(point, neighbors, seeds);
                    while (!seeds.isEmpty()) {
                        Point tmp = seeds.poll();
                        Points tmpNeighbors = tmp.regionQuery(eps, points);
                        tmp.setVisited(true);
                        orderedList.add(tmp);
                        if (tmpNeighbors.size() >= minPts) {
                            update(tmp, tmpNeighbors, seeds);
                        }
                    }
                }
            }
        }
    }

    /**
     * Update method
     * @param core
     * @param neighbors
     * @param seeds
     */
    private void update(Point core, Points neighbors, PriorityQueue<Point> seeds) {
        double coreDist = coreDistance(core, neighbors);

        for (Point point: neighbors) {
            if (!point.isVisited()) {
                double newReachDist = Math.max(coreDist, core.distance(point));
                if (point.getReachDist() == 0) {
                    point.setReachDist(newReachDist);
                    seeds.add(point);
                }
                else if (point.getReachDist() > newReachDist) {
                    point.setReachDist(newReachDist);
                }
            }
        }
    }

    /**
     * Calculating core distance method
     * @param core
     * @param neighbors
     * @return
     */
    private double coreDistance(Point core, Points neighbors) {
        double coreDistance = 0;

        for (Point point: neighbors) {
            if (point != core) {
                double tmp = point.distance(core);
                if (core.regionQuery(tmp, points).size() >= minPts) {
                    coreDistance = tmp;
                    break;
                }
            }
        }

        for (Point point: neighbors) {
            if (point != core) {
                double tmp = point.distance(core);
                if(core.regionQuery(tmp, points).size() >= minPts && tmp < coreDistance) {
                    coreDistance = tmp;
                }
            }
        }

        return coreDistance;
    }
}