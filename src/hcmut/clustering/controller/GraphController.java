package hcmut.clustering.controller;

import hcmut.clustering.model.Cluster;
import hcmut.clustering.model.Clusters;
import hcmut.clustering.model.Point;
import hcmut.clustering.model.Points;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphController {
    private Color[] colorArray;
    private GraphicsContext graphicsContext;
    private int size;

    //TODO: cai nay co can thiet phai la int ko? t cho double dc hem?
    private int maxValue;

    public GraphController(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        colorArray = new Color[] {Color.RED, Color.DARKORANGE, Color.LIGHTYELLOW, Color.DARKGREEN,
                                  Color.DEEPSKYBLUE, Color.DARKVIOLET, Color.AQUA, Color.CHOCOLATE, Color.BROWN};
    }

    public GraphController(GraphicsContext graphicsContext, int size, int maxValue) {
        this(graphicsContext);
        this.size = size;
        this.maxValue = maxValue;
    }

    public void setMaxValue(int max) {
        this.maxValue = max;
    }

    /**
     * Draw single point by coordinate
     * @param x
     * @param y
     */
    private void drawPoint(double x, double y) {
        graphicsContext.fillOval(x, y, 5, 5);
    }

    /**
     * Draw object Point by converting its attributes to coordinate
     * @param point
     * @param color
     */
    public void draw(Point point, Color color) {
        if (color == null)
            graphicsContext.setFill(Color.BLACK);
        else
            graphicsContext.setFill(color);
        double x = ((double) point.getAttribute(0) * size / maxValue);
        double y = size - ((double) point.getAttribute(1) * size / maxValue);
        this.drawPoint(x, y);
    }

    /**
     * Draw list of Points
     * @param points
     * @param color
     */
    public void draw(Points points, Color color) {
        if (color == null)
            graphicsContext.setFill(Color.BLACK);
        else
            graphicsContext.setFill(color);
        double x, y;
        for (Point point : points) {
            x = point.getAttribute(0) * size / maxValue;
            y = size -  point.getAttribute(1) * size / maxValue;
            this.drawPoint(x, y);
        }
    }

    /**
     * Draw a single Cluster
     * @param cluster
     * @param color
     */
    public void draw(Cluster cluster, Color color) {
        Points points = cluster.getPoints();
        this.draw(points, color);
    }

    /**
     * Draw list of Cluster
     * @param clusters
     */
    public void draw(Clusters clusters) {
        int index = 0;
        Color color;
        for (Cluster cluster : clusters) {
            color = colorArray[index];
            this.draw(cluster, color);
            index++;
            if (index == colorArray.length)
                index = 0;
        }
    }

    /**
     * Clear drawing section
     */
    public void clearScreen() {
    // TODO: clear drawing section
    }
}
