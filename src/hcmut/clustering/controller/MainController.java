package hcmut.clustering.controller;

import hcmut.clustering.engine.DBSCAN;
import hcmut.clustering.engine.OPTICS;
import hcmut.clustering.model.Cluster;
import hcmut.clustering.model.Points;
import hcmut.clustering.model.Point;
import hcmut.clustering.utility.ReadData;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class MainController {

    private static int DBSCAN_INDEX = 0;
    private static int OPTICS_INDEX = 1;

    private Points points;

    private Stage primaryStage;

    private GraphController graphController;

    private DBSCAN dbscan;

    private OPTICS optics;

    @FXML
    private TextField inputFilePath;
    @FXML
    private ChoiceBox chbAlgorithm;
    @FXML
    private TextField inputEps;
    @FXML
    private TextField inputMinPts;
    @FXML
    private TextField inputPrefEps;
    @FXML
    private TextField numPoints;
    @FXML
    private Button btnConfirmDataSet;
    @FXML
    private Button btnConfirmAlgorithm;
    @FXML
    private Button btnConfirmParameters;
    @FXML
    private Button btnConstructClusters;
    @FXML
    private Button btnConfirmOPTICSControl;
    @FXML
    private LineChart lineChart;
    @FXML
    private TextField outputEvaluation;

    public MainController() {
        dbscan = new DBSCAN();
        optics = new OPTICS();
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setGraphController(GraphController graphController) {
        this.graphController = graphController;
    }

    /**
     * Click event handler for Open file button
     */
    public void btnOpenFileClicked() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose input file for clustering");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Weka files (*.arff)", "*.arff"));

            File file = fileChooser.showOpenDialog(this.primaryStage);

            if (file != null) {
                this.inputFilePath.setText(file.getPath());
                this.points = ReadData.readData(file.getPath());

                this.graphController.setMaxValue(this.points.getMaxCoordinateValue());

                this.numPoints.setText(Integer.toString(this.points.size()));
                this.btnConfirmDataSet.setDisable(false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click event handler for Confirm Algorithm button
     */
    public void btnConfirmAlgorithmClicked() {
        this.btnConfirmAlgorithm.setDisable(true);

        if (chbAlgorithm.getSelectionModel().getSelectedIndex() == OPTICS_INDEX) {

        }
    }

    /**
     * Click event handler for Confirm Data Set button
     */
    public void btnConfirmDataSetClicked() {
        this.btnConfirmDataSet.setDisable(true);
        this.graphController.draw(this.points, null);
    }

    /**
     * Click event handler for Confirm Parameters button
     */
    public void btnConfirmParametersClicked() {
        this.btnConstructClusters.setDisable(false);
        this.btnConfirmParameters.setDisable(true);
    }

    /**
     * Click event handler for Construct Clusters button
     */
    public void btnConstructClustersClicked() {
        try {
            constructCluster(ReadData.readData(inputFilePath.getText()), Double.parseDouble(inputEps.getText()),
                    Integer.parseInt(inputMinPts.getText()), chbAlgorithm.getSelectionModel().getSelectedIndex());
            this.btnConstructClusters.setDisable(true);
            this.btnConfirmOPTICSControl.setDisable(false);

            //TODO: write evaluation function
            outputEvaluation.setText("");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click event handler for Start Over button
     */
    public void btnStartOverClicked() {
        //Clear all fields
        this.inputFilePath.setText(null);
        this.inputMinPts.setText(null);
        this.inputEps.setText(null);
        this.inputPrefEps.setText(null);
        this.numPoints.setText(null);
        this.chbAlgorithm.getSelectionModel().clearSelection();
        this.outputEvaluation.setText(null);

        //Reset buttons
        this.btnConfirmDataSet.setDisable(false);
        this.btnConfirmAlgorithm.setDisable(false);
        this.btnConfirmParameters.setDisable(false);
        this.btnConfirmOPTICSControl.setDisable(true);
        this.btnConstructClusters.setDisable(true);

        //Clear drawing section
        this.graphController.clearScreen();
    }

    public void btnConfirmOPTICSControlClicked() {
//        this.graphController.draw(optics.getClusters(Double.parseDouble(inputPrefEps.getText())));
        System.out.println("Prefer eps: " + Double.parseDouble(inputPrefEps.getText()));
        for (Cluster cluster: optics.getClusters(Double.parseDouble(inputPrefEps.getText()))) {
            for (Point point: cluster.getPoints()) {
                System.out.print("(" + point.getAttribute(0) + "," + point.getAttribute(1) + ")");
            }
            System.out.println();
        }
    }

    /**
     * Construct clusters method
     * @param points
     * @param eps
     * @param minPts
     * @param algorithm
     */
    public void constructCluster(Points points, double eps, int minPts, int algorithm) {
        if (algorithm == DBSCAN_INDEX) {
            dbscan.setArguments(points, eps, minPts);
            dbscan.constructCluster();
            this.graphController.draw(dbscan.getClusters());
        }
        else if (algorithm == OPTICS_INDEX) {
            optics.setArguments(points, eps, minPts);
            optics.constructClusters();

            //TODO: draw ordered list computed by OPTICS engine to chart
            optics.getOrderedList();

            //Debug
            for (Point point: optics.getOrderedList()) {
                System.out.println("(" + point.getAttribute(0) + "," + point.getAttribute(1) + "):" + point.getReachDist());
            }
        }
    }
}
