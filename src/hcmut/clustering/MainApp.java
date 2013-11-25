package hcmut.clustering;

import hcmut.clustering.controller.ChartController;
import hcmut.clustering.controller.GraphController;
import hcmut.clustering.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private BorderPane rootLayout;
    private MainController mainCtrl;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMain();
        showGraphics();
    }

    private void showMain() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/RootLayout.fxml"));
            this.rootLayout = (BorderPane) loader.load();

            this.mainCtrl = loader.getController();
            this.mainCtrl.setStage(this.primaryStage);

            this.primaryStage.setTitle("Clustering App");
            this.primaryStage.setScene(new Scene(this.rootLayout));
            this.primaryStage.setResizable(false);
            this.primaryStage.show();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showGraphics() {
        Canvas canvas = new Canvas(550, 550);
        GraphController gc = new GraphController(canvas.getGraphicsContext2D(), 550, 100);
        mainCtrl.setGraphController(gc);

        ((BorderPane) this.rootLayout.getCenter()).setCenter(canvas);
        ChartController chartCtrl = new ChartController();
        mainCtrl.setChartController(chartCtrl);
    }

}
