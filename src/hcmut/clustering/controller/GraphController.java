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

    private double maxValue;

    public GraphController(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        colorArray = new Color[] {Color.RED, Color.DARKORANGE, Color.YELLOW, Color.DARKGREEN, Color.FIREBRICK,
                                  Color.DEEPSKYBLUE, Color.DARKVIOLET, Color.AQUA, Color.CHOCOLATE, Color.BROWN,
                                  Color.ALICEBLUE, Color.ORANGERED, Color.AZURE, Color.BURLYWOOD, Color.DARKSALMON,
                                  Color.LIGHTCYAN, Color.LIGHTCORAL, Color.HOTPINK, Color.MAGENTA};
    }

    public GraphController(GraphicsContext graphicsContext, int size, int maxValue) {
        this(graphicsContext);
        this.size = size;
        this.maxValue = maxValue;
    }

    public void setMaxValue(double max) {
        if (max > 0)
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
        double x = 5 + ((double) point.getAttribute(0) * (size - 10) / maxValue);
        double y = size - 5 - ((double) point.getAttribute(1) * (size - 10) / maxValue);
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
            x = 5 +  point.getAttribute(0) * (size - 10) / maxValue;
            y = size - 5 -  point.getAttribute(1) * (size - 10) / maxValue;
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
        graphicsContext.clearRect(0, 0, size, size);
    }
}
