package hcmut.clustering.controller;

import hcmut.clustering.model.Cluster;
import hcmut.clustering.model.Clusters;
import hcmut.clustering.model.Point;
import hcmut.clustering.model.Points;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class GraphController {
    private Color[] colorArray;
    private GraphicsContext gc;
    private int size;
    private int maxValue;

    public GraphController(GraphicsContext gc) {
        this.gc = gc;
        colorArray = new Color[] {Color.RED, Color.DARKORANGE, Color.LIGHTYELLOW, Color.DARKGREEN,
                                  Color.DEEPSKYBLUE, Color.DARKVIOLET, Color.AQUA, Color.CHOCOLATE, Color.BROWN};
    }

    public GraphController(GraphicsContext gc, int size, int maxValue) {
        this(gc);
        this.size = size;
        this.maxValue = maxValue;
    }

    public void setMaxValue(int max) {
        this.maxValue = max;
    }

    public void drawTest() {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(15, 15, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }

    private void drawPoint(int x, int y) {
        gc.fillOval(x, y, 5, 5);
    }

    public void draw(Point p) {
        gc.setFill(Color.BLACK);
        int x = ((int) p.getAttribute(0) * size / maxValue);
        int y = size - ((int) p.getAttribute(1) * size / maxValue);
        this.drawPoint(x, y);
    }

    public void draw(Points points, Color color) {
        gc.setFill(color);
        int x, y;
        for (Point p : points) {
            x = (int) p.getAttribute(0) * size / maxValue;
            y = size - (int) p.getAttribute(1) * size / maxValue;
            this.drawPoint(x, y);
        }
    }

    public void draw(Cluster cluster, Color color) {
        Points p = cluster.getPoints();
        this.draw(p, color);
    }

    public void drawCluster(Clusters clusters) {
        int i = 0;
        Color c;
        for (Cluster cluster : clusters) {
            c = colorArray[i];
            this.draw(cluster, c);
            i++;
            if (i == colorArray.length)
                i =0;
        }
    }
}
