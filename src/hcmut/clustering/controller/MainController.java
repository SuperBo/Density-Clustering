package hcmut.clustering.controller;

import hcmut.clustering.engine.DBSCAN;
import hcmut.clustering.engine.OPTICS;
import hcmut.clustering.model.Points;
import hcmut.clustering.utility.ReadData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    private static int DBSCAN_INDEX = 0;
    private static int OPTICS_INDEX = 1;
    private Points points;

    private Stage primaryStage;
    private GraphController graphCtrl;

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

    public MainController() {

    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setGraphCtrl(GraphController graphController) {
        this.graphCtrl = graphController;
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
                this.numPoints.setText(Integer.toString(this.points.size()));
                btnConfirmDataSet.setDisable(false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click event handler for Confirm Data Set button
     */
    public void btnConfirmDataSetClicked() {
        //TODO: Draw all points of data set
        btnConfirmAlgorithm.setDisable(false);
        btnConfirmDataSet.setDisable(true);
    }

    /**
     * Click event handler for Confirm Algorithm button
     */
    public void btnConfirmAlgorithmClicked() {
        btnConfirmParameters.setDisable(false);
        btnConfirmAlgorithm.setDisable(true);
    }

    /**
     * Click event handler for Confirm Parameters button
     */
    public void btnConfirmParametersClicked() {
        btnConstructClusters.setDisable(false);
        btnConfirmParameters.setDisable(true);
    }

    /**
     * Click event handler for Construct Clusters button
     */
    public void btnConstructClustersClicked() {
        try {
            constructCluster(ReadData.readData(inputFilePath.getText()), Double.parseDouble(inputEps.getText()),
                    Integer.parseInt(inputMinPts.getText()), chbAlgorithm.getSelectionModel().getSelectedIndex());
            btnConstructClusters.setDisable(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click event handler for Start Over button
     */
    public void btnStartOverClicked() {
        inputFilePath.setText("");
        inputMinPts.setText("");
        inputEps.setText("");
        chbAlgorithm.getSelectionModel().clearSelection();
        btnConfirmDataSet.setDisable(false);
        btnConfirmAlgorithm.setDisable(true);
        btnConfirmParameters.setDisable(true);
        btnConstructClusters.setDisable(true);
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
            dbscan.getClusters();
            //TODO Draw Clusters
            this.graphCtrl.drawTest();
        }
        else if (algorithm == OPTICS_INDEX) {
            OPTICS optics = new OPTICS(points, eps, minPts);
            optics.constructClusters();
        }
    }
}
