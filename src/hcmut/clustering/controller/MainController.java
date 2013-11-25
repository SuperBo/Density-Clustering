package hcmut.clustering.controller;

import hcmut.clustering.engine.DBSCAN;
import hcmut.clustering.engine.OPTICS;
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

    @FXML
    private TextField inputFilePath;
    @FXML
    private ChoiceBox chbAlgorithm;
    @FXML
    private TextField inputEps;
    @FXML
    private TextField inputMinPts;
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
    private LineChart lineChart;

    public MainController() {
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
        this.numPoints.setText(null);
        this.chbAlgorithm.getSelectionModel().clearSelection();

        //Reset buttons
        this.btnConfirmDataSet.setDisable(false);
        this.btnConfirmAlgorithm.setDisable(false);
        this.btnConfirmParameters.setDisable(false);
        this.btnConstructClusters.setDisable(true);

        //Clear drawing section
        this.graphController.clearScreen();
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
            DBSCAN dbscan = new DBSCAN(points, eps, minPts);
            dbscan.constructCluster();
            this.graphController.draw(dbscan.getClusters());
        }
        else if (algorithm == OPTICS_INDEX) {
            OPTICS optics = new OPTICS(points, eps, minPts);
            optics.constructClusters();
            ArrayList<Point> orderedList = optics.getOrderedList();
            for (Point point: orderedList) {
                System.out.println("(" + point.getAttribute(0) + "," + point.getAttribute(1) + "):" + point.getReachDist());
            }
        }
    }
}
