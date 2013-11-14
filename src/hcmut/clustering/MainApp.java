package hcmut.clustering;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import hcmut.clustering.model.Point;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    private BorderPane rootLayout;
    private GraphicsContext gc;
    private Stage primaryStage;
    private File inputFile = null;

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

    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose input file for clustering...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Weka files", "*.arff"));
        this.inputFile = fileChooser.showOpenDialog(this.primaryStage);
    }
}
