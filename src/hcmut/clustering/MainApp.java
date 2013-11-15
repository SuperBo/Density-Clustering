package hcmut.clustering;

import hcmut.clustering.controller.GraphController;
import hcmut.clustering.engine.DBSCAN;
import hcmut.clustering.model.Points;
import hcmut.clustering.utility.ReadData;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private static int DBSCAN_INDEX = 0;
    private static int OPTICS_INDEX = 1;

    private BorderPane rootLayout;
    private GraphicsContext gc;
    private Stage primaryStage;

    private Points points;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/RootLayout.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();
            this.rootLayout = rootLayout;
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("Clustering Graph Info");
            primaryStage.setScene(scene);

            showGraphicsContext();

            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showGraphicsContext() {
        Canvas canvas = new Canvas(500,500);
        gc = canvas.getGraphicsContext2D();
        GraphController graphCtrl = new GraphController(gc, 500, 10000);
        graphCtrl.draw();
        rootLayout.setCenter(canvas);
    }

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

    public void btnConfirmDataSetClicked() {
        //TODO: Draw all points of data set
        btnConfirmAlgorithm.setDisable(false);
        btnConfirmDataSet.setDisable(true);
    }

    public void btnConfirmAlgorithmClicked() {
        btnConfirmParameters.setDisable(false);
        btnConfirmAlgorithm.setDisable(true);
    }

    public void btnConfirmParametersClicked() {
        btnConstructClusters.setDisable(false);
        btnConfirmParameters.setDisable(true);
    }

    public void btnConstructClustersClicked() {
        try {
            constructCluster(ReadData.readData(inputFilePath.getText()), Double.parseDouble(inputEps.getText()),
                    Integer.parseInt(inputMinPts.getText()), chbAlgorithm.getSelectionModel().getSelectedIndex());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void constructCluster(Points points, double eps, int minPts, int algorithm) {
        if (algorithm == DBSCAN_INDEX) {
            DBSCAN dbscan = new DBSCAN(points, eps, minPts);
            dbscan.constructCluster();

            //TODO Draw Clusters
        }
    }
}
