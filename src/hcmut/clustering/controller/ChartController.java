package hcmut.clustering.controller;

import hcmut.clustering.model.Point;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class ChartController {
    @FXML
    LineChart<Integer, Double> lineChart;

    private int maxXValue = 0;

    public void ChartController(LineChart<Integer, Double> lineChart) {
        this.lineChart = lineChart;
    }

    public void setLineChart(LineChart<Integer, Double> lineChart) {
        this.lineChart = lineChart;
    }

    public void draw(ArrayList<Point> listPoint) {
        maxXValue = listPoint.size() - 1;
        lineChart.setTitle("Chart");
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        for (int i = 0; i < listPoint.size(); i++) {
            double reachD = listPoint.get(i).getReachDist();
            series.getData().add(new XYChart.Data<Integer, Double>(i, reachD));
        }
        lineChart.getData().clear();
        lineChart.getData().add(series);
        //lineChart.setCreateSymbols(false);
    }

    public void drawPrefLine(double pref) {
        XYChart.Series<Integer, Double> series = new XYChart.Series<Integer, Double>();
        series.getData().add(new XYChart.Data<Integer, Double>(0, pref));
        series.getData().add(new XYChart.Data<Integer, Double>(maxXValue, pref));
        if (lineChart.getData().size() > 1) {
            lineChart.getData().set(1, series);
        }
        else
            lineChart.getData().add(series);
    }
}
