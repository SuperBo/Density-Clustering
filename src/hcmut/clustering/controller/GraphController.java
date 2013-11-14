package hcmut.clustering.controller;

/**
 * Created with IntelliJ IDEA.
 * User: Super
 * Date: 11/12/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */

import hcmut.clustering.model.Points;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;
import hcmut.clustering.model.Point;
import hcmut.clustering.model.Points;

public class GraphController {
    private GraphicsContext gc;
    private int size;
    private int maxValue;

    public GraphController(GraphicsContext gc) {
        this.gc = gc;
    }

    public GraphController(GraphicsContext gc, int size, int maxValue) {
        this(gc);
        this.size = size;
        this.maxValue = maxValue;
    }

    public void draw() {
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
        gc.fillOval(x, y, 1, 1);
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
            y = size - (int) p.getAttribute(1) * size /maxValue;
            this.drawPoint(x, y);
        }
    }
}
